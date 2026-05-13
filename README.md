# Cab Booking System - Low Level Design (LLD)

A Java-based implementation of a cab booking system demonstrating industry-standard design patterns with thread-safe atomic operations for concurrent environments.

## 📋 Overview

This cab booking system is a backend service that enables users to book rides with available drivers. The system manages the complete lifecycle of a trip from booking to completion while implementing flexible, pluggable strategies for driver lookup and pricing.

**Core Functionality:**
- User registration and ride status tracking
- Driver registration and availability management
- Trip creation and lifecycle management (NOT_STARTED → ONGOING → COMPLETED)
- Strategy-based driver lookup and pricing
- Thread-safe operations using atomic primitives

## 🏗️ System Architecture

The system follows a **three-tier layered architecture**:

```
CabSystem (Facade Layer)
    ↓
Managers (Business Logic Layer)
    ├── UserManager
    └── DriverManager
        ├── DriverLookupContext
        └── PricingContext
    ↓
Models (Domain Layer)
    ├── User
    ├── Driver
    ├── Trip
    └── TripData
```

## 🎨 Design Patterns Used

1. **Strategy Pattern** - Flexible driver lookup and pricing algorithms
2. **Factory Pattern** - Dynamic strategy instantiation
3. **Context Pattern** - Encapsulates strategy selection and execution
4. **Facade Pattern** - Simplified public API through CabSystem

## 📊 Class Diagram

![Cab Booking System Class Diagram](./Cab%20Booking%20System.jpg)

[**View Interactive Class Diagram** →](https://viewer.diagrams.net/?tags=%7B%7D&lightbox=1&highlight=0000ff&edit=_blank&layers=1&nav=1&title=Cab%20Booking%20System.drawio.html&dark=auto#Uhttps%3A%2F%2Fdrive.google.com%2Fuc%3Fid%3D1wujHFvIP7JlSw8RbF-8wLDZbV1ClVOFv%26export%3Ddownload)

## 🔑 Key Components

### CabSystem (Facade)
Central coordinator for all cab booking operations. Provides simplified public interface for users and drivers.

### UserManager
Manages user lifecycle including:
- User registration with auto-generated IDs
- Ride status tracking (onRide/offRide)
- Booking eligibility validation

### DriverManager
Manages drivers and trip orchestration including:
- Driver registration with auto-generated IDs
- Trip creation using strategy patterns
- Driver availability management
- Fare calculation

### Models
- **User** - User entity with atomic ride status
- **Driver** - Driver entity with atomic availability status
- **Trip** - Trip entity with auto-generated IDs and trip status
- **TripData** - Data transfer object for trip requests

### Strategies

**Driver Lookup Strategies:**
- DefaultDriverLookupStrategy - Finds first available driver

**Pricing Strategies:**
- DefaultPricingStrategy - Fixed rate per km (₹10/km)

## ✨ Key Features

- ✅ Auto-generated unique IDs using AtomicInteger
- ✅ Thread-safe operations (AtomicInteger, AtomicBoolean)
- ✅ Flexible strategy-based architecture
- ✅ Automatic driver availability management
- ✅ Trip state management
- ✅ Extensible design for new strategies

## 🔒 Thread Safety

All ID generation and status updates use **atomic operations**:
- `AtomicInteger` for unique ID generation across threads
- `AtomicBoolean` for driver availability and user ride status
- Lock-free, high-performance concurrent operations
- No race conditions in multi-threaded environments

## 📁 Project Structure

```
Cab Booking System/
├── models/               # Domain entities
│   ├── User.java
│   ├── Driver.java
│   ├── Trip.java
│   └── TripData.java
├── Managers/             # Business logic
│   ├── UserManager.java
│   └── DriverManager.java
├── CabSystem/            # Facade
│   └── CabSystem.java
├── Enums/                # Strategy enums
│   ├── DriverLookupStrategy.java
│   ├── PricingStrategy.java
│   └── TripStatus.java
├── Strategy/             # Strategy implementations
│   ├── DriverLookup/
│   │   ├── LookupStrategy.java
│   │   ├── DefaultDriverLookupStrategy.java
│   │   └── DriverLookupContext.java
│   └── PricingStrategy/
│       ├── PricingStrategy.java
│       ├── DefaultPricingStrategy.java
│       └── PricingContext.java
├── Factory/              # Object creation
│   ├── DriverLookupFactory.java
│   └── PricingFactory.java
├── Main.java             # Demo application
└── README.md             # Documentation
```

## 🚀 Future Enhancements

- Location-based driver search (Geohashing)
- Multiple ride types (Shared, Premium, Pool)
- Rating and review system
- Payment integration
- Real-time GPS tracking
- Cancellation and refund system
- Database persistence
- REST API implementation

## 📚 Learning Objectives

This project demonstrates:
- Strategy and Factory design patterns in action
- Thread-safe concurrent programming
- SOLID principles and clean code architecture
- Object-oriented system design
- Extensible, maintainable code structure

---

**Language:** Java 16+  
**Architecture:** Layered + Strategy Pattern  
**Concurrency:** Atomic Operations (Lock-free)


