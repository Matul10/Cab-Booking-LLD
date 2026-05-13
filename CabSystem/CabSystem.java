package CabSystem;

import Enums.DriverLookupStrategy;
import Enums.PricingStrategy;
import Enums.TripStatus;
import Managers.DriverManager;
import Managers.UserManager;
import models.Driver;
import models.Trip;
import models.TripData;
import models.User;

public class CabSystem {
    private DriverManager driverManager;
    private UserManager userManager;

    public CabSystem(){
        this.driverManager = new DriverManager();
        this.userManager = new UserManager();
    }

    public Trip createTrip(String source, String destination,User user, DriverLookupStrategy lookupStrategy, PricingStrategy pricingStrategy, Double distance){
        TripData tripData = new TripData(source,destination,user,lookupStrategy,pricingStrategy,distance);

        Trip trip = driverManager.createTrip(tripData);
        if(trip != null) userManager.startUserRide(user);

        return trip;
    }
    public Driver addDriver(String name, String vehicleNum){
        return driverManager.addDriver(name, vehicleNum);
    }
    public User addUser(String name){
        return userManager.addUser(name);
    }

    public void startTrip(Trip trip){
        if(trip == null){
            System.out.println("no trip exists/passed !");
            return;
        }
        if(trip.getStatus() == TripStatus.NOT_STARTED){
            trip.setStatus(TripStatus.ONGOING);
            System.out.println("Trip with id: " + trip.getId() + " started!");
        }else{
            System.out.println("Trip is already in " + trip.getStatus() + " state, can't start!!");
        }

    }

    public void completeTrip(Trip trip){
        if(trip == null){
            System.out.println("no trip exists/passed !");
            return;
        }
        if(trip.getStatus() == TripStatus.ONGOING){
            trip.setStatus(TripStatus.COMPLETED);

            userManager.endUserRide(trip.getUser());
            trip.getDriver().markAvailable();

            System.out.println("Trip with id: " + trip.getId() + " completed successfully!");
        }else{
            System.out.println("Trip is in " + trip.getStatus() + " state, can't mark as complete!!");
        }

    }

}
