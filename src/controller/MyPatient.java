package controller;

import Data.Hospital.Department;
import Data.Hospital.DiseaseNode;
import Data.Hospital.DiseaseTree;
import Data.Hospital.Doctor;
import Data.HospitalData;
import Data.Structure.MyHashMap;
import Data.Patient.Patient;
import Data.Patient.Record;
import Data.RecordData;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import jfxtras.styles.jmetro8.JMetro;
import logic.LogicDoctorM;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;

import static javafx.fxml.FXMLLoader.load;

public class MyPatient implements Initializable
{

    @FXML
    private Button next;

    @FXML
    private TableColumn<?, ?> address_column;

    @FXML
    private ComboBox<Doctor> doctor;

    @FXML
    private Button search;

    @FXML
    private TableColumn<Patient, String> gender_column;

    @FXML
    private TableView<Patient> patientTable;

    @FXML
    private TableColumn<Patient, String > ID_column;

    @FXML
    private TableColumn<Patient, String> name_column;

    @FXML
    private ComboBox<Department> department;

    @FXML
    private TableColumn<Patient, String> Id_column;

    @FXML
    private TableColumn<Patient, Integer> age_column;

    @FXML
    private TreeView<String> treeView;

    @FXML
    private TextField textField;

    LogicDoctorM  doctorM= LogicDoctorM.getInstance();
    RecordData recordData=RecordData.getInstance();
    MyHashMap<Integer, Record> records;
    ArrayList<Patient> allPatients=new ArrayList<>();
    TreeItem<String> rootItem;
    DiseaseTree tree;
    ArrayList<DiseaseNode> disease;

    public MyPatient() throws IOException, ClassNotFoundException {
    }

    @FXML
    void turnToRecordPage(ActionEvent event) throws IOException {
        Stage stage= (Stage) textField.getScene().getWindow();
        Parent root = load(getClass().getResource("/view/doctorC/Doctorchufang.fxml"));
        Scene scene = new Scene(root);
        new JMetro(JMetro.Style.LIGHT).applyTheme(root);
        stage.setTitle("医疗系统");
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            records= RecordData.getInstance().getRecords();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(Integer key:records.keySet()){
            Patient p=records.getV(key).getOwner();
            p.setId(String.valueOf(records.getV(key).getID()));
            allPatients.add(p);
        }
        name_column.setCellValueFactory(new PropertyValueFactory<>("name"));
        gender_column.setCellValueFactory(new PropertyValueFactory<>("gender"));
        age_column.setCellValueFactory(new PropertyValueFactory<>("age"));
        Id_column.setCellValueFactory(new PropertyValueFactory<>("Id"));
        ID_column.setCellValueFactory(new PropertyValueFactory<>("ID"));
        patientTable.setItems(FXCollections.observableArrayList(allPatients));

        try {
            tree = HospitalData.getInstance().getTree();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        rootItem = new TreeItem<> (tree.getRoot().getName());//,// rootIcon);
        rootItem.setExpanded(true);
        treeInOrder(tree.getRoot(),rootItem);
        this.treeView.setRoot(rootItem);
    }
    public void treeInOrder(DiseaseNode node, TreeItem nodeItem){
        if(node.getSub_diseases()==null) {
            return;
        }
        for(int i=0; i<node.getSub_diseases().size();i++){
            TreeItem<String> children=new TreeItem<>(node.getSub_diseases().get(i).getName());
            nodeItem.getChildren().add(children);
            treeInOrder(node.getSub_diseases().get(i),children);
        }

       /* treeView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            show();
        });*/
       treeView.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
               if(event.getClickCount()==2){
                   patientTable.setItems(null);
                   show();
               }
           }
       });

    }


    public void search(){
//        System.out.println(treeView.getSelectionModel().getSelectedItems().get(0).toString());
        String target=textField.getText();
        searchAndSet(target,rootItem);
        Vector<String> p=tree.getNodePatients(target);
        ArrayList<Patient> temp=new ArrayList<>();
        for (String s:p
        ) {
            Record record= recordData.getRecords().getV(Integer.parseInt(s));
            temp.add(record.getOwner());
        }
        patientTable.setItems(FXCollections.observableArrayList(temp));
    }

    //在树中搜索病，选择并对其进行展开
    private void searchAndSet(String target,TreeItem node){
        if(node.getValue().toString().equals(target)) {
            treeView.getSelectionModel().select(node);
            int i=treeView.getSelectionModel().getSelectedIndex();
            if(node.getParent()!=null){
                node.getParent().setExpanded(true);
            }

            treeView.getFocusModel().focus(i);
            System.out.println(treeView.getSelectionModel().getSelectedItems().toString());
        }
        if(node.getChildren()==null) return;
        for(int i=0; i<node.getChildren().size();i++){
            searchAndSet(target, (TreeItem) node.getChildren().get(i));
        }
    }


    public void show(){
        ArrayList<Patient> pat=new ArrayList<>();
        System.out.println("尝试展示中");
        String target=treeView.getSelectionModel().getSelectedItem().getValue();
        Vector<String> p=tree.getNodePatients(target);
        for (String s:p
        ) {
            //System.out.println(s);
            Record record= recordData.getRecords().getV(Integer.parseInt(s));
            pat.add(record.getOwner());
        }
        patientTable.setItems(FXCollections.observableArrayList(pat));
    }

    //排序
    public void sortByID(){
        ArrayList<Patient> temp=new ArrayList<>();
        temp.addAll(patientTable.getItems());
        patientTable.setItems(null);
        patientTable.setItems(FXCollections.observableArrayList(doctorM.qSortByID(temp)));

    }

    public void sortByName(){
        ArrayList<Patient> temp=new ArrayList<>();
        temp.addAll(patientTable.getItems());
        patientTable.setItems(null);
        patientTable.setItems(FXCollections.observableArrayList( doctorM.qSortByName(temp)));
    }
}
