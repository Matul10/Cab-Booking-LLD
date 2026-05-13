package Factory;

import Strategy.PricingStrategy.DefaultPricingStrategy;
import Strategy.PricingStrategy.PricingStrategy;

public class PricingFactory {
    public static PricingStrategy get(Enums.PricingStrategy strategy){
        return switch (strategy){
            case DEFAULT -> new DefaultPricingStrategy();
            default -> throw new IllegalArgumentException("Invalid pricing strategy enum!");
        };
    }
}
