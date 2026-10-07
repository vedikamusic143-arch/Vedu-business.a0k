package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.UserRole
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.AdminNavigationTab
import com.example.ui.viewmodel.VeduViewModel

@Composable
fun VeduApp(
    viewModel: VeduViewModel = viewModel()
) {
    val currentRole by viewModel.currentRole.collectAsStateWithLifecycle()
    val businessProfile by viewModel.businessProfile.collectAsStateWithLifecycle()
    val isKillSwitchActive by viewModel.isGlobalKillSwitchActive.collectAsStateWithLifecycle()
    val adminTab by viewModel.adminTab.collectAsStateWithLifecycle()

    // Dialogs
    val showOnboarding by viewModel.showOnboardingDialog.collectAsStateWithLifecycle()
    val showKillSwitch by viewModel.showKillSwitchDialog.collectAsStateWithLifecycle()

    // System Back Navigation Handlers per Android UX guidelines
    BackHandler(enabled = showOnboarding) {
        viewModel.closeOnboarding()
    }
    BackHandler(enabled = showKillSwitch) {
        viewModel.closeKillSwitchDialog()
    }
    BackHandler(enabled = !showOnboarding && !showKillSwitch && currentRole != UserRole.BUSINESS_OWNER) {
        viewModel.switchRole(UserRole.BUSINESS_OWNER)
    }
    BackHandler(enabled = !showOnboarding && !showKillSwitch && currentRole.isBusinessRole && adminTab != AdminNavigationTab.COMMAND_CENTER) {
        viewModel.setAdminTab(AdminNavigationTab.COMMAND_CENTER)
    }

    // Firebase Auth
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val isSigningIn by viewModel.isSigningIn.collectAsStateWithLifecycle()
    val signInError by viewModel.signInError.collectAsStateWithLifecycle()
    val isGuestMode by viewModel.isGuestMode.collectAsStateWithLifecycle()

    if (currentUser == null && !isGuestMode) {
        SignInScreen(
            isSigningIn = isSigningIn,
            errorMessage = signInError,
            onSignInWithGoogle = { viewModel.signInWithGoogle() },
            onContinueGuest = { role -> viewModel.continueAsGuest(role) }
        )
        return
    }

    // Business Data
    val pulse by viewModel.businessPulse.collectAsStateWithLifecycle()
    val todayIntelligence by viewModel.todayIntelligence.collectAsStateWithLifecycle()
    val aiAgents by viewModel.aiAgents.collectAsStateWithLifecycle()
    val approvalRequests by viewModel.approvalRequests.collectAsStateWithLifecycle()
    val simulationScenario by viewModel.simulationScenario.collectAsStateWithLifecycle()
    val opportunities by viewModel.opportunities.collectAsStateWithLifecycle()
    val demandForecasts by viewModel.demandForecasts.collectAsStateWithLifecycle()

    // Trust Data
    val complianceItems by viewModel.complianceItems.collectAsStateWithLifecycle()
    val vendorTrustItems by viewModel.vendorTrustItems.collectAsStateWithLifecycle()
    val securitySignals by viewModel.securitySignals.collectAsStateWithLifecycle()
    val auditLogs by viewModel.auditLogs.collectAsStateWithLifecycle()

    // Consumer Data
    val consumerProfile by viewModel.consumerProfile.collectAsStateWithLifecycle()
    val consumerVaultItems by viewModel.consumerVaultItems.collectAsStateWithLifecycle()
    val consumerConsents by viewModel.consumerConsents.collectAsStateWithLifecycle()
    val consumerBenefits by viewModel.consumerBenefits.collectAsStateWithLifecycle()
    val securityAlerts by viewModel.securityShieldAlerts.collectAsStateWithLifecycle()

    // Chat
    val askMessages by viewModel.askVeduMessages.collectAsStateWithLifecycle()
    val currentLang by viewModel.currentLanguage.collectAsStateWithLifecycle()
    val isVoiceListening by viewModel.isVoiceListening.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            VeduHeader(
                businessName = businessProfile.name,
                currentRole = currentRole,
                isDemoMode = businessProfile.isDemoMode,
                isKillSwitchActive = isKillSwitchActive,
                onRoleSelected = { viewModel.switchRole(it) },
                onKillSwitchClicked = { viewModel.openKillSwitchDialog() },
                onToggleDemoMode = { viewModel.toggleDemoMode() },
                onOpenOnboarding = { viewModel.openOnboarding() },
                onSignOut = { viewModel.signOut() }
            )
        },
        bottomBar = {
            if (currentRole.isBusinessRole) {
                NavigationBar(
                    containerColor = VeduMidnight,
                    contentColor = VeduTextPrimary,
                    windowInsets = WindowInsets.navigationBars,
                    modifier = Modifier.testTag("admin_bottom_navigation")
                ) {
                    NavigationBarItem(
                        selected = adminTab == AdminNavigationTab.COMMAND_CENTER,
                        onClick = { viewModel.setAdminTab(AdminNavigationTab.COMMAND_CENTER) },
                        icon = { Icon(Icons.Default.Dashboard, contentDescription = "Command Center") },
                        label = { Text("Pulse") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = VeduCyan,
                            selectedTextColor = VeduCyan,
                            unselectedIconColor = VeduTextMuted,
                            unselectedTextColor = VeduTextMuted,
                            indicatorColor = VeduSurfaceHighlight
                        )
                    )

                    NavigationBarItem(
                        selected = adminTab == AdminNavigationTab.BUSINESS_BRAIN,
                        onClick = { viewModel.setAdminTab(AdminNavigationTab.BUSINESS_BRAIN) },
                        icon = { Icon(Icons.Default.Science, contentDescription = "Twin & Sim") },
                        label = { Text("Twin") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = VeduCyan,
                            selectedTextColor = VeduCyan,
                            unselectedIconColor = VeduTextMuted,
                            unselectedTextColor = VeduTextMuted,
                            indicatorColor = VeduSurfaceHighlight
                        )
                    )

                    NavigationBarItem(
                        selected = adminTab == AdminNavigationTab.AI_AGENTS,
                        onClick = { viewModel.setAdminTab(AdminNavigationTab.AI_AGENTS) },
                        icon = { Icon(Icons.Default.SmartToy, contentDescription = "AI Agents") },
                        label = { Text("Agents") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = VeduCyan,
                            selectedTextColor = VeduCyan,
                            unselectedIconColor = VeduTextMuted,
                            unselectedTextColor = VeduTextMuted,
                            indicatorColor = VeduSurfaceHighlight
                        )
                    )

                    NavigationBarItem(
                        selected = adminTab == AdminNavigationTab.TRUST_CENTER,
                        onClick = { viewModel.setAdminTab(AdminNavigationTab.TRUST_CENTER) },
                        icon = { Icon(Icons.Default.VerifiedUser, contentDescription = "Trust Center") },
                        label = { Text("Trust") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = VeduCyan,
                            selectedTextColor = VeduCyan,
                            unselectedIconColor = VeduTextMuted,
                            unselectedTextColor = VeduTextMuted,
                            indicatorColor = VeduSurfaceHighlight
                        )
                    )

                    NavigationBarItem(
                        selected = adminTab == AdminNavigationTab.ASK_VEDU,
                        onClick = { viewModel.setAdminTab(AdminNavigationTab.ASK_VEDU) },
                        icon = { Icon(Icons.Default.AutoAwesome, contentDescription = "Ask VEDU") },
                        label = { Text("Ask VEDU") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = VeduCyan,
                            selectedTextColor = VeduCyan,
                            unselectedIconColor = VeduTextMuted,
                            unselectedTextColor = VeduTextMuted,
                            indicatorColor = VeduSurfaceHighlight
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(VeduObsidian)
        ) {
            when {
                // CONSUMER EXPERIENCE
                currentRole == UserRole.CONSUMER -> {
                    ConsumerView(
                        profile = consumerProfile,
                        vaultItems = consumerVaultItems,
                        consents = consumerConsents,
                        benefits = consumerBenefits,
                        securityAlerts = securityAlerts,
                        onUpdateConsent = { id, st -> viewModel.updateConsumerConsent(id, st) },
                        onClaimBenefit = { id -> viewModel.claimConsumerBenefit(id) },
                        onAskPersonalVedu = { q -> viewModel.sendChatMessage(q) }
                    )
                }

                // SUPER ADMIN PLATFORM OVERSIGHT
                currentRole == UserRole.SUPER_ADMIN -> {
                    SuperAdminView(
                        businessProfile = businessProfile,
                        isKillSwitchActive = isKillSwitchActive,
                        agentsCount = aiAgents.size,
                        auditCount = auditLogs.size,
                        onOpenOnboarding = { viewModel.openOnboarding() },
                        onTriggerKillSwitch = { viewModel.openKillSwitchDialog() }
                    )
                }

                // BUSINESS OPERATING SYSTEM (Owner, Admin, Manager, Employee)
                else -> {
                    when (adminTab) {
                        AdminNavigationTab.COMMAND_CENTER -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState())
                            ) {
                                BusinessPulseCards(
                                    businessName = businessProfile.name,
                                    pulse = pulse,
                                    onAskVeduClick = { viewModel.setAdminTab(AdminNavigationTab.ASK_VEDU) }
                                )

                                HumanApprovalFirewallCard(
                                    requests = approvalRequests,
                                    onApprove = { viewModel.approveRequest(it) },
                                    onReject = { viewModel.rejectRequest(it) }
                                )

                                TodaysIntelligenceSection(
                                    items = todayIntelligence,
                                    onTriggerAction = { item ->
                                        // Send action to Ask VEDU / Firewall
                                        viewModel.sendChatMessage("Execute recommended action: ${item.recommendedAction}")
                                        viewModel.setAdminTab(AdminNavigationTab.ASK_VEDU)
                                    }
                                )

                                Spacer(modifier = Modifier.height(24.dp))
                            }
                        }

                        AdminNavigationTab.BUSINESS_BRAIN -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState())
                            ) {
                                DigitalTwinSimulationSection(
                                    scenario = simulationScenario,
                                    opportunities = opportunities,
                                    demandForecasts = demandForecasts,
                                    onDeltaChange = { viewModel.updateSimulationDelta(it) },
                                    onRunCycleCompleted = { viewModel.recordAuditAction(it) }
                                )

                                Spacer(modifier = Modifier.height(24.dp))
                            }
                        }

                        AdminNavigationTab.AI_AGENTS -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState())
                            ) {
                                AiAgentsLedgerSection(
                                    agents = aiAgents,
                                    onToggleAgentStatus = { viewModel.toggleAgentStatus(it) },
                                    onUpdatePermissions = { id, p -> viewModel.updateAgentPermissions(id, p) },
                                    onKillSwitchClick = { viewModel.openKillSwitchDialog() }
                                )

                                IntegrationsAndMarketplaceSection(
                                    onTriggerAuditLog = { viewModel.recordAuditAction(it) }
                                )

                                Spacer(modifier = Modifier.height(24.dp))
                            }
                        }

                        AdminNavigationTab.TRUST_CENTER -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState())
                            ) {
                                TrustCenterSection(
                                    complianceItems = complianceItems,
                                    vendorTrustItems = vendorTrustItems,
                                    securitySignals = securitySignals,
                                    auditLogs = auditLogs
                                )

                                Spacer(modifier = Modifier.height(24.dp))
                            }
                        }

                        AdminNavigationTab.ASK_VEDU -> {
                            AskVeduScreen(
                                messages = askMessages,
                                currentLanguage = currentLang,
                                isVoiceListening = isVoiceListening,
                                onSendMessage = { viewModel.sendChatMessage(it) },
                                onVoicePrompt = { viewModel.simulateVoiceInput(it) },
                                onLanguageChange = { viewModel.setLanguage(it) }
                            )
                        }
                    }
                }
            }
        }
    }

    // Onboarding Dialog
    if (showOnboarding) {
        OnboardingDialog(
            initialProfile = businessProfile,
            onDismiss = { viewModel.closeOnboarding() },
            onSaveProfile = { name, ind, ctry, curr, sz, goals, pref ->
                viewModel.saveOnboardingProfile(name, ind, ctry, curr, sz, goals, pref)
            }
        )
    }

    // Kill Switch Confirmation Dialog
    if (showKillSwitch) {
        KillSwitchDialog(
            isCurrentlyActive = isKillSwitchActive,
            onDismiss = { viewModel.closeKillSwitchDialog() },
            onConfirmKill = { reason -> viewModel.triggerGlobalKillSwitch(reason) },
            onResume = {
                viewModel.resumeAiOperations()
                viewModel.closeKillSwitchDialog()
            }
        )
    }
}

@Composable
private fun SuperAdminView(
    businessProfile: com.example.model.BusinessProfile,
    isKillSwitchActive: Boolean,
    agentsCount: Int,
    auditCount: Int,
    onOpenOnboarding: () -> Unit,
    onTriggerKillSwitch: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "SUPER ADMIN CONSOLE",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = VeduTextPrimary
        )
        Text(
            text = "Platform Infrastructure & Multi-Tenant Governance Oversight",
            style = MaterialTheme.typography.bodyMedium,
            color = VeduTextSecondary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Surface(
            shape = RoundedCornerShape(12.dp),
            color = VeduSurface,
            border = androidx.compose.foundation.BorderStroke(1.dp, VeduBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "System Health & Provider Architecture",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = VeduCyan
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "• AI Provider: Google Gemini Server-Side Orchestrator",
                    style = MaterialTheme.typography.bodyMedium,
                    color = VeduTextPrimary
                )
                Text(
                    text = "• Model Layer: Provider-Agnostic Abstraction Layer",
                    style = MaterialTheme.typography.bodyMedium,
                    color = VeduTextSecondary
                )
                Text(
                    text = "• Active Tenant Organization: ${businessProfile.name} (${businessProfile.id})",
                    style = MaterialTheme.typography.bodyMedium,
                    color = VeduTextSecondary
                )
                Text(
                    text = "• Provisioned Agents: $agentsCount online",
                    style = MaterialTheme.typography.bodyMedium,
                    color = VeduTextSecondary
                )
                Text(
                    text = "• Immutable Audit Records: $auditCount verified hashes",
                    style = MaterialTheme.typography.bodyMedium,
                    color = VeduEmerald
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        onClick = onOpenOnboarding,
                        colors = ButtonDefaults.buttonColors(containerColor = VeduCyan, contentColor = VeduObsidian),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Reconfigure Tenant Brain", fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = onTriggerKillSwitch,
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = VeduCrimson),
                        border = androidx.compose.foundation.BorderStroke(1.dp, VeduCrimson),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = if (isKillSwitchActive) "Disengage Kill Switch" else "Engage Kill Switch")
                    }
                }
            }
        }
    }
}
