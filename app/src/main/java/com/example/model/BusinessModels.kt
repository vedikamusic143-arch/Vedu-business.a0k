package com.example.model

data class BusinessProfile(
    val id: String = "org-vedu-001",
    val name: String = "Aarav Sharma",
    val businessType: String = "D2C Brand & Tech Manufacturing",
    val industry: String = "Consumer Electronics & IoT",
    val country: String = "India",
    val currency: String = "₹",
    val businessSize: String = "Growth (50-200)",
    val employeesCount: Int = 84,
    val primaryGoals: List<String> = listOf("Scale Revenue 2.5x", "Optimize Supply Chain", "Reduce Churn"),
    val activeIntegrations: List<String> = listOf("Shopify", "Razorpay", "Zendesk", "Zoho Books"),
    val aiAutomationPreference: String = "Balanced (Human in the Loop)",
    val isBrainInitialized: Boolean = true,
    val isDemoMode: Boolean = true
)

enum class IntelligenceType(val label: String) {
    OPPORTUNITY("Opportunity"),
    RISK("Risk"),
    ANOMALY("Anomaly"),
    COMPLIANCE("Compliance"),
    SECURITY("Security"),
    TASK("Action Required")
}

enum class ActionRiskLevel(val label: String) {
    LOW("Low Risk • Auto-Executable"),
    MEDIUM("Medium Risk • Requires Approval"),
    HIGH("High Risk • Multi-Factor Approval")
}

data class TodayIntelligenceItem(
    val id: String,
    val type: IntelligenceType,
    val title: String,
    val description: String,
    val whyItMatters: String,
    val evidenceSource: String,
    val recommendedAction: String,
    val confidenceScore: Int, // e.g. 92%
    val riskLevel: ActionRiskLevel,
    val impactEstimate: String,
    val isActionPending: Boolean = true,
    val isResolved: Boolean = false
)

data class BusinessPulse(
    val revenueDisplay: String,
    val revenueChangePercent: Double,
    val growthRatePercent: Double,
    val totalCustomers: Int,
    val customerGrowthPercent: Double,
    val retentionRatePercent: Double,
    val operationsEfficiencyScore: Int, // 0-100
    val marketingRoi: Double, // 3.8x
    val overallRiskScore: Int, // 0-100 (lower is better)
    val openOpportunitiesCount: Int
)
