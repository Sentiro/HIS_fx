package Data.Others;

import java.io.Serializable;

public class RLevel implements Serializable {
    private String name;
    private int ID;
    private int charge;

    public RLevel(String name, int ID, int charge) {
        this.name = name;
        this.ID = ID;
        this.charge = charge;
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

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    @Override
    public String toString() {
        return name;
    }
}
