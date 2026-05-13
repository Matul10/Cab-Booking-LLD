package Managers;

import Strategy.DriverLookup.DriverLookupContext;
import Strategy.PricingStrategy.PricingContext;
import models.Driver;
import models.Trip;
import models.TripData;
import models.User;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class DriverManager {
    private Map<Integer, Driver> drivers;
    private DriverLookupContext driverLookup;
    private PricingContext pricing;

    public DriverManager() {
        drivers = new HashMap<>();
        driverLookup = new DriverLookupContext();
        pricing = new PricingContext();
    }

    public Trip createTrip(TripData tripData){
        Driver driver = findDriver(tripData);
        if(driver == null){
            System.out.println("Hey " + tripData.getUser().getName() + ", No driver found for your journy from " +
                    tripData.getSource() + " to " + tripData.getDestination() + ", try after some time");
            return null;
        }
        Double fare = calcPrice(tripData);
        Trip trip = new Trip(tripData,fare,driver);

        System.out.println("Hey " + tripData.getUser().getName() + ", trip was created successfully for your journy from "
                + tripData.getSource() + " to " + tripData.getDestination() + ", " + trip.getDriver().getName() +
                " with vehicle num " + trip.getDriver().getVehicleNum() + " is coming for pickup.");

        return trip;
    }

    public boolean driverExists(int id){
        return drivers.containsKey(id);
    }

    public Driver addDriver(String name, String vehicleNum){
//        if(driverExists(id)){
//            System.out.println("Driver already exists for id: " + id);
//            return;
//        }
        Driver driver = new Driver(name, vehicleNum);
        drivers.put(driver.getId(),driver);
        System.out.println("Driver   successfully added, id: " + driver.getId() + " name: " + name);
        return driver;
    }

    private Driver findDriver(TripData tripData){
        return driverLookup.findDriver(tripData.getDriverLookupStrategy(),drivers);
    }

    private Double calcPrice(TripData tripData){
        return pricing.calcPrice(tripData);
    }

    public void markDriverAvailable(Driver driver){
        driver.markAvailable();
    }

    public void markDriverUnavailable(Driver driver){
        driver.markUnavailable();
    }
}
