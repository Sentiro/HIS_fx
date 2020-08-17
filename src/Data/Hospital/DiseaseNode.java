package Data.Hospital;

import java.io.Serializable;
import java.util.Vector;

public class DiseaseNode implements Serializable {
    private String ID;
   private String name;
   private String parentID;
   private String parentName;
    private DiseaseNode Parent;
    Vector<DiseaseNode> sub_diseases;
    Vector<String> patients;

    public DiseaseNode(String name) {
        this.name = name;
        sub_diseases=new Vector<>();
        patients=new Vector<>();
    }

    public DiseaseNode() {
        sub_diseases=new Vector<>();
        patients=new Vector<>();
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public DiseaseNode getParent() {
        return Parent;
    }

    public void setParent(DiseaseNode parent) {
        Parent = parent;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        parentID = parentID;
    }

    public Vector<DiseaseNode> getSub_diseases() {
        return sub_diseases;
    }

    public void setSub_diseases(Vector<DiseaseNode> sub_diseases) {
        this.sub_diseases = sub_diseases;
    }

    public Vector<String> getPatients() {
        return patients;
    }

    public void setPatients(Vector<String> patients) {
        this.patients = patients;
    }

    @Override
    public String toString() {
        return "DiseaseNode{" +
                "ID='" + ID + '\'' +
                ", name='" + name + '\'' +
                ", parentName='" + parentName + '\'' +
                ", Parent=" + Parent +
                ", sub_diseases=" + sub_diseases +
                ", patients=" + patients +
                '}';
    }
}
