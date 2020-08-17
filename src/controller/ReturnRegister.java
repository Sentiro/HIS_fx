package controller;
import Data.Others.FileUse;
import Data.Patient.OneRecord;
import Data.Patient.Record;
import Data.RecordData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jfxtras.styles.jmetro8.JMetro;
import logic.Login;
import logic.RegisterV;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.fxml.FXMLLoader.load;

public class ReturnRegister  implements Initializable
{

    @FXML
    private AnchorPane ReturnRegister;

    @FXML
    private Button delete;

    @FXML
    private TableColumn<Record, String> IDCol;

    @FXML
    private TableColumn<?, ?> choose;

    @FXML
    private TableColumn<Record, Integer> idCol;

    @FXML
    private TableColumn<OneRecord, String> nameCol;

    @FXML
    private TextField name;

    @FXML
    private TableView<OneRecord> registers;

    @FXML
    private TextField id;

    @FXML
    private TableColumn<Record, LocalDateTime> time;

    @FXML
    private TextField ID;

    @FXML
    private TableColumn<Record, String> department;

    @FXML
    private TableColumn<Record, String> status;
    //获取所有病历本的信息
    private RecordData recordData=RecordData.getInstance();
    Login logicLogin=Login.getInstance();

    public ReturnRegister() throws IOException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    //填充列表 先通过输入的病历号定位到某个患者的病例 然后得到患者的信息及挂号记录
    void getRegisters(ActionEvent event) throws IOException {
          Record record= recordData.getRecords().getV(Integer.parseInt(id.getText()));//得到病例
          List<OneRecord> many=record.getMyRecord();//一个人的很多次挂号记录的集合
          ObservableList<OneRecord> data0 = FXCollections.observableArrayList(many);
           //设置每一列的信息
           nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
           IDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));//身份证
           idCol.setCellValueFactory(new PropertyValueFactory<>("Id"));//病历号
           time.setCellValueFactory(new PropertyValueFactory<Record, LocalDateTime>("date"));
           department.setCellValueFactory(new PropertyValueFactory<Record,String>("department"));
           status.setCellValueFactory(new PropertyValueFactory<Record,String>("status"));
           registers.setItems(data0);
    }

    public void handleDeletePerson() throws IOException {
        int selectedIndex = registers.getSelectionModel().getSelectedIndex();
        System.out.println(selectedIndex);
        //判断是否还未看诊
        if(registers.getItems().get(selectedIndex).getRegister().isStatus()==false){
            //根据表上的被选中行的index 得到对应的OneRecord value 再根据这个value 得到系统数据中的index
            OneRecord tempOneRecord=registers.getItems().get(selectedIndex);
            registers.getItems().remove(selectedIndex);
            Record record= recordData.getRecords().getV(Integer.parseInt(id.getText()));//得到病例
            int index= RegisterV.getInstance().getIndex(tempOneRecord,record.getMyRecord());
            record.getMyRecord().remove(index);//删除记录
            FileUse.writeRecords("Records.txt",recordData.getRecords());
            System.out.println("duwenjjian ");
            //FileUse.readFileO("Records.txt");
            logicLogin.showSuccessfulPage();
        }else{
            System.out.println("不能选择");
            logicLogin.showFailPage("/view/WarningReturnR.fxml");
        }

    }

    //切换挂号页面
    public void turnToRegisterPage(){
        Stage sign=(Stage) delete.getScene().getWindow();
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

    //点击缴费时切换页面
    public void turnToChargePage(){
        Stage sign=(Stage) delete.getScene().getWindow();
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

    //点击退费时切换页面
    public void turnToRefundPage(){
        Stage sign=(Stage) delete.getScene().getWindow();
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


}

