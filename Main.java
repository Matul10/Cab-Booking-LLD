import CabSystem.CabSystem;
import Enums.DriverLookupStrategy;
import Enums.PricingStrategy;
import models.Driver;
import models.Trip;
import models.TripData;
import models.User;


// class diagram -> https://viewer.diagrams.net/?tags=%7B%7D&lightbox=1&highlight=0000ff&edit=_blank&layers=1&nav=1&title=Cab%20Booking%20System.drawio.html&dark=auto#Uhttps%3A%2F%2Fdrive.google.com%2Fuc%3Fid%3D1wujHFvIP7JlSw8RbF-8wLDZbV1ClVOFv%26export%3Ddownload

public class Main {
    public static void main(String[] args) {
        CabSystem uber = new CabSystem();

        //users
        User matul = uber.addUser("matul");
        User rahul = uber.addUser("rahul");
        User chanchal = uber.addUser("chanchal");
        System.out.println("");
        //drivers
        Driver vansh = uber.addDriver("vansh","HR01");
        Driver navneet = uber.addDriver("navneet","BR02");
        System.out.println("");


        Trip matulTrip = uber.createTrip("ITPL","Airport",matul,DriverLookupStrategy.DEFAULT,PricingStrategy.DEFAULT,30.0);
        Trip rahulTrip = uber.createTrip("ITPL","Bellandur",rahul,DriverLookupStrategy.DEFAULT,PricingStrategy.DEFAULT,9.3);
        Trip chanchalTrip = uber.createTrip("ITPL","Indiranagar",chanchal,DriverLookupStrategy.DEFAULT,PricingStrategy.DEFAULT,11.4);
        System.out.println("");
        uber.startTrip(rahulTrip);
        chanchalTrip = uber.createTrip("ITPL","Indiranagar",chanchal,DriverLookupStrategy.DEFAULT,PricingStrategy.DEFAULT,11.4);
        System.out.println("");
        uber.completeTrip(rahulTrip);
        chanchalTrip = uber.createTrip("ITPL","Indiranagar",chanchal,DriverLookupStrategy.DEFAULT,PricingStrategy.DEFAULT,11.4);

        uber.printRecentTrips(10);

    }
}
