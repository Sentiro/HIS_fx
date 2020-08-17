package controller;

import Data.Hospital.*;
import Data.HospitalData;
import Data.Structure.MyHashMap;
import Data.Others.FileUse;
import Data.Patient.OneRecord;
import Data.Patient.Patient;
import Data.Patient.Record;
import Data.RecordData;
import com.alibaba.fastjson.JSON;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jfxtras.styles.jmetro8.JMetro;
import logic.LogicDoctorM;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.fxml.FXMLLoader.load;

public class DoctorC implements Initializable {
    @FXML
    private Label details;

    @FXML
    private Button next;

    @FXML
    private Text gendar;

    @FXML
    private Text birthday;

    @FXML
    private TextArea history1;

    @FXML
    private TextField zhusu;

    @FXML
    private TextArea history2;

    @FXML
    private TableView<OneRecord> untreatedPatientTable;

    @FXML
    private Button submit;

    @FXML
    private TableView<DiseaseNode> diseaseTable;

    @FXML
    private Button addDisease;

    @FXML
    private TextArea history;

    @FXML
    private ComboBox<String> TypeBox;

    @FXML
    private TableView<OneRecord> treatedPatientTable;

    @FXML
    private Text name;

    @FXML
    private Button removeDisease;

    @FXML
    private VBox vbox;

    @FXML
    private Pane profile;

    @FXML
    private Text address;

    @FXML
    private Text ID;

    @FXML
    private TableColumn<OneRecord, String> name_untreated;

    @FXML
    private TableColumn<OneRecord, String> name_table;

    @FXML
    private TableColumn<OneRecord, String> medicineID_untreated;

    @FXML
    private TableColumn<OneRecord, String> medicineID;

    @FXML
    private TableColumn<DiseaseNode, String> diseaseName;

    @FXML
    private TableColumn<DiseaseNode, String> ICD_table;

    @FXML
    private Text IDNumber;

    @FXML
    private Tab examination;

    @FXML
    private TabPane tabPane;

    @FXML
    private Text time;

    @FXML
    private Text zhusuText;

    @FXML
    private Text type;

    @FXML
    private Text isOneRecord;

    @FXML
    private TableColumn<Data.Hospital.Examination, String> departmentE;

    @FXML
    private TableColumn<Data.Hospital.Examination, String> numberE;

    @FXML
    private TableColumn<Data.Hospital.Examination, String> nameE;

    @FXML
    private TableColumn<Data.Hospital.Examination, String> priceE;

    @FXML
    private TableColumn<Data.Hospital.Examination, String> statusE;

    @FXML
    private TableView<Data.Hospital.Examination> examination_table;

    @FXML
    private TableColumn<OneRecord, String> departmentM;

    @FXML
    private TableColumn<OneRecord, String> timeM;

    @FXML
    private TableColumn<OneRecord, String> IDM;

    @FXML
    private TableColumn<OneRecord, String> nameM;

    @FXML
    private TableColumn<OneRecord, String> statusM;

    @FXML
    private TableView<OneRecord> makeSureTable;

    @FXML
    private TableColumn<Mould, String> nameMould;

    @FXML
    private TableColumn<Mould, String> statusMould;

    @FXML
    private TableView<Mould> mouldTable;

    @FXML
    private TableView<Medicine> medicineTable;

    @FXML
    private TableColumn<Medicine, String> medicineName;

    @FXML
    private TableColumn<Medicine, String> medicineForm;

    @FXML
    private TableColumn<Medicine, String> medicinePrice;

    @FXML
    private TableColumn<Medicine, String> medicineNumbers;

    @FXML
    private TableColumn<Medicine, String> medicineTID;

    @FXML
    private ListView<String> myOneRecordList;



    LogicDoctorM doctorM=LogicDoctorM.getInstance();
    Doctor doctor;
    ArrayList<OneRecord> myOneRecords=new ArrayList<>();
    ArrayList<DiseaseNode> diseases=new ArrayList<>();
    ArrayList<Data.Hospital.Examination> examinations=new ArrayList<>();
    OneRecord current;
    OneRecord clickCurrent;
    HospitalData hospitalData=HospitalData.getInstance();
    ArrayList<OneRecord> patientsUntreated;
    ArrayList<OneRecord> patientsTreated;
    ArrayList<Medicine> allMedicine= (ArrayList<Medicine>) hospitalData.getMedicines();
    public DoctorC() throws IOException, ClassNotFoundException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //根据登录信息确定医生
        int ID = LoginC.ID;
        doctor = doctorM.getDoctorThroughID(ID);

        //设置TypeBox的内容
        ArrayList<String> s = new ArrayList<>();
        s.add("中医诊断");
        s.add("西医诊断" );
        TypeBox.setItems(FXCollections.observableArrayList(s));
        //获取该医生的病人（病历本）
       // myOneRecords=doctor.getPatientHeap().getPatientList();
        try {
            ArrayList<OneRecord> all=LogicDoctorM.getInstance().myPatientOneRecords(doctor);
            for(OneRecord o:all){
                if (o.getStatus().equals("未诊断")){
                    myOneRecords.add(o);
                 //   System.out.println(o+"1111111");
                }
            }
         //   doctor.getPatientHeap().removeAll();
            //System.out.println(myOneRecords.size()+myOneRecords.toString());
            doctor.getPatientHeap().build(myOneRecords);
            myOneRecords=doctor.getPatientHeap().getPatientList();
            myOneRecords=doctor.getPatientHeap().sort(myOneRecords);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        /*try {
            myRecords = LogicDoctorM.getInstance().myPatientRecords(doctor);
            System.out.println(myRecords);
            myOneRecords = LogicDoctorM.getInstance().myPatientOneRecords(doctor);

        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //填充病人列表
        name_table.setCellValueFactory(new PropertyValueFactory<>("name"));
        medicineID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        name_untreated.setCellValueFactory(new PropertyValueFactory<>("name"));
        medicineID_untreated.setCellValueFactory(new PropertyValueFactory<>("Id"));
       patientsUntreated=new ArrayList<>();
        patientsTreated=new ArrayList<>();
       patientsUntreated.addAll(myOneRecords);

        try {
            patientsTreated.addAll(LogicDoctorM.getInstance().treatedOneRecords(doctor));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        myOneRecordList.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
          public void handle(MouseEvent event) {
         if (event.getClickCount() == 2) {
          //   System.out.println("111111111111");
             int index=myOneRecordList.getSelectionModel().getSelectedIndex();
             OneRecord temp=clickCurrent.getRecord().getMyRecord().get(index);
             try {
                 FXMLLoader loader=new FXMLLoader();
                 loader.setLocation(getClass().getResource("/view/doctorC/Confine.fxml"));
                 Parent root=loader.load();
                 ConfineOneRecord controller=loader.getController();
                 controller.setCurrent(temp);
                 //   Parent root = load(getClass().getResource("/view/doctorC/ChooseDisease.fxml"));
                 new JMetro(JMetro.Style.LIGHT).applyTheme(root);
                 //    Group root=new Group();
                 Stage stage=new Stage();
                 Scene scene=new Scene(root);
                 stage.setTitle("修改诊断");
                 stage.setScene(scene);
                 stage.showAndWait();
             } catch (IOException e) {
                 e.printStackTrace();
             }
             }
          }
       });


        ObservableList<OneRecord> A = FXCollections.observableArrayList(patientsUntreated);
        ObservableList<OneRecord> B = FXCollections.observableArrayList(patientsTreated);
        untreatedPatientTable.setItems(A);
        treatedPatientTable.setItems(B);

        treatedPatientTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                if(untreatedPatientTable.getSelectionModel().getSelectedItem()!=null){
                    untreatedPatientTable.getSelectionModel().clearSelection();
                }
                clickCurrent=newSelection;
                clickPatient();
            }
        });
        untreatedPatientTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                if(treatedPatientTable.getSelectionModel().getSelectedItem()!=null){
                    treatedPatientTable.getSelectionModel().clearSelection();
                }
                clickCurrent=newSelection;
                clickPatient();
            }
        });
        mouldTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection!=null)
            clickMould();
                });
        //设置药品表格 模板表格
        medicineName.setCellValueFactory(new PropertyValueFactory<>("name"));
        medicineTID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        medicineForm.setCellValueFactory(new PropertyValueFactory<>("form"));
        medicineNumbers.setCellValueFactory(new PropertyValueFactory<>("numbers"));
        medicinePrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        nameMould.setCellValueFactory(new PropertyValueFactory<>("name"));
        statusMould.setCellValueFactory(new PropertyValueFactory<>("status"));

        //设置确诊表格
        IDM.setCellValueFactory(new PropertyValueFactory<>("Id"));
        nameM.setCellValueFactory(new PropertyValueFactory<>("name"));
        departmentM.setCellValueFactory(new PropertyValueFactory<>("department"));
        timeM.setCellValueFactory(new PropertyValueFactory<>("time"));
        statusM.setCellValueFactory(new PropertyValueFactory<>("status"));



        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if((newSelection!=null&&newSelection.getText().equals("开立处方")&&(!clickCurrent.getStatus().equals("已确诊")))){
                Alert a=new Alert(Alert.AlertType.WARNING);
                new JMetro(JMetro.Style.LIGHT).applyTheme(a.getDialogPane());
                a.setContentText("请选择已确诊病人进行进一步的开立处方");
                a.showAndWait();
                tabPane.getSelectionModel().select(0);
            }if(newSelection!=null&&newSelection.getText().equals("开立诊断")&&(clickCurrent!=current)){
                Alert a=new Alert(Alert.AlertType.WARNING);
                new JMetro(JMetro.Style.LIGHT).applyTheme(a.getDialogPane());
                a.setContentText("请选择当前病人进行诊断");
                a.showAndWait();
                tabPane.getSelectionModel().select(0);
            }
        });
    }
    @FXML
    void turnToMyPatientPage(ActionEvent event) throws IOException {
        Stage stage= (Stage) tabPane.getScene().getWindow();
        Parent root = load(getClass().getResource("/view/doctorC/myPatients.fxml"));
        Scene scene = new Scene(root);
        new JMetro(JMetro.Style.LIGHT).applyTheme(root);
        stage.setTitle("医疗系统");
        stage.setScene(scene);
        stage.show();
    }

    public void submit() throws IOException {
        //更新表格内容 将病人从待诊删除 添加到已诊里面
        untreatedPatientTable.getItems().remove(current);
        patientsUntreated.remove(current);
        treatedPatientTable.getItems().add(current);
        patientsTreated.add(current);

        //更新record内容
        MyHashMap<Integer,Record> records= RecordData.getInstance().getRecords();

        Record record=records.getV(current.getId());
        //System.out.println(record.getMyRecord());
        for(int i =0;i<record.getMyRecord().size();i++){
            if(record.getMyRecord().get(i).getDate().equals(current.getDate())){
                record.getMyRecord().remove(record.getMyRecord().get(i));
            }
        }
        current.setZhusu(zhusu.getText());
        current.setTime(LocalDateTime.now());
        current.setHistory(history.getText());
        current.setHistory1(history1.getText());
        current.setHistory2(history2.getText());
        current.setTime(LocalDateTime.now());
        current.setType(TypeBox.getValue());
        if(diseaseTable.getItems().size()==0){
            current.setStatus("未检查");
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            new JMetro(JMetro.Style.LIGHT).applyTheme(alert.getDialogPane().getScene());
            alert.setContentText("未选择疾病，请进行进一步检查");
            alert.showAndWait();
            tabPane.getSelectionModel().select(examination);
        }else{
            for(DiseaseNode d: diseaseTable.getItems()){
                hospitalData.getTree().fetch(d.getName()).getPatients().add(String.valueOf(current.getId()));
            }
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            new JMetro(JMetro.Style.LIGHT).applyTheme(alert.getDialogPane().getScene());
            alert.setContentText("提交成功");
            alert.showAndWait();
            current.getDisease().addAll(diseaseTable.getItems());
            current.setStatus("未确诊");
        }

        record.getMyRecord().add(current);
        records.replace(current.getId(),record);
        FileUse.writeFileO("temp.txt",hospitalData.getTree());
        //更新department内容 在当前医生的带诊病人里删除该病人
        for(Department d: hospitalData.getDepartments()){
            for(Doctor doc:d.getDoctors()){
                if(doc.getName().equals(doctor.getName())){
                    doc.getPatientHeap().remove();
                    //System.out.println("删除病人啦");
                    break;
                }
            }
        }
      //更新文件
        FileUse.writeDepartments("department.txt",hospitalData.getDepartments());
        FileUse.writeRecords("Records.txt",records);

        if(current.getStatus().equals("未确诊")){
            current=null;
        }
        zhusu.setText(null);
        history2.setText(null);
        history.setText(null);
        history1.setText(null);
        diseaseTable.setItems(null);
        if(current!=null)
        current.setDisease(new ArrayList<>());

    }

    @FXML
    void nextOne(ActionEvent event) {
       // System.out.println(current==null);
        if(current!=null){//记得每次提交后把当前设成null 或者改成原来的
           // System.out.println("!");
            Alert alert=new Alert(Alert.AlertType.WARNING);
            new JMetro(JMetro.Style.LIGHT).applyTheme(alert.getDialogPane());
            alert.setContentText("当前患者未诊断完无法进行下一个");
            alert.showAndWait();
            return;
        }
         current=untreatedPatientTable.getItems().get(0);
         untreatedPatientTable.getFocusModel().focus(0);
         untreatedPatientTable.getSelectionModel().select(0);
         details.setText(current.getName()+" "+current.getId()+" "+current.getGender());
         zhusu.setText(current.getZhusu());
         history.setText(current.getHistory());
         history2.setText(current.getHistory2());
         history1.setText(current.getHistory1());
    }

    @FXML
    void addDisease(ActionEvent event) throws IOException {
        if(TypeBox.getValue()==null){
            Alert alert=new Alert(Alert.AlertType.WARNING,"请选择诊断类型");
            new JMetro(JMetro.Style.LIGHT).applyTheme(alert.getDialogPane());
            alert.showAndWait();
            return;
        }
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/doctorC/ChooseDisease.fxml"));
        Parent root=loader.load();
        ChooseDisease controller=loader.getController();
     //   Parent root = load(getClass().getResource("/view/doctorC/ChooseDisease.fxml"));
        new JMetro(JMetro.Style.LIGHT).applyTheme(root);
    //    Group root=new Group();
        Stage stage=new Stage();
        Scene scene=new Scene(root);
        stage.setTitle("诊断");
        stage.setScene(scene);
        stage.showAndWait();
        if(stage.isShowing()){}
        else {
            diseases.addAll(controller.getDisease());
        }

        ICD_table.setCellValueFactory(new PropertyValueFactory<>("ID"));
        diseaseName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ObservableList<DiseaseNode> tempDisease= FXCollections.observableArrayList(diseases);
        diseaseTable.setItems(tempDisease);

    }



    @FXML
    void deleteDisease(ActionEvent event) {
        DiseaseNode diseaseNode=diseaseTable.getSelectionModel().getSelectedItem();
        diseaseTable.getItems().remove(diseaseNode);
        diseases.remove(diseaseNode);
    }

    public void clickPatient(){
        //vbox.set
        Record record=clickCurrent.getRecord();
        Patient p=record.getOwner();
        isOneRecord.setText(null);
        //设置病人简介
        name.setText(p.getName());
        gendar.setText(p.getGender());
        birthday.setText(p.getBirthDate().toString());
        IDNumber.setText(p.getID());
        address.setText(p.getAddress());
        ID.setText(String.valueOf(clickCurrent.getId()));
        //设置以前的看病记录
        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        ArrayList<String> temp=new ArrayList<>();
        for(OneRecord r : record.getMyRecord()){
            if(r.getZhusu()!=null){
                String s="诊断类型： "+r.getType()+"  诊断时间："+dtf.format(r.getTime())+"\n"+"主诉: "+r.getZhusu()+"   现病史："+r.getHistory();
                temp.add(s);
            }
            myOneRecordList.setItems(FXCollections.observableArrayList(temp));

        //    pane.getChildren().set()
        //    vbox.getChildren().add();
        }

    }
    @FXML
    void addExamination(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/doctorC/NewExamination.fxml"));
        Parent root=loader.load();
        ExaminationView controller=loader.getController();
        //   Parent root = load(getClass().getResource("/view/doctorC/ChooseDisease.fxml"));
        new JMetro(JMetro.Style.LIGHT).applyTheme(root);
        //    Group root=new Group();
        Stage stage=new Stage();
        Scene scene=new Scene(root);
        stage.setTitle("检查项目");
        stage.setScene(scene);
        stage.showAndWait();
        if(stage.isShowing()){}
        else {
            examinations.addAll(controller.getExaminations());
        }

        nameE.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceE.setCellValueFactory(new PropertyValueFactory<>("price"));
        statusE.setCellValueFactory(new PropertyValueFactory<>("status"));
        departmentE.setCellValueFactory(new PropertyValueFactory<>("department"));
        numberE.setCellValueFactory(new PropertyValueFactory<>("number"));
        ObservableList<Data.Hospital.Examination> tempExam= FXCollections.observableArrayList(examinations);
        examination_table.setItems(tempExam);
    }

    @FXML
    void saveExamination(ActionEvent event) {

    }

    @FXML
    void deleteExamination(ActionEvent event) {
        Data.Hospital.Examination exa=examination_table.getSelectionModel().getSelectedItem();
        examination_table.getItems().remove(exa);
        examinations.remove(exa);
    }

    @FXML
    void removeExamination(ActionEvent event) {

    }

    @FXML
    void submitE(ActionEvent event) throws IOException {
        //更新OneRecord
        MyHashMap<Integer,Record> records= RecordData.getInstance().getRecords();
        Record record=records.getV(current.getId());
        record.getMyRecord().remove(current);
       current.setStatus("检查中");
       current.setPatientType("复诊患者");
       current.setPriority(0);
       current.getExaminations().addAll(examinations);
       record.getMyRecord().add(current);
       records.put(current.getId(),record);

       //还可以写个小界面啥的
        //写文件
        //更新文件
        /*for(Department d: hospitalData.getDepartments()){
            for(Doctor doc:d.getDoctors()){
                if(doc.getName().equals(doctor.getName())){
                    doc.getPatientHeap().build(myOneRecords);
                    break;
                }
            }
        }*/
        //更新文件
        FileUse.writeRecords("Records.txt",records);

        current=null;
        history.setText(null);
        history1.setText(null);
        history2.setText(null);
        zhusu.setText(null);
        examination_table.setItems(null);
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        new JMetro(JMetro.Style.LIGHT).applyTheme(alert.getDialogPane());
        alert.setContentText("检查提交成功");
        alert.showAndWait();
    }
//确诊界面
    public void confine() throws IOException {
        OneRecord temp=makeSureTable.getSelectionModel().getSelectedItem();
            if(!temp.getStatus().equals("未确诊")){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            new JMetro(JMetro.Style.LIGHT).applyTheme(alert.getDialogPane());
            alert.setContentText(temp.getStatus()+"记录无法修改，请选择未确诊项目");
            alert.showAndWait();
            return;
            }
        if(temp==null){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setContentText("请选择需要修改的对象");
            new JMetro(JMetro.Style.LIGHT).applyTheme(alert.getDialogPane());
            alert.showAndWait();
            return;
        }
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/doctorC/Confine.fxml"));
        Parent root=loader.load();
        ConfineOneRecord controller=loader.getController();
        controller.setCurrent(temp);
        //   Parent root = load(getClass().getResource("/view/doctorC/ChooseDisease.fxml"));
        new JMetro(JMetro.Style.LIGHT).applyTheme(root);
        //    Group root=new Group();
        Stage stage=new Stage();
        Scene scene=new Scene(root);
        stage.setTitle("修改诊断");
        stage.setScene(scene);
        stage.showAndWait();
        //怎么更新？？？
    }

    public void submitStatus() throws IOException {
        OneRecord temp=makeSureTable.getSelectionModel().getSelectedItem();
        if(!temp.getStatus().equals("未确诊")){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            new JMetro(JMetro.Style.LIGHT).applyTheme(alert.getDialogPane());
            alert.setContentText(temp.getStatus()+"患者无法确诊");
            alert.showAndWait();
            return;
        }
        patientsTreated.remove(temp);
        MyHashMap<Integer,Record> records= RecordData.getInstance().getRecords();
        Record record=records.getV(temp.getId());
        record.getMyRecord().remove(temp);
        temp.setStatus("已确诊");
        record.getMyRecord().add(temp);
        records.put(temp.getId(),record);
        //更新当前列表
        patientsTreated.add(temp);
        treatedPatientTable.setItems(FXCollections.observableArrayList(patientsTreated));
        //更新文件
        FileUse.writeRecords("Records.txt",records);
    }

    public void refresh() throws IOException, ClassNotFoundException {
        ArrayList<OneRecord> temp=new ArrayList<>();
        temp.addAll(LogicDoctorM.getInstance().makeSureTableOneRecords(doctor));
        ObservableList<OneRecord> tempExam= FXCollections.observableArrayList(temp);
        makeSureTable.setItems(null);
       // System.out.println("333333333333");
        makeSureTable.setItems(tempExam);
        makeSureTable.refresh();

    }

    //开处方界面
    //注意还有点击动作
    public void addMedicine() throws IOException {
        if(mouldTable.getSelectionModel().getSelectedItem()==null){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            new JMetro(JMetro.Style.LIGHT).applyTheme(alert.getDialogPane());
            alert.setContentText("请选择相应模板");
            alert.showAndWait();
            return;
        }
       //打开新的界面
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/doctorC/ChooseMedicine.fxml"));
        Parent root=loader.load();
        ChooseMedicine controller=loader.getController();
        new JMetro(JMetro.Style.LIGHT).applyTheme(root);
        //    Group root=new Group();
        controller.setAllMedicines(allMedicine);
        Stage stage=new Stage();
        Scene scene=new Scene(root);
        stage.setTitle("开药");
        stage.setScene(scene);
        stage.showAndWait();
        if(controller.getMyChoice().size()!=0){
            mouldTable.getSelectionModel().getSelectedItem().getMedicines().addAll(controller.getMyChoice());
            clickMould();
        }

    }

    public void newMould(){
        TextInputDialog dialog = new TextInputDialog();
        new JMetro(JMetro.Style.LIGHT).applyTheme(dialog.getDialogPane());
        dialog.setTitle("Set Name Dialog");
        dialog.showAndWait();
        if(dialog.getResult()!=null){
            Mould mould = new Mould();
            mould.setStatus("已开立");
            mould.setName(dialog.getResult());
            mouldTable.getItems().add(mould);
        }

    }

    public void addMould(){

    }

    public void clickMould(){
        ArrayList<Medicine> medicines=mouldTable.getSelectionModel().getSelectedItem().getMedicines();
        ObservableList<Medicine> temp= FXCollections.observableArrayList(medicines);
        medicineTable.setItems(temp);
    }

    public void deleteMould(){
        Mould m=mouldTable.getSelectionModel().getSelectedItem();
        mouldTable.getItems().remove(m);
    }

    public void removeMedicine(){
       Medicine m=medicineTable.getSelectionModel().getSelectedItem();
       mouldTable.getSelectionModel().getSelectedItem().getMedicines().remove(m);
       medicineTable.getItems().remove(m);
       for(Medicine medicine:allMedicine){
           if(m.getID()==medicine.getID()){
               int n=medicine.getTotalNumber();
               medicine.setTotalNumber(n+Integer.parseInt(m.getNumbers()));
           }
       }
    }
    public void refreshPatientTable() throws IOException, ClassNotFoundException {
        patientsTreated.clear();
        patientsTreated.addAll(LogicDoctorM.getInstance().treatedOneRecords(doctor));
        treatedPatientTable.setItems(FXCollections.observableArrayList(patientsTreated));
        patientsUntreated.clear();
        patientsUntreated.addAll(myOneRecords);
        untreatedPatientTable.setItems(FXCollections.observableArrayList(patientsUntreated));

    }

    public void submitPrescription() throws IOException {
        //更新药品
        ArrayList<Medicine> finalMedicines=new ArrayList<>();
        for(Mould m: mouldTable.getItems()){
            finalMedicines.addAll(m.getMedicines());
        }

        ArrayList<String> json=new ArrayList<>();
        for(Medicine medicine:allMedicine){
            String s=JSON.toJSONString(medicine);
            json.add(s);
        }
        FileUse.writeFile("medicine.txt",json);
        //更新record
        treatedPatientTable.getItems().remove(clickCurrent);
        clickCurrent.setStatus("待缴费");
        clickCurrent.setMedicines(finalMedicines);
        Record record=RecordData.getInstance().getRecords().getV(clickCurrent.getId());
        for(int i =0;i<record.getMyRecord().size();i++){
            OneRecord temp=record.getMyRecord().get(i);
            if(temp.getDate().equals(clickCurrent.getDate())){
                record.getMyRecord().remove(temp);
            }
        }
        record.getMyRecord().add(clickCurrent);
        MyHashMap records=RecordData.getInstance().getRecords();
        records.replace(record.getID(),record);
       FileUse.writeRecords("Records.txt",records);
        //清楚页面
        mouldTable.setItems(FXCollections.observableArrayList(new ArrayList<Mould>()));
        medicineTable.setItems(null);

    }

}
