# 🏥 Hospital Management System (HMS) - Robust RESTful Backend API

[![Java Version](https://img.shields.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![Spring Boot](https://img.shields.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Database](https://img.shields.shields.io/badge/Database-MySQL%20%2F%20Clever%20Cloud-blue.svg)](https://www.mysql.com/)
[![License](https://img.shields.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Live API Status](https://img.shields.shields.io/badge/API-Live%20&%20Running-success.svg)](https://hms-backend-1f3e.onrender.com)

Welcome to the backend engine of the **Hospital Management System (HMS)**. This repository houses the highly scalable, enterprise-grade RESTful API built with **Java 17**, **Spring Boot**, **Spring Data JPA**, and **Spring Security (JWT)**. It serves as the single source of truth for patients, medical professionals, and administrators, orchestrating clinic scheduling, billing, diagnostics, and secure role-based portals.

🔗 **Live API URL**: [https://hms-backend-1f3e.onrender.com](https://hms-backend-1f3e.onrender.com)  
🔗 **Live Frontend App**: [https://hms-frontend-78ssc5dxt-kopals-projects.vercel.app/](https://hms-frontend-78ssc5dxt-kopals-projects.vercel.app/)

---

## 🛠️ Architecture & Technology Stack

The backend follows clean architecture principles using a controller-service-repository-model pattern to ensure separation of concerns and ease of maintenance:

*   **Core Framework**: Spring Boot 3.x
*   **Security & Auth**: Spring Security & stateless JSON Web Tokens (JWT)
*   **Persistence Layer**: Spring Data JPA / Hibernate ORM
*   **Database**: Production MySQL (hosted on Clever Cloud free tier)
*   **Build & Dependency Tool**: Apache Maven 3.x
*   **Deployment & Containerization**: Multi-stage Docker deployment optimized for Render runtime

---

## 🔒 Security Configuration & Performance Tuning

### Role-Based Access Control (RBAC)
Security is implemented using custom stateless JWT filters. The system defines three roles with granular endpoint permissions:
*   `ROLE_ADMIN`: Full access to the hospital registry, database seeding, invoicing, doctor schedules, and billing models.
*   `ROLE_STAFF`: Manage patient entries, schedule appointments, and coordinate consult sheets.
*   `ROLE_PATIENT`: Access personal profiles, view medical logs, schedule personal consultations, and pay invoices.

### Clever Cloud Connection Pool Optimization
To host the database for free, this project leverages a Clever Cloud MySQL add-on which enforces a **strict limit of 5 concurrent database connections**. 
By default, Spring Boot's HikariCP pool requests 10 connections, leading to startup crashes. To solve this, the pool configuration was dynamically throttled:
```properties
spring.datasource.hikari.maximum-pool-size=3
```
This safely limits the connection pool size to 3 while maintaining high-performance, concurrent application throughput without database starvation.

---

## 📂 Database Entity Relationship (ER) Schema

The database model consists of highly normalized relational entities:
*   `AppUser`: Security identity mapping login credentials to roles.
*   `Patient`: Profile records, contact details, and vital demographics.
*   `Doctor`: Medical registry, specialties, and consult details.
*   `Appointment`: Connects patients, doctors, schedules, and active statuses.
*   `Prescription` & `Medicine`: Tracks pharmaceutical logs and specific medicines.
*   `LabReport` & `LabTest`: Diagnostics logs and diagnostic categories.
*   `Billing` & `Payment`: Financial ledger tracking invoices, revenue, and pay histories.

---

## 🛣️ API Endpoints Reference

### 1. Authentication Portal (`/auth/**`)
*   `POST /auth/signup` - Register a new application user (Admin, Staff, or Patient).
*   `POST /auth/login` - Authenticate credentials and return a signed bearer JWT token.

### 2. Patients Portal (`/api/patients/**`)
*   `GET /api/patients/me` - Fetch the authenticated patient's profile details.
*   `GET /api/patients` - Retrieve all patients (Requires `ADMIN` or `STAFF`).
*   `POST /api/patients` - Create a new patient entry (Requires `ADMIN` or `STAFF`).
*   `PUT /api/patients/{id}` - Update patient records.

### 3. Medical & Administrative Portals
*   `GET /api/doctors` - View all active medical professionals.
*   `POST /api/doctors` - Register a new doctor into the registry (Requires `ADMIN`).
*   `GET /api/appointments/my` - Fetch a patient's personal appointment history.
*   `POST /api/appointments` - Book a new consultation slot.
*   `GET /api/prescriptions/my` - Fetch all medical prescriptions issued to the active patient.
*   `GET /api/billings/my` - Fetch all invoices and billing history.
*   `POST /api/payments/pay` - Process invoices using our secure transaction gateway.

---

## 🚀 Local Development Setup

To run this Spring Boot application locally, follow these instructions:

### Prerequisites
*   **Java JDK 17** or higher
*   **Maven 3.x**
*   **MySQL Server** (Running locally on port 3306)

### 1. Clone & Configure Workspace
```bash
git clone https://github.com/Kopal31/hms-backend.git
cd hms-backend
```

### 2. Configure Database Variables
Create your local database:
```sql
CREATE DATABASE hms_db;
```

Update your `src/main/resources/application.properties` with your database credentials or set the corresponding environment variables:
```properties
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/hms_db
SPRING_DATASOURCE_USERNAME=your_username
SPRING_DATASOURCE_PASSWORD=your_password
```

### 3. Build & Run Application
Make the Maven wrapper executable and start the Spring Boot server:
```bash
chmod +x mvnw
./mvnw clean spring-boot:run
```
The server will start on port **`8080`** and automatically initialize tables and seed default users.

---

## 🐋 Docker & Production Deployment

To run in production, the app uses a multi-stage `Dockerfile` to compile and execute natively:
```dockerfile
# Build stage
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

## 🧑‍💻 Seeded Demo Logins
Use these default credentials to test the portals:
*   **Administrator**: `admin@hms.com` / `root123`
*   **Medical Staff**: `staff@hms.com` / `root123`
