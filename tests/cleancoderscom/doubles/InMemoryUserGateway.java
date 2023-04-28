package cleancoderscom.doubles;

import cleancoderscom.entities.User;
import cleancoderscom.gateways.UserGateway;

public class InMemoryUserGateway extends GatewayUtilities<User> implements UserGateway {
    @Override
    public User findUserByName(String username) {
        for (User user : getEntities()) {
            if (user.getUserName().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
