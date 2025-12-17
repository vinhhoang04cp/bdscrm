# Authentication API Documentation

## Tổng quan
Module Authentication cung cấp các API để xác thực và quản lý tài khoản người dùng với JWT token.

## Cấu hình

### 1. JWT Configuration (application.properties)
```properties
# JWT Secret Key (nên dùng key mạnh hơn trong production)
jwt.secret=your-secret-key-here-minimum-256-bits-replace-with-strong-key
# JWT Token expiration (86400000ms = 24 giờ)
jwt.expiration=86400000
```

### 2. Email Configuration (application.properties)
```properties
# Gmail SMTP
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

**Lưu ý**: Để sử dụng Gmail, bạn cần:
1. Bật 2-Step Verification trong Google Account
2. Tạo App Password tại: https://myaccount.google.com/apppasswords
3. Sử dụng App Password thay vì password thật

## API Endpoints

### 1. Đăng ký (Register)
**POST** `/api/auth/register`

**Request Body:**
```json
{
  "username": "johndoe",
  "email": "john@example.com",
  "password": "password123",
  "fullName": "John Doe",
  "phone": "0123456789",
  "address": "123 Main St"
}
```

**Response (201 Created):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "userId": 1,
  "username": "johndoe",
  "email": "john@example.com",
  "fullName": "John Doe"
}
```

**Lỗi có thể gặp:**
- 400: Username/Email/Phone đã tồn tại

### 2. Đăng nhập (Login)
**POST** `/api/auth/login`

**Request Body:**
```json
{
  "usernameOrEmail": "johndoe",
  "password": "password123"
}
```
Hoặc dùng email:
```json
{
  "usernameOrEmail": "john@example.com",
  "password": "password123"
}
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "userId": 1,
  "username": "johndoe",
  "email": "john@example.com",
  "fullName": "John Doe"
}
```

**Lỗi có thể gặp:**
- 401: Username/Email hoặc mật khẩu không chính xác
- 400: Tài khoản đã bị khóa hoặc không hoạt động

### 3. Quên mật khẩu (Forgot Password)
**POST** `/api/auth/forgot-password`

**Request Body:**
```json
{
  "email": "john@example.com"
}
```

**Response (200 OK):**
```json
{
  "message": "Email reset mật khẩu đã được gửi"
}
```

**Lưu ý:**
- Email sẽ chứa link reset password với token
- Token có hiệu lực 24 giờ
- Link format: `http://localhost:8080/api/auth/reset-password?token=xxx`

### 4. Reset mật khẩu (Reset Password)
**POST** `/api/auth/reset-password`

**Request Body:**
```json
{
  "token": "abc123-token-from-email",
  "newPassword": "newpassword123",
  "confirmPassword": "newpassword123"
}
```

**Response (200 OK):**
```json
{
  "message": "Mật khẩu đã được reset thành công"
}
```

**Lỗi có thể gặp:**
- 400: Token không hợp lệ
- 400: Token đã được sử dụng
- 400: Token đã hết hạn
- 400: Mật khẩu xác nhận không khớp

## Sử dụng JWT Token

### Gửi request với JWT Token
Sau khi đăng nhập/đăng ký thành công, bạn nhận được JWT token. Để truy cập các API bảo mật, thêm token vào header:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### Ví dụ với cURL:
```bash
curl -X GET http://localhost:8080/api/users \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

### Ví dụ với JavaScript (Fetch API):
```javascript
fetch('http://localhost:8080/api/users', {
  method: 'GET',
  headers: {
    'Authorization': 'Bearer ' + token,
    'Content-Type': 'application/json'
  }
})
```

## Database Schema

### Table: password_reset_tokens
```sql
CREATE TABLE password_reset_tokens (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  token VARCHAR(255) NOT NULL UNIQUE,
  user_id BIGINT NOT NULL,
  expiry_date DATETIME NOT NULL,
  used BOOLEAN DEFAULT FALSE,
  created_at DATETIME NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id)
);
```

## Security Configuration

### Public Endpoints (không cần token):
- `/api/auth/**` - Tất cả auth endpoints
- `/swagger-ui/**` - Swagger UI
- `/v3/api-docs/**` - API Documentation

### Protected Endpoints (cần token):
- Tất cả các endpoint khác

## Best Practices

### 1. Bảo mật JWT Secret
- Không hardcode secret trong code
- Sử dụng biến môi trường trong production
- Secret phải có ít nhất 256 bits (32 characters)

### 2. Token Expiration
- Set thời gian hợp lý (24 giờ cho development)
- Production nên ngắn hơn (1-2 giờ) và implement refresh token

### 3. Password Security
- Sử dụng BCrypt để hash password
- Yêu cầu password mạnh (ít nhất 6 ký tự, tốt nhất là 8+)
- Implement password complexity rules

### 4. Rate Limiting
- Implement rate limiting cho login/register endpoints
- Tránh brute force attacks

### 5. Email Security
- Sử dụng App Password cho Gmail
- Không lưu password trong plain text
- Consider using email service providers (SendGrid, Mailgun, etc.)

## Testing với Swagger UI

1. Truy cập: http://localhost:8080/swagger-ui.html
2. Tìm section "Authentication"
3. Test các API:
   - Register user mới
   - Login để lấy token
   - Copy token
   - Click "Authorize" button ở góc trên bên phải
   - Paste token vào (format: `Bearer YOUR_TOKEN`)
   - Bây giờ bạn có thể test các protected APIs

## Troubleshooting

### Lỗi: "Could not autowire PasswordEncoder"
- Đảm bảo SecurityConfig đã được tạo và có @Configuration
- Check Bean PasswordEncoder đã được define

### Lỗi: "Communications link failure" với email
- Check email configuration trong application.properties
- Verify Gmail App Password
- Check firewall/antivirus không block port 587

### Lỗi: "Token expired"
- Token JWT có thời gian hết hạn (default 24h)
- User cần login lại để lấy token mới

### Lỗi: 403 Forbidden
- Đảm bảo đã gửi JWT token trong header
- Check token format: `Bearer YOUR_TOKEN`
- Verify token chưa hết hạn

