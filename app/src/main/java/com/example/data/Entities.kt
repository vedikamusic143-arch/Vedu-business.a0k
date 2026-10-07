package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "audit_logs")
data class AuditLogEntity(
    @PrimaryKey val id: String,
    val timestamp: String,
    val actor: String,
    val role: String,
    val action: String,
    val resource: String,
    val result: String,
    val aiAgentName: String?,
    val approvalId: String?,
    val tamperHash: String
)

@Entity(tableName = "approval_requests")
data class ApprovalRequestEntity(
    @PrimaryKey val id: String,
    val agentCode: String,
    val agentName: String,
    val title: String,
    val description: String,
    val impactSummary: String,
    val riskLevel: String,
    val payloadPreview: String,
    val timestamp: String,
    val status: String,
    val requiresMfa: Boolean
)

@Entity(tableName = "consumer_vault_items")
data class ConsumerVaultEntity(
    @PrimaryKey val id: String,
    val title: String,
    val merchant: String,
    val docType: String,
    val dateIssued: String,
    val expiryDaysRemaining: Int?,
    val serialNumber: String,
    val coverageSummary: String,
    val verifiedHash: String
)
