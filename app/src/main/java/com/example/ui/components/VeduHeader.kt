package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import com.example.model.UserRole
import com.example.ui.theme.*

@Composable
fun VeduHeader(
    businessName: String,
    currentRole: UserRole,
    isDemoMode: Boolean,
    isKillSwitchActive: Boolean,
    onRoleSelected: (UserRole) -> Unit,
    onKillSwitchClicked: () -> Unit,
    onToggleDemoMode: () -> Unit,
    onOpenOnboarding: () -> Unit,
    onSignOut: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var showRoleMenu by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(VeduObsidian)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Top row: Brand & Kill Switch & Role
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Brand emblem & name with official 3D ribbon symbol
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.testTag("brand_header")
            ) {
                VeduRibbonSymbol(size = 36.dp)

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        VeduBrandWordmark()

                        Spacer(modifier = Modifier.width(8.dp))

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(if (isDemoMode) Color(0x333B82F6) else Color(0x3310B981))
                                .clickable { onToggleDemoMode() }
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                .testTag("demo_mode_badge")
                        ) {
                            Text(
                                text = if (isDemoMode) "DEMO DATA" else "LIVE DATA",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isDemoMode) VeduTextCyan else VeduEmerald,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Text(
                        text = "Intelligence That Moves Business.",
                        style = MaterialTheme.typography.labelSmall,
                        color = VeduTextSecondary,
                        fontSize = 9.sp
                    )
                }
            }

            // Kill Switch & Role Chip
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Kill switch button
                Button(
                    onClick = onKillSwitchClicked,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isKillSwitchActive) VeduCrimson else Color(0x33EF4444)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                    modifier = Modifier
                        .height(34.dp)
                        .testTag("kill_switch_button")
                ) {
                    Icon(
                        imageVector = if (isKillSwitchActive) Icons.Default.Warning else Icons.Default.PowerSettingsNew,
                        contentDescription = "Global Kill Switch",
                        tint = if (isKillSwitchActive) Color.White else VeduCrimson,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (isKillSwitchActive) "KILL SWITCH ON" else "STOP AI",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (isKillSwitchActive) Color.White else VeduCrimson
                    )
                }

                // Active Role Pill
                Box {
                    Surface(
                        onClick = { showRoleMenu = true },
                        shape = RoundedCornerShape(8.dp),
                        color = VeduSurfaceLight,
                        border = androidx.compose.foundation.BorderStroke(1.dp, VeduBorder),
                        modifier = Modifier
                            .height(34.dp)
                            .testTag("role_selector_button")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        ) {
                            Icon(
                                imageVector = when (currentRole) {
                                    UserRole.BUSINESS_OWNER -> Icons.Default.BusinessCenter
                                    UserRole.ADMIN -> Icons.Default.AdminPanelSettings
                                    UserRole.MANAGER -> Icons.Default.SupervisedUserCircle
                                    UserRole.EMPLOYEE -> Icons.Default.Person
                                    UserRole.CONSUMER -> Icons.Default.AccountCircle
                                    UserRole.SUPER_ADMIN -> Icons.Default.Security
                                },
                                contentDescription = null,
                                tint = VeduCyan,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = currentRole.label,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = VeduTextPrimary
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = VeduTextSecondary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    DropdownMenu(
                        expanded = showRoleMenu,
                        onDismissRequest = { showRoleMenu = false },
                        modifier = Modifier.background(VeduSurfaceHighlight)
                    ) {
                        UserRole.entries.forEach { role ->
                            DropdownMenuItem(
                                text = {
                                    Column {
                                        Text(
                                            text = role.label,
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = if (role == currentRole) FontWeight.Bold else FontWeight.Normal,
                                            color = if (role == currentRole) VeduCyan else VeduTextPrimary
                                        )
                                        Text(
                                            text = role.subtitle,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = VeduTextMuted
                                        )
                                    }
                                },
                                onClick = {
                                    onRoleSelected(role)
                                    showRoleMenu = false
                                }
                            )
                        }
                        if (onSignOut != null) {
                            HorizontalDivider(color = VeduBorder)
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = "Sign Out",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = VeduCrimson
                                    )
                                },
                                onClick = {
                                    showRoleMenu = false
                                    onSignOut()
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Logout,
                                        contentDescription = null,
                                        tint = VeduCrimson,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }

        // Active Kill switch alert banner if triggered
        if (isKillSwitchActive) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0x44EF4444))
                    .border(1.dp, VeduCrimson, RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = VeduCrimson,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "GLOBAL AI KILL SWITCH ENGAGED",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "All model generations and agent execution queues are frozen. Click STOP AI to disengage.",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFFFFD1D1)
                    )
                }
            }
        }
    }
}
