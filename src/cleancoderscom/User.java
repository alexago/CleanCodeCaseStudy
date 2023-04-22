package cleancoderscom;

public class User extends Entity {
    private final String userName;
    public String getUserName() {
        return userName;
    }


    public User(String userName) {
        this.userName = userName;
    }

}
