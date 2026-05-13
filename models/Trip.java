package models;

import Enums.DriverLookupStrategy;
import Enums.PricingStrategy;
import Enums.TripStatus;

import java.util.concurrent.atomic.AtomicInteger;

public class Trip {
    private static final AtomicInteger nextId = new AtomicInteger(1);

    private int id;
    private String source;
    private String destination;
    private DriverLookupStrategy driverLookupStrategy;
    private PricingStrategy pricingStrategy;
    private Double distance;
    private User user;
    private Driver driver;
    private Double fare;
    private TripStatus status;

    public Trip(TripData tripData,Double fare, Driver driver) {
        this.id = nextId.getAndIncrement();
        this.source = tripData.getSource();
        this.destination = tripData.getDestination();
        this.driverLookupStrategy = tripData.getDriverLookupStrategy();
        this.pricingStrategy = tripData.getPricingStrategy();
        this.distance = tripData.getDistance();
        this.user = tripData.getUser();
        this.driver = driver;
        this.fare = fare;
        this.status = TripStatus.NOT_STARTED;
    }

    public int getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public DriverLookupStrategy getDriverLookupStrategy() {
        return driverLookupStrategy;
    }

    public Double getDistance() {
        return distance;
    }

    public PricingStrategy getPricingStrategy() {
        return pricingStrategy;
    }

    public Double getFare() {
        return fare;
    }

    public Driver getDriver() {
        return driver;
    }

    public TripStatus getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public void setStatus(TripStatus status) {
        this.status = status;
    }
}
