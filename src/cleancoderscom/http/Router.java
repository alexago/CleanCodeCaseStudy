package cleancoderscom.http;

import java.util.HashMap;
import java.util.Map;

public class Router {
    private final Map<String, Controller> routes = new HashMap<>();

    public String route(ParsedRequest request) {
        System.out.println("[DEBUG] request.path = " + request.path);
        String[] parts = request.path.split("/");
        String controllerKey = parts.length > 1 ? parts[1] : "";
        Controller controller = routes.get(controllerKey);
        if (controller == null) {
            return "HTTP/1.1 404 OK\n";
        }
        return controller.handle(request);
    }

    public void addPath(String path, Controller controller) {
        routes.put(path, controller);
    }
}
