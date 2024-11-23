package components.car;

import annotations.Autowired;
import annotations.Component;

@Component(customProp="international")
public class InternationalServerAccess implements ServerAccess {

    @Autowired
    public InternationalServerAccess() {}

    @Override
    public void accessServer() {
        System.out.println("Accessing International Server...");
    }
}
