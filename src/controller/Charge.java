package controller;;
import Data.Others.FileUse;
import Data.Hospital.Medicine;
import Data.Others.item;
import Data.Patient.OneRecord;
import Data.Patient.Record;
import Data.RecordData;
import com.alibaba.fastjson.JSON;
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

public class Charge  implements Initializable {
    RecordData recordData=RecordData.getInstance();
   LogicSetStatus logicSetStatus = LogicSetStatus.getInstance();
    @FXML
    private Button add;
    @FXML
    private TableView<item> itemTable;

    @FXML
    private TableColumn<item, ?> timeColumn;

    @FXML
    private TextField actualAmount;

    @FXML
    private TextField change;

    @FXML
    private TextField dueAmount;

    @FXML
    private TableColumn<item, ?> nameColumn;

    @FXML
    private TableColumn<item, ?> statusColumn;

    @FXML
    private TableColumn<item, ?> IdColumn;

    @FXML
    private TableColumn<item, ?> itemColumn;

    @FXML
    private TextField id;

    @FXML
    private TextField ID;

    @FXML
    private TableColumn<item, ?> numberColumn;

    @FXML
    private TableColumn<item, ?> priceColumn;
    Login logicLogin=Login.getInstance();
    public Charge() throws IOException {

    }
    // TODO 表格 文本框的预设值调整 如果没有查询到东西 要显示出来“没有相应记录”
    // TODO 美化页面和跳转 尝试设置一些跳出来的提示窗口 如“确认？？”
    //TODO 调整一些控件的焦点问题 更加人性化 如只有回车才能提交？？
    //TODO 8.24
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        itemTable.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<item>() {
                    @Override
                    public void onChanged(Change<? extends item> c) {
                        calculateTempSumPrice();
                    }
                });
    }

//页面切换功能
    @FXML
    //切换退号页面
    public void turnToReturnPage(){
        Stage sign=(Stage) add.getScene().getWindow();
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
    //切换挂号页面
    public void turnToRegisterPage(){
        Stage sign=(Stage) add.getScene().getWindow();
        sign.setResizable(false);
        Parent in = null;
        try {
            in = load(getClass().getResource("/view/doctorR/Register.fxml"));
            new JMetro(JMetro.Style.LIGHT).applyTheme(in);
            Scene scene=new Scene(in, 1208, 800);
            sign.setScene(scene);
            sign.setTitle("东软云HIS系统");
            sign.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    //点击退费时切换页面
    public void turnToRefundPage(){
        Stage sign=(Stage) add.getScene().getWindow();
        sign.setResizable(false);
        Parent in = null;
        try {
            in = load(getClass().getResource("/view/doctorR/Refund.fxml"));
            new JMetro(JMetro.Style.LIGHT).applyTheme(in);
            Scene scene=new Scene(in, 1208, 800);
            sign.setScene(scene);
            sign.setTitle("东软云HIS系统");
            sign.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

//根据患者信息或者身份证号检索到患者的缴费项目
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
                    if(m.getMedicineStatus().equals("已开立")){
                        tempItem.setStatus("未缴费");
                    }else{
                        tempItem.setStatus("已缴费");
                    }
                        tempItem.setTime(dtf.format(oneRecord.getTime()));
                        tempItem.setPrice(String.valueOf(m.getPrice()));
                        items.add(tempItem);

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


//缴费功能，点击后获取当前表格中被选择的条目，在数据库中将相应药的状态改为已缴费，
// 然后清空表格中被选择的条目，清空页面中的其他信息
    //最后将状态更新重新写入
    @FXML
    void pay(ActionEvent event) throws IOException {
        int target=0;
          Iterator<item> itemIterator=itemTable.getSelectionModel().getSelectedItems().iterator();
          while(itemIterator.hasNext()){
              item tempMedicineItem=itemIterator.next();
              System.out.println(tempMedicineItem);
              String medicineName=tempMedicineItem.getItemName();
              String medicineTime=tempMedicineItem.getTime();
              int recordID= Integer.parseInt(tempMedicineItem.getId());
              if(tempMedicineItem.getStatus().equals("已缴费")){
                  target=1;
                  break;
              }

              logicSetStatus.setSpecificMedicinePayedInOneRecord(recordID,medicineName,medicineTime,"已缴费");
          }
          if(target==1){
              logicLogin.showFailPage("/view/WarningCharge.fxml");
          }else{
              Record record=RecordData.getInstance().getRecords().getV(Integer.valueOf(id.getText()));

              itemTable.getItems().removeAll(itemTable.getSelectionModel().getSelectedItems());
              change.setText(null);
              dueAmount.setText(null);
              actualAmount.setText(null);
              //更新文件内容

              FileUse.writeRecords("Records.txt",recordData.getRecords());
              System.out.println("duwenjjian ");
              FileUse.readFile("Records.txt");
              System.out.println("添加成功");
              logicLogin.showSuccessfulPage();
              //跳转打印发票界面
          }


    }
    @FXML
    //根据药品选择情况计算总价钱
    public void calculateTempSumPrice(){
        float sumPrice=0;
        Iterator<item> itemIterator=itemTable.getSelectionModel().getSelectedItems().iterator();
        while(itemIterator.hasNext()){
            sumPrice=sumPrice+ Float.parseFloat(itemIterator.next().getPrice());
        }
        dueAmount.setText(String.valueOf(sumPrice));
    }
    @FXML
    //根据总价钱和输入的实际金额计算找零金额并显示 在输入实付金额后 回车调用
    public void calculateChange(){
        float shifu= Float.parseFloat(actualAmount.getText());
        float yingfu= Float.parseFloat(dueAmount.getText());
        change.setText(String.valueOf((shifu-yingfu)));
    }


}

