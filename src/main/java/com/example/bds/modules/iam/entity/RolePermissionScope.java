package com.example.bds.modules.iam.entity;

public enum RolePermissionScope {
    GLOBAL("Global", "Toàn cục"), // Phạm vi toàn cục
    BRANCH("Branch", "phòng ban, chi nhánh"), // Phạm vi dự án
    TEAM("Team", "Đội nhóm"), // Phạm vi đội nhóm
    OWN("Own", "Cá nhân"); // Phạm vi cá nhân

    private final String code; // Mã phạm vi
    private final String description; // Mô tả phạm vi

    RolePermissionScope(String code, String description) { // Constructor để khởi tạo các trường
        this.code = code; // Gán mã phạm vi
        this.description = description; // Gán mô tả phạm vi
    }

    public String getCode() { // Phương thức lấy mã phạm vi
        return code;
    }

    public String getDescription() { // Phương thức lấy mô tả phạm vi
        return description;
    }
}
