package components.movie;

import annotations.Component;
import annotations.Preferred;

@Component
@Preferred
public class HttpsNetworkCommunicator implements NetworkCommunicator {
    @Override
    public void connect() {
        System.out.println("Connecting via HTTPS...");
    }
}
