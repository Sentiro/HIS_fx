package Data.User;

import java.io.Serializable;

public class User implements Serializable {
    private int ID;
    private String password;
    private String name;

    public User(int ID, String password,String name) {
        this.ID = ID;
        this.password = password;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
