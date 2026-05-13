package CabSystem;

import Enums.DriverLookupStrategy;
import Enums.PricingStrategy;
import Enums.TripStatus;
import Managers.DriverManager;
import Managers.TripManager;
import Managers.UserManager;
import models.Driver;
import models.Trip;
import models.TripData;
import models.User;

public class CabSystem {
    private DriverManager driverManager;
    private UserManager userManager;
    private TripManager tripManager;

    public CabSystem(){
        this.driverManager = new DriverManager();
        this.userManager = new UserManager();
        tripManager = new TripManager();
    }

    public Trip createTrip(String source, String destination,User user, DriverLookupStrategy lookupStrategy, PricingStrategy pricingStrategy, Double distance){
        Driver driver = null;
        try{
            TripData tripData = new TripData(source,destination,user,lookupStrategy,pricingStrategy,distance);
            driver = driverManager.findDriver(tripData);

            if(driver == null){
                System.out.println("Hey " + tripData.getUser().getName() + ", No driver found for your journy from " +
                    tripData.getSource() + " to " + tripData.getDestination() + ", try after some time");
                return null;
            }

            Trip trip = tripManager.createTrip(tripData,driver);
            System.out.println("Hey " + tripData.getUser().getName() + ", trip was created successfully for your journy from "
                + tripData.getSource() + " to " + tripData.getDestination() + ", " + trip.getDriver().getName() +
                " with vehicle num " + trip.getDriver().getVehicleNum() + " is coming for pickup.");


            return trip;
        } catch (Exception e) {
            if(driver != null) driver.markAvailable();
            throw new RuntimeException(e);
        }
    }


    public Driver addDriver(String name, String vehicleNum){
        return driverManager.addDriver(name, vehicleNum);
    }
    public User addUser(String name){
        return userManager.addUser(name);
    }

    public void startTrip(Trip trip){
        tripManager.startTrip(trip);

    }

    public void completeTrip(Trip trip){
        if(tripManager.completeTrip(trip)){
            userManager.endUserRide(trip.getUser());
            trip.getDriver().markAvailable();
        }

    }

    public void printRecentTrips(int count){
        tripManager.printRecentTrips(count);
    }

}
