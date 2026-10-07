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
import com.example.model.DemandForecastItem
import com.example.model.OpportunityItem
import com.example.model.SimulationScenario
import com.example.ui.theme.*

@Composable
fun DigitalTwinSimulationSection(
    scenario: SimulationScenario,
    opportunities: List<OpportunityItem>,
    demandForecasts: List<DemandForecastItem>,
    onDeltaChange: (Float) -> Unit,
    onRunCycleCompleted: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
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
                    imageVector = Icons.Default.Science,
                    contentDescription = null,
                    tint = VeduPurple,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "BUSINESS DIGITAL TWIN",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = VeduTextPrimary,
                    letterSpacing = 1.sp
                )
            }

            Surface(
                shape = RoundedCornerShape(4.dp),
                color = Color(0x338B5CF6)
            ) {
                Text(
                    text = "AI SANDBOX MODE",
                    style = MaterialTheme.typography.labelSmall,
                    color = VeduPurple,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Simulate strategic business moves before modifying live operations. Predictions are estimated probability models.",
            style = MaterialTheme.typography.bodyMedium,
            color = VeduTextSecondary
        )

        Spacer(modifier = Modifier.height(14.dp))

        // 10-Step VEDU Intelligence Loop Visualizer
        IntelligenceLoopVisualizer(onRunCycleCompleted = onRunCycleCompleted)

        Spacer(modifier = Modifier.height(14.dp))

        // Isometric 3D Digital Twin City visual from screenshot
        DigitalTwinCityCard()

        Spacer(modifier = Modifier.height(14.dp))

        // Scenario: Price Change showcase card from screenshot
        ScenarioPriceChangeCard(
            onViewReport = { },
            onCreateActionPlan = { }
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Interactive Simulation Card
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("simulation_sandbox_card"),
            shape = RoundedCornerShape(12.dp),
            color = VeduSurface,
            border = androidx.compose.foundation.BorderStroke(1.dp, VeduBorder)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Title & Risk pill
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = scenario.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = VeduTextPrimary
                    )

                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = VeduSurfaceLight
                    ) {
                        Text(
                            text = "Confidence: ${scenario.confidencePercent}%",
                            style = MaterialTheme.typography.labelSmall,
                            color = VeduCyan,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = scenario.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = VeduTextSecondary
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Slider control
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = scenario.variableParameter,
                        style = MaterialTheme.typography.labelLarge,
                        color = VeduTextPrimary
                    )
                    Text(
                        text = "${if (scenario.currentDelta > 0) "+" else ""}${scenario.currentDelta.toInt()}%",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = VeduCyan
                    )
                }

                Slider(
                    value = scenario.currentDelta,
                    onValueChange = onDeltaChange,
                    valueRange = scenario.sliderMin..scenario.sliderMax,
                    colors = SliderDefaults.colors(
                        thumbColor = VeduCyan,
                        activeTrackColor = VeduCyan,
                        inactiveTrackColor = VeduSurfaceHighlight
                    ),
                    modifier = Modifier.testTag("simulation_delta_slider")
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Simulated Impact Matrix (Revenue, Cost, Margin)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SimMetric(
                        label = "Revenue Impact",
                        value = "${if (scenario.estimatedRevenueDeltaPercent > 0) "+" else ""}${String.format("%.1f", scenario.estimatedRevenueDeltaPercent)}%",
                        color = if (scenario.estimatedRevenueDeltaPercent >= 0) VeduEmerald else VeduCrimson,
                        modifier = Modifier.weight(1f)
                    )

                    SimMetric(
                        label = "Cost Impact",
                        value = "${if (scenario.estimatedCostDeltaPercent > 0) "+" else ""}${String.format("%.1f", scenario.estimatedCostDeltaPercent)}%",
                        color = VeduAmber,
                        modifier = Modifier.weight(1f)
                    )

                    SimMetric(
                        label = "Margin Expansion",
                        value = "${if (scenario.estimatedMarginDeltaPercent > 0) "+" else ""}${String.format("%.1f", scenario.estimatedMarginDeltaPercent)}%",
                        color = if (scenario.estimatedMarginDeltaPercent >= 0) VeduEmerald else VeduCrimson,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Assumptions & Downside Guard
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(VeduSurfaceLight)
                        .padding(10.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "Model Assumptions & Risks",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = VeduTextPrimary
                        )
                        scenario.primaryAssumptions.forEach {
                            Text(
                                text = "• $it",
                                style = MaterialTheme.typography.labelSmall,
                                color = VeduTextSecondary
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Downside Risk: ${scenario.possibleDownside}",
                            style = MaterialTheme.typography.labelSmall,
                            color = VeduAmber
                        )
                        Text(
                            text = "Upside Potential: ${scenario.possibleUpside}",
                            style = MaterialTheme.typography.labelSmall,
                            color = VeduEmerald
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Opportunity Radar Section
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Radar,
                contentDescription = null,
                tint = VeduCyan,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "BUSINESS OPPORTUNITY RADAR",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = VeduTextPrimary,
                letterSpacing = 1.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            opportunities.forEach { opp ->
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
                                text = opp.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = VeduTextPrimary
                            )
                            Text(
                                text = opp.estimatedImpact,
                                style = MaterialTheme.typography.labelSmall,
                                color = VeduEmerald,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = opp.whyItMatters,
                            style = MaterialTheme.typography.bodyMedium,
                            color = VeduTextSecondary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Evidence: ${opp.evidence}",
                            style = MaterialTheme.typography.labelSmall,
                            color = VeduTextMuted
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Future Demand Radar
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Timeline,
                contentDescription = null,
                tint = VeduBlue,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "FUTURE DEMAND RADAR (ESTIMATES)",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = VeduTextPrimary,
                letterSpacing = 1.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            demandForecasts.forEach { df ->
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = VeduSurface,
                    border = androidx.compose.foundation.BorderStroke(1.dp, VeduBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = df.productCategory,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = VeduTextPrimary
                            )
                            Text(
                                text = "${df.horizon} • ${df.seasonalFactor}",
                                style = MaterialTheme.typography.labelSmall,
                                color = VeduTextSecondary
                            )
                            Text(
                                text = "Recommended Buffer: ${df.recommendedBuffer}",
                                style = MaterialTheme.typography.labelSmall,
                                color = VeduCyan
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = df.expectedDemandTrend,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                color = VeduEmerald
                            )
                            Text(
                                text = df.inventoryRisk,
                                style = MaterialTheme.typography.labelSmall,
                                color = VeduAmber
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SimMetric(
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = VeduSurfaceLight
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = label, style = MaterialTheme.typography.labelSmall, color = VeduTextMuted)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
    }
}
