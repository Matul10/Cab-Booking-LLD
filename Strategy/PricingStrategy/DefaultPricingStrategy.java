package Strategy.PricingStrategy;

import models.TripData;

public class DefaultPricingStrategy implements PricingStrategy{
    private Double farePerKm = 10.0;
    @Override
    public Double calculatePrice(TripData tripData) {
        Double fare = 0.0;
        Double distance = tripData.getDistance();
        return farePerKm*distance;
    }
}
