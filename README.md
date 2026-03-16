# Hospital Management System – Backend API

This repository contains the **Spring Boot backend API** for the Hospital Management System.

The backend is responsible for managing core hospital operations including patients, doctors, appointments, prescriptions, and user authentication.

It exposes RESTful APIs used by the React frontend application.

---

# Overview

The backend is built using **Spring Boot** and follows a layered architecture separating controllers, services, repositories, and entities.

Key responsibilities include:

* Managing hospital data
* Handling authentication and authorization
* Processing business logic
* Providing REST APIs for frontend integration

---

# Technology Stack

Backend Framework

* Spring Boot
* Spring MVC

Security

* Spring Security
* JWT Authentication
* Role-based authorization

Persistence

* Spring Data JPA
* Hibernate

Database

* PostgreSQL

Utilities

* ModelMapper
* Maven

---

# Backend Architecture

The backend follows a **layered architecture**:

controller
service
repository
entity
dto
mapper
security
config
exception
error

Request flow:

Controller → Service → Repository → Database


# Core Modules

## Authentication

Handles user authentication and JWT token generation.

Key components:

* AuthController
* AuthService
* JwtAuthFilter
* CustomUserDetailsService


## User Management

Manages application users and roles.

Roles supported:

* Admin
* Doctor

Authorization rules restrict access to protected endpoints.


## Patient Management

Handles patient records including:

* Creating patients
* Updating patient information
* Retrieving patient records


## Doctor Management

Manages doctor profiles and doctor-related information.


## Appointment Management

Handles appointment scheduling between doctors and patients.

Features include:

* Creating appointments
* Tracking appointment status
* Managing doctor-patient scheduling

---

## Prescription Management

Handles prescriptions issued to patients.

Includes:

* Prescription creation
* Prescription items
* Medication tracking

---

# Security

Authentication is implemented using **JWT tokens**.

Authentication flow:

1. User submits login credentials
2. Backend validates credentials
3. JWT token is generated
4. Token is returned to the client
5. Client includes token in subsequent API requests

Spring Security filters validate tokens for protected endpoints.

---

# Running the Backend Locally

Requirements

* Java 17+
* Maven
* PostgreSQL

Run the application:


mvn spring-boot:run


The backend server will start at:


http://localhost:8080


# Related Repositories

Frontend (React)

https://github.com/MohammadSafwan97/React-HMS-FrontEnd

AI Assistant Service (Flask)

https://github.com/MohammadSafwan97/hms-ai-assistant


# Future Improvements

Planned improvements include:

* API documentation using Swagger / OpenAPI
* Pagination for large datasets
* Unit and integration testing
* Docker containerization
* CI/CD pipeline
