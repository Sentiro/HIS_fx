package controller;

import Data.Others.FileUse;
import Data.Hospital.Medicine;
import Data.Others.item;
import Data.Patient.OneRecord;
import Data.Patient.Record;
import Data.RecordData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jfxtras.styles.jmetro8.JMetro;
import logic.LogicSetStatus;
import logic.Login;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static javafx.fxml.FXMLLoader.load;

public class MedicineDistribution {
    RecordData recordData=RecordData.getInstance();
    LogicSetStatus logicSetStatus = LogicSetStatus.getInstance();
    @FXML
    private TableColumn<?, ?> timeColumn;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private TableColumn<?, ?> statusColumn;

    @FXML
    private TableView<item> itemTable;

    @FXML
    private TableColumn<?, ?> IdColumn;

    @FXML
    private TableColumn<?, ?> itemColumn;

    @FXML
    private TextField ID;

    @FXML
    private TextField id;

    @FXML
    private TableColumn<?, ?> numberColumn;

    @FXML
    private TableColumn<?, ?> priceColumn;

    Login logicLogin=Login.getInstance();

    public MedicineDistribution() throws IOException {

    }
    private static Stage Warning=new Stage();


    //缴费功能，点击后获取当前表格中被选择的条目，在数据库中将相应药的状态改为已取药，
    //然后清空表格中被选择的条目，清空页面中的其他信息
    //最后将状态更新重新写入
    @FXML
    void setStatus(ActionEvent event) throws IOException {
        int flag=0;
        Iterator<item> itemIterator=itemTable.getSelectionModel().getSelectedItems().iterator();
        while(itemIterator.hasNext()){
            item tempMedicineItem=itemIterator.next();
            //System.out.println(tempMedicineItem);
            if (tempMedicineItem.getStatus().equals("已缴费")) {
                flag = 1;
                break;
            }

        }
        if(flag==0){
            Parent rootSucceed = FXMLLoader.load(getClass().getResource("/view/Warning.fxml"));
            Warning.setScene(new Scene(rootSucceed, 600, 400));
            Warning.setTitle("取药失败");
            Warning.show();

            //Warning.close();
        }else{
            Iterator<item> itemIterator1=itemTable.getSelectionModel().getSelectedItems().iterator();
            while(itemIterator1.hasNext()){
                item tempMedicineItem=itemIterator1.next();
                //System.out.println(tempMedicineItem);
                    String medicineName=tempMedicineItem.getItemName();
                    String medicineTime=tempMedicineItem.getTime();
                    int recordID= Integer.parseInt(tempMedicineItem.getId());
                    logicSetStatus.setSpecificMedicinePayedInOneRecord(recordID,medicineName,medicineTime,"已取药");
                }
            itemTable.getItems().removeAll(itemTable.getSelectionModel().getSelectedItems());
            //更新文件内容
            FileUse.writeRecords("Records.txt",recordData.getRecords());
            System.out.println("duwenjjian ");
            //FileUse.readFile("Records.txt");
            System.out.println("添加成功");
            logicLogin.showSuccessfulPage();
        }

    }
    //根据患者信息或者身份证号检索到患者的已缴费项目
    @FXML

    void showItems(ActionEvent event) {
        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
//        List<Medicine> medicines=new ArrayList<>();
//        List<OneRecord> oneRecords=new ArrayList<>();
        //得到一个正常通用格式的ArrayList,用来储存表格里每一行的信息
        List<item> items=new ArrayList<>();
        int ID= Integer.parseInt(id.getText());
        System.out.println(ID);
        Record tempRecord=recordData.getRecords().getV(ID);
        System.out.println(tempRecord);
        for (OneRecord oneRecord:tempRecord.getMyRecord()) {
            System.out.println(oneRecord);
            System.out.println(oneRecord.getMedicines());
            if(oneRecord.getMedicines()!=null) {
                for (Medicine m : oneRecord.getMedicines()) {
                    System.out.println(m);
                    item tempItem = new item();
                    tempItem.setId(id.getText());
                    tempItem.setName(tempRecord.getOwner().getName());
                    tempItem.setItemName(m.getName());
                    tempItem.setNumber(m.getNumbers());
                    tempItem.setTime(dtf.format(oneRecord.getTime()));
                    tempItem.setPrice(String.valueOf(m.getPrice()));
                    if(oneRecord.getStatus().equals("已缴费")){
                        tempItem.setStatus("已缴费");
                    }else if(m.getMedicineStatus().equals("已取药")){
                        tempItem.setStatus("已取药");
                    }else{
                        tempItem.setStatus("未缴费");
                    }
                    items.add(tempItem);
                }
                oneRecord.setStatus("已取药");
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

//        ListChangeListener<item> indicesListener = new   ListChangeListener<item>() {
//            @Override public void onChanged(Change<? extends item> c) {
//                while (c.next()) {
//                    selectionUpdated(c.getAddedSubList(), c.getRemoved());
//                }
//            }
//        };

        // itemTable.getSelectionModel().getSelectedItems().addListener(indicesListener);
        itemTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        itemTable.getSelectionModel().setCellSelectionEnabled(false);

    }
}
