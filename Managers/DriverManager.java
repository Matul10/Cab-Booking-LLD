package Managers;

import Strategy.DriverLookup.DriverLookupContext;
import Strategy.PricingStrategy.PricingContext;
import models.Driver;
import models.Trip;
import models.TripData;

import java.util.HashMap;
import java.util.Map;

public class DriverManager {
    private Map<Integer, Driver> drivers;
    private DriverLookupContext driverLookup;

    public DriverManager() {
        drivers = new HashMap<>();
        driverLookup = new DriverLookupContext();
    }

    public boolean driverExists(int id){
        return drivers.containsKey(id);
    }

    public Driver addDriver(String name, String vehicleNum){
        Driver driver = new Driver(name, vehicleNum);
        drivers.put(driver.getId(),driver);
        System.out.println("Driver   successfully added, id: " + driver.getId() + " name: " + name);
        return driver;
    }

    public Driver findDriver(TripData tripData){
        return driverLookup.findDriver(tripData.getDriverLookupStrategy(),drivers);
    }


//    public void markDriverAvailable(Driver driver){
//        driver.tryAndReserve();
//    }

//    public void markDriverUnavailable(Driver driver){
//        driver.markUnavailable();
//    }
}
