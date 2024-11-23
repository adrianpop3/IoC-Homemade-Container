package components.movie;

import annotations.Component;

@Component
public class HttpNetworkCommunicator implements NetworkCommunicator {
    @Override
    public void connect() {
        System.out.println("Connecting via HTTP...");
    }
}
