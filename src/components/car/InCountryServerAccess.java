package components.car;

import annotations.Autowired;
import annotations.Component;

@Component
public class InCountryServerAccess implements ServerAccess{

    @Autowired
    public InCountryServerAccess() {}

    @Override
    public void accessServer() {
        System.out.println("Accessing In Country Server...");
    }
}
