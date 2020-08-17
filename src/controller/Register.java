package controller;

import Data.Hospital.DiseaseNode;
import Data.Hospital.DiseaseTree;
import Data.Others.ConnectDB;
import Data.Others.FileUse;
import Data.Hospital.Department;
import Data.Hospital.Doctor;
import Data.HospitalData;
import Data.Others.RLevel;
import Data.Others.RLevelData;
import Data.Patient.OneRecord;
import Data.Patient.Patient;
import Data.Patient.Record;
import Data.RecordData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import jfxtras.styles.jmetro8.JMetro;
import logic.Login;
import logic.RegisterV;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.fxml.FXMLLoader.load;

/*
暂时问题 界面要再设计设计
如果没有输入全部信息 就不能进行下一步
 */
public class Register implements Initializable
{
    RegisterV logicRegister=RegisterV.getInstance();
    //获取所有病历本的信息
    private RecordData recordData=RecordData.getInstance();
    //一个病人的所有记录
    private Record Record;

    Login logicLogin=Login.getInstance();
    //挂号级别集合
    List<RLevel> RLevels;
    ObservableList levels;
    //科室集合
    List<Department> departments;

    private DiseaseTree tree;

    @FXML
    private Button add;

    @FXML
    private Button delete;

    @FXML
    private CheckBox a;

    @FXML
    private TextField address;

    @FXML
    private TextField charge;

    @FXML
    private ComboBox<String> gender;

    @FXML
    private ComboBox<RLevel> level;

    @FXML
    private TextField id=new TextField();

    @FXML
    private TextField invoice=new TextField();

    @FXML
    private TextField type;

    @FXML
    private DatePicker birthDate;

    @FXML
    private ComboBox<Doctor> doctor;

    @FXML
    private TextField name;

    @FXML
    private TextField ID;

    @FXML
    private ComboBox<Department> department;

    @FXML
    private TextField age;

    @FXML
    private ComboBox<String> ageType;
    int tempPrice;
    @FXML
    private Button b4;
    @FXML
    private Button b1;
    @FXML
    private Button b2;
    @FXML
    private Button b3;

    public Register() throws IOException {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        b1.setFont(Font.font (30));
//        b2.setStyle("-fx-font: 30 arial");
//        b3.setStyle("-fx-font: 30 Microsoft YaHei UI Light");
//        b4.setStyle("-fx-font: 24 Microsoft YaHei UI Light");
        //获取疾病树
        try {
            tree=HospitalData.getInstance().getTree();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        //设置挂号级别下拉框的内容
        RLevels= RLevelData.getInstance().getRLevels();
        System.out.println(RLevels.get(1));
        ObservableList levels = FXCollections.observableArrayList(RLevels);
        level.setItems(levels);

        //设置性别下拉框的内容
        ArrayList<String> g=new ArrayList<>();
        g.add("男");g.add("女");
        gender.setItems(FXCollections.observableArrayList(g));

        //设置科室下拉框的内容
        try {
            departments= HospitalData.getInstance().getDepartments1();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        //System.out.println(departments);
        department.setItems(FXCollections.observableArrayList(departments));

       //设置ageType下拉框的内容
        ArrayList<String> s=new ArrayList<>();
        s.add("岁");s.add("周岁");
        ageType.setItems(FXCollections.observableArrayList(s));


        //对控件的变化进行监听
        id.focusedProperty().addListener((observable, oldValue, newValue) -> {

            if(oldValue==true&&newValue==false){
                addBingliID();
            }
        });

        birthDate.focusedProperty().addListener((observable, oldValue, newValue) -> {

            if(oldValue==true&&newValue==false&&birthDate.getValue()!=null){
                addBirth();
            }

        });
        department.focusedProperty().addListener((observable, oldValue, newValue) -> {

            if(oldValue==true&&newValue==false){
                addDoctors();
            }
        });

        charge.focusedProperty().addListener((observable, oldValue, newValue) -> {

            if(oldValue==false&&newValue==true){
                calculateCharge();
            }
        });
    }



    //输入病例号的时候自动填写病人信息
    public void addBingliID() {

        //如果能找到病历本 就自动填充信息
        int tempID=Integer.parseInt(id.getText());
        //找到病历本
        Record=recordData.getRecords().getV(tempID);
        if(Record==null){
            //Record=new Record();
        }else{
            //得到病人信息
            Patient tempPatient=Record.getOwner();
         name.setText(tempPatient.getName());//显示名字
         ID.setText(tempPatient.getID());//身份证号
         gender.setValue(tempPatient.getGender());//性别
         //出生日期没法直接赋值
            birthDate.setValue(tempPatient.getBirthDate());
         age.setText(String.valueOf(tempPatient.getAge()));
         ageType.setValue(tempPatient.getAgeType());
         address.setText(tempPatient.getAddress());
        }
    }

   //输入生日的时候自动填写年龄
    @FXML
    void addBirth() {
         LocalDate birth=birthDate.getValue();
         if(birth==null){
             age.setText(null);
             return;
         }
         LocalDate now=LocalDate.now();
         int Age=now.getYear()-birth.getYear();
         age.setText(String.valueOf(Age));
    }
   //点击应收费用时自动填充
    public void calculateCharge(){
        tempPrice=logicRegister.calculatePrice(a.isSelected(),level.getValue());
        charge.setText(String.valueOf(tempPrice));
    }

    //按确认的时候将所得到的新信息建立对象并储存
    @FXML
    void add(ActionEvent event) throws IOException, SQLException {

        //新建挂号记录
        Data.Patient.Register newRegister=new Data.Patient.Register();
        newRegister.setInvoice(invoice.getText());
        newRegister.setChargeCategory("现金");
        newRegister.setDate(LocalDateTime.now());
        newRegister.setDepartment(department.getValue().getName());
        newRegister.setDoctor(doctor.getValue().getName());
        newRegister.setLevel(level.getValue());
        newRegister.setM(a.isSelected());
        //newRegister.setChargeCategory(type.getText());
        newRegister.setPrice(tempPrice);
        System.out.println(newRegister);
        //新建一次看病记录
        OneRecord oneRecord=new OneRecord();
        oneRecord.setRegister(newRegister);
        oneRecord.setStatus("未分诊");
        System.out.println(oneRecord.getStatus());
        //oneRecord.setType("xxx");
        if(level.getValue().getName().equals("急诊号")){
            oneRecord.setPatientType("急诊患者");
        }else oneRecord.setPatientType("普通患者");
        //System.out.println(oneRecord.getRegister());

        if(Record==null){
            //如果从来都没有记录 新建一个病历本
            Record=new Record();
            Record.setID(Integer.parseInt(id.getText()));
            //录入患者信息
            Patient patient=new Patient();
            patient.setID(ID.getText());
            patient.setName(name.getText());
            patient.setGender(gender.getValue());
            patient.setAge(Integer.parseInt(age.getText()));
            patient.setAgeType(ageType.getValue());
            patient.setBirthDate(birthDate.getValue());
            patient.setAddress(address.getText());
            //将患者信息加入
            Record.setOwner(patient);
            recordData.getRecords().put(Record.getID(),Record);
        }else{
            int tempID=Integer.parseInt(id.getText());
            Record=recordData.getRecords().getV(tempID);
            int size=Record.getMyRecord().size();
            OneRecord last=Record.getMyRecord().get(size-1);
            if(last.getEnd()!=true){
                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setContentText("当前患者未完成上一次诊断，操作失败");
                alert.showAndWait();
                return;
            }
        }
        //拿到病历本的看病记录 将这一次记录加入
        oneRecord.setRecord(Record);
        //System.out.println(Record);
        List<OneRecord> myRecord;
        if(Record.getMyRecord()==null){
            myRecord=new ArrayList<>();
        }else myRecord=Record.getMyRecord();
        myRecord.add(oneRecord);
        Record.setMyRecord(myRecord);
        System.out.println(Record);
        //将这个病例本加到所有病历本中
        /*String str=department.getValue().getName();
        DiseaseNode node=tree.fetch(str);
        node.getPatients().add(id.getText());*/
       // System.out.println(tree.fetch(department.getValue().getName()).getPatients().toString());
       // if((invoice.getText()||id.getText()||name.getText()||ID.getText()||gender.getValue()||ageType.getValue()||age.getText()||birthDate.getValue()||address.getText()||level.getValue()||department.getValue()||doctor.getValue()||type.getText()||charge.getText())==null) {
       FileUse.writeRecords("Records.txt",recordData.getRecords());
       // FileUse.readFileO("Records.txt");
        ConnectDB.connectDB();
        //in p_shenfenID varchar(255),in p_name varchar(45),p_sex varchar(255),p_birth date,in p_age int,in p_ageType varchar(45),in p_addr varchar(255),
        //in p_date date,in p_wubie varchar(255),in p_doctorID int,in p_regLevel int,in p_regType varchar(255),
        //in bingli varchar(255),in p_registrarID int,
        //out result varchar(255)
        int regLevel=0;
        if(level.getValue().getName().equals("急诊号")){
            regLevel=3;
        }else if(level.getValue().getName().equals("专家号")){
            regLevel=2;
        }else regLevel=1;
        String bingli="否";
        if(a.isSelected()){
            bingli="是";
        }
        String s=ConnectDB.registerDB(ID.getText(),name.getText(),gender.getValue(),birthDate.getValue(),Integer.parseInt(age.getText()),ageType.getValue(),address.getText(),
                LocalDate.now(),"上午",1,regLevel,"现金",bingli,9
        );
        System.out.println(s);
        logicLogin.showSuccessfulPage();

    }

    //鼠标点到别处时 根据已选择的department 设置不同的doctor内容
    public void addDoctors() {
        ArrayList<Doctor> doctors=new ArrayList<>();
        //doctors=department.getValue().getDoctors();
        if(department.getValue()==null){
            doctor.setItems(null);
        }else
            doctors=department.getValue().getDoctors();
        doctor.setItems(FXCollections.observableArrayList(doctors));
    }

    //判断挂号信息填写是否完整
//    public boolean check(){
//        if(invoice.getText()==null){
//            invoice.setText("请填写发票号");
//        }
//        if(id.getText()==null){
//            id.setText("请填写");
//        }
//    }
    //一键清空所有填写的内容
    public void deleteAll(){
        invoice.setText(null);
        id.setText(null);
        name.setText(null);
        ID.setText(null);
        gender.setValue(null);
        ageType.setValue(null);
        age.setText(null);
        birthDate.setValue(null);
        address.setText(null);
        level.setValue(null);
        department.setValue(null);
        doctor.setValue(null);
        a.setSelected(false);
        type.setText(null);
        charge.setText(null);
    }

    //点击退号时切换页面
    public void turnToReturnPage(){
        Stage sign=(Stage) a.getScene().getWindow();
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
         Stage sign=(Stage) a.getScene().getWindow();
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
        Stage sign=(Stage) a.getScene().getWindow();
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
