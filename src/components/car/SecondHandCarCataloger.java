package components.car;

import annotations.Autowired;
import annotations.Component;

@Component
public class SecondHandCarCataloger implements CarCataloger {
    private final ServerAccess serverAccess;

    @Autowired
    public SecondHandCarCataloger(ServerAccess serverAccess) {
        this.serverAccess = serverAccess;
    }

    @Override
    public void makeCarCatalogue() {
        System.out.println("Making Second Hand Car Catalogue...");
        serverAccess.accessServer();
    }
}
