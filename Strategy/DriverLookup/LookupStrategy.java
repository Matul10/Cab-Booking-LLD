package Strategy.DriverLookup;

import models.Driver;

import java.util.Map;

public interface LookupStrategy {
    Driver driverLookup(Map<Integer,Driver> drivers);
}
