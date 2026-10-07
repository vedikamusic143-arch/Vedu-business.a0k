package com.example.data

import android.content.Context
import com.example.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class VeduRepository(context: Context) {
    private val database = VeduDatabase.getDatabase(context)
    private val dao = database.veduDao()
    private val scope = CoroutineScope(Dispatchers.IO)

    // Active User Role
    private val _currentRole = MutableStateFlow(UserRole.BUSINESS_OWNER)
    val currentRole: StateFlow<UserRole> = _currentRole.asStateFlow()

    // Business Profile & Onboarding
    private val _businessProfile = MutableStateFlow(BusinessProfile())
    val businessProfile: StateFlow<BusinessProfile> = _businessProfile.asStateFlow()

    // Global AI Kill Switch
    private val _isGlobalKillSwitchActive = MutableStateFlow(false)
    val isGlobalKillSwitchActive: StateFlow<Boolean> = _isGlobalKillSwitchActive.asStateFlow()

    // Business Pulse (Operating metrics)
    private val _businessPulse = MutableStateFlow(
        BusinessPulse(
            revenueDisplay = "₹ 4,82,000",
            revenueChangePercent = 18.0,
            growthRatePercent = 22.4,
            totalCustomers = 2430,
            customerGrowthPercent = 14.0,
            retentionRatePercent = 78.0,
            operationsEfficiencyScore = 86,
            marketingRoi = 3.8,
            overallRiskScore = 18,
            openOpportunitiesCount = 3
        )
    )
    val businessPulse: StateFlow<BusinessPulse> = _businessPulse.asStateFlow()

    // Today's Intelligence
    private val _todayIntelligence = MutableStateFlow<List<TodayIntelligenceItem>>(emptyList())
    val todayIntelligence: StateFlow<List<TodayIntelligenceItem>> = _todayIntelligence.asStateFlow()

    // AI Agents
    private val _aiAgents = MutableStateFlow<List<AIAgent>>(emptyList())
    val aiAgents: StateFlow<List<AIAgent>> = _aiAgents.asStateFlow()

    // Human Approval Firewall Requests
    private val _approvalRequests = MutableStateFlow<List<HumanApprovalRequest>>(emptyList())
    val approvalRequests: StateFlow<List<HumanApprovalRequest>> = _approvalRequests.asStateFlow()

    // Digital Twin Simulation
    private val _simulationScenario = MutableStateFlow(
        SimulationScenario(
            id = "sim-pricing-q4",
            title = "Dynamic Pricing Elasticity",
            description = "Simulate the impact of adjusting tier-1 IoT smart gadget pricing across tier-1 & 2 cities.",
            variableParameter = "Price Adjustment (%)",
            sliderMin = -20f,
            sliderMax = 30f,
            currentDelta = 8f,
            baseRevenue = 4250000.0,
            baseMarginPercent = 34.0,
            estimatedRevenueDeltaPercent = 6.4f,
            estimatedCostDeltaPercent = 1.2f,
            estimatedMarginDeltaPercent = 2.4f,
            riskLevel = "Low-Moderate",
            confidencePercent = 89
        )
    )
    val simulationScenario: StateFlow<SimulationScenario> = _simulationScenario.asStateFlow()

    // Opportunities & Forecasts
    private val _opportunities = MutableStateFlow<List<OpportunityItem>>(emptyList())
    val opportunities: StateFlow<List<OpportunityItem>> = _opportunities.asStateFlow()

    private val _demandForecasts = MutableStateFlow<List<DemandForecastItem>>(emptyList())
    val demandForecasts: StateFlow<List<DemandForecastItem>> = _demandForecasts.asStateFlow()

    // Trust Center & Compliance
    private val _complianceItems = MutableStateFlow<List<ComplianceItem>>(emptyList())
    val complianceItems: StateFlow<List<ComplianceItem>> = _complianceItems.asStateFlow()

    private val _vendorTrustItems = MutableStateFlow<List<VendorTrustItem>>(emptyList())
    val vendorTrustItems: StateFlow<List<VendorTrustItem>> = _vendorTrustItems.asStateFlow()

    private val _securitySignals = MutableStateFlow<List<SecuritySignal>>(emptyList())
    val securitySignals: StateFlow<List<SecuritySignal>> = _securitySignals.asStateFlow()

    // Consumer Space
    private val _consumerProfile = MutableStateFlow(ConsumerProfile())
    val consumerProfile: StateFlow<ConsumerProfile> = _consumerProfile.asStateFlow()

    private val _consumerVaultItems = MutableStateFlow<List<ConsumerVaultItem>>(emptyList())
    val consumerVaultItems: StateFlow<List<ConsumerVaultItem>> = _consumerVaultItems.asStateFlow()

    private val _consumerConsents = MutableStateFlow<List<ConsumerConsentItem>>(emptyList())
    val consumerConsents: StateFlow<List<ConsumerConsentItem>> = _consumerConsents.asStateFlow()

    private val _consumerBenefits = MutableStateFlow<List<ConsumerBenefitItem>>(emptyList())
    val consumerBenefits: StateFlow<List<ConsumerBenefitItem>> = _consumerBenefits.asStateFlow()

    private val _securityShieldAlerts = MutableStateFlow<List<ConsumerSecurityShieldAlert>>(emptyList())
    val securityShieldAlerts: StateFlow<List<ConsumerSecurityShieldAlert>> = _securityShieldAlerts.asStateFlow()

    // Audit Trail
    private val _auditLogs = MutableStateFlow<List<AuditLogEntry>>(emptyList())
    val auditLogs: StateFlow<List<AuditLogEntry>> = _auditLogs.asStateFlow()

    // Ask VEDU Chat
    private val _askVeduMessages = MutableStateFlow<List<AskVeduMessage>>(emptyList())
    val askVeduMessages: StateFlow<List<AskVeduMessage>> = _askVeduMessages.asStateFlow()

    private val _currentLanguage = MutableStateFlow(AiLanguage.ENGLISH)
    val currentLanguage: StateFlow<AiLanguage> = _currentLanguage.asStateFlow()

    init {
        seedInitialDemoData()
        loadPersistedData()
    }

    private fun loadPersistedData() {
        scope.launch {
            dao.getAllAuditLogs().collect { entities ->
                if (entities.isNotEmpty()) {
                    _auditLogs.value = entities.map {
                        AuditLogEntry(
                            id = it.id,
                            timestamp = it.timestamp,
                            actor = it.actor,
                            role = it.role,
                            action = it.action,
                            resource = it.resource,
                            result = try { AuditResult.valueOf(it.result) } catch (_: Exception) { AuditResult.SUCCESS },
                            aiAgentName = it.aiAgentName,
                            approvalId = it.approvalId,
                            tamperHash = it.tamperHash
                        )
                    }
                }
            }
        }
    }

    private fun seedInitialDemoData() {
        _todayIntelligence.value = listOf(
            TodayIntelligenceItem(
                id = "intel-1",
                type = IntelligenceType.OPPORTUNITY,
                title = "30% Surge in Northern Regional Demand",
                description = "Cluster of Tier-2 smart hub orders detected with 94% retention probability.",
                whyItMatters = "Opening regional micro-fulfillment can shave 18 hours off delivery & boost customer LTV by 28%.",
                evidenceSource = "Shopify Sales Stream & DTDC Courier Telemetry",
                recommendedAction = "Deploy ₹45,000 regional promotional boost & pre-allocate 200 units to Delhi hub.",
                confidenceScore = 93,
                riskLevel = ActionRiskLevel.MEDIUM,
                impactEstimate = "+₹2,80,000 Revenue"
            ),
            TodayIntelligenceItem(
                id = "intel-2",
                type = IntelligenceType.RISK,
                title = "Critical Chipset Vendor Delay Notice",
                description = "Lead time for BLE micro-controllers pushed back by 12 days at Shenzhen facility.",
                whyItMatters = "May create a 9-day assembly backlog if buffer stock drops below 180 units.",
                evidenceSource = "Vendor ERP API & Freight Forwarder Tracking",
                recommendedAction = "Switch secondary allocation to domestic Chennai supplier at +2.8% marginal cost.",
                confidenceScore = 89,
                riskLevel = ActionRiskLevel.HIGH,
                impactEstimate = "Avoid ₹5,10,000 production downtime"
            ),
            TodayIntelligenceItem(
                id = "intel-3",
                type = IntelligenceType.COMPLIANCE,
                title = "DPDP Act 2023 Consent Verification Audit",
                description = "240 legacy user consent records lack granular purpose limitation timestamp.",
                whyItMatters = "Indian Digital Personal Data Protection Act requires explicit verifiable affirmative consent.",
                evidenceSource = "VEDU Privacy Radar Automated Scanner",
                recommendedAction = "Trigger automated consent refresh banner on next user session.",
                confidenceScore = 98,
                riskLevel = ActionRiskLevel.LOW,
                impactEstimate = "100% Regulatory Immunity"
            ),
            TodayIntelligenceItem(
                id = "intel-4",
                type = IntelligenceType.SECURITY,
                title = "Anomalous API Token Activity Detected",
                description = "External analytics webhook made 420 requests within 60 seconds from unrecognized IP.",
                whyItMatters = "Could indicate compromised credential or misconfigured scraping bot.",
                evidenceSource = "VEDU AI Cyber Sentinel Telemetry",
                recommendedAction = "Enforce IP allowlisting and rotate webhook secret key.",
                confidenceScore = 96,
                riskLevel = ActionRiskLevel.HIGH,
                impactEstimate = "Prevent Potential Data Leak"
            )
        )

        _aiAgents.value = listOf(
            AIAgent(
                id = "agent-sales",
                code = AgentCode.SALES,
                name = "Sales Intelligence Agent",
                purpose = "Monitors buyer intent, drafts personalized B2B quotes, and recommends cross-sell bundles.",
                permissions = AgentPermissions(canRead = true, canWrite = true, canExecute = false, canExport = false, canDelete = false),
                dataScope = "Orders, Leads, CRM Interactions (PII Redacted)",
                actionScope = "Draft proposals, recommend follow-ups (Cannot send unapproved bulk campaigns)",
                status = AgentStatus.ACTIVE,
                tasksCompleted = 342,
                successRate = 98,
                humanOverrides = 6,
                escalations = 2,
                tokensUsedFormatted = "284K tokens",
                estimatedBusinessImpact = "+₹4.2L"
            ),
            AIAgent(
                id = "agent-mktg",
                code = AgentCode.MARKETING,
                name = "Marketing Engine Agent",
                purpose = "Evaluates ad spend ROI, generates localized campaign drafts, and predicts creative fatigue.",
                permissions = AgentPermissions(canRead = true, canWrite = true, canExecute = false, canExport = false, canDelete = false),
                dataScope = "Ad Platforms, Campaign Analytics, Creative Assets",
                actionScope = "Generate draft ad copy, adjust keyword bids within ₹5,000 ceiling",
                status = AgentStatus.ACTIVE,
                tasksCompleted = 512,
                successRate = 95,
                humanOverrides = 14,
                escalations = 4,
                tokensUsedFormatted = "410K tokens",
                estimatedBusinessImpact = "+₹6.8L"
            ),
            AIAgent(
                id = "agent-ops",
                code = AgentCode.OPERATIONS,
                name = "Supply & Logistics Agent",
                purpose = "Monitors inventory stockout velocity, warehouse throughput, and freight carrier SLAs.",
                permissions = AgentPermissions(canRead = true, canWrite = true, canExecute = true, canExport = false, canDelete = false),
                dataScope = "Inventory DB, Shipping APIs, Supplier Lead Times",
                actionScope = "Create PO drafts, re-route orders if carrier is delayed >24 hrs",
                status = AgentStatus.ACTIVE,
                tasksCompleted = 219,
                successRate = 99,
                humanOverrides = 2,
                escalations = 1,
                tokensUsedFormatted = "190K tokens",
                estimatedBusinessImpact = "+₹3.1L"
            ),
            AIAgent(
                id = "agent-finance",
                code = AgentCode.FINANCE,
                name = "Financial Forecasting Agent",
                purpose = "Maintains cash flow runaways, working capital forecasting, and tax liability estimates.",
                permissions = AgentPermissions(canRead = true, canWrite = false, canExecute = false, canExport = true, canDelete = false),
                dataScope = "Invoices, Bank Feeds (Read-only), Tax Ledgers",
                actionScope = "Prepare financial forecast models, alert on runway dips (Zero fund transfer authority)",
                status = AgentStatus.ACTIVE,
                tasksCompleted = 120,
                successRate = 100,
                humanOverrides = 0,
                escalations = 0,
                tokensUsedFormatted = "95K tokens",
                estimatedBusinessImpact = "+₹2.4L"
            ),
            AIAgent(
                id = "agent-compliance",
                code = AgentCode.COMPLIANCE,
                name = "DPDP & Legal Guardian Agent",
                purpose = "Tracks DPDP compliance, verifies customer data retention policies, flags vendor risk.",
                permissions = AgentPermissions(canRead = true, canWrite = true, canExecute = false, canExport = false, canDelete = false),
                dataScope = "Data DNA mappings, Consent logs, Vendor contracts",
                actionScope = "Generate compliance checklists, draft data processing notices",
                status = AgentStatus.ACTIVE,
                tasksCompleted = 88,
                successRate = 100,
                humanOverrides = 1,
                escalations = 0,
                tokensUsedFormatted = "75K tokens",
                estimatedBusinessImpact = "Zero Regulatory Fines"
            ),
            AIAgent(
                id = "agent-customer",
                code = AgentCode.CUSTOMER,
                name = "Customer Support Sentinel",
                purpose = "Assists support agents with contextual resolution suggestions and sentiment triage.",
                permissions = AgentPermissions(canRead = true, canWrite = true, canExecute = false, canExport = false, canDelete = false),
                dataScope = "Support tickets, warranty status, public FAQs",
                actionScope = "Suggest response templates, escalate frustrated customers to Human Leads",
                status = AgentStatus.ACTIVE,
                tasksCompleted = 840,
                successRate = 96,
                humanOverrides = 18,
                escalations = 22,
                tokensUsedFormatted = "320K tokens",
                estimatedBusinessImpact = "+₹1.9L"
            )
        )

        _approvalRequests.value = listOf(
            HumanApprovalRequest(
                id = "appr-01",
                agentCode = AgentCode.MARKETING,
                agentName = "Marketing Engine Agent",
                title = "Launch Festive Flash Sale Campaign to 12,400 Customers",
                description = "Agent prepared localized WhatsApp & Email discount broadcast offering 15% off smart sensors.",
                impactSummary = "Est. cost ₹18,600 • Est. revenue ₹3,40,000 • Margin impact -2.1%",
                riskLevel = ActionRiskLevel.MEDIUM,
                payloadPreview = "Template: 'VEDU Festive Special' • Channel: WhatsApp Business API • Cohort: Past 90d Active",
                timestamp = "Today, 09:42 AM",
                status = ApprovalStatus.PENDING,
                requiresMfa = false
            ),
            HumanApprovalRequest(
                id = "appr-02",
                agentCode = AgentCode.OPERATIONS,
                agentName = "Supply & Logistics Agent",
                title = "Emergency Purchase Order to Secondary Chennai Supplier",
                description = "Procure 450 units of BLE-902 controllers to prevent assembly line stoppage next week.",
                impactSummary = "Total PO Value ₹2,15,000 • +2.8% over primary contract unit rate",
                riskLevel = ActionRiskLevel.HIGH,
                payloadPreview = "Vendor: MicroTech Circuits Pvt Ltd • Payment Terms: Net 30 • Delivery: 72 hrs",
                timestamp = "Today, 08:15 AM",
                status = ApprovalStatus.PENDING,
                requiresMfa = true
            )
        )

        _opportunities.value = listOf(
            OpportunityItem(
                id = "opp-1",
                title = "Smart Hub + Sensor Accessory Bundle",
                category = "Product Bundling",
                whyItMatters = "42% of customers purchase auxiliary sensors within 14 days of smart hub delivery.",
                evidence = "Basket analysis of 2,400 orders across Q3 shows strong affinity (Pearson score 0.81).",
                estimatedImpact = "+₹1,90,000 Gross Profit / Mo",
                actionLabel = "Simulate Bundle Discount"
            ),
            OpportunityItem(
                id = "opp-2",
                title = "Dormant Tier-2 B2B Client Re-engagement",
                category = "Retention",
                whyItMatters = "18 retail distributor accounts have crossed their 45-day re-order cycle without restocking.",
                evidence = "Invoice history indicates avg restock cycle is 32 days; likely delayed by festival planning.",
                estimatedImpact = "₹6,80,000 Pipeline Value",
                actionLabel = "Draft Customized B2B Outreach"
            )
        )

        _demandForecasts.value = listOf(
            DemandForecastItem(
                id = "df-1",
                productCategory = "Smart IoT Gateway Pro",
                horizon = "Next 30 Days",
                expectedDemandTrend = "+28% Surge",
                seasonalFactor = "Diwali Pre-Ordering Cycle",
                inventoryRisk = "Buffer critical in 16 days",
                recommendedBuffer = "+350 Units"
            ),
            DemandForecastItem(
                id = "df-2",
                productCategory = "Ambient Environmental Sensor",
                horizon = "Next 60 Days",
                expectedDemandTrend = "+12% Steady Growth",
                seasonalFactor = "Winter Indoor AQI Awareness",
                inventoryRisk = "Healthy stock level",
                recommendedBuffer = "+100 Units"
            )
        )

        _complianceItems.value = listOf(
            ComplianceItem(
                id = "comp-1",
                framework = "DPDP Act 2023 (India)",
                title = "Granular Consent & Data Principal Rights",
                whyItApplies = "All entities processing digital personal data in India must offer transparent consent withdrawal and notice.",
                currentStatus = "Configured (Audit Pending)",
                missingTask = "Implement automated Consent Expiry notifications for dormant consumer accounts (>180 days).",
                suggestedReview = "Review Section 6 of Digital Personal Data Protection Act with legal counsel.",
                officialReference = "MeitY Gazette DPDP Act 2023",
                isCritical = true
            ),
            ComplianceItem(
                id = "comp-2",
                framework = "CERT-In Cyber Guidelines",
                title = "System Clock Synchronization & 6-Hour Incident Reporting",
                whyItApplies = "Mandatory for Indian enterprise networks, cloud infrastructure and customer-facing APIs.",
                currentStatus = "Active & Verified",
                missingTask = "Quarterly incident response drill with AI Sentinel isolation runbook.",
                suggestedReview = "Validate NTP server locks against National Physical Laboratory (NPL) India.",
                officialReference = "CERT-In Cyber Directions 2022",
                isCritical = false
            ),
            ComplianceItem(
                id = "comp-3",
                framework = "Consumer Protection (E-Commerce) Rules",
                title = "Country of Origin & Warranty Disclosures",
                whyItApplies = "Required on all product specification pages and digital invoices.",
                currentStatus = "Fully Compliant",
                missingTask = "Ensure digital warranty certificate auto-populates in Consumer Vault.",
                suggestedReview = "Consumer Protection E-Commerce Rules 2020",
                officialReference = "Dept of Consumer Affairs",
                isCritical = false
            )
        )

        _vendorTrustItems.value = listOf(
            VendorTrustItem(
                id = "v-1",
                vendorName = "Razorpay Payments",
                category = "Payment Gateway",
                dataAccessScope = "Order ID, Billing Amount, Customer Token (PCI-DSS Scoped)",
                criticality = "High",
                lastReviewDate = "15 Sep 2026",
                riskScore = 12,
                status = "Active & Verified"
            ),
            VendorTrustItem(
                id = "v-2",
                vendorName = "Zendesk Support",
                category = "Customer Helpdesk",
                dataAccessScope = "Ticket conversations, Customer Email, Order references",
                criticality = "Medium",
                lastReviewDate = "01 Aug 2026",
                riskScore = 24,
                status = "Active"
            ),
            VendorTrustItem(
                id = "v-3",
                vendorName = "Mixpanel Analytics",
                category = "Product Telemetry",
                dataAccessScope = "Anonymized click events, Screen sessions (PII Stripped)",
                criticality = "Low",
                lastReviewDate = "10 Jul 2026",
                riskScore = 18,
                status = "Active"
            )
        )

        _securitySignals.value = listOf(
            SecuritySignal(
                id = "sec-1",
                alertType = "Unusual Token Spike",
                severity = "WARNING",
                description = "External analytics webhook queried customer lookup API 420 times in 1 min.",
                detectedTime = "09:12 AM",
                status = "Throttled by Sentinel",
                recommendedDefense = "Rotate API key & restrict webhook to dedicated CIDR."
            ),
            SecuritySignal(
                id = "sec-2",
                alertType = "Privileged Access Verification",
                severity = "INFO",
                description = "Admin console accessed from trusted IP in Bengaluru (MFA Completed).",
                detectedTime = "08:30 AM",
                status = "Verified",
                recommendedDefense = "No action required."
            )
        )

        _consumerVaultItems.value = listOf(
            ConsumerVaultItem(
                id = "vault-1",
                title = "Smart Home IoT Gateway Pro",
                merchant = "Aether Dynamics Official",
                docType = VaultDocType.WARRANTY,
                dateIssued = "18 Sep 2025",
                expiryDaysRemaining = 19,
                serialNumber = "SN-AETH-99201-IND",
                coverageSummary = "Comprehensive hardware & sensor repair or replacement guarantee."
            ),
            ConsumerVaultItem(
                id = "vault-2",
                title = "Tax Invoice #INV-2026-881",
                merchant = "Aether Dynamics Official",
                docType = VaultDocType.RECEIPT,
                dateIssued = "18 Sep 2025",
                expiryDaysRemaining = null,
                serialNumber = "GSTIN: 29AAAAA0000A1Z5",
                coverageSummary = "Billed amount ₹8,499 (CGST 9% + SGST 9% included)."
            ),
            ConsumerVaultItem(
                id = "vault-3",
                title = "Annual Extended Device Care Plan",
                merchant = "Aether Care Shield",
                docType = VaultDocType.SUBSCRIPTION,
                dateIssued = "01 Jan 2026",
                expiryDaysRemaining = 86,
                serialNumber = "POL-SHIELD-4819",
                coverageSummary = "Covers accidental surge damage and priority courier replacements."
            )
        )

        _consumerConsents.value = listOf(
            ConsumerConsentItem(
                id = "c-1",
                categoryName = "Order Delivery & Logistics",
                dataType = "Shipping address, phone number",
                purpose = "Fulfilling and tracking shipments via verified courier partner",
                whoCanAccess = "Logistics Team & Designated Delivery Partner",
                status = ConsentStatus.ALLOWED,
                retentionPeriod = "Order lifecycle + 30 days return window"
            ),
            ConsumerConsentItem(
                id = "c-2",
                categoryName = "Warranty & Vault Storage",
                dataType = "Serial numbers, purchase receipts, service history",
                purpose = "Maintaining proof of ownership and automated warranty alerts",
                whoCanAccess = "Consumer, Authorized Support Technicians",
                status = ConsentStatus.ALLOWED,
                retentionPeriod = "Active warranty period + 3 years"
            ),
            ConsumerConsentItem(
                id = "c-3",
                categoryName = "Personalized Offer Insights",
                dataType = "Product category browsing habits, loyalty score",
                purpose = "Recommending relevant replacement accessories and rewards",
                whoCanAccess = "VEDU Recommendation Engine (No 3rd party sale)",
                status = ConsentStatus.ALLOWED,
                retentionPeriod = "Until consent withdrawn"
            )
        )

        _consumerBenefits.value = listOf(
            ConsumerBenefitItem(
                id = "ben-1",
                title = "₹500 Loyalty Credit Available",
                value = "₹500.00",
                category = "Loyalty Tier",
                whyEligible = "You have accumulated 1,850 VEDU Reward points across 3 verified purchases.",
                expiryNote = "Valid on orders above ₹2,000 through end of month"
            ),
            ConsumerBenefitItem(
                id = "ben-2",
                title = "Complimentary Sensor Firmware Upgrade",
                value = "Free",
                category = "Warranty Extension",
                whyEligible = "Your Smart IoT Gateway has 19 days remaining on base warranty.",
                expiryNote = "Claim within 14 days to unlock +6 months extended coverage"
            )
        )

        _securityShieldAlerts.value = listOf(
            ConsumerSecurityShieldAlert(
                id = "sh-1",
                senderIdentifier = "SMS from +91-98110-XXXXX",
                messageSnippet = "Dear customer, your device warranty expired! Click link http://short.url/pay to renew immediately.",
                riskClassification = "Potentially Suspicious Brand Impersonation",
                timestamp = "Yesterday, 04:15 PM",
                recommendedAction = "Do NOT click external links. Genuine warranty updates are verified in your VEDU Vault."
            )
        )

        _auditLogs.value = listOf(
            AuditLogEntry(
                id = "aud-001",
                timestamp = "2026-10-06 09:42:15",
                actor = "Marketing Engine Agent",
                role = "AI_AGENT",
                action = "PROPOSE_CAMPAIGN",
                resource = "Broadcast / Festive Sale",
                result = AuditResult.FLAGGED_FOR_REVIEW,
                aiAgentName = "Marketing Engine Agent",
                approvalId = "appr-01"
            ),
            AuditLogEntry(
                id = "aud-002",
                timestamp = "2026-10-06 09:12:04",
                actor = "AI Cyber Sentinel",
                role = "SYSTEM_SECURITY",
                action = "THROTTLE_UNUSUAL_API",
                resource = "API Gateway / IP 104.28.14.9",
                result = AuditResult.SUCCESS,
                aiAgentName = "Cyber Sentinel"
            ),
            AuditLogEntry(
                id = "aud-003",
                timestamp = "2026-10-06 08:30:19",
                actor = "Vikram Aditya (Business Owner)",
                role = "BUSINESS_OWNER",
                action = "LOGIN_MFA_SUCCESS",
                resource = "Admin Console Session",
                result = AuditResult.SUCCESS
            )
        )

        _askVeduMessages.value = listOf(
            AskVeduMessage(
                id = "msg-welcome",
                sender = MessageSender.VEDU_AI,
                text = "Welcome to VEDU. I am your Business Operating System and Trust Intelligence partner.\n\nToday's Business Pulse is steady (+14.8% revenue growth). I have identified 3 actionable opportunities, 2 operational risks, and 2 pending actions in your Human Approval Firewall.\n\nAsk me anything about revenue forecasts, run a What-If simulation, or review agent governance.",
                timestamp = "09:00 AM",
                classification = IntentClassification.INFORMATION,
                confidence = 99
            )
        )
    }

    // Role Switch
    fun switchRole(newRole: UserRole) {
        _currentRole.value = newRole
        recordAudit("ROLE_SWITCH", "User switched active role to ${newRole.name}", newRole.name)
    }

    // Business Profile Update / Onboarding
    fun updateBusinessProfile(profile: BusinessProfile) {
        _businessProfile.value = profile
        recordAudit("PROFILE_UPDATE", "Updated business profile '${profile.name}'", _currentRole.value.name)
    }

    fun toggleDemoMode() {
        val current = _businessProfile.value
        _businessProfile.value = current.copy(isDemoMode = !current.isDemoMode)
        recordAudit("DEMO_MODE_TOGGLE", "Toggled demo mode to ${_businessProfile.value.isDemoMode}", _currentRole.value.name)
    }

    // Global AI Kill Switch
    fun triggerGlobalKillSwitch(reason: String) {
        _isGlobalKillSwitchActive.value = true
        // Pause all agents
        _aiAgents.value = _aiAgents.value.map { it.copy(status = AgentStatus.KILLED) }
        recordAudit("KILL_SWITCH_ENGAGED", "GLOBAL KILL SWITCH TRIGGERED: $reason", _currentRole.value.name, AuditResult.KILL_SWITCH_ENFORCED)
    }

    fun resumeAiOperations() {
        _isGlobalKillSwitchActive.value = false
        _aiAgents.value = _aiAgents.value.map { it.copy(status = AgentStatus.ACTIVE) }
        recordAudit("KILL_SWITCH_DISENGAGED", "AI Operations resumed by authorized user", _currentRole.value.name, AuditResult.SUCCESS)
    }

    // Agent Controls
    fun toggleAgentStatus(agentId: String) {
        _aiAgents.value = _aiAgents.value.map { agent ->
            if (agent.id == agentId) {
                val newStatus = if (agent.status == AgentStatus.ACTIVE) AgentStatus.PAUSED else AgentStatus.ACTIVE
                recordAudit("AGENT_STATUS_CHANGE", "Agent '${agent.name}' set to $newStatus", _currentRole.value.name)
                agent.copy(status = newStatus)
            } else agent
        }
    }

    fun updateAgentPermissions(agentId: String, newPermissions: AgentPermissions) {
        _aiAgents.value = _aiAgents.value.map { agent ->
            if (agent.id == agentId) {
                recordAudit("AGENT_PERMISSION_UPDATE", "Updated permissions for '${agent.name}'", _currentRole.value.name)
                agent.copy(permissions = newPermissions)
            } else agent
        }
    }

    // Human Approval Firewall Actions
    fun approveRequest(id: String) {
        val req = _approvalRequests.value.find { it.id == id } ?: return
        _approvalRequests.value = _approvalRequests.value.map {
            if (it.id == id) it.copy(status = ApprovalStatus.EXECUTED) else it
        }
        recordAudit("APPROVAL_GRANTED_AND_EXECUTED", "Action approved: ${req.title}", _currentRole.value.name, AuditResult.SUCCESS, req.agentName, id)
    }

    fun rejectRequest(id: String, reason: String = "Rejected by authorized manager") {
        val req = _approvalRequests.value.find { it.id == id } ?: return
        _approvalRequests.value = _approvalRequests.value.map {
            if (it.id == id) it.copy(status = ApprovalStatus.REJECTED) else it
        }
        recordAudit("APPROVAL_REJECTED", "Action rejected: ${req.title} (Reason: $reason)", _currentRole.value.name, AuditResult.BLOCKED_BY_FIREWALL, req.agentName, id)
    }

    // Digital Twin Simulation Sandbox
    fun updateSimulationDelta(delta: Float) {
        val current = _simulationScenario.value
        val revFactor = 1.0f + (delta * 0.8f / 100f)
        val costFactor = 1.0f + (delta * 0.25f / 100f)
        val marginDelta = (delta * 0.3f)

        _simulationScenario.value = current.copy(
            currentDelta = delta,
            estimatedRevenueDeltaPercent = delta * 0.8f,
            estimatedCostDeltaPercent = delta * 0.25f,
            estimatedMarginDeltaPercent = marginDelta,
            riskLevel = if (delta > 20 || delta < -15) "High Elasticity Risk" else "Calculated & Manageable",
            confidencePercent = if (delta > 25) 76 else 91
        )
    }

    // Consumer Consents
    fun updateConsumerConsent(id: String, newStatus: ConsentStatus) {
        _consumerConsents.value = _consumerConsents.value.map {
            if (it.id == id) it.copy(status = newStatus) else it
        }
        recordAudit("CONSUMER_CONSENT_CHANGE", "Consent $id changed to ${newStatus.name}", "CONSUMER")
    }

    fun claimConsumerBenefit(id: String) {
        _consumerBenefits.value = _consumerBenefits.value.map {
            if (it.id == id) it.copy(isClaimed = true) else it
        }
    }

    // Language Toggle
    fun setLanguage(language: AiLanguage) {
        _currentLanguage.value = language
    }

    // Ask VEDU AI Engine & Orchestrator Pipeline
    fun askVedu(userPrompt: String) {
        val userMsgId = UUID.randomUUID().toString()
        val timeNow = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
        val role = _currentRole.value
        val lang = _currentLanguage.value

        // 1. Add user message
        val userMsg = AskVeduMessage(
            id = userMsgId,
            sender = MessageSender.USER,
            text = userPrompt,
            timestamp = timeNow,
            language = lang
        )
        _askVeduMessages.value = _askVeduMessages.value + userMsg

        // 2. Kill switch check
        if (_isGlobalKillSwitchActive.value) {
            val killedMsg = AskVeduMessage(
                id = UUID.randomUUID().toString(),
                sender = MessageSender.SYSTEM_FIREWALL,
                text = "GLOBAL AI KILL SWITCH IS ACTIVE. All model generation, autonomous recommendations and execution loops have been halted for safety. An administrator must disengage the kill switch to resume.",
                timestamp = timeNow,
                classification = IntentClassification.ACTION,
                actionRiskLevel = ActionRiskLevel.HIGH
            )
            _askVeduMessages.value = _askVeduMessages.value + killedMsg
            return
        }

        // 3. Orchestration pipeline: Intent Classification & Safety Policy Check
        val lower = userPrompt.lowercase()
        val intent: IntentClassification
        val responseText: String
        var actionPayload: String? = null
        var requiresApproval = false
        var riskLevel: ActionRiskLevel? = null
        var approvalId: String? = null

        when {
            // Action Requests: e.g. launch, send, transfer, delete, change price
            lower.contains("launch") || lower.contains("send") || lower.contains("execute") || lower.contains("discount") || lower.contains("bhejo") || lower.contains("karo") -> {
                intent = IntentClassification.ACTION
                riskLevel = ActionRiskLevel.MEDIUM
                requiresApproval = true
                approvalId = "appr-" + UUID.randomUUID().toString().take(6)

                val newApproval = HumanApprovalRequest(
                    id = approvalId,
                    agentCode = AgentCode.MARKETING,
                    agentName = "Marketing Engine Agent",
                    title = "Proposed Action: ${userPrompt.take(50)}...",
                    description = "AI formulated action based on your command: '$userPrompt'.",
                    impactSummary = "Est. cost ₹12,000 • Est. reach 4,500 accounts • Margin impact -0.8%",
                    riskLevel = ActionRiskLevel.MEDIUM,
                    payloadPreview = "User Triggered Action • Verified via VEDU Orchestration Layer",
                    timestamp = "Just Now",
                    status = ApprovalStatus.PENDING
                )
                _approvalRequests.value = listOf(newApproval) + _approvalRequests.value

                responseText = when (lang) {
                    AiLanguage.HINDI -> "VEDU सुरक्षा फ़ायरवॉल ने इस एक्शन को इंटरसेप्ट किया है। यह मीडियम रिस्क ऑपरेशन है, इसलिए इसे सीधे निष्पादित करने के बजाय 'Human Approval Firewall' में सबमिट किया गया है। एडमिन अनुमति के बाद ही यह लागू होगा।"
                    AiLanguage.HINGLISH -> "VEDU Human Approval Firewall alert: Maine yeh action prepare kar liya hai, but security policy ke under direct execute nahi kar sakta. Approval request queue me bhej diya gaya hai. Aap review karke Approve kar sakte hain."
                    AiLanguage.ENGLISH -> "VEDU HUMAN APPROVAL FIREWALL ACTIVATED.\n\nIn accordance with VEDU Governance Principle #18, operational actions cannot bypass human oversight. I have packaged this into an Approval Request (ID: $approvalId) in your Command Center with simulated cost, margin, and reach metrics."
                }
                actionPayload = "Action ID: $approvalId • Awaiting Human Authorization"
                recordAudit("AI_ACTION_BLOCKED_BY_FIREWALL", "Action from prompt intercepted: '$userPrompt'", role.name, AuditResult.BLOCKED_BY_FIREWALL)
            }

            // Simulation Requests: what if, simulate, kya hoga, agar price
            lower.contains("what if") || lower.contains("simulate") || lower.contains("kya hoga") || lower.contains("agar") || lower.contains("forecast") -> {
                intent = IntentClassification.SIMULATION
                val sim = _simulationScenario.value
                responseText = when (lang) {
                    AiLanguage.HINDI -> "डिजिटल ट्विन सिमुलेशन परिणाम:\n\nयदि आप मूल्य में ${sim.currentDelta}% का बदलाव करते हैं, तो अनुमानित राजस्व वृद्धि +${sim.estimatedRevenueDeltaPercent}% (लगभग ₹3.2 लाख/माह) होगी। लागत प्रभाव +${sim.estimatedCostDeltaPercent}% रहेगा और सकल मार्जिन विस्तार +${sim.estimatedMarginDeltaPercent}% होगा। कॉन्फिडेंस स्कोर: ${sim.confidencePercent}%।"
                    AiLanguage.HINGLISH -> "Digital Twin Simulation:\nAgar pricing me ${sim.currentDelta}% delta apply karenge, toh projected revenue impact +${sim.estimatedRevenueDeltaPercent}% hoga. Margin growth +${sim.estimatedMarginDeltaPercent}% expect kiya ja sakta hai. Confidence: ${sim.confidencePercent}%. Note: Yeh estimate historical elasticity data par based hai."
                    AiLanguage.ENGLISH -> "DIGITAL TWIN SIMULATION REPORT:\n\n• Baseline Revenue: ₹42,50,000/mo\n• Estimated Revenue Delta: +${sim.estimatedRevenueDeltaPercent}%\n• Estimated Cost Delta: +${sim.estimatedCostDeltaPercent}%\n• Net Margin Expansion: +${sim.estimatedMarginDeltaPercent}%\n• Confidence Score: ${sim.confidencePercent}%\n• Key Assumption: Competitor price elasticity stays within ±1.5%.\n• Possible Downside: ${sim.possibleDownside}"
                }
                recordAudit("AI_SIMULATION_QUERY", "Ran simulation on: '$userPrompt'", role.name)
            }

            // Recommendation Requests: how to, suggest, advice, kaise, strategy
            lower.contains("30 din") || lower.contains("badh") || lower.contains("how") || lower.contains("suggest") || lower.contains("recommend") || lower.contains("kaise") || lower.contains("improve") || lower.contains("karna chahiye") -> {
                intent = IntentClassification.RECOMMENDATION
                responseText = when (lang) {
                    AiLanguage.HINDI -> "आपके बिज़नेस के लिए पर्सनलाइज़्ड ग्रोथ प्लान:\n\nशीर्ष 3 अवसर:\n1. क्रॉस-सेलिंग अवसर (अनुमानित +18% राजस्व वृद्धि)\n2. पुनः जुड़ाव अभियान (143 निष्क्रिय ग्राहक)\n3. उच्च मांग उत्पाद रुझान (अगले 30 दिन)"
                    AiLanguage.HINGLISH -> "Yeh raha aapke business ke liye personalized growth plan:\n\nTop 3 Opportunities:\n1. Cross-selling opportunity (Estimated +18% revenue)\n2. Re-engagement campaign (143 inactive customers)\n3. High-demand product trend (Next 30 days)"
                    AiLanguage.ENGLISH -> "Here is your personalized business growth plan:\n\nTop 3 Opportunities:\n1. Cross-selling opportunity (Estimated +18% revenue)\n2. Re-engagement campaign (143 inactive customers)\n3. High-demand product trend (Next 30 days)"
                }
                recordAudit("AI_RECOMMENDATION_QUERY", "Provided recommendations for: '$userPrompt'", role.name)
            }

            // General Information: sales, profit, data, customers, risk
            else -> {
                intent = IntentClassification.INFORMATION
                val pulse = _businessPulse.value
                responseText = when (lang) {
                    AiLanguage.HINDI -> "वर्तमान बिज़नेस पल्स:\n\n• कुल राजस्व: ${pulse.revenueDisplay} (+${pulse.revenueChangePercent}% वृद्धि)\n• सक्रिय ग्राहक: ${pulse.totalCustomers} (${pulse.retentionRatePercent}% रिटेंशन)\n• मार्केटिंग ROI: ${pulse.marketingRoi}x\n• समग्र जोखिम स्कोर: ${pulse.overallRiskScore}/100 (स्वस्थ)\n• सक्रिय एजेंट: 6 एजेंट ऑपरेशनल हैं।"
                    AiLanguage.HINGLISH -> "Business Pulse Summary:\n\nTotal Revenue: ${pulse.revenueDisplay} (+${pulse.revenueChangePercent}% MoM growth). Total Customers: ${pulse.totalCustomers} with ${pulse.retentionRatePercent}% retention rate. Marketing ROI 3.8x hai. System health healthy hai aur DPDP governance verified hai."
                    AiLanguage.ENGLISH -> "CURRENT BUSINESS INTELLIGENCE:\n\n• Monthly Revenue: ${pulse.revenueDisplay} (▲ +${pulse.revenueChangePercent}% MoM)\n• Customer Base: ${pulse.totalCustomers} Active (Retention: ${pulse.retentionRatePercent}%)\n• Operational Efficiency: ${pulse.operationsEfficiencyScore}/100\n• Marketing ROI: ${pulse.marketingRoi}x\n• Platform Governance: Active & Compliant with DPDP 2023\n• Active AI Agents: 6 online with Human Approval Firewall engaged."
                }
                recordAudit("AI_INFO_QUERY", "Retrieved business intelligence for: '$userPrompt'", role.name)
            }
        }

        val aiMsg = AskVeduMessage(
            id = UUID.randomUUID().toString(),
            sender = MessageSender.VEDU_AI,
            text = responseText,
            timestamp = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date()),
            classification = intent,
            confidence = 94,
            actionPayload = actionPayload,
            actionRiskLevel = riskLevel,
            requiresApproval = requiresApproval,
            approvalId = approvalId,
            language = lang
        )
        _askVeduMessages.value = _askVeduMessages.value + aiMsg
    }

    fun recordCustomAudit(
        action: String,
        resource: String = ""
    ): AuditLogEntry {
        val entry = AuditLogEntry(
            id = "aud-" + UUID.randomUUID().toString().take(8),
            timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date()),
            actor = "User (${_currentRole.value.name})",
            role = _currentRole.value.name,
            action = action,
            resource = if (resource.isNotBlank()) resource else action,
            result = AuditResult.SUCCESS,
            aiAgentName = null,
            approvalId = null,
            tamperHash = "sha256:" + UUID.randomUUID().toString().replace("-", "")
        )
        _auditLogs.value = listOf(entry) + _auditLogs.value
        scope.launch {
            dao.insertAuditLog(
                AuditLogEntity(
                    id = entry.id,
                    timestamp = entry.timestamp,
                    actor = entry.actor,
                    role = entry.role,
                    action = entry.action,
                    resource = entry.resource,
                    result = entry.result.name,
                    aiAgentName = entry.aiAgentName,
                    approvalId = entry.approvalId,
                    tamperHash = entry.tamperHash
                )
            )
        }
        return entry
    }

    private fun recordAudit(
        action: String,
        resource: String,
        actorRole: String,
        result: AuditResult = AuditResult.SUCCESS,
        agentName: String? = null,
        approvalId: String? = null
    ) {
        val entry = AuditLogEntry(
            id = "aud-" + UUID.randomUUID().toString().take(8),
            timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date()),
            actor = if (agentName != null) agentName else "User ($actorRole)",
            role = actorRole,
            action = action,
            resource = resource,
            result = result,
            aiAgentName = agentName,
            approvalId = approvalId,
            tamperHash = "sha256:" + UUID.randomUUID().toString().replace("-", "")
        )
        _auditLogs.value = listOf(entry) + _auditLogs.value
        scope.launch {
            dao.insertAuditLog(
                AuditLogEntity(
                    id = entry.id,
                    timestamp = entry.timestamp,
                    actor = entry.actor,
                    role = entry.role,
                    action = entry.action,
                    resource = entry.resource,
                    result = entry.result.name,
                    aiAgentName = entry.aiAgentName,
                    approvalId = entry.approvalId,
                    tamperHash = entry.tamperHash
                )
            )
        }
    }
}
