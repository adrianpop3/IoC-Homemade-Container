package components.car;

import annotations.Autowired;
import annotations.Component;
import annotations.Preferred;

@Component
@Preferred
public class NewCarCataloger implements CarCataloger {
    private final ServerAccess serverAccess;

    @Autowired(implType="international")
    public NewCarCataloger(ServerAccess serverAccess) {
        this.serverAccess = serverAccess;
    }

    @Override
    public void makeCarCatalogue() {
        System.out.println("Making New Car Catalogue...");
        serverAccess.accessServer();
    }
}
