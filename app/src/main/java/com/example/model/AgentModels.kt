package com.example.model

enum class AgentCode(val displayName: String, val category: String) {
    SALES("Sales Intelligence Agent", "Revenue"),
    MARKETING("Marketing Engine Agent", "Acquisition"),
    CUSTOMER("Customer Support Agent", "Retention"),
    RESEARCH("Market & Rival Research Agent", "Intelligence"),
    OPERATIONS("Supply & Logistics Agent", "Operations"),
    FINANCE("Financial Forecasting Agent", "Finance"),
    COMPLIANCE("DPDP & Legal Guardian Agent", "Governance"),
    STRATEGY("Corporate Strategy Agent", "Leadership")
}

enum class AgentStatus {
    ACTIVE,
    PAUSED,
    KILLED
}

data class AgentPermissions(
    val canRead: Boolean = true,
    val canWrite: Boolean = false,
    val canExecute: Boolean = false,
    val canExport: Boolean = false,
    val canDelete: Boolean = false
)

data class AIAgent(
    val id: String,
    val code: AgentCode,
    val name: String,
    val purpose: String,
    val permissions: AgentPermissions,
    val dataScope: String,
    val actionScope: String,
    val status: AgentStatus = AgentStatus.ACTIVE,
    val tasksCompleted: Int = 0,
    val successRate: Int = 98,
    val humanOverrides: Int = 0,
    val escalations: Int = 0,
    val tokensUsedFormatted: String = "142K tokens",
    val estimatedBusinessImpact: String = "+₹1.8L"
)

enum class ApprovalStatus {
    PENDING,
    APPROVED,
    REJECTED,
    EXECUTED
}

data class HumanApprovalRequest(
    val id: String,
    val agentCode: AgentCode,
    val agentName: String,
    val title: String,
    val description: String,
    val impactSummary: String,
    val riskLevel: ActionRiskLevel,
    val payloadPreview: String,
    val timestamp: String,
    val status: ApprovalStatus = ApprovalStatus.PENDING,
    val requiresMfa: Boolean = false
)
