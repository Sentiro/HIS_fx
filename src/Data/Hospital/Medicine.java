package Data.Hospital;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

public class Medicine implements Serializable {
    //药品编码（唯一）、药品名称、拼音助记码、药品规格、剂型（针剂、片剂、胶囊等）、包装单位、单价、药品类型(西药、中成药、中草药)等。

    private String name;//药品名称
    private String form;//规格
    private String ID;//药品编码
    private String pinyin;//拼音助记码
    private String type;//类型
    private float price;
    //用于开药的时候记录药品的具体信息 如用法 频次 数量
//    private final StringProperty  usage=new SimpleStringProperty();
//    private final StringProperty useTimes=new SimpleStringProperty();
//    private final StringProperty numbers=new SimpleStringProperty();
    private String usage;
    private String useTimes;
    private String numbers;
    private String medicineStatus="已开立";//药品状态 已开立 已缴费 已取药
    private int totalNumber;

    public Medicine(){

    }
    //版本一
    public Medicine(String ID, String name, String pinyin,int price) {
        this.ID = ID;
        this.name = name;
        this.pinyin = pinyin;
        this.price=price;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getMedicineStatus() {
        return medicineStatus;
    }

    public void setMedicineStatus(String medicineStatus) {
        this.medicineStatus = medicineStatus;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getUseTimes() {
        return useTimes;
    }

    public void setUseTimes(String useTimes) {
        this.useTimes = useTimes;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }
//    public String getUsage() {
//        if(usage==null){
//        return  " ";
//    }else
//        return usage.get();
//    }
//
//    public void setUsage(String usage) {
//            this.usage.set(usage);
//    }
////    public StringProperty UsageProperty() {
////        return usage;
////    }
//    public String getUseTimes() {
//        return useTimes.get();
//    }
//
//    public void setUseTimes(String useTimes) {
//        this.useTimes.set(useTimes);
//    }
//
//    public String  getNumbers() {
//        return numbers.get();
//    }
//
//    public void setNumbers(String  numbers) {
//        this.numbers.set(numbers);
//    }

    @Override
    public String toString() {
        return  name+" "+form+" "+ ID  +" "+pinyin+" "+ type+" "+  price;
    }
}
