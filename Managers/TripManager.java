package Managers;

import Enums.TripStatus;
import Strategy.PricingStrategy.PricingContext;
import models.Driver;
import models.Trip;
import models.TripData;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TripManager {
    private Map<Integer, Trip> trips;
    private PricingContext pricing;

    public TripManager() {
        trips = new HashMap<>();
        pricing = new PricingContext();
    }

    public Trip createTrip(TripData tripData, Driver driver){
        Double fare = calcPrice(tripData);
        Trip trip = new Trip(tripData,fare,driver);
        trips.put(trip.getId(),trip);
        return trip;
    }

    private Double calcPrice(TripData tripData){
        return pricing.calcPrice(tripData);
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

    public boolean completeTrip(Trip trip){
        if(trip == null){
            System.out.println("no trip exists/passed !");
            return false;
        }
        if(trip.getStatus() == TripStatus.ONGOING){
            trip.setStatus(TripStatus.COMPLETED);
            System.out.println("Trip with id: " + trip.getId() + " completed successfully!");
            return true;
        }else{
            System.out.println("Trip is in " + trip.getStatus() + " state, can't mark as complete!!");
            return false;
        }

    }


    /**
     * Prints details of the most recent trips (user-friendly format)
     * Shows only information relevant to rider: driver, route, fare, and status
     * @param count number of recent trips to display
     */
    public void printRecentTrips(int count) {
        if (trips.isEmpty()) {
            System.out.println("No trips found!");
            return;
        }

        System.out.println("\n" + "=".repeat(70));
        System.out.println("RECENT TRIPS - Last " + Math.min(count, trips.size()) + " trips");
        System.out.println("=".repeat(70));

        // Get the most recent trips (highest IDs) and limit to count
        trips.values().stream()
                .sorted((t1, t2) -> Integer.compare(t2.getId(), t1.getId()))  // Sort by ID descending (most recent first)
                .limit(count)
                .collect(Collectors.toList())
                .forEach(this::printTripDetails);

        System.out.println("=".repeat(70) + "\n");
    }

    /**
     * Prints details of a single trip in user-friendly format
     * Excludes internal details like PricingStrategy and DriverLookupStrategy
     * @param trip the trip to print
     */
    private void printTripDetails(Trip trip) {
        System.out.println("┌─ Trip ID: " + trip.getId());
        System.out.println("├─ Rider: " + trip.getUser().getName());
        System.out.println("├─ Driver: " + trip.getDriver().getName() + " (" + trip.getDriver().getVehicleNum() + ")");
        System.out.println("├─ Route: " + trip.getSource() + " → " + trip.getDestination());
        System.out.println("├─ Distance: " + trip.getDistance() + " km");
        System.out.println("├─ Fare: ₹" + String.format("%.2f", trip.getFare()));
        System.out.println("└─ Status: " + trip.getStatus());
        System.out.println();
    }


}
