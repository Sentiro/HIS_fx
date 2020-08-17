package controller;

import Data.Hospital.Medicine;
import Data.Others.FileUse;
import Data.Others.item;
import Data.Patient.OneRecord;
import Data.Patient.Record;
import Data.RecordData;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jfxtras.styles.jmetro8.JMetro;
import logic.LogicSetStatus;
import logic.Login;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.fxml.FXMLLoader.load;

public class Refund implements Initializable {

    @FXML
    private TableColumn<item, ?> timeColumn;

    @FXML
    private TableColumn<item, ?> nameColumn;

    @FXML
    private TableColumn<item, ?> statusColumn;

    @FXML
    private TableView<item> itemTable;

    @FXML
    private TableColumn<item, ?> IdColumn;

    @FXML
    private TableColumn<item, ?> itemColumn;

    @FXML
    private TextField sum;

    @FXML
    private TextField ID;

    @FXML
    private TextField id;

    @FXML
    private TableColumn<item, ?> numberColumn;

    @FXML
    private Button refundButton;

    @FXML
    private TableColumn<item, ?> priceColumn;
    RecordData recordData=RecordData.getInstance();
    LogicSetStatus logicSetStatus = LogicSetStatus.getInstance();
    Login logicLogin=Login.getInstance();
    public Refund() throws IOException {


    }
    public void initialize(URL location, ResourceBundle resources) {
        itemTable.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<item>() {
            @Override
            public void onChanged(Change<? extends item> c) {
                calculateTempSumPrice();
            }
        });
    }

    @FXML
    void showItems(ActionEvent event) {
        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        List<item> items = new ArrayList<>();
        int ID = Integer.parseInt(id.getText());
        System.out.println(ID);
        Record tempRecord = recordData.getRecords().getV(ID);
        System.out.println(tempRecord);
        for (OneRecord oneRecord : tempRecord.getMyRecord()) {
            System.out.println(oneRecord);
            System.out.println(oneRecord.getMedicines());

            if (oneRecord.getMedicines() != null) {
                for (Medicine m : oneRecord.getMedicines()) {
                    System.out.println(m);
                    if (m.getMedicineStatus().equals("已缴费")) {
                        item tempItem = new item();
                        tempItem.setId(id.getText());
                        tempItem.setName(tempRecord.getOwner().getName());
                        tempItem.setItemName(m.getName());
                        tempItem.setNumber(m.getNumbers());
                        tempItem.setStatus("已缴费");
                        tempItem.setTime(dtf.format(oneRecord.getTime()));
                        tempItem.setPrice(String.valueOf(m.getPrice()));
                        items.add(tempItem);
                    }
                }

            }
        }

        //得到一个fx格式的ArrayList 并对行和列进行绑定
        ObservableList<item> tempItemsArrayList = FXCollections.observableArrayList(items);
        IdColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        itemTable.setItems(tempItemsArrayList);

        itemTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        itemTable.getSelectionModel().setCellSelectionEnabled(false);
    }

    //点击退号时切换页面
    public void turnToReturnPage(){
        Stage sign=(Stage) ID.getScene().getWindow();
        sign.setResizable(false);
        Parent in = null;
        try {
            in = load(getClass().getResource("/view/doctorR/ReturnRegister.fxml"));
            new JMetro(JMetro.Style.LIGHT).applyTheme(in);
            Scene scene=new Scene(in, 1208, 800);
            sign.setScene(scene);
            sign.setTitle("东软云HIS系统");
            sign.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //点击缴费时切换页面
    public void turnToChargePage(){
        Stage sign=(Stage) ID.getScene().getWindow();
        sign.setResizable(false);
        Parent in = null;
        try {
            in = load(getClass().getResource("/view/doctorR/Charge.fxml"));
            new JMetro(JMetro.Style.LIGHT).applyTheme(in);
            Scene scene=new Scene(in, 1208, 800);
            sign.setScene(scene);
            sign.setTitle("东软云HIS系统");
            sign.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    //切换挂号页面
    public void turnToRegisterPage() {
        Stage sign = (Stage) ID.getScene().getWindow();
        sign.setResizable(false);
        Parent in = null;
        try {
            in = load(getClass().getResource("/view/doctorR/Register.fxml"));
            new JMetro(JMetro.Style.LIGHT).applyTheme(in);
            Scene scene = new Scene(in, 1208, 800);
            sign.setScene(scene);
            sign.setTitle("东软云HIS系统");
            sign.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    void refund(ActionEvent event) throws IOException {
        Iterator<item> itemIterator=  itemTable.getSelectionModel().getSelectedItems().iterator();
        while(itemIterator.hasNext()){
            item tempMedicineItem=itemIterator.next();
            System.out.println(tempMedicineItem);
            String medicineName=tempMedicineItem.getItemName();
            String medicineTime=tempMedicineItem.getTime();
            int recordID= Integer.parseInt(tempMedicineItem.getId());
            logicSetStatus.setSpecificMedicinePayedInOneRecord(recordID,medicineName,medicineTime,"已退费");

        }
        itemTable.getItems().removeAll(itemTable.getSelectionModel().getSelectedItems());
        //更新文件内容
        FileUse.writeRecords("Records.txt",recordData.getRecords());
        System.out.println("duwenjjian ");
      //  FileUse.readFileO("Records.txt");
        System.out.println("添加成功");
        logicLogin.showSuccessfulPage();
    }

    public void calculateTempSumPrice(){
        float sumPrice=0;
        Iterator<item> itemIterator=itemTable.getSelectionModel().getSelectedItems().iterator();
        while(itemIterator.hasNext()){
            sumPrice=sumPrice+ Float.parseFloat(itemIterator.next().getPrice());
        }
        System.out.println(sumPrice);
        sum.setText(String.valueOf(sumPrice));
    }

}
