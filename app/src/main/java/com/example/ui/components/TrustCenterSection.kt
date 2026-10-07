package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.*
import com.example.ui.theme.*

@Composable
fun TrustCenterSection(
    complianceItems: List<ComplianceItem>,
    vendorTrustItems: List<VendorTrustItem>,
    securitySignals: List<SecuritySignal>,
    auditLogs: List<AuditLogEntry>,
    modifier: Modifier = Modifier
) {
    var activeSubTab by remember { mutableStateOf("COMPLIANCE") }

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
                    imageVector = Icons.Default.VerifiedUser,
                    contentDescription = null,
                    tint = VeduEmerald,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "TRUST CENTER & GOVERNANCE",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = VeduTextPrimary,
                    letterSpacing = 1.sp
                )
            }

            Surface(
                shape = RoundedCornerShape(4.dp),
                color = Color(0x3310B981)
            ) {
                Text(
                    text = "DPDP 2023 READY",
                    style = MaterialTheme.typography.labelSmall,
                    color = VeduEmerald,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Zero-trust privacy architecture, verified audit logging, and automated compliance monitoring.",
            style = MaterialTheme.typography.bodyMedium,
            color = VeduTextSecondary
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Sub Tabs: COMPLIANCE, SECURITY, VENDORS, DATA DNA, AUDIT LOGS
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            TabPill("COMPLIANCE", activeSubTab == "COMPLIANCE") { activeSubTab = "COMPLIANCE" }
            TabPill("SENTINEL", activeSubTab == "SENTINEL") { activeSubTab = "SENTINEL" }
            TabPill("VENDORS", activeSubTab == "VENDORS") { activeSubTab = "VENDORS" }
            TabPill("DATA DNA", activeSubTab == "DATA DNA") { activeSubTab = "DATA DNA" }
            TabPill("AUDIT LOG", activeSubTab == "AUDIT LOG") { activeSubTab = "AUDIT LOG" }
        }

        Spacer(modifier = Modifier.height(14.dp))

        when (activeSubTab) {
            "COMPLIANCE" -> {
                ComplianceView(complianceItems)
            }
            "SENTINEL" -> {
                CyberSentinelView(securitySignals)
            }
            "VENDORS" -> {
                VendorTrustView(vendorTrustItems)
            }
            "DATA DNA" -> {
                DataDnaMapView()
            }
            "AUDIT LOG" -> {
                AuditLogStreamView(auditLogs)
            }
        }
    }
}

@Composable
private fun TabPill(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(6.dp),
        color = if (isSelected) VeduSurfaceHighlight else VeduSurface,
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isSelected) VeduCyan else VeduBorder
        )
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) VeduCyan else VeduTextSecondary,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun ComplianceView(items: List<ComplianceItem>) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        // Legal notice disclaimer
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
                .background(Color(0x22F59E0B))
                .padding(8.dp)
        ) {
            Text(
                text = "Disclaimer: VEDU Compliance Copilot provides operational assistance and guidelines tracking. Output does not constitute formal legal advice.",
                style = MaterialTheme.typography.labelSmall,
                color = VeduAmber
            )
        }

        items.forEach { comp ->
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = VeduSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, VeduBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = comp.framework,
                            style = MaterialTheme.typography.labelSmall,
                            color = VeduCyan,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = comp.currentStatus,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (comp.isCritical) VeduAmber else VeduEmerald,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = comp.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = VeduTextPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = comp.whyItApplies,
                        style = MaterialTheme.typography.bodyMedium,
                        color = VeduTextSecondary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Suggested Review: ${comp.suggestedReview}",
                        style = MaterialTheme.typography.labelSmall,
                        color = VeduTextMuted
                    )
                }
            }
        }
    }
}

@Composable
private fun CyberSentinelView(signals: List<SecuritySignal>) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        signals.forEach { sig ->
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = VeduSurface,
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (sig.severity == "WARNING") VeduAmber else VeduBorder
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = sig.alertType,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (sig.severity == "WARNING") VeduAmber else VeduTextPrimary
                        )
                        Text(
                            text = sig.status,
                            style = MaterialTheme.typography.labelSmall,
                            color = VeduCyan
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = sig.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = VeduTextSecondary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Automated Defense: ${sig.recommendedDefense}",
                        style = MaterialTheme.typography.labelSmall,
                        color = VeduEmerald
                    )
                }
            }
        }
    }
}

@Composable
private fun VendorTrustView(vendors: List<VendorTrustItem>) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        vendors.forEach { v ->
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = VeduSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, VeduBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = v.vendorName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = VeduTextPrimary
                        )
                        Text(
                            text = "Risk Score: ${v.riskScore}/100",
                            style = MaterialTheme.typography.labelSmall,
                            color = VeduEmerald,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Category: ${v.category} • Criticality: ${v.criticality}",
                        style = MaterialTheme.typography.labelSmall,
                        color = VeduTextMuted
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Data Scope: ${v.dataAccessScope}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = VeduTextSecondary
                    )
                }
            }
        }
    }
}

@Composable
private fun DataDnaMapView() {
    val steps = listOf(
        "CUSTOMER" to "Explicit Consent Granted for Order Processing",
        "DATA" to "Encrypted Shipping & Device Telemetry (AES-256)",
        "PURPOSE" to "Fulfillment & Predictive Warranty Alerting",
        "SYSTEM" to "Zero-Trust Microservice Architecture",
        "AI ORCHESTRATOR" to "Permission-Bounded Query Filter",
        "AGENT" to "Supply & Logistics Agent (Read-Only)",
        "ACTION" to "Firewall-Gated Action Formulation",
        "RESULT" to "Tamper-Evident SHA-256 Audit Record"
    )

    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = "VISUAL DATA DNA MAP: VERIFIED ACCESS FLOW",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = VeduCyan,
            letterSpacing = 1.sp
        )

        steps.forEachIndexed { idx, (stage, desc) ->
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = VeduSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, VeduBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(VeduSurfaceHighlight),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${idx + 1}",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = VeduCyan
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = stage,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = VeduTextPrimary
                        )
                        Text(
                            text = desc,
                            style = MaterialTheme.typography.labelSmall,
                            color = VeduTextSecondary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AuditLogStreamView(logs: List<AuditLogEntry>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "TAMPER-RESISTANT AUDIT TRAIL",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = VeduTextMuted
        )

        logs.forEach { log ->
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = VeduSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, VeduBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = log.action,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = when (log.result) {
                                AuditResult.SUCCESS -> VeduEmerald
                                AuditResult.BLOCKED_BY_FIREWALL -> VeduAmber
                                AuditResult.FLAGGED_FOR_REVIEW -> VeduCyan
                                AuditResult.KILL_SWITCH_ENFORCED -> VeduCrimson
                            }
                        )
                        Text(
                            text = log.timestamp,
                            style = MaterialTheme.typography.labelSmall,
                            color = VeduTextMuted
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Actor: ${log.actor} (${log.role}) • Resource: ${log.resource}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = VeduTextSecondary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Hash: ${log.tamperHash.take(28)}...",
                        style = MaterialTheme.typography.labelSmall,
                        color = VeduTextMuted
                    )
                }
            }
        }
    }
}
