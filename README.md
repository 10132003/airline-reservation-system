# ✈️ Airline Reservation System

A comprehensive backend **Airline Reservation System** developed using **Java 17, Spring Boot, Spring Data JPA, Hibernate, MySQL, and Maven**. The application provides RESTful APIs for managing airports, aircraft, flights, passengers, bookings, payments, tickets, notifications, and administrative dashboard analytics. The project follows a clean layered architecture with DTO-based communication, centralized exception handling, validation, and transactional business workflows.

---

# 🚀 Features

## 👤 User Management

* Register new users
* Retrieve user details
* Update user information
* Delete users
* View all registered users

---

## 🛫 Airport Management

* Create airports
* Update airport details
* Delete airports
* Retrieve airport information
* View all airports

---

## ✈️ Aircraft Management

* Register aircraft
* Update aircraft information
* Delete aircraft
* Retrieve aircraft details
* View all aircraft

---

## 🛩️ Flight Management

* Schedule flights
* Update flight schedules
* Delete flights
* Retrieve flight details
* View all flights
* Assign aircraft to flights
* Configure departure and arrival airports
* Manage departure and arrival timings
* Store ticket pricing

---

## 💺 Flight Seat Management

* Generate seats for every flight
* Retrieve available seats
* Reserve seats during booking
* Release seats after cancellation
* Maintain seat status and seat class

---

## 👥 Passenger Management

* Create passenger records
* Retrieve passenger details
* Update passenger information
* Delete passenger records

---

## 🎫 Booking Management

* Create bookings
* Retrieve booking details
* Update bookings
* Cancel bookings
* View booking history
* Generate unique PNR numbers
* Track booking status

---

## 💳 Payment Management

* Create payment records
* Update payment status
* Retrieve payment details
* View payment history
* Associate payments with bookings
* Maintain payment amount and transaction details

---

## 🎟️ Ticket Management

* Generate tickets
* Retrieve ticket information
* Cancel tickets
* Associate tickets with bookings

---

## 🔔 Notification Management

Automatically generates notifications for:

* Booking confirmation
* Payment success
* Ticket cancellation

Each notification stores:

* Title
* Message
* Notification type
* Read status
* Timestamp

---

## 📊 Dashboard Analytics

Provides administrator statistics including:

* Total Flights
* Total Bookings
* Confirmed Bookings
* Cancelled Bookings
* Today's Bookings
* Available Seats
* Total Revenue

---

# 🔄 Business Workflows

## Complete Booking Workflow

1. Passenger selects a flight.
2. Booking record is created.
3. Passenger information is stored.
4. Selected seat is reserved.
5. Payment record is created with pending status.
6. Ticket is generated.
7. Booking notification is created.
8. Complete booking response is returned.

---

## Payment Success Workflow

1. Validate payment.
2. Update payment status.
3. Confirm booking.
4. Generate payment notification.
5. Return payment confirmation.

---

## Ticket Cancellation Workflow

1. Validate booking.
2. Cancel booking.
3. Release reserved seat.
4. Update ticket status.
5. Generate cancellation notification.

---

# 🏗️ Project Architecture

The project follows a layered architecture:

```text
Controller
      │
      ▼
Service
      │
      ▼
Repository
      │
      ▼
Database (MySQL)
```

### Layers

* Controller Layer
* Service Layer
* Repository Layer
* DTO Layer
* Entity Layer
* Exception Layer
* Configuration Layer

---

# 🛠️ Technology Stack

### Backend

* Java 17
* Spring Boot
* Spring MVC
* Spring Data JPA
* Hibernate

### Database

* MySQL

### Build Tool

* Maven

### API Development

* REST APIs
* Swagger / OpenAPI
* Postman

### Validation

* Jakarta Bean Validation

### Version Control

* Git
* GitHub

---

# 📁 Project Structure

```text
src
├── controller
├── service
├── repository
├── entity
├── dto
├── exception
├── config
├── dashboard
├── booking
├── payment
├── passenger
├── notification
├── flight
├── flightseat
├── aircraft
├── airport
└── ticket
```

---

# ⚙️ Getting Started

## Prerequisites

* Java 17
* Maven
* MySQL
* IntelliJ IDEA or VS Code

---

## Installation

Clone the repository:

```bash
git clone https://github.com/10132003/airline-reservation-system.git
```

Navigate to the project directory:

```bash
cd airline-reservation-system
```

Configure your MySQL database in `application.properties`.

Build the project:

```bash
mvn clean install
```

Run the application:

```bash
mvn spring-boot:run
```

The application will start on:

```text
http://localhost:8080
```

---

# 📖 API Documentation

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

Open the URL in your browser after starting the application to explore and test the available REST APIs.

---

# ✅ Implemented Concepts

* RESTful API Development
* Layered Architecture
* DTO Pattern
* Dependency Injection
* Spring Data JPA
* Hibernate ORM
* Entity Relationships
* CRUD Operations
* Business Workflows
* Transaction Management
* Global Exception Handling
* Custom Exceptions
* Request Validation
* Swagger Documentation

---

# 🚀 Future Enhancements

* Spring Security
* JWT Authentication
* Role-Based Authorization
* Pagination
* Filtering
* Sorting
* Logging
* Email Notifications
* Docker Deployment
* Redis Caching
* CI/CD Pipeline
* AWS Deployment
* Microservices

---

# 👨‍💻 Author

**Thomas M**

Backend Developer | Java | Spring Boot | REST APIs

GitHub: https://github.com/10132003

LinkedIn: https://linkedin.com/in/thomasm-tech/

---

## ⭐ If you found this project useful, consider giving it a star!
