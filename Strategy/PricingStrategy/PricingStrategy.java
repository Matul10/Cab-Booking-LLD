package Strategy.PricingStrategy;

import models.TripData;

public interface PricingStrategy {
    Double calculatePrice(TripData tripData);
}
