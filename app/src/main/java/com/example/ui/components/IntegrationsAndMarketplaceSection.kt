package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

data class IntegrationDetail(
    val name: String,
    val color: Color,
    val icon: ImageVector,
    var isConnected: Boolean,
    val scope: String,
    val lastSync: String
)

@Composable
fun IntegrationsAndMarketplaceSection(
    onTriggerAuditLog: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var activeTab by remember { mutableStateOf("MARKETPLACE") }
    var selectedIntegration by remember { mutableStateOf<IntegrationDetail?>(null) }
    var integrationsList by remember {
        mutableStateOf(
            listOf(
                IntegrationDetail("WhatsApp", Color(0xFF25D366), Icons.Default.Chat, true, "Customer alerts & order receipts", "2m ago"),
                IntegrationDetail("Gmail", Color(0xFFEA4335), Icons.Default.Email, true, "Invoice ingestion & approval emails", "12m ago"),
                IntegrationDetail("Drive", Color(0xFF34A853), Icons.Default.CloudQueue, true, "Encrypted contract vault synchronization", "1h ago"),
                IntegrationDetail("CRM", Color(0xFF0284C7), Icons.Default.Hub, true, "Lead intelligence & pipeline sync", "5m ago"),
                IntegrationDetail("Stripe", Color(0xFF6366F1), Icons.Default.Payment, true, "Payment webhook telemetry & revenue ledger", "Live stream"),
                IntegrationDetail("ERP", Color(0xFF8B5CF6), Icons.Default.Dns, true, "Inventory levels & BOM cost data", "30m ago")
            )
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Mode Switcher: MARKETPLACE & CONNECTORS vs DEVELOPER & API ECOSYSTEM
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { activeTab = "MARKETPLACE" },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (activeTab == "MARKETPLACE") VeduCyan else VeduMidnight,
                    contentColor = if (activeTab == "MARKETPLACE") VeduObsidian else VeduTextSecondary
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .weight(1f)
                    .testTag("tab_marketplace_btn")
            ) {
                Icon(Icons.Default.Storefront, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Marketplace & Connectors", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = { activeTab = "DEVELOPER" },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (activeTab == "DEVELOPER") VeduCyan else VeduMidnight,
                    contentColor = if (activeTab == "DEVELOPER") VeduObsidian else VeduTextSecondary
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .weight(1f)
                    .testTag("tab_developer_btn")
            ) {
                Icon(Icons.Default.Terminal, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Developer & API Portal", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
            }
        }

        if (activeTab == "DEVELOPER") {
            DeveloperApiEcosystemSection(onTriggerAuditLog = onTriggerAuditLog)
        } else {
            // Integrations Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = VeduSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, VeduBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Enterprise Connectors",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = VeduTextPrimary
                            )
                            Text(
                                text = "Tap any connector to view data permissions & sync",
                                style = MaterialTheme.typography.labelSmall,
                                color = VeduTextSecondary
                            )
                        }

                        Text(
                            text = "${integrationsList.count { it.isConnected }}/${integrationsList.size} Active",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = VeduEmerald
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Integration Grid: WhatsApp, Gmail, Google Drive, CRM, Stripe, ERP
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        integrationsList.forEach { item ->
                            IntegrationAppIcon(
                                item = item,
                                onClick = { selectedIntegration = item }
                            )
                        }
                    }
                }
            }

            // Agent Marketplace Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = VeduSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, VeduBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Agent Marketplace",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = VeduTextPrimary
                            )
                            Text(
                                text = "Extend Your Business Operating System with Verified Agents",
                                style = MaterialTheme.typography.labelSmall,
                                color = VeduTextSecondary
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = VeduSurfaceLight
                        ) {
                            Text(
                                text = "Verified Only",
                                style = MaterialTheme.typography.labelSmall,
                                color = VeduCyan,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Marketplace list
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        MarketplaceItem(
                            title = "E-commerce Growth Agent",
                            developer = "by VEDU Labs",
                            rating = "★ 4.8",
                            pricing = "Free / Enterprise"
                        )
                        MarketplaceItem(
                            title = "Supply Chain Risk Sentinel",
                            developer = "by GlobalTech",
                            rating = "★ 4.7",
                            pricing = "Verified Free"
                        )
                        MarketplaceItem(
                            title = "Tax & GST Compliance Auditor",
                            developer = "by FinVedu",
                            rating = "★ 4.9",
                            pricing = "Pro / Licensed"
                        )
                    }
                }
            }

            // Tech Stack & Trust Layer Banner
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFF091322),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1E3A8A))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TechPill("Cloud Firestore", "Encrypted at Rest")
                    TechPill("AI Orchestrator", "Permission Bounded")
                    TechPill("Trust Firewall", "Human in the Loop")
                }
            }
        }
    }

    // Integration Detail Dialog
    selectedIntegration?.let { integration ->
        AlertDialog(
            onDismissRequest = { selectedIntegration = null },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(integration.color.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = integration.icon,
                            contentDescription = null,
                            tint = integration.color,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("${integration.name} Connector", color = VeduTextPrimary)
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Status: ${if (integration.isConnected) "Connected & Synchronized" else "Disconnected"}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (integration.isConnected) VeduEmerald else VeduCrimson
                    )
                    Text(
                        text = "Data Scope: ${integration.scope}",
                        style = MaterialTheme.typography.bodySmall,
                        color = VeduTextSecondary
                    )
                    Text(
                        text = "Last Successful Sync: ${integration.lastSync}",
                        style = MaterialTheme.typography.labelSmall,
                        color = VeduTextMuted
                    )
                    Text(
                        text = "Zero-trust privacy: All payload data is tokenized before ingestion into the Business Brain.",
                        style = MaterialTheme.typography.labelSmall,
                        color = VeduCyan,
                        fontSize = 10.sp
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val newStatus = !integration.isConnected
                        integrationsList = integrationsList.map {
                            if (it.name == integration.name) it.copy(isConnected = newStatus) else it
                        }
                        onTriggerAuditLog(
                            if (newStatus) "Reconnected integration: ${integration.name}"
                            else "Disconnected integration: ${integration.name}"
                        )
                        selectedIntegration = null
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (integration.isConnected) VeduCrimson else VeduCyan,
                        contentColor = if (integration.isConnected) Color.White else VeduObsidian
                    )
                ) {
                    Text(if (integration.isConnected) "Disconnect" else "Connect Now", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedIntegration = null }) {
                    Text("Close", color = VeduTextSecondary)
                }
            },
            containerColor = VeduMidnight
        )
    }
}

@Composable
private fun IntegrationAppIcon(
    item: IntegrationDetail,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .testTag("connector_${item.name.lowercase()}")
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(item.color.copy(alpha = if (item.isConnected) 0.2f else 0.05f))
                .border(
                    1.dp,
                    if (item.isConnected) item.color.copy(alpha = 0.6f) else VeduBorder,
                    RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.name,
                tint = if (item.isConnected) item.color else VeduTextMuted,
                modifier = Modifier.size(22.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = item.name,
            style = MaterialTheme.typography.labelSmall,
            color = if (item.isConnected) VeduTextPrimary else VeduTextMuted,
            fontSize = 10.sp
        )
        Text(
            text = if (item.isConnected) "Syncing" else "Off",
            style = MaterialTheme.typography.labelSmall,
            color = if (item.isConnected) VeduEmerald else VeduCrimson,
            fontSize = 8.sp
        )
    }
}

@Composable
private fun MarketplaceItem(
    title: String,
    developer: String,
    rating: String,
    pricing: String
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = VeduSurfaceLight,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(VeduCyanGlow),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.SmartToy,
                        contentDescription = null,
                        tint = VeduCyan,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = VeduTextPrimary
                    )
                    Text(
                        text = "$developer • $rating",
                        style = MaterialTheme.typography.labelSmall,
                        color = VeduTextMuted,
                        fontSize = 9.sp
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(4.dp),
                color = VeduSurfaceHighlight
            ) {
                Text(
                    text = pricing,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = VeduCyan,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
    }
}

@Composable
private fun TechPill(title: String, subtitle: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = title, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = VeduTextPrimary)
        Text(text = subtitle, style = MaterialTheme.typography.labelSmall, color = VeduCyan, fontSize = 8.sp)
    }
}
