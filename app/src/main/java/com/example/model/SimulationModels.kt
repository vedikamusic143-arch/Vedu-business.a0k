package com.example.model

data class SimulationScenario(
    val id: String,
    val title: String,
    val description: String,
    val variableParameter: String,
    val sliderMin: Float = -30f,
    val sliderMax: Float = 50f,
    val currentDelta: Float = 10f,
    val baseRevenue: Double = 4250000.0,
    val baseMarginPercent: Double = 34.0,
    val estimatedRevenueDeltaPercent: Float = 8.4f,
    val estimatedCostDeltaPercent: Float = 4.2f,
    val estimatedMarginDeltaPercent: Float = 1.8f,
    val riskLevel: String = "Moderate",
    val confidencePercent: Int = 88,
    val primaryAssumptions: List<String> = listOf(
        "Competitor price elasticity remains steady (±1.5%)",
        "Raw materials procurement costs fixed through Q4",
        "Customer conversion rates sustain within 2.8% to 3.2%"
    ),
    val possibleDownside: String = "Short-term order volume dip of 3-5% for first 14 days",
    val possibleUpside: String = "Net gross margin expansion of ₹3,40,000/mo by Day 45",
    val unknownFactors: String = "Macro festive season demand spikes and shipping carrier surcharges"
)

data class OpportunityItem(
    val id: String,
    val title: String,
    val category: String,
    val whyItMatters: String,
    val evidence: String,
    val estimatedImpact: String,
    val actionLabel: String,
    val confidencePercent: Int = 92
)

data class DemandForecastItem(
    val id: String,
    val productCategory: String,
    val horizon: String,
    val expectedDemandTrend: String, // e.g. "+24% High Surge"
    val seasonalFactor: String,
    val inventoryRisk: String, // e.g. "Stockout likely in 18 days"
    val recommendedBuffer: String
)
