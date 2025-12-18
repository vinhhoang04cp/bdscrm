package com.example.bds.modules.iam.entity;

public enum PermissionModule {
    USER("User", "Quản lý người dùng"),
    ROLE("Role", "Quản lý vai trò"),
    PERMISSION("Permission", "Quản lý quyền"),

    PROPERTY("Property", "Quản lý bất động sản"),
    PROPERTY_TYPE("PropertyType", "Quản lý loại BDS"),

    CUSTOMER("Customer", "Quản lý khách hàng"),
    LEAD("Lead", "Quản lý khách hàng tiềm năng"),

    PROJECT("Project", "Quản lý dự án"),

    CONTRACT("Contract", "Quản lý hợp đồng"),
    TRANSACTION("Transaction", "Quản lý giao dịch"),
    PAYMENT("Payment", "Quản lý thanh toán"),

    APPOINTMENT("Appointment", "Quản lý lịch hẹn"),
    TASK("Task", "Quản lý công việc"),

    REPORT("Report", "Quản lý báo cáo"),
    DASHBOARD("Dashboard", "Quản lý dashboard"),

    NOTIFICATION("Notification", "Quản lý thông báo"),
    SETTING("Setting", "Cấu hình hệ thống");

    private final String code;
    private final String description;

    PermissionModule(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
