package com.example.model

enum class UserRole(val label: String, val subtitle: String) {
    BUSINESS_OWNER("Business Owner", "Full control & strategic governance"),
    ADMIN("Administrator", "Operations, team & system settings"),
    MANAGER("Manager", "Team workflows & department analytics"),
    EMPLOYEE("Employee", "Assigned tasks & execution scope"),
    CONSUMER("Consumer", "Personal VEDU, Vault & Privacy Center"),
    SUPER_ADMIN("Super Admin", "Multi-tenant platform & system architecture");

    val isBusinessRole: Boolean
        get() = this in listOf(BUSINESS_OWNER, ADMIN, MANAGER, EMPLOYEE)

    val canManageKillSwitch: Boolean
        get() = this in listOf(BUSINESS_OWNER, ADMIN, SUPER_ADMIN)

    val canApproveHighRisk: Boolean
        get() = this in listOf(BUSINESS_OWNER, SUPER_ADMIN)
}
