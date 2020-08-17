package Data.Others;

import java.io.Serializable;

public class Invoice implements Serializable {
    private int ID;
    private String type;
    private int price;

    public Invoice(String type) {
        this.type = type;
    }
}
