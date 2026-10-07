package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.*
import com.example.ui.theme.*

@Composable
fun ConsumerView(
    profile: ConsumerProfile,
    vaultItems: List<ConsumerVaultItem>,
    consents: List<ConsumerConsentItem>,
    benefits: List<ConsumerBenefitItem>,
    securityAlerts: List<ConsumerSecurityShieldAlert>,
    onUpdateConsent: (String, ConsentStatus) -> Unit,
    onClaimBenefit: (String) -> Unit,
    onAskPersonalVedu: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var activeConsumerTab by remember { mutableStateOf("VAULT") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(VeduObsidian)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Consumer Hero Header: "Hi Priya - Your journey, your control."
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    VeduRibbonSymbol(size = 28.dp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Hi ${profile.name.split(" ").firstOrNull() ?: "Priya"}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = VeduTextPrimary
                    )
                }
                Text(
                    text = "Your journey, your control.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = VeduTextSecondary
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0x3310B981))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "${profile.loyaltyPoints} PTS",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = VeduEmerald
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // My Orders Purple Gradient Card from screenshot: "My Orders - 2 Active Orders [Track →]"
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            color = Color.Transparent
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color(0xFF6366F1), Color(0xFF8B5CF6), Color(0xFFA855F7))
                        )
                    )
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0x33FFFFFF)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingBag,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = "My Orders",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "${profile.activeOrdersCount} Active Orders",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFFE2E8F0)
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Color(0x33FFFFFF)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "Track",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 4-Card Quick Action Grid: Rewards, Offers, Support, Your Data Control
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ConsumerMiniCard(
                title = "Rewards",
                subtitle = "Available Points",
                highlight = "1,250",
                icon = Icons.Default.CardGiftcard,
                accentColor = Color(0xFFF59E0B),
                modifier = Modifier.weight(1f)
            )
            ConsumerMiniCard(
                title = "Offers",
                subtitle = "Save up to",
                highlight = "20%",
                icon = Icons.Default.LocalOffer,
                accentColor = Color(0xFF38BDF8),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ConsumerMiniCard(
                title = "Support",
                subtitle = "Customer Care",
                highlight = "Get Help",
                icon = Icons.Default.HeadsetMic,
                accentColor = Color(0xFF10B981),
                modifier = Modifier.weight(1f)
            )
            ConsumerMiniCard(
                title = "Your Data Control",
                subtitle = "You control data & privacy",
                highlight = "Manage →",
                icon = Icons.Default.Lock,
                accentColor = VeduCyan,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Personal AI Bar from screenshot: "Personal AI - Ask me anything..."
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            color = VeduSurface,
            border = androidx.compose.foundation.BorderStroke(1.dp, VeduBorder)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAskPersonalVedu("Check my active orders and warranty status") }
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0x33A855F7)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.SmartToy,
                            contentDescription = null,
                            tint = Color(0xFFA855F7),
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = "Personal AI",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = VeduTextPrimary
                        )
                        Text(
                            text = "Ask me anything about your orders or warranty...",
                            style = MaterialTheme.typography.labelSmall,
                            color = VeduTextMuted
                        )
                    }
                }

                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = "Voice",
                    tint = VeduCyan,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Navigation pills
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            TabPill("DIGITAL VAULT (${vaultItems.size})", activeConsumerTab == "VAULT") { activeConsumerTab = "VAULT" }
            TabPill("MY DATA & PRIVACY", activeConsumerTab == "PRIVACY") { activeConsumerTab = "PRIVACY" }
            TabPill("BENEFITS", activeConsumerTab == "BENEFITS") { activeConsumerTab = "BENEFITS" }
            TabPill("SECURITY SHIELD", activeConsumerTab == "SHIELD") { activeConsumerTab = "SHIELD" }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Tab Content
        when (activeConsumerTab) {
            "VAULT" -> {
                DigitalVaultSection(vaultItems)
            }
            "PRIVACY" -> {
                ConsumerPrivacySection(consents, onUpdateConsent)
            }
            "BENEFITS" -> {
                PersonalBenefitsSection(benefits, onClaimBenefit)
            }
            "SHIELD" -> {
                SecurityShieldSection(securityAlerts)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun ConsumerMiniCard(
    title: String,
    subtitle: String,
    highlight: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        color = VeduSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, VeduBorder)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = VeduTextPrimary)
                Icon(imageVector = icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(16.dp))
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = subtitle, style = MaterialTheme.typography.labelSmall, color = VeduTextMuted, fontSize = 9.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = highlight, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = accentColor)
        }
    }
}

@Composable
private fun TabPill(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(6.dp),
        color = if (isSelected) VeduSurfaceHighlight else VeduSurfaceLight,
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isSelected) VeduCyan else VeduBorderSubtle
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
private fun DigitalVaultSection(items: List<ConsumerVaultItem>) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "DIGITAL VAULT (VERIFIED DOCS & WARRANTIES)",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = VeduTextPrimary
            )
            Surface(
                shape = RoundedCornerShape(4.dp),
                color = VeduSurfaceLight
            ) {
                Text(
                    text = "View All Documents →",
                    style = MaterialTheme.typography.labelSmall,
                    color = VeduCyan,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }

        items.forEach { doc ->
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = VeduSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, VeduBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("vault_item_${doc.id}")
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = doc.docType.label,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = VeduCyan
                        )

                        if (doc.expiryDaysRemaining != null) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color(0x33F59E0B))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "Warranty expires in ${doc.expiryDaysRemaining} days",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = VeduAmber,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = doc.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = VeduTextPrimary
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "Merchant: ${doc.merchant} • Issued: ${doc.dateIssued}",
                        style = MaterialTheme.typography.labelSmall,
                        color = VeduTextSecondary
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = doc.coverageSummary,
                        style = MaterialTheme.typography.bodyMedium,
                        color = VeduTextSecondary
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Verified Hash: ${doc.verifiedHash}",
                        style = MaterialTheme.typography.labelSmall,
                        color = VeduTextMuted
                    )
                }
            }
        }
    }
}

@Composable
private fun ConsumerPrivacySection(
    consents: List<ConsumerConsentItem>,
    onUpdateConsent: (String, ConsentStatus) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = "MY DATA: PRIVACY & PURPOSE LIMITATION",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = VeduTextPrimary
        )

        consents.forEach { consent ->
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = VeduSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, VeduBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = consent.categoryName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = VeduTextPrimary
                        )

                        // Toggle status
                        Surface(
                            onClick = {
                                val nextStatus = if (consent.status == ConsentStatus.ALLOWED) ConsentStatus.RESTRICTED else ConsentStatus.ALLOWED
                                onUpdateConsent(consent.id, nextStatus)
                            },
                            shape = RoundedCornerShape(4.dp),
                            color = if (consent.status == ConsentStatus.ALLOWED) Color(0x3310B981) else Color(0x33EF4444)
                        ) {
                            Text(
                                text = consent.status.name,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (consent.status == ConsentStatus.ALLOWED) VeduEmerald else VeduCrimson,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Data: ${consent.dataType}",
                        style = MaterialTheme.typography.labelSmall,
                        color = VeduCyan
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "Purpose: ${consent.purpose}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = VeduTextSecondary
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Access Scope: ${consent.whoCanAccess} • Retention: ${consent.retentionPeriod}",
                        style = MaterialTheme.typography.labelSmall,
                        color = VeduTextMuted
                    )
                }
            }
        }
    }
}

@Composable
private fun PersonalBenefitsSection(
    benefits: List<ConsumerBenefitItem>,
    onClaim: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = "PERSONAL BENEFIT ENGINE (EXPLAINABLE REWARDS)",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = VeduTextPrimary
        )

        benefits.forEach { ben ->
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = VeduSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, VeduBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = ben.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = VeduTextPrimary
                        )
                        Text(
                            text = ben.value,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = VeduEmerald
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Why You're Eligible: ${ben.whyEligible}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = VeduTextSecondary
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = ben.expiryNote,
                        style = MaterialTheme.typography.labelSmall,
                        color = VeduTextMuted
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = { onClaim(ben.id) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (ben.isClaimed) VeduSurfaceHighlight else VeduEmerald,
                            contentColor = if (ben.isClaimed) VeduTextMuted else VeduObsidian
                        ),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(
                            text = if (ben.isClaimed) "Claimed" else "Claim Benefit",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SecurityShieldSection(alerts: List<ConsumerSecurityShieldAlert>) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = "CONSUMER SECURITY SHIELD (SUSPICIOUS ACTIVITY RADAR)",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = VeduCrimson
        )

        alerts.forEach { alert ->
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = VeduSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, VeduCrimson),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = alert.riskClassification,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = VeduCrimson
                        )
                        Text(
                            text = alert.timestamp,
                            style = MaterialTheme.typography.labelSmall,
                            color = VeduTextMuted
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Sender: ${alert.senderIdentifier}",
                        style = MaterialTheme.typography.labelSmall,
                        color = VeduTextPrimary,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "\"${alert.messageSnippet}\"",
                        style = MaterialTheme.typography.bodyMedium,
                        color = VeduAmber
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Shield Guidance: ${alert.recommendedAction}",
                        style = MaterialTheme.typography.labelSmall,
                        color = VeduTextSecondary
                    )
                }
            }
        }
    }
}
