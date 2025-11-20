# 🚀 Scalable Blog Platform with RBAC & JWT Authentication

### Backend Developer Intern Assignment - PrimeTrade AI

This project is a full-stack blog application designed to demonstrate **Scalable Backend Architecture**. It features secure authentication, Role-Based Access Control (RBAC), and a RESTful API built with **Spring Boot**, connected to a **PostgreSQL** database, and integrated with **Cloudinary** for image storage and **Redis** for OTP caching.

---


# Screenshots

## Homepage-featured-post

![Homepage-featured-post](images\homepage.png)

## Homepage-posts
![Homepage-posts](images\homepage2.png)

## user-dashboard
![user-dashboard](images\userdashboard.png)

## admin-dashboard
![admin-dashboard](images\admindashboard.png)



## 🌟 Key Features

### ✅ Backend (Primary Focus)
* **Secure Authentication:**
    * JWT (JSON Web Token) based stateless authentication.
    * BCrypt password hashing.
    * **Two-Step Signup:** Image upload -> Cloudinary URL -> Database.
    * **OTP Verification:** Redis-backed OTP system with email delivery (Brevo API).
* **Role-Based Access Control (RBAC):**
    * **Admin:** Can Create, Edit, and Delete *any* Category or Post.
    * **User:** Can Create, Edit, and Delete *only their own* posts.
    * **Guest:** Can Read posts and categories (Public access).
* **Database Design:**
    * **PostgreSQL** relational schema with Foreign Keys (Users <-> Posts <-> Categories).
    * **Data Seeding:** Automatic seeding of default categories on startup.
* **Scalability & Performance:**
    * **Cloudinary Integration:** Offloads binary image data to a CDN (Content Delivery Network) instead of bloating the database.
    * **Redis Caching:** High-performance caching for short-lived data (OTP codes).
* **Documentation:**
    * Full **Swagger UI** (OpenAPI 3.0) integration for testing APIs.

### ✅ Frontend (Supportive)
* **Tech Stack:** Vanilla JavaScript (ES6+), HTML5, CSS3 (Responsive).
* **Single Page Application (SPA) Logic:**
    * Dynamic content loading without page refreshes.
    * **Dashboard:** Unified interface for managing posts and categories.
    * **Search:** Client-side search filtering.
    * **Toast Notifications:** Custom non-blocking UI alerts.

---

## 🛠️ Tech Stack

| Component | Technology |
| :--- | :--- |
| **Language** | Java 17 |
| **Framework** | Spring Boot 3.x (Web, Security, Data JPA) |
| **Database** | PostgreSQL |
| **Cache** | Redis (Upstash) |
| **File Storage** | Cloudinary |
| **Documentation** | Swagger / OpenAPI 3.0 |
| **Frontend** | HTML, CSS, Vanilla JavaScript |
| **Build Tool** | Maven |

---

## ⚙️ Setup & Installation

### 1. Prerequisites
* Java 17+ SDK installed.
* Maven installed.
* PostgreSQL running locally or via Docker.

### 2. Clone the Repository
```bash
git clone https://github.com/SonarAkash/blog-platform.git
cd blog-platform
```

### 3. Configure Environment
Update src/main/resources/application.properties with your credentials:

```Properties

spring.application.name=assignment

jwt.secret=

jwt.expiration=86400000


spring.datasource.url=
spring.datasource.username=
spring.datasource.password=

spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
spring.sql.init.mode=always
spring.sql.init.continue-on-error=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

# Redis Configuration (Upstash)
spring.data.redis.url=

# Brevo API Key
BREVO_API_KEY=

# App Settings
app.otp.expiration-minutes=5

cloudinary.api.key=
```


4. Run the Application
```bash

mvn spring-boot:run
```
The server will start at http://localhost:8080.


### 📖 API Documentation (Swagger)
Once the server is running, you can access the interactive API documentation here:

👉 http://localhost:8080/swagger-ui/index.html

### How to use Swagger:

* Sign Up or Login via the Auth Controller.

* Copy the token from the response.

* Click the Authorize button (top right) and paste the token.

*  Now you can test protected endpoints like POST /api/posts.

### 🖥️ Frontend Walkthrough
The frontend files are served statically by Spring Boot. Just open: http://localhost:8080

* User Flow:
* Sign Up: Upload an avatar, enter details.
* Verify: Enter the OTP sent to your email (simulated in logs if email API limit reached).
* Login: Use email/password.

* Dashboard:

    Write Post: Upload thumbnail, select category, publish.

* Manage Posts: Edit or Delete your own posts.

* Home: View all posts, filter by search, or click categories.

### 📈 Scalability Note
To ensure this system scales to millions of users, the following architectural decisions were made:

**Stateless Authentication (JWT):**  
The server does not store session data. This allows the application to be easily horizontally scaled (e.g., deploying multiple instances behind a Load Balancer) without worrying about sticky sessions.

**Database Indexing & Relationships:**  
- Foreign Keys maintain data integrity.  
- Future optimization: Add indexes on `category_id` and `author_id` for faster lookups as the `posts` table grows.

**Media Offloading (Cloudinary):**  
Storing images as BLOBs in the database harms performance.  
Cloudinary stores media on a CDN, keeping the DB lean and speeding up API responses and image loading via edge locations.

**Redis for Ephemeral Data:**  
OTP codes are temporary and do not belong in PostgreSQL (slow I/O for short-lived data).  
Redis provides in-memory sub-millisecond access with automatic TTL expiration.

---

### 👤 Author
**Akash Sonar**
 
**Email:** akashsonar.9113@gmail.com  

Built for **PrimeTrade AI Backend Developer Internship Assignment**.
