package com.example.data

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import com.example.R
import com.example.model.*
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseManager(private val context: Context) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val credentialManager = CredentialManager.create(context)

    // Dedicated custom Firestore database instance as provisioned
    val firestore: FirebaseFirestore by lazy {
        val dbId = context.getString(R.string.firestore_database_id)
        FirebaseFirestore.getInstance(FirebaseApp.getInstance(), dbId)
    }

    private val _currentUser = MutableStateFlow<FirebaseUser?>(auth.currentUser)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser.asStateFlow()

    private val _isSigningIn = MutableStateFlow(false)
    val isSigningIn: StateFlow<Boolean> = _isSigningIn.asStateFlow()

    private val _signInError = MutableStateFlow<String?>(null)
    val signInError: StateFlow<String?> = _signInError.asStateFlow()

    init {
        auth.addAuthStateListener { firebaseAuth ->
            _currentUser.value = firebaseAuth.currentUser
        }
    }

    suspend fun signInWithGoogle(): Boolean {
        _isSigningIn.value = true
        _signInError.value = null
        try {
            val serverClientId = context.getString(R.string.default_web_client_id)
            val googleIdOption = GetSignInWithGoogleOption.Builder(serverClientId)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val result = credentialManager.getCredential(
                request = request,
                context = context
            )

            val credential = result.credential
            if (credential is CustomCredential &&
                credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
            ) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val authCredential = GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
                val authResult = auth.signInWithCredential(authCredential).await()
                _currentUser.value = authResult.user

                // Initialize user profile in Firestore
                authResult.user?.let { user ->
                    syncUserProfile(user)
                }
                _isSigningIn.value = false
                return true
            } else {
                _signInError.value = "Unexpected credential format returned."
            }
        } catch (e: GetCredentialCancellationException) {
            Log.d("FirebaseManager", "Sign-in cancelled by user")
            _signInError.value = null
        } catch (e: GetCredentialException) {
            Log.e("FirebaseManager", "Credential Manager failure: ${e.message}", e)
            _signInError.value = "Google Sign-In failed: ${e.message}"
        } catch (e: Exception) {
            Log.e("FirebaseManager", "Authentication failed: ${e.message}", e)
            _signInError.value = "Sign in failed: ${e.message}"
        } finally {
            _isSigningIn.value = false
        }
        return false
    }

    fun signOut() {
        auth.signOut()
        _currentUser.value = null
    }

    private suspend fun syncUserProfile(user: FirebaseUser) {
        try {
            val userDoc = firestore.collection("users").document(user.uid)
            val profileData = mapOf(
                "uid" to user.uid,
                "email" to (user.email ?: ""),
                "displayName" to (user.displayName ?: "Enterprise User"),
                "photoUrl" to (user.photoUrl?.toString() ?: ""),
                "lastActive" to System.currentTimeMillis()
            )
            userDoc.set(profileData, SetOptions.merge()).await()
        } catch (e: Exception) {
            Log.e("FirebaseManager", "Error syncing user profile: ${e.message}", e)
        }
    }

    // Sync Audit Logs to Firestore
    suspend fun recordCloudAuditLog(orgId: String, log: AuditLogEntry) {
        val uid = auth.currentUser?.uid ?: return
        try {
            val docRef = firestore.collection("organizations")
                .document(orgId)
                .collection("audit_logs")
                .document(log.id)

            val logData = mapOf(
                "id" to log.id,
                "timestamp" to log.timestamp,
                "actor" to log.actor,
                "role" to log.role,
                "action" to log.action,
                "resource" to log.resource,
                "result" to log.result.name,
                "aiAgentName" to (log.aiAgentName ?: ""),
                "approvalId" to (log.approvalId ?: ""),
                "tamperHash" to log.tamperHash,
                "recordedByUid" to uid
            )
            docRef.set(logData).await()
        } catch (e: Exception) {
            Log.e("FirebaseManager", "Error syncing audit log: ${e.message}", e)
        }
    }

    // Sync Human Approvals to Firestore
    suspend fun syncApprovalRequest(orgId: String, request: HumanApprovalRequest) {
        val uid = auth.currentUser?.uid ?: return
        try {
            val docRef = firestore.collection("organizations")
                .document(orgId)
                .collection("approval_requests")
                .document(request.id)

            val reqData = mapOf(
                "id" to request.id,
                "agentCode" to request.agentCode.name,
                "agentName" to request.agentName,
                "title" to request.title,
                "description" to request.description,
                "impactSummary" to request.impactSummary,
                "riskLevel" to request.riskLevel.name,
                "payloadPreview" to request.payloadPreview,
                "timestamp" to request.timestamp,
                "status" to request.status.name,
                "requiresMfa" to request.requiresMfa,
                "updatedByUid" to uid
            )
            docRef.set(reqData, SetOptions.merge()).await()
        } catch (e: Exception) {
            Log.e("FirebaseManager", "Error syncing approval request: ${e.message}", e)
        }
    }

    // Sync Business Profile to Firestore
    suspend fun syncBusinessProfile(profile: BusinessProfile) {
        val uid = auth.currentUser?.uid ?: return
        try {
            val docRef = firestore.collection("organizations").document(profile.id)
            val data = mapOf(
                "id" to profile.id,
                "name" to profile.name,
                "businessType" to profile.businessType,
                "industry" to profile.industry,
                "country" to profile.country,
                "currency" to profile.currency,
                "businessSize" to profile.businessSize,
                "primaryGoals" to profile.primaryGoals,
                "isBrainInitialized" to profile.isBrainInitialized,
                "aiAutomationPreference" to profile.aiAutomationPreference,
                "ownerUid" to uid,
                "updatedAt" to System.currentTimeMillis()
            )
            docRef.set(data, SetOptions.merge()).await()
        } catch (e: Exception) {
            Log.e("FirebaseManager", "Error syncing business profile: ${e.message}", e)
        }
    }

    // Sync Consumer Consent to Firestore
    suspend fun syncConsumerConsent(userId: String, consent: ConsumerConsentItem) {
        try {
            val docRef = firestore.collection("users")
                .document(userId)
                .collection("consents")
                .document(consent.id)
            val data = mapOf(
                "id" to consent.id,
                "categoryName" to consent.categoryName,
                "dataType" to consent.dataType,
                "purpose" to consent.purpose,
                "whoCanAccess" to consent.whoCanAccess,
                "status" to consent.status.name,
                "retentionPeriod" to consent.retentionPeriod,
                "legalBasis" to consent.legalBasis,
                "updatedAt" to System.currentTimeMillis()
            )
            docRef.set(data, SetOptions.merge()).await()
        } catch (e: Exception) {
            Log.e("FirebaseManager", "Error syncing consumer consent: ${e.message}", e)
        }
    }

    // Sync Consumer Vault Item to Firestore
    suspend fun syncConsumerVaultItem(userId: String, item: ConsumerVaultItem) {
        try {
            val docRef = firestore.collection("users")
                .document(userId)
                .collection("vault_items")
                .document(item.id)
            val data = mapOf(
                "id" to item.id,
                "title" to item.title,
                "merchant" to item.merchant,
                "docType" to item.docType.name,
                "dateIssued" to item.dateIssued,
                "expiryDaysRemaining" to (item.expiryDaysRemaining ?: 0),
                "serialNumber" to item.serialNumber,
                "coverageSummary" to item.coverageSummary,
                "verifiedHash" to item.verifiedHash
            )
            docRef.set(data, SetOptions.merge()).await()
        } catch (e: Exception) {
            Log.e("FirebaseManager", "Error syncing vault item: ${e.message}", e)
        }
    }

    // Sync Simulation to Firestore
    suspend fun syncSimulationScenario(orgId: String, scenario: SimulationScenario) {
        try {
            val docRef = firestore.collection("organizations")
                .document(orgId)
                .collection("simulations")
                .document(scenario.id)
            val data = mapOf(
                "id" to scenario.id,
                "title" to scenario.title,
                "variableParameter" to scenario.variableParameter,
                "currentDelta" to scenario.currentDelta,
                "baseRevenue" to scenario.baseRevenue,
                "estimatedRevenueDeltaPercent" to scenario.estimatedRevenueDeltaPercent,
                "estimatedMarginDeltaPercent" to scenario.estimatedMarginDeltaPercent,
                "riskLevel" to scenario.riskLevel,
                "confidencePercent" to scenario.confidencePercent,
                "simulatedAt" to System.currentTimeMillis()
            )
            docRef.set(data, SetOptions.merge()).await()
        } catch (e: Exception) {
            Log.e("FirebaseManager", "Error syncing simulation: ${e.message}", e)
        }
    }
}
