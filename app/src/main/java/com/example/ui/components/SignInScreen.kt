package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.UserRole
import com.example.ui.theme.*

@Composable
fun SignInScreen(
    isSigningIn: Boolean,
    errorMessage: String?,
    onSignInWithGoogle: () -> Unit,
    onContinueGuest: (UserRole) -> Unit,
    modifier: Modifier = Modifier
) {
    var emailInput by remember { mutableStateOf("owner@vedu.ai") }
    var passwordInput by remember { mutableStateOf("Vedu@Owner2026") }
    var passwordVisible by remember { mutableStateOf(false) }
    var selectedRole by remember { mutableStateOf(UserRole.BUSINESS_OWNER) }
    var loginError by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(VeduObsidian)
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Official VEDU 3D Ribbon Logo
            VeduRibbonSymbol(size = 72.dp)

            Spacer(modifier = Modifier.height(12.dp))

            // VEDU Wordmark
            VeduBrandWordmark()

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Intelligence That Moves Business.",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFE2E8F0),
                letterSpacing = 0.5.sp
            )

            Text(
                text = "AI-Powered Business Operating & Trust Platform",
                style = MaterialTheme.typography.labelSmall,
                color = VeduCyan,
                fontSize = 10.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Error notice if any
            val activeError = errorMessage ?: loginError
            if (activeError != null) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0x33EF4444),
                    border = androidx.compose.foundation.BorderStroke(1.dp, VeduCrimson),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = activeError,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFFFFD1D1),
                        modifier = Modifier.padding(10.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Interactive "Sign in with Google" Button
            Button(
                onClick = onSignInWithGoogle,
                enabled = !isSigningIn,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("google_sign_in_button"),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF1F2937)
                )
            ) {
                if (isSigningIn) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = VeduCobalt,
                        strokeWidth = 2.dp
                    )
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "G",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF4285F4)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Sign in with Google",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1F2937)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Divider: OR ENTER CREDENTIALS
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = VeduBorder)
                Text(
                    text = "  OR SELECT ACCESS PASS  ",
                    style = MaterialTheme.typography.labelSmall,
                    color = VeduTextMuted,
                    fontSize = 9.sp
                )
                HorizontalDivider(modifier = Modifier.weight(1f), color = VeduBorder)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 1-TAP DEMO PASS CARDS (Company, Admin, Customer)
            Text(
                text = "⚡ 1-Tap Quick Panel Login",
                style = MaterialTheme.typography.labelSmall,
                color = VeduCyan,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Company / Owner Pass
                QuickPassButton(
                    title = "Company",
                    roleName = "Owner",
                    icon = Icons.Default.BusinessCenter,
                    accent = VeduCyan,
                    isSelected = selectedRole == UserRole.BUSINESS_OWNER,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        selectedRole = UserRole.BUSINESS_OWNER
                        emailInput = "owner@vedu.ai"
                        passwordInput = "Vedu@Owner2026"
                        loginError = null
                        onContinueGuest(UserRole.BUSINESS_OWNER)
                    }
                )

                // Admin Pass
                QuickPassButton(
                    title = "Admin",
                    roleName = "Portal",
                    icon = Icons.Default.AdminPanelSettings,
                    accent = VeduPurple,
                    isSelected = selectedRole == UserRole.ADMIN || selectedRole == UserRole.SUPER_ADMIN,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        selectedRole = UserRole.ADMIN
                        emailInput = "admin@vedu.ai"
                        passwordInput = "Vedu@Admin2026"
                        loginError = null
                        onContinueGuest(UserRole.ADMIN)
                    }
                )

                // Customer Pass
                QuickPassButton(
                    title = "Customer",
                    roleName = "Consumer",
                    icon = Icons.Default.AccountCircle,
                    accent = VeduEmerald,
                    isSelected = selectedRole == UserRole.CONSUMER,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        selectedRole = UserRole.CONSUMER
                        emailInput = "customer@vedu.ai"
                        passwordInput = "Vedu@Customer2026"
                        loginError = null
                        onContinueGuest(UserRole.CONSUMER)
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Manual Credentials Form
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = VeduSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, VeduBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Access ID & Password Sign In",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = VeduTextPrimary
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = emailInput,
                        onValueChange = { emailInput = it },
                        label = { Text("Access ID / Email") },
                        leadingIcon = {
                            Icon(Icons.Default.Email, contentDescription = null, tint = VeduCyan, modifier = Modifier.size(18.dp))
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = VeduMidnight,
                            unfocusedContainerColor = VeduMidnight,
                            focusedBorderColor = VeduCyan,
                            unfocusedBorderColor = VeduBorder,
                            focusedTextColor = VeduTextPrimary,
                            unfocusedTextColor = VeduTextPrimary
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = passwordInput,
                        onValueChange = { passwordInput = it },
                        label = { Text("Password") },
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = null, tint = VeduCyan, modifier = Modifier.size(18.dp))
                        },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = null,
                                    tint = VeduTextSecondary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = VeduMidnight,
                            unfocusedContainerColor = VeduMidnight,
                            focusedBorderColor = VeduCyan,
                            unfocusedBorderColor = VeduBorder,
                            focusedTextColor = VeduTextPrimary,
                            unfocusedTextColor = VeduTextPrimary
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            val role = when {
                                emailInput.contains("admin", ignoreCase = true) -> UserRole.ADMIN
                                emailInput.contains("customer", ignoreCase = true) || emailInput.contains("priya", ignoreCase = true) -> UserRole.CONSUMER
                                emailInput.contains("manager", ignoreCase = true) -> UserRole.MANAGER
                                emailInput.contains("super", ignoreCase = true) -> UserRole.SUPER_ADMIN
                                else -> UserRole.BUSINESS_OWNER
                            }
                            onContinueGuest(role)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("login_submit_btn"),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = VeduCyan,
                            contentColor = VeduObsidian
                        )
                    ) {
                        Text("Log In to Console →", fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Trust Sentinel Badge
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = VeduSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, VeduBorder)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = null,
                        tint = VeduEmerald,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Zero-Trust Architecture • Cloud Firestore • DPDP India 2023",
                        style = MaterialTheme.typography.labelSmall,
                        color = VeduTextSecondary,
                        fontSize = 9.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun QuickPassButton(
    title: String,
    roleName: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    accent: Color,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        color = if (isSelected) accent.copy(alpha = 0.15f) else VeduSurface,
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isSelected) accent else VeduBorder
        ),
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = accent,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = VeduTextPrimary,
                fontSize = 11.sp
            )
            Text(
                text = roleName,
                style = MaterialTheme.typography.labelSmall,
                color = accent,
                fontSize = 9.sp
            )
        }
    }
}
