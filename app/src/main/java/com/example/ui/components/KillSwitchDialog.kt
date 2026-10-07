package com.example.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.*

@Composable
fun KillSwitchDialog(
    isCurrentlyActive: Boolean,
    onDismiss: () -> Unit,
    onConfirmKill: (String) -> Unit,
    onResume: () -> Unit
) {
    var reason by remember { mutableStateOf("Manual emergency override initiated by administrator.") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = VeduSurface,
            border = androidx.compose.foundation.BorderStroke(1.dp, VeduCrimson),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("kill_switch_dialog")
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = VeduCrimson,
                        modifier = Modifier.size(26.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isCurrentlyActive) "DISENGAGE KILL SWITCH" else "ENGAGE GLOBAL KILL SWITCH",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = VeduCrimson
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = if (isCurrentlyActive)
                        "Disengaging will reactivate background AI agents, automated recommendations, and the execution loop. Proceed with caution."
                    else
                        "CRITICAL SAFETY OVERRIDE: Engaging the Global AI Kill Switch will immediately halt all autonomous agent loops, cancel pending non-human approved executions, and freeze model completions across your business organization.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = VeduTextSecondary
                )

                if (!isCurrentlyActive) {
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = reason,
                        onValueChange = { reason = it },
                        label = { Text("Audit Reason") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = VeduTextPrimary,
                            unfocusedTextColor = VeduTextPrimary,
                            focusedBorderColor = VeduCrimson,
                            unfocusedBorderColor = VeduBorder
                        ),
                        maxLines = 2
                    )
                }

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
                            if (isCurrentlyActive) onResume() else onConfirmKill(reason)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isCurrentlyActive) VeduEmerald else VeduCrimson,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = if (isCurrentlyActive) "Resume AI Systems" else "STOP ALL AI ACTIONS",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
