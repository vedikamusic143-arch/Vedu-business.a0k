package com.example.model

enum class VaultDocType(val label: String) {
    WARRANTY("Warranty Card"),
    RECEIPT("Tax Invoice"),
    SERVICE_RECORD("Maintenance"),
    SUBSCRIPTION("Subscription Agreement")
}

data class ConsumerVaultItem(
    val id: String,
    val title: String,
    val merchant: String,
    val docType: VaultDocType,
    val dateIssued: String,
    val expiryDaysRemaining: Int?, // e.g. 19 -> "Warranty expires in 19 days"
    val serialNumber: String,
    val coverageSummary: String,
    val verifiedHash: String = "SHA256-VERIFIED-VAULT-RECORD"
)

enum class ConsentStatus {
    ALLOWED,
    RESTRICTED,
    WITHDRAWN
}

data class ConsumerConsentItem(
    val id: String,
    val categoryName: String,
    val dataType: String,
    val purpose: String,
    val whoCanAccess: String,
    val status: ConsentStatus = ConsentStatus.ALLOWED,
    val retentionPeriod: String,
    val legalBasis: String = "Explicit Consent under DPDP"
)

data class ConsumerBenefitItem(
    val id: String,
    val title: String,
    val value: String,
    val category: String, // "Loyalty Tier", "Seasonal Cashback", "Warranty Extension"
    val whyEligible: String,
    val expiryNote: String,
    val isClaimed: Boolean = false
)

data class ConsumerSecurityShieldAlert(
    val id: String,
    val senderIdentifier: String,
    val messageSnippet: String,
    val riskClassification: String, // "Potentially Suspicious Brand Impersonation"
    val timestamp: String,
    val recommendedAction: String,
    val isVerifiedLegitimate: Boolean = false
)

data class ConsumerProfile(
    val id: String = "usr-cons-771",
    val name: String = "Priya Sharma",
    val email: String = "priya.sharma@example.com",
    val loyaltyPoints: Int = 1850,
    val activeOrdersCount: Int = 2,
    val activeWarrantiesCount: Int = 3,
    val vaultItemsCount: Int = 7
)
