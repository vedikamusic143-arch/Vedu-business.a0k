package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AIAgent
import com.example.model.AgentPermissions
import com.example.model.AgentStatus
import com.example.ui.theme.*

@Composable
fun AiAgentsLedgerSection(
    agents: List<AIAgent>,
    onToggleAgentStatus: (String) -> Unit,
    onUpdatePermissions: (String, AgentPermissions) -> Unit,
    onKillSwitchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Section Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.SmartToy,
                    contentDescription = null,
                    tint = VeduCyan,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "AI AGENT ORCHESTRATION",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = VeduTextPrimary,
                    letterSpacing = 1.sp
                )
            }

            Surface(
                shape = RoundedCornerShape(4.dp),
                color = VeduSurfaceLight
            ) {
                Text(
                    text = "${agents.count { it.status == AgentStatus.ACTIVE }} ACTIVE / ${agents.size} TOTAL",
                    style = MaterialTheme.typography.labelSmall,
                    color = VeduEmerald,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Every agent operates within bounded data scopes and explicit permissions. Unrestricted access is forbidden.",
            style = MaterialTheme.typography.bodyMedium,
            color = VeduTextSecondary
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Agents List
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            agents.forEach { agent ->
                AgentCard(
                    agent = agent,
                    onToggleStatus = { onToggleAgentStatus(agent.id) },
                    onPermissionToggle = { pName, currentVal ->
                        val currentP = agent.permissions
                        val newP = when (pName) {
                            "READ" -> currentP.copy(canRead = !currentVal)
                            "WRITE" -> currentP.copy(canWrite = !currentVal)
                            "EXECUTE" -> currentP.copy(canExecute = !currentVal)
                            "EXPORT" -> currentP.copy(canExport = !currentVal)
                            "DELETE" -> currentP.copy(canDelete = !currentVal)
                            else -> currentP
                        }
                        onUpdatePermissions(agent.id, newP)
                    }
                )
            }
        }
    }
}

@Composable
private fun AgentCard(
    agent: AIAgent,
    onToggleStatus: () -> Unit,
    onPermissionToggle: (String, Boolean) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("agent_card_${agent.id}"),
        shape = RoundedCornerShape(10.dp),
        color = VeduSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, VeduBorder)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header: Name, Category, Status Toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(
                                when (agent.status) {
                                    AgentStatus.ACTIVE -> VeduEmerald
                                    AgentStatus.PAUSED -> VeduAmber
                                    AgentStatus.KILLED -> VeduCrimson
                                }
                            )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = agent.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = VeduTextPrimary
                        )
                        Text(
                            text = "Category: ${agent.code.category}",
                            style = MaterialTheme.typography.labelSmall,
                            color = VeduTextMuted
                        )
                    }
                }

                // Status Action Button
                FilledTonalButton(
                    onClick = onToggleStatus,
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = if (agent.status == AgentStatus.ACTIVE) Color(0x33EF4444) else Color(0x3310B981),
                        contentColor = if (agent.status == AgentStatus.ACTIVE) VeduCrimson else VeduEmerald
                    ),
                    shape = RoundedCornerShape(6.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                    modifier = Modifier.height(28.dp)
                ) {
                    Text(
                        text = if (agent.status == AgentStatus.ACTIVE) "PAUSE" else "RESUME",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = agent.purpose,
                style = MaterialTheme.typography.bodyMedium,
                color = VeduTextSecondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Data & Action Scope
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .background(VeduSurfaceLight)
                    .padding(8.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text(
                        text = "Data Scope: ${agent.dataScope}",
                        style = MaterialTheme.typography.labelSmall,
                        color = VeduCyan
                    )
                    Text(
                        text = "Action Scope: ${agent.actionScope}",
                        style = MaterialTheme.typography.labelSmall,
                        color = VeduTextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Permission Matrix Chips (READ, WRITE, EXECUTE, EXPORT, DELETE)
            Text(
                text = "AGENT PERMISSION FIREWALL",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = VeduTextMuted,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                PermissionChip("READ", agent.permissions.canRead) { onPermissionToggle("READ", agent.permissions.canRead) }
                PermissionChip("WRITE", agent.permissions.canWrite) { onPermissionToggle("WRITE", agent.permissions.canWrite) }
                PermissionChip("EXECUTE", agent.permissions.canExecute) { onPermissionToggle("EXECUTE", agent.permissions.canExecute) }
                PermissionChip("EXPORT", agent.permissions.canExport) { onPermissionToggle("EXPORT", agent.permissions.canExport) }
                PermissionChip("DELETE", agent.permissions.canDelete) { onPermissionToggle("DELETE", agent.permissions.canDelete) }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // AI Performance Ledger Metrics
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Ledger: ${agent.tasksCompleted} tasks • ${agent.successRate}% success • ${agent.humanOverrides} overrides",
                    style = MaterialTheme.typography.labelSmall,
                    color = VeduTextMuted
                )

                Text(
                    text = "Impact: ${agent.estimatedBusinessImpact}",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = VeduEmerald
                )
            }
        }
    }
}

@Composable
private fun PermissionChip(
    label: String,
    granted: Boolean,
    onToggle: () -> Unit
) {
    Surface(
        onClick = onToggle,
        shape = RoundedCornerShape(4.dp),
        color = if (granted) Color(0x3310B981) else Color(0x2264748B),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (granted) VeduEmerald else VeduBorder
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (granted) Icons.Default.Check else Icons.Default.Block,
                contentDescription = null,
                tint = if (granted) VeduEmerald else VeduTextMuted,
                modifier = Modifier.size(10.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = if (granted) VeduEmerald else VeduTextMuted,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
