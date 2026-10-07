package com.example.model

enum class MessageSender {
    USER,
    VEDU_AI,
    SYSTEM_FIREWALL
}

enum class IntentClassification(val label: String, val badgeColorHex: Long) {
    INFORMATION("Information Request", 0xFF3B82F6),
    RECOMMENDATION("Strategic Recommendation", 0xFF10B981),
    SIMULATION("Digital Twin Simulation", 0xFF8B5CF6),
    ACTION("Operational Action Request", 0xFFF59E0B)
}

enum class AiLanguage(val displayName: String, val promptPrefix: String) {
    ENGLISH("English", "Responding in professional English"),
    HINDI("हिन्दी (Hindi)", "Responding in formal Hindi business tone"),
    HINGLISH("Hinglish", "Responding in conversational bilingual Hinglish")
}

data class AskVeduMessage(
    val id: String,
    val sender: MessageSender,
    val text: String,
    val timestamp: String,
    val classification: IntentClassification = IntentClassification.INFORMATION,
    val confidence: Int = 94,
    val actionPayload: String? = null,
    val actionRiskLevel: ActionRiskLevel? = null,
    val requiresApproval: Boolean = false,
    val approvalId: String? = null,
    val language: AiLanguage = AiLanguage.ENGLISH
)

data class AiMemoryEntry(
    val id: String,
    val layerName: String, // "Session Memory", "Business Memory", "Operational Memory"
    val content: String,
    val dateRecorded: String,
    val category: String,
    val canDelete: Boolean = true
)
