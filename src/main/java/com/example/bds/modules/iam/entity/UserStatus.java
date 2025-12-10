package com.example.bds.modules.iam.entity;

public enum UserStatus {
    ACTIVE("Active", "Đang hoạt động"), // Định nghĩa trạng thái "Đang hoạt động"
    INACTIVE("Inactive", "Ngừng hoạt động"); // Định nghĩa trạng thái "Ngừng hoạt động"

    private final String code; // Mã trạng thái
    private final String description; // Mô tả trạng thái

    UserStatus(String code, String description) { // Constructor để khởi tạo các trường
        this.code = code; // Gán mã trạng thái
        this.description = description; // Gán mô tả trạng thái
    }

    public String getCode() { // Phương thức lấy mã trạng thái
        return code;
    }

    public String getDescription() { // Phương thức lấy mô tả trạng thái
        return description;
    }
}
