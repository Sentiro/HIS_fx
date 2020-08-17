package controller;

import Data.Hospital.Examination;
import Data.HospitalData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ExaminationView implements Initializable {
    @FXML
    private TableColumn<?, ?> number;

    @FXML
    private TableView<Examination> examination_table;

    @FXML
    private TableColumn<?, ?> price;

    @FXML
    private TextField textfield;

    @FXML
    private TableColumn<?, ?> name;

    @FXML
    private TableColumn<?, ?> department;




    ArrayList<Examination> examinations;

    ArrayList<Examination> examinationItems=new ArrayList<>();

    ArrayList<Examination> results=new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            examinations= HospitalData.getInstance().getExaminations();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        department.setCellValueFactory(new PropertyValueFactory<>("department"));
        number.setCellValueFactory(new PropertyValueFactory<>("number"));
        ObservableList<Examination> tempExam= FXCollections.observableArrayList(examinations);
        examination_table.setItems(tempExam);
    }

    @FXML
    void submit(ActionEvent event) {
        results.addAll(examination_table.getSelectionModel().getSelectedItems());
        Stage current= (Stage) textfield.getScene().getWindow();
        current.close();
    }

    public ArrayList<Examination> getExaminations()
    {return results;}


    @FXML
    void search(ActionEvent event) {
        examination_table.getItems().removeAll();
           String target=textfield.getText();
        String pattern = ".*" + target + ".*";
        for (Examination m : examinations) {
            if (Pattern.matches(pattern, m.getName()) || Pattern.matches(pattern, m.getNumber()) ) {
                examinationItems.add(m);
                ObservableList<Examination> tempItemsArrayList = FXCollections.observableArrayList(examinationItems);
                examination_table.setItems(tempItemsArrayList);
                }
            }
        }

        }
