import components.car.CarConfigurer;
import ioc.IoCContainer;

public class CarMain {
    public static void main(String[] args) {
        IoCContainer carContainer = new IoCContainer("components.car");

        CarConfigurer carConfigurer = carContainer.resolve(CarConfigurer.class);
        carConfigurer.configureCar();
    }
}
