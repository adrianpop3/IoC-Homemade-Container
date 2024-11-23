package components.movie;

import annotations.Autowired;
import annotations.Component;

@Component
public class MongoDatabaseAccess implements DatabaseAccess {
    private final NetworkCommunicator networkCommunicator;

    @Autowired
    public MongoDatabaseAccess(NetworkCommunicator networkCommunicator) {
        this.networkCommunicator = networkCommunicator;
    }

    @Override
    public void accessDatabase() {
        System.out.println("Accessing Mongo database...");
        networkCommunicator.connect();
    }
}
