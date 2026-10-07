package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VeduDao {
    @Query("SELECT * FROM audit_logs ORDER BY timestamp DESC")
    fun getAllAuditLogs(): Flow<List<AuditLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuditLog(log: AuditLogEntity)

    @Query("SELECT * FROM approval_requests ORDER BY timestamp DESC")
    fun getAllApprovals(): Flow<List<ApprovalRequestEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApproval(approval: ApprovalRequestEntity)

    @Query("UPDATE approval_requests SET status = :newStatus WHERE id = :id")
    suspend fun updateApprovalStatus(id: String, newStatus: String)

    @Query("SELECT * FROM consumer_vault_items")
    fun getAllVaultItems(): Flow<List<ConsumerVaultEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVaultItem(item: ConsumerVaultEntity)
}
