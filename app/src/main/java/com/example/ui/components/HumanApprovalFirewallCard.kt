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
import com.example.model.ActionRiskLevel
import com.example.model.ApprovalStatus
import com.example.model.HumanApprovalRequest
import com.example.ui.theme.*

@Composable
fun HumanApprovalFirewallCard(
    requests: List<HumanApprovalRequest>,
    onApprove: (String) -> Unit,
    onReject: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val pendingRequests = requests.filter { it.status == ApprovalStatus.PENDING }

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
                    imageVector = Icons.Default.Shield,
                    contentDescription = null,
                    tint = VeduAmber,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "HUMAN APPROVAL FIREWALL",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = VeduTextPrimary,
                    letterSpacing = 1.sp
                )
            }

            Surface(
                shape = RoundedCornerShape(4.dp),
                color = if (pendingRequests.isNotEmpty()) Color(0x33F59E0B) else Color(0x3310B981)
            ) {
                Text(
                    text = "${pendingRequests.size} PENDING ACTIONS",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (pendingRequests.isNotEmpty()) VeduAmber else VeduEmerald,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "AI agents cannot execute consequential operations autonomously without human review.",
            style = MaterialTheme.typography.bodyMedium,
            color = VeduTextSecondary
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (pendingRequests.isEmpty()) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                color = VeduSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, VeduBorder)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = VeduEmerald,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "All AI queues verified. Zero unauthorized actions pending execution.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = VeduTextSecondary
                    )
                }
            }
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                pendingRequests.forEach { req ->
                    ApprovalItemCard(
                        request = req,
                        onApprove = { onApprove(req.id) },
                        onReject = { onReject(req.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ApprovalItemCard(
    request: HumanApprovalRequest,
    onApprove: () -> Unit,
    onReject: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("approval_card_${request.id}"),
        shape = RoundedCornerShape(10.dp),
        color = VeduSurface,
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (request.riskLevel == ActionRiskLevel.HIGH) VeduCrimson else VeduAmber
        )
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header: Agent + Risk pill + Time
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0x333B82F6))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = request.agentName,
                            style = MaterialTheme.typography.labelSmall,
                            color = VeduCyan,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    if (request.requiresMfa) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0x33EF4444))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "MFA REQUIRED",
                                style = MaterialTheme.typography.labelSmall,
                                color = VeduCrimson,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Text(
                    text = request.timestamp,
                    style = MaterialTheme.typography.labelSmall,
                    color = VeduTextMuted
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Title
            Text(
                text = request.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = VeduTextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Description
            Text(
                text = request.description,
                style = MaterialTheme.typography.bodyMedium,
                color = VeduTextSecondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Impact Summary Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .background(VeduSurfaceLight)
                    .padding(8.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Simulated Impact: ${request.impactSummary}",
                        style = MaterialTheme.typography.labelSmall,
                        color = VeduTextPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Payload: ${request.payloadPreview}",
                        style = MaterialTheme.typography.labelSmall,
                        color = VeduTextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = onReject,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = VeduCrimson),
                    border = androidx.compose.foundation.BorderStroke(1.dp, VeduCrimson),
                    shape = RoundedCornerShape(6.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Reject Action", style = MaterialTheme.typography.labelSmall)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = onApprove,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VeduEmerald,
                        contentColor = VeduObsidian
                    ),
                    shape = RoundedCornerShape(6.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Authorize & Execute",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
