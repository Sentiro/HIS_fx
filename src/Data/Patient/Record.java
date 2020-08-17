package Data.Patient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//一个病历本
public class Record  implements Serializable {
    private int ID;//
    private Patient owner;
    private List<OneRecord> myRecord;


    public String getGender() {
        if (owner != null) {
            return owner.getGender();
        }

        return "";
    }
    public Record() {
        myRecord=new ArrayList<>();
    }

    public int getID() {
        return ID;
    }


    public void setID(int ID) {
        this.ID=ID;
    }



    public Patient getOwner() {
        return owner;
    }

    public void setOwner(Patient owner) {
        this.owner = owner;
    }

    public List<OneRecord> getMyRecord() {
        return myRecord;
    }

    public void setMyRecord(List<OneRecord> myRecord) {
        this.myRecord = myRecord;
    }

    @Override
    public String toString() {
        return "Record{" +
                "ID=" + ID +
                ", owner=" + owner +
                ", myRecord=" + myRecord +
                '}';
    }
}
