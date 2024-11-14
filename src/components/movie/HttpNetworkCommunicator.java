package components.movie;

import annotations.Component;
import annotations.Primary;

@Component
@Primary
public class HttpNetworkCommunicator implements NetworkCommunicator {
    @Override
    public void connect() {
        System.out.println("Connecting via HTTP...");
    }
}
