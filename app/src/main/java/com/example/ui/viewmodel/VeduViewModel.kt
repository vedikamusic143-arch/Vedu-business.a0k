package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.FirebaseManager
import com.example.data.VeduRepository
import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class AdminNavigationTab(val label: String) {
    COMMAND_CENTER("Command Center"),
    BUSINESS_BRAIN("Business Brain & Twin"),
    AI_AGENTS("AI Agents & Ledger"),
    TRUST_CENTER("Trust & Governance"),
    ASK_VEDU("Ask VEDU")
}

enum class ConsumerNavigationTab(val label: String) {
    HOME("My VEDU"),
    VAULT("Digital Vault"),
    PRIVACY("Privacy & Consents"),
    BENEFITS("Benefits & Shield"),
    ASK_VEDU("Personal VEDU")
}

class VeduViewModel(application: Application) : AndroidViewModel(application) {
    val repository = VeduRepository(application)
    val firebaseManager = FirebaseManager(application)

    // Firebase Auth States
    val currentUser = firebaseManager.currentUser
    val isSigningIn = firebaseManager.isSigningIn
    val signInError = firebaseManager.signInError

    private val _isGuestMode = MutableStateFlow(true)
    val isGuestMode: StateFlow<Boolean> = _isGuestMode.asStateFlow()

    // Roles and navigation
    val currentRole = repository.currentRole
    val businessProfile = repository.businessProfile
    val isGlobalKillSwitchActive = repository.isGlobalKillSwitchActive

    private val _adminTab = MutableStateFlow(AdminNavigationTab.COMMAND_CENTER)
    val adminTab: StateFlow<AdminNavigationTab> = _adminTab.asStateFlow()

    private val _consumerTab = MutableStateFlow(ConsumerNavigationTab.HOME)
    val consumerTab: StateFlow<ConsumerNavigationTab> = _consumerTab.asStateFlow()

    // Business Data
    val businessPulse = repository.businessPulse
    val todayIntelligence = repository.todayIntelligence
    val aiAgents = repository.aiAgents
    val approvalRequests = repository.approvalRequests
    val simulationScenario = repository.simulationScenario
    val opportunities = repository.opportunities
    val demandForecasts = repository.demandForecasts

    // Trust & Governance
    val complianceItems = repository.complianceItems
    val vendorTrustItems = repository.vendorTrustItems
    val securitySignals = repository.securitySignals
    val auditLogs = repository.auditLogs

    // Consumer Data
    val consumerProfile = repository.consumerProfile
    val consumerVaultItems = repository.consumerVaultItems
    val consumerConsents = repository.consumerConsents
    val consumerBenefits = repository.consumerBenefits
    val securityShieldAlerts = repository.securityShieldAlerts

    // Ask VEDU
    val askVeduMessages = repository.askVeduMessages
    val currentLanguage = repository.currentLanguage

    // UI Dialogs
    private val _showOnboardingDialog = MutableStateFlow(false)
    val showOnboardingDialog: StateFlow<Boolean> = _showOnboardingDialog.asStateFlow()

    private val _showKillSwitchDialog = MutableStateFlow(false)
    val showKillSwitchDialog: StateFlow<Boolean> = _showKillSwitchDialog.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isVoiceListening = MutableStateFlow(false)
    val isVoiceListening: StateFlow<Boolean> = _isVoiceListening.asStateFlow()

    fun signInWithGoogle() {
        viewModelScope.launch {
            val success = firebaseManager.signInWithGoogle()
            if (success) {
                _isGuestMode.value = false
            }
        }
    }

    fun continueAsGuest(role: UserRole = UserRole.BUSINESS_OWNER) {
        repository.switchRole(role)
        _isGuestMode.value = true
    }

    fun signOut() {
        firebaseManager.signOut()
        _isGuestMode.value = false
    }

    fun switchRole(role: UserRole) {
        repository.switchRole(role)
    }

    fun setAdminTab(tab: AdminNavigationTab) {
        _adminTab.value = tab
    }

    fun setConsumerTab(tab: ConsumerNavigationTab) {
        _consumerTab.value = tab
    }

    fun openOnboarding() {
        _showOnboardingDialog.value = true
    }

    fun closeOnboarding() {
        _showOnboardingDialog.value = false
    }

    fun openKillSwitchDialog() {
        _showKillSwitchDialog.value = true
    }

    fun closeKillSwitchDialog() {
        _showKillSwitchDialog.value = false
    }

    fun triggerGlobalKillSwitch(reason: String) {
        repository.triggerGlobalKillSwitch(reason)
        _showKillSwitchDialog.value = false
    }

    fun resumeAiOperations() {
        repository.resumeAiOperations()
    }

    fun toggleAgentStatus(agentId: String) {
        repository.toggleAgentStatus(agentId)
    }

    fun updateAgentPermissions(agentId: String, permissions: AgentPermissions) {
        repository.updateAgentPermissions(agentId, permissions)
    }

    fun approveRequest(id: String) {
        repository.approveRequest(id)
        viewModelScope.launch {
            val req = approvalRequests.value.find { it.id == id }
            if (req != null) {
                firebaseManager.syncApprovalRequest(businessProfile.value.id, req)
            }
        }
    }

    fun rejectRequest(id: String, reason: String = "Rejected by authorized manager") {
        repository.rejectRequest(id, reason)
        viewModelScope.launch {
            val req = approvalRequests.value.find { it.id == id }
            if (req != null) {
                firebaseManager.syncApprovalRequest(businessProfile.value.id, req)
            }
        }
    }

    fun updateSimulationDelta(delta: Float) {
        repository.updateSimulationDelta(delta)
        viewModelScope.launch {
            firebaseManager.syncSimulationScenario(businessProfile.value.id, simulationScenario.value)
        }
    }

    fun toggleDemoMode() {
        repository.toggleDemoMode()
    }

    fun updateConsumerConsent(id: String, newStatus: ConsentStatus) {
        repository.updateConsumerConsent(id, newStatus)
        viewModelScope.launch {
            val consent = consumerConsents.value.find { it.id == id }
            val uid = currentUser.value?.uid ?: "guest-consumer"
            if (consent != null) {
                firebaseManager.syncConsumerConsent(uid, consent.copy(status = newStatus))
            }
        }
    }

    fun claimConsumerBenefit(id: String) {
        repository.claimConsumerBenefit(id)
    }

    fun recordAuditAction(actionDescription: String) {
        viewModelScope.launch {
            val log = repository.recordCustomAudit(actionDescription)
            firebaseManager.recordCloudAuditLog(businessProfile.value.id, log)
        }
    }

    fun setLanguage(language: AiLanguage) {
        repository.setLanguage(language)
    }

    fun sendChatMessage(text: String) {
        if (text.isBlank()) return
        repository.askVedu(text.trim())
    }

    fun simulateVoiceInput(simulatedSpeechPrompt: String) {
        _isVoiceListening.value = true
        viewModelScope.launch {
            kotlinx.coroutines.delay(1200)
            _isVoiceListening.value = false
            repository.askVedu(simulatedSpeechPrompt)
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun saveOnboardingProfile(
        name: String,
        industry: String,
        country: String,
        currency: String,
        size: String,
        goals: List<String>,
        aiPref: String
    ) {
        val current = businessProfile.value
        val updated = current.copy(
            name = name.ifBlank { "Aarav Sharma" },
            industry = industry.ifBlank { "Consumer Electronics & IoT" },
            country = country.ifBlank { "India" },
            currency = currency.ifBlank { "₹" },
            businessSize = size.ifBlank { "Growth (50-200)" },
            primaryGoals = if (goals.isNotEmpty()) goals else current.primaryGoals,
            aiAutomationPreference = aiPref,
            isBrainInitialized = true
        )
        repository.updateBusinessProfile(updated)
        viewModelScope.launch {
            firebaseManager.syncBusinessProfile(updated)
        }
        _showOnboardingDialog.value = false
    }
}
