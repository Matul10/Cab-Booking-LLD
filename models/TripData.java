package models;

import Enums.DriverLookupStrategy;
import Enums.PricingStrategy;

import java.util.concurrent.atomic.AtomicInteger;

public class TripData {
    private static final AtomicInteger nextId = new AtomicInteger(1);

    private int id;
    private String source;
    private String destination;
    private User user;
    private DriverLookupStrategy driverLookupStrategy;
    private PricingStrategy pricingStrategy;
    private Double distance;

    public TripData(String source, String destination,User user, DriverLookupStrategy driverLookupStrategy, PricingStrategy pricingStrategy, Double distance) {
        this.id = nextId.getAndIncrement();
        this.source = source;
        this.destination = destination;
        this.user = user;
        this.driverLookupStrategy = driverLookupStrategy;
        this.pricingStrategy = pricingStrategy;
        this.distance = distance;

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

    public User getUser() {
        return user;
    }

    public DriverLookupStrategy getDriverLookupStrategy() {
        return driverLookupStrategy;
    }

    public PricingStrategy getPricingStrategy() {
        return pricingStrategy;
    }

    public Double getDistance() {
        return distance;
    }
}
