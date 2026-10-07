package com.example.model

enum class AuditResult {
    SUCCESS,
    BLOCKED_BY_FIREWALL,
    FLAGGED_FOR_REVIEW,
    KILL_SWITCH_ENFORCED
}

data class AuditLogEntry(
    val id: String,
    val timestamp: String,
    val actor: String,
    val role: String,
    val action: String,
    val resource: String,
    val result: AuditResult,
    val aiAgentName: String? = null,
    val approvalId: String? = null,
    val tamperHash: String = "sha256:7f83b1657ff1fc53b92dc18148a1d65dfc2d4b1fa3d677284addd200126d9069"
)

data class ComplianceItem(
    val id: String,
    val framework: String, // e.g. "DPDP Act 2023", "CERT-In Directive", "RBI Payment Guidelines"
    val title: String,
    val whyItApplies: String,
    val currentStatus: String, // "Configured", "Action Pending", "Under Periodic Review"
    val missingTask: String,
    val suggestedReview: String,
    val officialReference: String,
    val isCritical: Boolean = false
)

data class VendorTrustItem(
    val id: String,
    val vendorName: String,
    val category: String,
    val dataAccessScope: String,
    val criticality: String, // High, Medium, Low
    val lastReviewDate: String,
    val riskScore: Int, // 0-100 (lower is safer)
    val status: String // Active, Under Audit, Restricted
)

data class DataDnaStep(
    val stage: String, // Customer -> Data -> Purpose -> System -> AI -> Agent -> Action -> Result
    val title: String,
    val detail: String,
    val privacyStatus: String
)

data class DataRetentionPolicy(
    val category: String, // Customer PII, Financial Transactions, AI Telemetry, Support Logs
    val retentionPeriod: String, // e.g. "180 Days", "7 Years (Statutory)", "30 Days (Rolling)"
    val postRetentionAction: String, // "Cryptographic Anonymization", "Irreversible Shredding", "Archive"
    val legalBasis: String
)

data class SecuritySignal(
    val id: String,
    val alertType: String,
    val severity: String, // CRITICAL, WARNING, INFO
    val description: String,
    val detectedTime: String,
    val status: String,
    val recommendedDefense: String
)
