package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
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
fun AskVeduScreen(
    messages: List<AskVeduMessage>,
    currentLanguage: AiLanguage,
    isVoiceListening: Boolean,
    onSendMessage: (String) -> Unit,
    onVoicePrompt: (String) -> Unit,
    onLanguageChange: (AiLanguage) -> Unit,
    modifier: Modifier = Modifier
) {
    var inputText by remember { mutableStateOf("") }
    var showUploadDialog by remember { mutableStateOf(false) }
    var attachedDocName by remember { mutableStateOf<String?>(null) }
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(VeduObsidian)
            .padding(16.dp)
    ) {
        // Header with Official Logo & Language selector
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                VeduRibbonSymbol(size = 32.dp)
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        VeduBrandWordmark()
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "AI",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = VeduCyan
                        )
                    }
                    Text(
                        text = "Permission-bounded Orchestrator",
                        style = MaterialTheme.typography.labelSmall,
                        color = VeduTextSecondary,
                        fontSize = 9.sp
                    )
                }
            }

            // Language switcher pills
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                AiLanguage.entries.forEach { lang ->
                    Surface(
                        onClick = { onLanguageChange(lang) },
                        shape = RoundedCornerShape(4.dp),
                        color = if (lang == currentLanguage) VeduSurfaceHighlight else VeduSurface,
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (lang == currentLanguage) VeduCyan else VeduBorder
                        )
                    ) {
                        Text(
                            text = when (lang) {
                                AiLanguage.ENGLISH -> "EN"
                                AiLanguage.HINDI -> "हिन्दी"
                                AiLanguage.HINGLISH -> "Hinglish"
                            },
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (lang == currentLanguage) FontWeight.Bold else FontWeight.Normal,
                            color = if (lang == currentLanguage) VeduCyan else VeduTextSecondary,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Suggested Prompts from screenshot:
        // "Sales agle 30 din mein?", "Risks batao", "Simulate +10% Price"
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            PromptChip("Sales agle 30 din mein?") {
                onSendMessage("Mere business ki sales agle 30 din mein kaise badh sakti hai?")
            }
            PromptChip("Aaj ke business risks") {
                onVoicePrompt("VEDU, aaj ke business risks batao.")
            }
            PromptChip("Price -10% karo") {
                onSendMessage("What if we reduce product price by 10%?")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Message Thread
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(messages) { msg ->
                MessageBubble(
                    message = msg,
                    onSimulateClick = { onSendMessage("Simulate the revenue and margin impact of this growth plan") },
                    onCreateCampaignClick = { onSendMessage("Launch the festive sale campaign immediately") }
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Voice listening indicator
        if (isVoiceListening) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0x3300F0FF))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = null,
                    tint = VeduCyan,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Listening to voice input in ${currentLanguage.displayName}...",
                    style = MaterialTheme.typography.labelSmall,
                    color = VeduCyan,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
        }

        // Attached document indicator chip
        if (attachedDocName != null) {
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = VeduSurfaceHighlight,
                border = androidx.compose.foundation.BorderStroke(1.dp, VeduCyan),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Attachment,
                            contentDescription = null,
                            tint = VeduCyan,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Attached: $attachedDocName",
                            style = MaterialTheme.typography.labelSmall,
                            color = VeduTextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    IconButton(
                        onClick = { attachedDocName = null },
                        modifier = Modifier.size(20.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Remove",
                            tint = VeduTextMuted,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }

        // Input bar with Voice, Upload, Share as shown in screenshot 3
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Upload button
            IconButton(
                onClick = { showUploadDialog = true },
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.UploadFile,
                    contentDescription = "Upload Document",
                    tint = if (attachedDocName != null) VeduCyan else VeduTextSecondary,
                    modifier = Modifier.size(20.dp)
                )
            }

            // Voice mic button
            IconButton(
                onClick = {
                    onVoicePrompt("VEDU, summarize business pulse and today's top risks.")
                },
                modifier = Modifier
                    .size(36.dp)
                    .testTag("voice_input_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = "Voice Input",
                    tint = VeduCyan,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                placeholder = {
                    Text(
                        text = if (attachedDocName != null) "Ask anything about $attachedDocName..." else "Type a message...",
                        color = VeduTextMuted,
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .testTag("ask_vedu_input_field"),
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = VeduSurface,
                    unfocusedContainerColor = VeduSurface,
                    focusedBorderColor = VeduCyan,
                    unfocusedBorderColor = VeduBorder,
                    focusedTextColor = VeduTextPrimary,
                    unfocusedTextColor = VeduTextPrimary
                ),
                maxLines = 3
            )

            Spacer(modifier = Modifier.width(6.dp))

            IconButton(
                onClick = {
                    if (inputText.isNotBlank() || attachedDocName != null) {
                        val fullPrompt = if (attachedDocName != null) {
                            val doc = attachedDocName
                            attachedDocName = null
                            if (inputText.isNotBlank()) "[Attached Document: $doc]\n$inputText"
                            else "Analyze the uploaded document: $doc for executive intelligence and risk flags."
                        } else {
                            inputText
                        }
                        onSendMessage(fullPrompt)
                        inputText = ""
                    }
                },
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(21.dp))
                    .background(VeduCyan)
                    .testTag("ask_vedu_send_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send",
                    tint = VeduObsidian,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }

    if (showUploadDialog) {
        val sampleDocs = listOf(
            "Q3-Sales-Ledger.csv" to "Revenue, SKU margins & channel breakdown",
            "Vendor-Supply-Contract.pdf" to "Procurement SLAs, penalty & volume pricing",
            "Customer-Feedback-CSAT.json" to "Support transcripts & NPS sentiment vectors",
            "Q4-Marketing-CAC.xlsx" to "Campaign spend, ROAS & CAC analytics"
        )

        AlertDialog(
            onDismissRequest = { showUploadDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CloudUpload, contentDescription = null, tint = VeduCyan)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Attach Enterprise Document", color = VeduTextPrimary)
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Choose an enterprise dataset or document to ingest into Ask VEDU's private contextual memory:",
                        style = MaterialTheme.typography.bodySmall,
                        color = VeduTextSecondary
                    )

                    sampleDocs.forEach { (docName, desc) ->
                        Surface(
                            onClick = {
                                attachedDocName = docName
                                showUploadDialog = false
                            },
                            shape = RoundedCornerShape(8.dp),
                            color = VeduMidnight,
                            border = androidx.compose.foundation.BorderStroke(1.dp, VeduBorder),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.InsertDriveFile,
                                    contentDescription = null,
                                    tint = VeduCyan,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = docName,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = VeduTextPrimary
                                    )
                                    Text(
                                        text = desc,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = VeduTextMuted,
                                        fontSize = 9.sp
                                    )
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showUploadDialog = false }) {
                    Text("Cancel", color = VeduTextSecondary)
                }
            },
            containerColor = VeduSurface
        )
    }
}

@Composable
private fun PromptChip(label: String, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        color = VeduSurfaceLight,
        border = androidx.compose.foundation.BorderStroke(1.dp, VeduBorderSubtle)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = VeduTextCyan,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            fontSize = 9.sp
        )
    }
}

@Composable
private fun MessageBubble(
    message: AskVeduMessage,
    onSimulateClick: () -> Unit,
    onCreateCampaignClick: () -> Unit
) {
    val isUser = message.sender == MessageSender.USER
    val isFirewall = message.sender == MessageSender.SYSTEM_FIREWALL

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        Surface(
            shape = RoundedCornerShape(
                topStart = 12.dp,
                topEnd = 12.dp,
                bottomStart = if (isUser) 12.dp else 2.dp,
                bottomEnd = if (isUser) 2.dp else 12.dp
            ),
            color = when {
                isUser -> VeduCobalt
                isFirewall -> Color(0x33EF4444)
                else -> VeduSurface
            },
            border = if (!isUser) androidx.compose.foundation.BorderStroke(
                1.dp,
                if (isFirewall) VeduCrimson else VeduBorder
            ) else null,
            modifier = Modifier.widthIn(max = 330.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                // Intent badge if from AI
                if (!isUser && !isFirewall) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(message.classification.badgeColorHex).copy(alpha = 0.2f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = message.classification.label.uppercase(),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color(message.classification.badgeColorHex),
                                fontSize = 9.sp
                            )
                        }
                    }
                }

                Text(
                    text = message.text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = VeduTextPrimary
                )

                // Action Chips from screenshot: [Simulate Impact] [Create Campaign] [More Options]
                if (!isUser && (message.classification == IntentClassification.RECOMMENDATION || message.text.contains("Opportunities"))) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Surface(
                            onClick = onSimulateClick,
                            shape = RoundedCornerShape(6.dp),
                            color = VeduSurfaceHighlight,
                            border = androidx.compose.foundation.BorderStroke(1.dp, VeduCyan)
                        ) {
                            Text(
                                text = "Simulate Impact",
                                style = MaterialTheme.typography.labelSmall,
                                color = VeduCyan,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                fontSize = 9.sp
                            )
                        }

                        Surface(
                            onClick = onCreateCampaignClick,
                            shape = RoundedCornerShape(6.dp),
                            color = VeduSurfaceHighlight,
                            border = androidx.compose.foundation.BorderStroke(1.dp, VeduBorder)
                        ) {
                            Text(
                                text = "Create Campaign",
                                style = MaterialTheme.typography.labelSmall,
                                color = VeduTextPrimary,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                fontSize = 9.sp
                            )
                        }
                    }
                }

                // Firewall action notice
                if (message.requiresApproval && message.actionPayload != null) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0x33F59E0B))
                            .padding(6.dp)
                    ) {
                        Text(
                            text = "FIREWALL INTERCEPT: ${message.actionPayload}",
                            style = MaterialTheme.typography.labelSmall,
                            color = VeduAmber,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = message.timestamp,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (isUser) Color(0xFFD0E1FD) else VeduTextMuted,
                    modifier = Modifier.align(Alignment.End),
                    fontSize = 9.sp
                )
            }
        }
    }
}
