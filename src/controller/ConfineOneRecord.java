package controller;

import Data.Hospital.DiseaseNode;
import Data.Patient.OneRecord;
import Data.Patient.Record;
import Data.RecordData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jfxtras.styles.jmetro8.JMetro;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ConfineOneRecord implements Initializable {

    @FXML
    private TableColumn<DiseaseNode, String> diseaseName;

    @FXML
    private TextArea history1;

    @FXML
    private TextField zhusu;

    @FXML
    private TextArea history2;

    @FXML
    private Button submit;

    @FXML
    private TableView<DiseaseNode> diseaseTable;


    @FXML
    private TextArea history;

    @FXML
    private ComboBox<String> TypeBox;

    @FXML
    private TableColumn<DiseaseNode, String> ICD_table;

    ArrayList<DiseaseNode> diseases;

    OneRecord current;

    public OneRecord getCurrent() {
        return current;
    }

    public void setCurrent(OneRecord current) {

        this.current = current;
        diseases=current.getDisease();
        zhusu.setText(current.getZhusu());
        history.setText(current.getHistory());
        history1.setText(current.getHistory1());
        history2.setText(current.getHistory2());
        TypeBox.setValue(current.getType());
        ICD_table.setCellValueFactory(new PropertyValueFactory<>("ID"));
        diseaseName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ObservableList<DiseaseNode> tempDisease= FXCollections.observableArrayList(diseases);
        diseaseTable.setItems(tempDisease);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> type=new ArrayList<>();
        type.add("中医诊断");type.add("西医诊断");
        TypeBox.setItems(FXCollections.observableArrayList(type));
    }

    @FXML
    void submit(ActionEvent event) {
         current.setZhusu(zhusu.getText());
         current.setHistory2(history2.getText());
         current.setHistory(history.getText());
         current.setHistory1(history1.getText());
         current.setDisease(diseases);
         Stage stage= (Stage) submit.getScene().getWindow();
         stage.close();
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


}
