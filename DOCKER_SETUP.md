# BDS Application - Docker Setup Guide

## Giới thiệu

Project này sử dụng Docker để chạy các services: MySQL, Redis, và phpMyAdmin.
Application Spring Boot sẽ chạy trên local ở cổng 8080 và kết nối với các services trong Docker.

## Cấu hình các cổng

- **Spring Boot Application**: `http://localhost:8080` (chạy trên local)
- **MySQL**: `localhost:3307` (từ local) / `mysql:3306` (trong Docker network)
- **Redis**: `localhost:6380` (từ local) / `redis:6379` (trong Docker network)
- **phpMyAdmin**: `http://localhost:8081` (truy cập qua browser)

## Thông tin Database

- **Database Name**: bds_db
- **Username**: bds_user
- **Password**: bds_password
- **Root Password**: root123

## Hướng dẫn sử dụng

### 1. Khởi động Docker services

```bash
docker-compose up -d
```

### 2. Kiểm tra trạng thái containers

```bash
docker-compose ps
```

### 3. Xem logs của services

```bash
# Xem tất cả logs
docker-compose logs -f

# Xem logs của MySQL
docker-compose logs -f mysql

# Xem logs của Redis
docker-compose logs -f redis
```

### 4. Chạy Spring Boot Application

```bash
# Sử dụng Maven wrapper
./mvnw spring-boot:run

# Hoặc với PowerShell
.\mvnw.cmd spring-boot:run
```

### 5. Truy cập phpMyAdmin

Mở browser và truy cập: `http://localhost:8081`

- **Server**: mysql
- **Username**: root
- **Password**: root123

Hoặc sử dụng user application:
- **Username**: bds_user
- **Password**: bds_password

### 6. Dừng Docker services

```bash
docker-compose down
```

### 7. Dừng và xóa volumes (xóa dữ liệu)

```bash
docker-compose down -v
```

## Kết nối từ Application

Application đã được cấu hình trong `application.properties`:

- **MySQL**: `jdbc:mysql://localhost:3307/bds_db`
- **Redis**: `localhost:6380`

## Troubleshooting

### Nếu port bị conflict

Bạn có thể thay đổi ports trong file `docker-compose.yml`:

```yaml
ports:
  - "3307:3306"  # Thay 3307 thành port khác
  - "6380:6379"  # Thay 6380 thành port khác
  - "8081:80"    # Thay 8081 thành port khác
```

Nhớ cập nhật lại `application.properties` cho phù hợp.

### Kiểm tra MySQL connection

```bash
docker exec -it bds-mysql mysql -u bds_user -p
# Nhập password: bds_password
```

### Kiểm tra Redis connection

```bash
docker exec -it bds-redis redis-cli
# Test: ping
```

## Lưu ý

- Dữ liệu MySQL và Redis được lưu trong Docker volumes, sẽ không bị mất khi restart containers
- Cần đảm bảo Docker Desktop đang chạy trước khi execute các lệnh docker-compose
- Application chạy trên local nên không cần build Docker image cho Spring Boot app

