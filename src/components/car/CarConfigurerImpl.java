package components.car;

import annotations.Autowired;
import annotations.Component;

@Component
public class CarConfigurerImpl implements CarConfigurer {
    private final CarCataloger carCataloger;

    @Autowired
    public CarConfigurerImpl(CarCataloger carCataloger) {
        this.carCataloger = carCataloger;
    }

    @Override
    public void configureCar() {
        System.out.println("\nConfiguring Car...");
        carCataloger.makeCarCatalogue();
    }
}
