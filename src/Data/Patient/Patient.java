package Data.Patient;

import javafx.beans.property.StringProperty;

import java.io.Serializable;
import java.time.LocalDate;

public class Patient  implements Serializable {
   private String name;
   private int age;
   private String gender;//性别
   private String ageType;//年 周 天
   private LocalDate birthDate;//出生日期
    private String ID;//身份证号
    private String address;//家庭住址
    private int priority;
    private String Id;//病历号


    public Patient(String ID, String name, int age, String gender, String ageType, LocalDate birthDate, String address) {
        this.name=name;
        this.age = age;
        this.gender = gender;
        this.ageType = ageType;
        this.birthDate = birthDate;
        this.ID=ID;
        this.address = address;
    }

    public Patient() {

    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAgeType() {
        return ageType;
    }

    public void setAgeType(String ageType) {
        this.ageType = ageType;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID=ID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

//    private void addRegister(Register a){
//        registerList.add(a);
//    }
//  //修改挂号信息
//    private void changeRegister(){
//
//    }

    @Override
    public String toString() {
        return "Patient{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", ageType='" + ageType + '\'' +
                ", birthDate=" + birthDate +
                ", ID='" + ID + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
