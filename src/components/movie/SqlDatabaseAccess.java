package components.movie;

import annotations.Autowired;
import annotations.Component;
import annotations.Preferred;

@Component
@Preferred
public class SqlDatabaseAccess implements DatabaseAccess {
    private final NetworkCommunicator networkCommunicator;

    @Autowired
    public SqlDatabaseAccess(NetworkCommunicator networkCommunicator) {
        this.networkCommunicator = networkCommunicator;
    }

    @Override
    public void accessDatabase() {
        System.out.println("Accessing SQL database...");
        networkCommunicator.connect();
    }
}
