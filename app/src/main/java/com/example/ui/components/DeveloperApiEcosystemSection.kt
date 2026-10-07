package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

data class ApiKeyModel(
    val id: String,
    val name: String,
    val keyPrefix: String,
    val environment: String,
    val created: String,
    val requestsLast24h: Int,
    val isRevoked: Boolean = false
)

data class WebhookModel(
    val id: String,
    val url: String,
    val events: List<String>,
    val status: String,
    val lastPingStatus: String = "200 OK"
)

@Composable
fun DeveloperApiEcosystemSection(
    onTriggerAuditLog: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var activeSubTab by remember { mutableStateOf("KEYS") }
    var apiKeys by remember {
        mutableStateOf(
            listOf(
                ApiKeyModel(
                    id = "key-01",
                    name = "Production Backend Pipeline",
                    keyPrefix = "vedu_live_8f3a...91ce",
                    environment = "Live Production",
                    created = "2 days ago",
                    requestsLast24h = 4120
                ),
                ApiKeyModel(
                    id = "key-02",
                    name = "ERP Staging Webhook Sync",
                    keyPrefix = "vedu_sand_2a9b...77df",
                    environment = "Sandbox / Test",
                    created = "5 days ago",
                    requestsLast24h = 840
                )
            )
        )
    }

    var webhooks by remember {
        mutableStateOf(
            listOf(
                WebhookModel(
                    id = "wh-01",
                    url = "https://api.aether.com/vedu/events",
                    events = listOf("risk.flagged", "approval.required", "twin.simulated"),
                    status = "Active"
                ),
                WebhookModel(
                    id = "wh-02",
                    url = "https://ops.internal.net/alerts/vedu",
                    events = listOf("killswitch.engaged", "compliance.breach"),
                    status = "Active"
                )
            )
        )
    }

    var showGenerateKeyDialog by remember { mutableStateOf(false) }
    var newKeyName by remember { mutableStateOf("") }
    var testPingMessage by remember { mutableStateOf<String?>(null) }
    var copiedNotice by remember { mutableStateOf<String?>(null) }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("developer_ecosystem_card"),
        shape = RoundedCornerShape(12.dp),
        color = VeduSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, VeduBorder)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0x333B82F6)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Terminal,
                            contentDescription = null,
                            tint = Color(0xFF60A5FA),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "DEVELOPER & API ECOSYSTEM",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = VeduTextPrimary
                        )
                        Text(
                            text = "REST Endpoints, Webhooks & Scoped Cryptographic Keys",
                            style = MaterialTheme.typography.labelSmall,
                            color = VeduTextSecondary,
                            fontSize = 10.sp
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = Color(0x3310B981)
                ) {
                    Text(
                        text = "v1.4 STABLE",
                        style = MaterialTheme.typography.labelSmall,
                        color = VeduEmerald,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Sub tabs: KEYS, WEBHOOKS, TELEMETRY, DOCS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                ApiTabPill("API KEYS", activeSubTab == "KEYS") { activeSubTab = "KEYS" }
                ApiTabPill("WEBHOOKS", activeSubTab == "WEBHOOKS") { activeSubTab = "WEBHOOKS" }
                ApiTabPill("QUOTAS & TELEMETRY", activeSubTab == "TELEMETRY") { activeSubTab = "TELEMETRY" }
                ApiTabPill("API DOCS", activeSubTab == "DOCS") { activeSubTab = "DOCS" }
            }

            Spacer(modifier = Modifier.height(14.dp))

            when (activeSubTab) {
                "KEYS" -> {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Authorized Integration Keys (${apiKeys.count { !it.isRevoked }} active)",
                                style = MaterialTheme.typography.labelMedium,
                                color = VeduTextSecondary
                            )

                            Button(
                                onClick = { showGenerateKeyDialog = true },
                                colors = ButtonDefaults.buttonColors(containerColor = VeduCyan, contentColor = VeduObsidian),
                                shape = RoundedCornerShape(6.dp),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                modifier = Modifier.testTag("generate_api_key_btn")
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("New Key", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                            }
                        }

                        if (copiedNotice != null) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color(0x3310B981),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = copiedNotice ?: "",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = VeduEmerald,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }

                        apiKeys.forEach { key ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = VeduMidnight,
                                border = androidx.compose.foundation.BorderStroke(
                                    1.dp,
                                    if (key.isRevoked) VeduCrimson.copy(alpha = 0.5f) else VeduBorder
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(
                                                text = key.name,
                                                style = MaterialTheme.typography.bodyMedium,
                                                fontWeight = FontWeight.Bold,
                                                color = if (key.isRevoked) VeduTextMuted else VeduTextPrimary
                                            )
                                            Text(
                                                text = "${key.environment} • Created ${key.created}",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = VeduTextSecondary,
                                                fontSize = 9.sp
                                            )
                                        }

                                        if (key.isRevoked) {
                                            Text(
                                                text = "REVOKED",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = VeduCrimson,
                                                fontWeight = FontWeight.Bold
                                            )
                                        } else {
                                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                                IconButton(
                                                    onClick = {
                                                        copiedNotice = "Key token ${key.keyPrefix} copied to clipboard."
                                                    },
                                                    modifier = Modifier.size(28.dp)
                                                ) {
                                                    Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = VeduCyan, modifier = Modifier.size(16.dp))
                                                }
                                                IconButton(
                                                    onClick = {
                                                        apiKeys = apiKeys.map {
                                                            if (it.id == key.id) it.copy(isRevoked = true) else it
                                                        }
                                                        onTriggerAuditLog("Revoked API key: ${key.name} (${key.keyPrefix})")
                                                    },
                                                    modifier = Modifier.size(28.dp)
                                                ) {
                                                    Icon(Icons.Default.DeleteOutline, contentDescription = "Revoke", tint = VeduCrimson, modifier = Modifier.size(16.dp))
                                                }
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(6.dp))

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(Color(0xFF09101C))
                                            .padding(horizontal = 8.dp, vertical = 4.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = key.keyPrefix,
                                            fontFamily = FontFamily.Monospace,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = if (key.isRevoked) VeduTextMuted else VeduCyan
                                        )
                                        Text(
                                            text = "${key.requestsLast24h} calls/24h",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = VeduTextSecondary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                "WEBHOOKS" -> {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "Outgoing Event Webhooks (HMAC-SHA256 signed)",
                            style = MaterialTheme.typography.labelMedium,
                            color = VeduTextSecondary
                        )

                        if (testPingMessage != null) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color(0x3310B981),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = testPingMessage ?: "",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = VeduEmerald,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }

                        webhooks.forEach { wh ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = VeduMidnight,
                                border = androidx.compose.foundation.BorderStroke(1.dp, VeduBorder),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = wh.url,
                                            fontFamily = FontFamily.Monospace,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = VeduTextPrimary,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = wh.lastPingStatus,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = VeduEmerald,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = "Subscribed Events: ${wh.events.joinToString(", ")}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = VeduTextSecondary,
                                        fontSize = 9.sp
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    OutlinedButton(
                                        onClick = {
                                            testPingMessage = "Test event payload dispatched to ${wh.url}. Delivery confirmed: 200 OK (38ms)."
                                            onTriggerAuditLog("Webhook test event ping delivered to ${wh.url}")
                                        },
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = VeduCyan),
                                        border = androidx.compose.foundation.BorderStroke(1.dp, VeduCyan),
                                        shape = RoundedCornerShape(6.dp),
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                        modifier = Modifier.align(Alignment.End)
                                    ) {
                                        Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(12.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Send Test Ping", style = MaterialTheme.typography.labelSmall)
                                    }
                                }
                            }
                        }
                    }
                }

                "TELEMETRY" -> {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TelemetryMetricBox("API Quota Used", "4,960 / 25,000", "79.8% Remaining", VeduCyan)
                            TelemetryMetricBox("P95 Latency", "42 ms", "Global Edge CDN", VeduEmerald)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TelemetryMetricBox("System Uptime", "99.98%", "Multi-Region SLA", VeduEmerald)
                            TelemetryMetricBox("Rate Limit", "100 req/sec", "Token Bucket Policy", VeduPurple)
                        }
                    }
                }

                "DOCS" -> {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        ApiDocItem("GET", "/v1/business/pulse", "Retrieve real-time operating metrics and risk scores.")
                        ApiDocItem("POST", "/v1/simulation/run", "Execute digital twin scenario simulation without applying to production.")
                        ApiDocItem("POST", "/v1/firewall/approvals/{id}", "Authorize or reject intercepted action request.")
                        ApiDocItem("GET", "/v1/agents/ledger", "List registered AI agents, permissions and execution histories.")
                    }
                }
            }
        }
    }

    if (showGenerateKeyDialog) {
        AlertDialog(
            onDismissRequest = { showGenerateKeyDialog = false },
            title = { Text("Generate Production API Key", color = VeduTextPrimary) },
            text = {
                Column {
                    Text(
                        text = "Name your integration client to generate a scoped, tamper-proof bearer token.",
                        style = MaterialTheme.typography.bodySmall,
                        color = VeduTextSecondary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = newKeyName,
                        onValueChange = { newKeyName = it },
                        label = { Text("Client Name") },
                        placeholder = { Text("e.g. Mobile POS Gateway") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val name = newKeyName.ifBlank { "Custom Enterprise Connector" }
                        val randomSuffix = (1000..9999).random()
                        val newKey = ApiKeyModel(
                            id = "key-${System.currentTimeMillis()}",
                            name = name,
                            keyPrefix = "vedu_live_${randomSuffix}...${(10..99).random()}ab",
                            environment = "Live Production",
                            created = "Just now",
                            requestsLast24h = 0
                        )
                        apiKeys = apiKeys + newKey
                        onTriggerAuditLog("Generated new API token: $name")
                        newKeyName = ""
                        showGenerateKeyDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = VeduCyan, contentColor = VeduObsidian)
                ) {
                    Text("Generate Key", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showGenerateKeyDialog = false }) {
                    Text("Cancel", color = VeduTextSecondary)
                }
            },
            containerColor = VeduMidnight
        )
    }
}

@Composable
private fun TelemetryMetricBox(
    title: String,
    value: String,
    subtitle: String,
    accent: Color
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = VeduMidnight,
        border = androidx.compose.foundation.BorderStroke(1.dp, VeduBorder),
        modifier = Modifier.width(160.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = title, style = MaterialTheme.typography.labelSmall, color = VeduTextSecondary)
            Text(text = value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = accent)
            Text(text = subtitle, style = MaterialTheme.typography.labelSmall, color = VeduTextMuted, fontSize = 9.sp)
        }
    }
}

@Composable
private fun ApiDocItem(
    method: String,
    endpoint: String,
    description: String
) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = VeduMidnight,
        border = androidx.compose.foundation.BorderStroke(1.dp, VeduBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = if (method == "GET") Color(0x3310B981) else Color(0x333B82F6)
                ) {
                    Text(
                        text = method,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (method == "GET") VeduEmerald else VeduCyan,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = endpoint,
                    fontFamily = FontFamily.Monospace,
                    style = MaterialTheme.typography.labelSmall,
                    color = VeduTextPrimary,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = VeduTextSecondary,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
private fun ApiTabPill(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) VeduSurfaceHighlight else VeduMidnight,
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
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
        )
    }
}

