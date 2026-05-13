# Cab Booking System - Low Level Design (LLD)

A Java-based implementation of a cab booking system demonstrating industry-standard design patterns with thread-safe atomic operations for concurrent environments. Built with **refactored architecture** following SOLID principles.

## 📋 Overview

This cab booking system is a backend service that enables users to book rides with available drivers. The system manages the complete lifecycle of a trip from booking to completion while implementing flexible, pluggable strategies for driver lookup and pricing.

**Core Functionality:**
- User registration and ride status tracking
- Driver registration with atomic availability management
- Trip creation and lifecycle management (NOT_STARTED → ONGOING → COMPLETED)
- Strategy-based driver lookup and pricing
- Thread-safe atomic operations for concurrent environments
- Trip history viewing with recent trips display

## 🏗️ System Architecture

The system follows a **three-tier layered architecture** with clear separation of concerns:

```
CabSystem (Orchestrator/Facade Layer)
    ↓
Managers (Business Logic Layer)
    ├── UserManager         (User lifecycle)
    ├── DriverManager       (Driver management & lookup)
    └── TripManager         (Trip lifecycle & operations) ← NEW!
        ├── PricingContext
        └── Trip state management
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
5. **Atomic Reservation Pattern** - Lock-free concurrent driver reservation

## 📊 Class Diagram

![Cab Booking System Class Diagram](./Cab%20Booking%20System.jpg)

[**View Interactive Class Diagram** →](https://viewer.diagrams.net/?tags=%7B%7D&lightbox=1&highlight=0000ff&edit=_blank&layers=1&nav=1&title=Cab%20Booking%20System.drawio.html&dark=auto#Uhttps%3A%2F%2Fdrive.google.com%2Fuc%3Fid%3D1wujHFvIP7JlSw8RbF-8wLDZbV1ClVOFv%26export%3Ddownload)

## 🔑 Key Components

### CabSystem (Facade/Orchestrator)
Central coordinator for all cab booking operations. Provides simplified public interface that orchestrates:
- User and driver registration
- Trip creation with driver lookup
- Trip lifecycle management (start, complete)
- Trip history viewing

### UserManager
Manages user lifecycle including:
- User registration with auto-generated IDs
- Ride status tracking (onRide/offRide) using atomic operations
- Booking eligibility validation

### DriverManager
Manages drivers and driver lookup:
- Driver registration with auto-generated IDs
- Driver availability management
- Driver lookup using pluggable strategies
- **Removed**: Trip creation and pricing logic (moved to TripManager)

### TripManager ⭐ **NEW**
Handles complete trip lifecycle:
- Trip creation with fare calculation
- Trip state management (NOT_STARTED → ONGOING → COMPLETED)
- Trip history and analytics
- `printRecentTrips(count)` - Display N most recent trips with user-friendly formatting

### Models
- **User** - User entity with atomic ride status (`AtomicBoolean`)
- **Driver** - Driver entity with atomic availability and atomic reservation capability
- **Trip** - Trip entity with auto-generated IDs and trip status tracking
- **TripData** - Data transfer object for trip requests

### Strategies

**Driver Lookup Strategies:**
- `DefaultDriverLookupStrategy` - Uses atomic reservation (`tryAndReserve()`) to find and reserve first available driver
  - **Benefit**: No race conditions, prevents double-booking in multi-threaded environments

**Pricing Strategies:**
- `DefaultPricingStrategy` - Fixed rate per km (₹10/km)

## ✨ Key Features

- ✅ **Atomic Driver Reservation** - `tryAndReserve()` prevents race conditions
- ✅ **Auto-generated unique IDs** using `AtomicInteger`
- ✅ **Thread-safe operations** (`AtomicInteger`, `AtomicBoolean`)
- ✅ **Flexible strategy-based architecture** for extensibility
- ✅ **Automatic driver availability management** with rollback on failure
- ✅ **Trip state management** with status tracking
- ✅ **Trip history** viewing with recent trips display
- ✅ **Error handling** with atomic rollback for failed bookings
- ✅ **SOLID principles** - Separation of concerns, single responsibility

## 🔒 Thread Safety & Race Condition Prevention

### Atomic ID Generation
```java
private static final AtomicInteger nextId = new AtomicInteger(1);
this.id = nextId.getAndIncrement();  // Thread-safe, lock-free
```

### Atomic Driver Reservation (Check-and-Set)
```java
// Only ONE thread can successfully reserve a driver
public boolean tryAndReserve() {
    return this.available.compareAndSet(true, false);  // Atomic CAS
}
```

### Multi-threaded Trip Booking Scenario
```
Thread 1                          Thread 2
   |                                |
   └─→ Find Driver A (available)    |
       tryAndReserve() → SUCCESS    |
       Driver A: true → false       |
                                   |
   Return Driver A                 └─→ Find Driver A
   Book trip with A                   tryAndReserve() → FAIL (already false)
                                      Try next driver (B)
```

**Result**: No double-booking, each driver assigned to only one trip ✅

## 📁 Project Structure

```
Cab Booking System/
├── models/                  # Domain entities
│   ├── User.java
│   ├── Driver.java
│   ├── Trip.java
│   └── TripData.java
│
├── Managers/                # Business logic layer
│   ├── UserManager.java
│   ├── DriverManager.java
│   └── TripManager.java     ← NEW! (Trip lifecycle)
│
├── CabSystem/               # Facade/Orchestrator
│   └── CabSystem.java
│
├── Enums/                   # Strategy enums
│   ├── DriverLookupStrategy.java
│   ├── PricingStrategy.java
│   └── TripStatus.java
│
├── Strategy/                # Strategy implementations
│   ├── DriverLookup/
│   │   ├── LookupStrategy.java
│   │   ├── DefaultDriverLookupStrategy.java
│   │   └── DriverLookupContext.java
│   │
│   └── PricingStrategy/
│       ├── PricingStrategy.java
│       ├── DefaultPricingStrategy.java
│       └── PricingContext.java
│
├── Factory/                 # Object creation
│   ├── DriverLookupFactory.java
│   └── PricingFactory.java
│
├── Main.java                # Demo application
├── README_UPDATED.md        # This documentation
└── Cab Booking System.jpg   # Class diagram
```

## 🔄 Recent Architecture Improvements

### 1. TripManager Creation ⭐
**What Changed:**
- Moved trip creation logic from `DriverManager` to new `TripManager`
- Moved trip state management from `CabSystem` to `TripManager`
- Added `printRecentTrips(count)` for trip history viewing

**Benefits:**
- ✅ **SRP**: Each manager has single responsibility
- ✅ **Testability**: Can test trip logic independently
- ✅ **Reusability**: TripManager can be used by other services

### 2. Atomic Driver Reservation Pattern ⭐
**What Changed:**
- Added `tryAndReserve()` method to Driver class
- Changed `DefaultDriverLookupStrategy` to use atomic reservation
- Removed side effects from strategy (only finds, doesn't modify during search)

**Benefits:**
- ✅ **Race Condition Prevention**: Atomic CAS operation
- ✅ **Consistency**: No double-booking in concurrent scenarios
- ✅ **Clean Separation**: Strategy only reserves, doesn't handle failures

### 3. Error Handling & Rollback
**What Changed:**
- Added try-catch in `CabSystem.createTrip()`
- Automatic rollback: if trip creation fails, driver is marked available again

**Code:**
```java
try {
    Trip trip = tripManager.createTrip(tripData, driver);
    return trip;
} catch (Exception e) {
    if(driver != null) driver.markAvailable();  // Rollback
    throw new RuntimeException(e);
}
```

### 4. Simplified DriverManager
**What Changed:**
- Removed trip creation logic
- Removed pricing calculation
- Now only handles driver management and lookup

**Before:** 73 lines (multiple responsibilities)  
**After:** 45 lines (single responsibility) ✅

## 📊 TripManager - Trip History Display

### Method Signature
```java
public void printRecentTrips(int count)
```

### Example Usage
```java
// Print 10 most recent trips
tripManager.printRecentTrips(10);

// Print 5 most recent trips
tripManager.printRecentTrips(5);
```

### Sample Output
```
======================================================================
RECENT TRIPS - Last 3 trips
======================================================================
┌─ Trip ID: 3
├─ Rider: chanchal
├─ Driver: navneet (BR02)
├─ Route: ITPL → Indiranagar
├─ Distance: 11.4 km
├─ Fare: ₹114.00
└─ Status: NOT_STARTED

┌─ Trip ID: 2
├─ Rider: rahul
├─ Driver: vansh (HR01)
├─ Route: ITPL → Bellandur
├─ Distance: 9.3 km
├─ Fare: ₹93.00
└─ Status: COMPLETED

┌─ Trip ID: 1
├─ Rider: matul
├─ Driver: navneet (BR02)
├─ Route: ITPL → Airport
├─ Distance: 30.0 km
├─ Fare: ₹300.00
└─ Status: ONGOING

======================================================================
```

## 🚀 Future Enhancements

- [ ] Location-based driver search (Geohashing)
- [ ] Multiple ride types (Shared, Premium, Pool)
- [ ] Rating and review system
- [ ] Payment integration & fare calculation
- [ ] Real-time GPS tracking
- [ ] Cancellation and refund system
- [ ] Database persistence (MySQL, MongoDB)
- [ ] REST API implementation (Spring Boot)
- [ ] Surge pricing strategy
- [ ] Loyalty discount system

## 📚 Learning Objectives

This project demonstrates:
- ✅ Strategy and Factory design patterns in action
- ✅ Separation of concerns and SRP (Single Responsibility Principle)
- ✅ Thread-safe concurrent programming with atomic operations
- ✅ Atomic check-and-set (CAS) for lock-free synchronization
- ✅ SOLID principles in real-world scenarios
- ✅ Object-oriented system design
- ✅ Error handling and rollback mechanisms
- ✅ Extensible, maintainable code structure

## 🐛 SOLID Principles Adherence

| Principle | Status | Details |
|-----------|--------|---------|
| **S**RP | ✅ Improved | TripManager separates trip concerns |
| **O**CP | ✅ Good | New strategies can be added without modifying existing code |
| **L**SP | ✅ Good | Strategy implementations are substitutable |
| **I**SP | ✅ Good | Managers expose only needed interfaces |
| **D**IP | ✅ Fair | Uses concrete classes, could benefit from dependency injection |

## 🔧 Configuration

- **Fare per km**: ₹10 (configurable in `DefaultPricingStrategy`)
- **Driver lookup**: First available driver (configurable via strategies)
- **Trip status flow**: NOT_STARTED → ONGOING → COMPLETED

## 📝 Notes

- All ID generation is thread-safe using `AtomicInteger`
- Driver availability uses `AtomicBoolean` with `compareAndSet` operations
- No synchronized blocks or locks - fully lock-free design
- PricingContext acts as a facade for pricing operations (no wrapper needed)

---

**Language:** Java 16+  
**Architecture:** Layered + Strategy Pattern + Atomic Reservation  
**Concurrency:** Lock-free Atomic Operations (AtomicInteger, AtomicBoolean)  
**Last Updated:** May 2026

