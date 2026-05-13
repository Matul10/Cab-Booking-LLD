package Strategy.DriverLookup;

import models.Driver;

import java.util.Map;

public class DefaultDriverLookupStrategy implements LookupStrategy{
    @Override
    public Driver driverLookup(Map<Integer, Driver> drivers) {
        Driver driver = null;
        for(Map.Entry<Integer,Driver> entry : drivers.entrySet()){
            int id = entry.getKey();
            Driver curDriver = entry.getValue();
            if(curDriver.getAvailable()){
                driver = curDriver;
                driver.markUnavailable();
                break;
            }
        }
        return driver;
    }
}
