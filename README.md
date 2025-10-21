# 🛍️ E-commerce Microservices Project

Dự án này là một hệ thống **thương mại điện tử** đơn giản, được xây dựng theo kiến trúc **Microservices** để xử lý quy trình **đặt hàng sản phẩm** cơ bản.

---

## 💡 Tổng quan Kiến trúc

Hệ thống được chia thành các dịch vụ độc lập, giao tiếp với nhau qua **REST API**. Các dịch vụ được quản lý và cấu hình tập trung để đảm bảo tính linh hoạt và khả năng mở rộng.



---

## 🛠️ Công nghệ & Thành phần Chính

Dự án sử dụng bộ công nghệ chủ yếu là **Spring Cloud** kết hợp với **Java 17** để xây dựng các Microservices.

| Thành phần | Công nghệ/Mục đích | Chi tiết |
| :--- | :--- | :--- |
| **Backend Framework** | **Spring Boot** (Java 17) | Xây dựng các Microservices độc lập. |
| **Database** | **MySQL** | Cơ sở dữ liệu quan hệ cho các dịch vụ. |
| **Service Discovery** | **Eureka Server** | Cho phép các dịch vụ tự đăng ký và tìm kiếm lẫn nhau. |
| **Configuration** | **Spring Cloud Config Server** | Quản lý cấu hình tập trung cho tất cả các dịch vụ. |
| **API Gateway** | **Spring Cloud Gateway** | Cổng vào duy nhất cho tất cả các request từ client, xử lý định tuyến và bảo mật. |
| **Giao tiếp Inter-Service** | **Feign Client** | Giao tiếp đồng bộ giữa các Microservices bằng cách sử dụng RESTful Web Service. |
| **Security** | **JWT** (JSON Web Token) | Xác thực và ủy quyền người dùng. |
| **Giao tiếp API** | **REST API** | Phương thức giao tiếp chính giữa các dịch vụ và giữa Gateway với Client. |
| **Chịu lỗi (Fault Tolerance)** | **Resilience4j** (Circuit Breaker) | Triển khai cơ chế **Circuit Breaker** để tăng tính ổn định và khả năng chịu lỗi của hệ thống. |

---

## ✅ Các Chức năng Cốt lõi

| Service | Endpoint | Chức năng chi tiết |
| :--- | :--- | :--- |
| **User Service** | `/api/users/register`, `/api/users/login` | Đăng ký, Đăng nhập, **Tạo/Kiểm tra JWT**. |
| **Product Service** | `/api/products`, `/api/products/{id}` | **CRUD** sản phẩm |
| **Order Service** | `/api/orders` (POST), `/api/orders/{id}`, `/api/orders/{id}/order_items` | **Tạo đơn hàng**, **Lấy đơn hàng**. Liên kết người dùng (User Service) và tồn kho (Product Service). |

---

## 🧩 Các Microservices

Dự án bao gồm 3 Microservices cốt lõi để thực hiện quy trình đặt hàng:

1.  **User Service**
    * Quản lý thông tin người dùng (Đăng ký, Đăng nhập).
    * Cung cấp API liên quan đến xác thực (**JWT** generation/validation).

2.  **Product Service**
    * Quản lý thông tin sản phẩm (Tạo, Xem, Cập nhật, Xóa).
    * Quản lý số lượng tồn kho của sản phẩm.

3.  **Order Service**
    * Xử lý logic đặt hàng (Tạo đơn hàng mới).
    * Sử dụng **Feign Client** để gọi đến **Product Service** (kiểm tra tồn kho và giảm số lượng) và **User Service** (lấy thông tin người đặt).
    * Áp dụng **Circuit Breaker** (**Resilience4j**) khi gọi các dịch vụ khác.

---

## 🚀 Cách Thức Hoạt động (Quy trình Đặt hàng)

1.  **Client** gửi yêu cầu Đặt hàng đến **API Gateway**.
2.  **API Gateway** (sau khi xác thực **JWT**) định tuyến yêu cầu đến **Order Service**.
3.  **Order Service** sử dụng **Feign Client** để:
    * Gọi **User Service** để lấy chi tiết người dùng.
    * Gọi **Product Service** để kiểm tra và trừ tồn kho.
    * Nếu **Product Service** không khả dụng, cơ chế **Circuit Breaker** của **Resilience4j** sẽ kích hoạt, có thể trả về một phản hồi dự phòng (**fallback**).
4.  Sau khi xác nhận, **Order Service** lưu thông tin đơn hàng vào DB và trả về kết quả cho Client qua **API Gateway**.

---

## ⚙️ Thiết lập và Chạy Dự án

### Yêu cầu Hệ thống

* **Java 17** trở lên.
* **Maven** 3.x.
* **MySQL** Database.

### Cấu trúc Thư mục
```
ecommerce-microservices/ 
├── config-server/ 
├── eureka-server/ 
├── api-gateway/ 
├── user-service/ 
├── product-service/ 
├── order-service/ 
└── README.md
```
### Cấu hình Database
Tạo các cơ sở dữ liệu (schema) cần thiết cho mỗi service: `user_db`, `product_db`, `order_db`.

### Các Bước Triển khai

1.  **Khởi động Config Server & Eureka Server:**
    * Clone các repository của `config-server` và `eureka-server`.
    * Chạy cả hai server để đảm bảo các dịch vụ khác có thể tìm thấy cấu hình và đăng ký.
2.  **Khởi tạo Database:**
    * Tạo các schema MySQL cần thiết cho User, Product, và Order services.
    * Cấu hình thông tin kết nối DB trong `config-server` hoặc trong file cấu hình cục bộ (`application.yml`).
3.  **Khởi chạy các Microservices:**
    * Lần lượt khởi động `user-service`, `product-service`, và `order-service`.
4.  **Khởi chạy API Gateway:**
    * Khởi động `api-gateway` để bắt đầu nhận request từ Client.

### Cổng Mặc định

| Dịch vụ | Cổng |
| :--- | :--- |
| **Eureka Server** | `8761` |
| **Config Server** | `8888` |
| **API Gateway** | `8080` |
| **Product Service** | `8081` |
| **User Service** | `8082` |
| **Order Service** | `8083` |

---

## 📌 Ghi chú Phát triển
* Dự án sử dụng phiên bản Spring Boot 3+ (tương thích với Java 17) và Spring Cloud 2022+.
* Để kiểm tra hệ thống dễ dàng, nên sử dụng Postman Collection (nếu có) để gọi các API qua API Gateway (http://localhost:8080/).
---

## 🤝 Đóng góp

Mọi đóng góp (pull requests) đều được hoan nghênh. Vui lòng tạo một issue trước nếu bạn muốn đề xuất thay đổi lớn.

---

## 📝 Giấy phép

N/A.
