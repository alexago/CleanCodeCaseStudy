package cleancoderscom.entities;

import cleancoderscom.entities.Entity;

public class User extends Entity {
    private final String userName;
    public String getUserName() {
        return userName;
    }


    public User(String userName) {
        this.userName = userName;
    }

}
