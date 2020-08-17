package controller;

import Data.Hospital.Medicine;
import Data.HospitalData;
import Data.User.DoctorM;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import jfxtras.styles.jmetro8.JMetro;
import logic.LogicDoctorM;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;

public class ChooseMedicine implements Initializable {

    @FXML
    private TableColumn<Medicine, String> totalNumbers;

    @FXML
    private TableColumn<Medicine, String> form;

    @FXML
    private TableColumn<Medicine, String> price;

    @FXML
    private TableColumn<Medicine, String> name;

    @FXML
    private TextField textfield;

    @FXML
    private TableColumn<Medicine, String> id;

    @FXML
    private TableView<Medicine> medicineTable;

    @FXML
    private TableColumn<Medicine, Button> option;

    private ArrayList<Medicine> allMedicines;
    private ArrayList<Medicine> myChoice=new ArrayList<>();
    private LogicDoctorM doctorM=LogicDoctorM.getInstance();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        id.setCellValueFactory(new PropertyValueFactory<>("ID"));//药品编号
        form.setCellValueFactory(new PropertyValueFactory<>("form"));
        totalNumbers.setCellValueFactory(new PropertyValueFactory<>("totalNumber"));//病历号药品规格
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        option.setCellValueFactory(
                cellData->{
                    Button button=new Button();
                    button.setText("选择");
                    button.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                writeMedicineDetails();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    return new SimpleObjectProperty<>(button);
                }
        );

    }
    public ChooseMedicine() throws IOException, ClassNotFoundException {

    }

    public void writeMedicineDetails() throws IOException {
         //新页面
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/doctorC/MedicinePage.fxml"));
        Parent root=loader.load();
        MedicinePage controller=loader.getController();
        System.out.println(medicineTable.getSelectionModel().getSelectedItem().getPrice());
        controller.setMedicine(medicineTable.getSelectionModel().getSelectedItem());
        new JMetro(JMetro.Style.LIGHT).applyTheme(root);
        //    Group root=new Group();
        Stage stage=new Stage();
        Scene scene=new Scene(root);
        stage.setTitle("开药");
        stage.setScene(scene);
        stage.showAndWait();
        Medicine m=controller.getMedicine();
        myChoice.add(m);
    }

    @FXML
    void search(ActionEvent event) {
        int ID=Integer.parseInt(textfield.getText());
        System.out.println(ID);
        Medicine m=doctorM.binarySearch(allMedicines,ID);
        ArrayList<Medicine> temp=new ArrayList<>();
        temp.add(m);
        medicineTable.setItems(null);
        medicineTable.setItems(FXCollections.observableArrayList(m));
    }

    public ArrayList<Medicine> getMyChoice(){
        return myChoice;
    }

    public void close(){
        Stage s= (Stage) medicineTable.getScene().getWindow();
        s.close();
    }

    public ArrayList<Medicine> getAllMedicines() {
        return allMedicines;
    }

    public void setAllMedicines(ArrayList<Medicine> allMedicines) {
        this.allMedicines = allMedicines;
        medicineTable.setItems( FXCollections.observableArrayList(allMedicines));
    }
}
