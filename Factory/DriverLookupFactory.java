package Factory;

import Enums.DriverLookupStrategy;
import Strategy.DriverLookup.DefaultDriverLookupStrategy;
import Strategy.DriverLookup.LookupStrategy;

public class DriverLookupFactory {
    public static LookupStrategy get(DriverLookupStrategy strategy){
        return switch (strategy){
            case DEFAULT -> new DefaultDriverLookupStrategy();
            default -> throw new IllegalArgumentException(" invalid lookup strategy enum! ");
        };
    }
}
