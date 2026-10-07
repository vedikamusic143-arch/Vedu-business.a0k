package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.model.BusinessProfile
import com.example.ui.theme.*

@Composable
fun OnboardingDialog(
    initialProfile: BusinessProfile,
    onDismiss: () -> Unit,
    onSaveProfile: (String, String, String, String, String, List<String>, String) -> Unit
) {
    var name by remember { mutableStateOf(initialProfile.name) }
    var industry by remember { mutableStateOf(initialProfile.industry) }
    var country by remember { mutableStateOf(initialProfile.country) }
    var currency by remember { mutableStateOf(initialProfile.currency) }
    var size by remember { mutableStateOf(initialProfile.businessSize) }
    var aiPref by remember { mutableStateOf(initialProfile.aiAutomationPreference) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = VeduSurface,
            border = androidx.compose.foundation.BorderStroke(1.dp, VeduBorder),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("onboarding_dialog")
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Business,
                        contentDescription = null,
                        tint = VeduCyan,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "BUSINESS ONBOARDING",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = VeduTextPrimary
                        )
                        Text(
                            text = "Initialize the VEDU Business Brain",
                            style = MaterialTheme.typography.labelSmall,
                            color = VeduTextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Business Name") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = VeduTextPrimary,
                        unfocusedTextColor = VeduTextPrimary,
                        focusedBorderColor = VeduCyan,
                        unfocusedBorderColor = VeduBorder
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = industry,
                    onValueChange = { industry = it },
                    label = { Text("Industry / Sector") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = VeduTextPrimary,
                        unfocusedTextColor = VeduTextPrimary,
                        focusedBorderColor = VeduCyan,
                        unfocusedBorderColor = VeduBorder
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = currency,
                        onValueChange = { currency = it },
                        label = { Text("Currency") },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = VeduTextPrimary,
                            unfocusedTextColor = VeduTextPrimary,
                            focusedBorderColor = VeduCyan,
                            unfocusedBorderColor = VeduBorder
                        ),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = country,
                        onValueChange = { country = it },
                        label = { Text("Country") },
                        modifier = Modifier.weight(2f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = VeduTextPrimary,
                            unfocusedTextColor = VeduTextPrimary,
                            focusedBorderColor = VeduCyan,
                            unfocusedBorderColor = VeduBorder
                        ),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = size,
                    onValueChange = { size = it },
                    label = { Text("Organization Size") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = VeduTextPrimary,
                        unfocusedTextColor = VeduTextPrimary,
                        focusedBorderColor = VeduCyan,
                        unfocusedBorderColor = VeduBorder
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = aiPref,
                    onValueChange = { aiPref = it },
                    label = { Text("AI Automation Preference") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = VeduTextPrimary,
                        unfocusedTextColor = VeduTextPrimary,
                        focusedBorderColor = VeduCyan,
                        unfocusedBorderColor = VeduBorder
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(text = "Cancel", color = VeduTextSecondary)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            onSaveProfile(name, industry, country, currency, size, initialProfile.primaryGoals, aiPref)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = VeduCyan,
                            contentColor = VeduObsidian
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Initialize Brain", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
