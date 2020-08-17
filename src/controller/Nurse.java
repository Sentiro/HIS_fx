package controller;

import Data.Hospital.Department;
import Data.Hospital.Doctor;
import Data.HospitalData;
import Data.Others.FileUse;
import Data.Patient.OneRecord;
import Data.Patient.Record;
import Data.RecordData;
import Data.Structure.Heap;
import Data.UserData;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import jfxtras.styles.jmetro8.JMetro;
import logic.LogicDoctorM;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Nurse implements Initializable {

    @FXML
    private Button search;



    @FXML
    private Label gender;

    @FXML
    private Button submit;

    @FXML
    private Accordion doctorList;

    @FXML
    private Label medicineID;

    @FXML
    private Label name;

    @FXML
    private Label ID;

    @FXML
    private Label type;

    @FXML
    private TextField textField;
    @FXML
    private Label department;
    @FXML
    private Label doctor;
    @FXML
    private javafx.scene.image.ImageView ImageView;
    @FXML
    private ComboBox<String> typeBox;

    ArrayList<Department> departments=null;
    private HospitalData hospitalData=HospitalData.getInstance();
    private RecordData recordData=RecordData.getInstance();
    private ArrayList<OneRecord> tempRecords;
    private Department currentDepartment;
    private String currentNurseName;
    private ArrayList<Doctor> doctors;
    LogicDoctorM doctorM=LogicDoctorM.getInstance();
    private OneRecord tempOneRecord;
    private Doctor tempDoctor;

    public Nurse() throws IOException, ClassNotFoundException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String > typeBoxTemp=new ArrayList<>();
        typeBoxTemp.add("普通"); typeBoxTemp.add("复诊"); typeBoxTemp.add("加急");
        typeBox.setItems(FXCollections.observableArrayList(typeBoxTemp));
        typeBox.setValue("普通");
        //确定当前科室和登录的护士账号,获取科室下的病人，获取科室下的医生

        int ID = LoginC.ID;
        ArrayList<Data.User.Nurse> nurses= UserData.getInstance().getNurse();

        departments= hospitalData.getDepartments();

        for(int i=0;i<nurses.size();i++){
            if(nurses.get(i).getID()==ID){
                currentNurseName=nurses.get(i).getName();
               // System.out.println(currentNurseName);
            }
        }
        for(int i=0;i<departments.size();i++){
            for(int j=0;j<departments.get(i).getDoctors().size();j++){
                if(departments.get(i).getDoctors().get(j).getName().equals(currentNurseName)){
                    currentDepartment=departments.get(i);
                   // System.out.println(currentDepartment);
                }
            }
        }
        doctors=currentDepartment.getDoctors();
        tempRecords=doctorM.getOneRecordsByDepartment(currentDepartment.getName());
        System.out.println(tempRecords);

        //确定doctorList控件的内容
        for(int i =0;i<doctors.size();i++){
            if(doctors.get(i).getName().equals("分诊医生")) return;
            //新建表格
            TableView patientTable=new TableView();
            TableColumn name_column=new TableColumn<OneRecord,String>("姓名");
            TableColumn medicineID_column=new TableColumn<OneRecord,String>("病历号");
            TableColumn type_column=new TableColumn<OneRecord,String>("类型");
            patientTable.getColumns().setAll(name_column,medicineID_column,type_column);
            name_column.setCellValueFactory(new PropertyValueFactory<>("name"));
            medicineID_column.setCellValueFactory(new PropertyValueFactory<>("Id"));
            type_column.setCellValueFactory(new PropertyValueFactory<>("patientType"));
            ArrayList<OneRecord> tempPatients= doctorM.myPatientOneRecords(doctors.get(i));
            doctors.get(i).getPatientHeap().build(tempPatients);
            if(tempPatients==null){

            }else{
                patientTable.setItems(FXCollections.observableArrayList(tempPatients));
            }
            patientTable.setId(doctors.get(i).getName());
            TitledPane pane=new TitledPane(doctors.get(i).getName(),patientTable);
            doctorList.getPanes().add(pane);
        }
    }

    public void search(){
        String tempID=textField.getText();
        //System.out.println(tempID);
        for (OneRecord a:tempRecords
             ) {
           // System.out.println(a.getId());
            if(String.valueOf(a.getId()).equals(tempID)){
                //显示病人信息
                Image image=null;
                if(a.getGender().equals("女")){
                  // File f = new File();
                   //String url = f.toURI().toString();
                    image=new Image("file:../../女1.jpg");
                }else{
                    image=new Image("file:../../男1.jpg");
                }
                ImageView.setImage(image);
                name.setText(a.getName());
                ID.setText(a.getID());
                medicineID.setText(String.valueOf(a.getId()));
                gender.setText(a.getGender());
                department.setText(a.getRegister().getDepartment());
                doctor.setText(a.getRegister().getDoctor());
                type.setText(a.getPatientType());
                tempOneRecord=a;
                for(Doctor d: doctors){
                    if(a.getRegister().getDoctor().equals(d.getName())){
                        tempDoctor=d;
                        System.out.println("现在输出doctor.getMyPatients+\n"+tempDoctor.getMyPatients());
                    }
                }

                return;
            }
        }
        Alert alert=new Alert(Alert.AlertType.ERROR);
        new JMetro(JMetro.Style.LIGHT).applyTheme(alert.getDialogPane());
        alert.setContentText("无法找到该患者，请重试");
        alert.showAndWait();
    }

    public void submit() throws IOException {
        /*Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("确认对该患者进行分诊？");
        alert.showAndWait();*/
        //患者加到表格里 并排序
        tempDoctor.getPatientHeap().build(doctorM.myPatientOneRecords(tempDoctor));
        tempDoctor.getMyPatients().addAll(doctorM.myPatientOneRecords(tempDoctor));
        tempDoctor.getMyPatients().add(tempOneRecord);
        tempDoctor.getAList().insert(tempOneRecord);
        tempDoctor.getBList().insert(tempOneRecord);

       // currentDepartment.getPatients().add(tempOneRecord);
        if(tempOneRecord.getStatus().equals("未分诊")&&typeBox.getValue().equals("复诊")){
            Alert alert=new Alert(Alert.AlertType.WARNING,"第一次看诊的患者无法设置未复诊患者");
            new JMetro(JMetro.Style.LIGHT).applyTheme(alert.getDialogPane());
            alert.showAndWait();
            return;
        }
        if(tempOneRecord.getStatus().equals("检查中")&&typeBox.getValue().equals("复诊")) tempOneRecord.setPatientType("复诊患者");
        if(typeBox.getValue().equals("加急")) tempOneRecord.setPatientType("急诊患者");
        int priority=calculatePriority(tempOneRecord,LocalDateTime.now());
        tempOneRecord.setPriority(priority);
        System.out.println(tempOneRecord.getId()+" "+priority);
        tempDoctor.getPatientHeap().insert(tempOneRecord);
        ArrayList<OneRecord> tempSort=tempDoctor.getPatientHeap().getPatientList();
        tempSort=tempDoctor.getPatientHeap().sort(tempSort);


        //ArrayList<OneRecord> result=tempDoctor.getPatientHeap().getPatientList();
        for(int i=0;i<doctorList.getPanes().size();i++){
            if(doctorList.getPanes().get(i).getText().equals(tempDoctor.getName())){
                doctorList.getPanes().get(i).setExpanded(true);
                TableView tempTable=(TableView) doctorList.getPanes().get(i).getContent();
                tempTable.setItems(FXCollections.observableArrayList(tempSort));
                doctorList.getPanes().get(i).setContent(tempTable);
                break;
            }
        }
        //将数据更新到department的数据中
        for(int i=0;i<departments.size();i++){
            for(int j=0;j<departments.get(i).getDoctors().size();j++){
                if(departments.get(i).getDoctors().get(j).getName().equals(currentNurseName)){
                    departments.set(i,currentDepartment);
                    hospitalData.setDepartments(departments);
                    // System.out.println(currentDepartment);
                }
            }
        }
        //将数据更新到records中


        Record tempRecord=recordData.getRecords().getV(tempOneRecord.getId());
        tempRecord.getMyRecord().remove(tempOneRecord);
        tempOneRecord.setStatus("未诊断");
        tempRecord.getMyRecord().add(tempOneRecord);
        recordData.getRecords().put(tempOneRecord.getId(),tempRecord);

//           FileUse.writeDepartments("department.txt",hospitalData.getDepartments());
//           FileUse.writeRecords("Records.txt",recordData.getRecords());
    }



    public int calculatePriority(OneRecord record, LocalDateTime now){
        int time=now.getSecond()+now.getMinute()*60+now.getHour()* 3600;
        if(record.getPatientType().equals("普通患者")){
            return 24*60*60-time;
        }else if(record.getPatientType().equals("急诊患者")){
            return 24*60*60-time+60*60;
        }else {
           // System.out.println(2000-list.indexOf(record)*5+23);
            return (24*60*60-time+30);
        }
    }
}
