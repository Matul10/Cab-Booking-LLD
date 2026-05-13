package Strategy.PricingStrategy;

import Factory.PricingFactory;
import models.TripData;

public class PricingContext {
    private PricingStrategy strategy;

    public PricingContext() {
        strategy = null;
    }

    public double calcPrice(TripData tripData){
        this.strategy = PricingFactory.get(tripData.getPricingStrategy());
        return strategy.calculatePrice(tripData);
    }
}
