package cleancoderscom;

public interface UserGateway {
    User findUserByName(String username);

    User save(User otherUser);
}
