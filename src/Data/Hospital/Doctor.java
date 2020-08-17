package Data.Hospital;

import Data.Patient.OneRecord;
import Data.Patient.Patient;
import Data.Patient.Record;
import Data.Structure.Heap;
import Data.Structure.MyQueue;

import java.io.Serializable;
import java.util.ArrayList;

public class Doctor implements Serializable {
    private String name;
    private Heap patientHeap;
    private ArrayList<OneRecord> myPatients=new ArrayList<>();
    private MyQueue<OneRecord> AList=new MyQueue<>();
    private MyQueue<OneRecord> BList=new MyQueue<>();
    public MyQueue<OneRecord> getAList() {
        return AList;
    }

    public void setAList(MyQueue<OneRecord> AList) {
        this.AList = AList;
    }

    public MyQueue<OneRecord> getBList() {
        return BList;
    }

    public void setBList(MyQueue<OneRecord> BList) {
        this.BList = BList;
    }

    public Doctor(String name) {
        this.name = name;
        this.patientHeap=new Heap();
    }

    public ArrayList<OneRecord> getMyPatients() {
        return myPatients;
    }

    public void setMyPatients(ArrayList<OneRecord> myPatients) {
        this.myPatients = myPatients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Heap getPatientHeap() {
        return patientHeap;
    }

    @Override
    public String toString() {
        return  name ;
    }
}
