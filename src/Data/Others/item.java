package Data.Others;

import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;

public class item{
       private final SimpleStringProperty time=new SimpleStringProperty();
       private final SimpleStringProperty name=new SimpleStringProperty();
       private final SimpleStringProperty status=new SimpleStringProperty();
       private final SimpleStringProperty Id=new SimpleStringProperty();
       private final SimpleStringProperty itemName=new SimpleStringProperty();
       private final SimpleStringProperty number=new SimpleStringProperty();
       private final SimpleStringProperty price=new SimpleStringProperty();

    public item() {
    }

    public String getTime() {
        return time.get();
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getId() {
        return Id.get();
    }

    public SimpleStringProperty idProperty() {
        return Id;
    }

    public void setId(String id) {
        this.Id.set(id);
    }

    public String getItemName() {
        return itemName.get();
    }

    public SimpleStringProperty itemNameProperty() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName.set(itemName);
    }

    public String getNumber() {
        return number.get();
    }

    public SimpleStringProperty numberProperty() {
        return number;
    }

    public void setNumber(String number) {
        this.number.set(number);
    }

    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }
}
