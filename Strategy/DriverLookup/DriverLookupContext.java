package Strategy.DriverLookup;

import Enums.DriverLookupStrategy;
import Factory.DriverLookupFactory;
import models.Driver;
import models.TripData;

import java.util.Map;

public class DriverLookupContext {
    LookupStrategy strategy;
    public DriverLookupContext(){
        strategy = null;
    }

    public Driver findDriver(DriverLookupStrategy lookupStrategy, Map<Integer, Driver> drivers){
        this.strategy = DriverLookupFactory.get(lookupStrategy);
        return strategy.driverLookup(drivers);
    }
}
