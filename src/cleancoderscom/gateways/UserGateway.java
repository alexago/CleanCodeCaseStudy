package cleancoderscom.gateways;

import cleancoderscom.entities.User;

public interface UserGateway {
    User findUserByName(String username);

    User save(User otherUser);
}
