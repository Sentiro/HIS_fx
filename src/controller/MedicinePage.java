package controller;

import Data.Hospital.Medicine;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jfxtras.styles.jmetro8.JMetro;

import java.net.URL;
import java.util.ResourceBundle;

public class MedicinePage implements Initializable {

    @FXML
    private TextField price;

    @FXML
    private TextField usage;

    @FXML
    private TextField name;

    @FXML
    private TextField numbers;

    @FXML
    private TextField ID;

    @FXML
    private TextField type;

    private Medicine medicine;
    private Medicine m;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
        this.price.setText(String.valueOf(medicine.getPrice()));
        this.name.setText(medicine.getName());
        this.ID.setText(medicine.getID());
        this.type.setText(medicine.getType());
    }

    public Medicine getMedicine(){
      return m;
    }

    public void close(){
        int number=medicine.getTotalNumber()-Integer.parseInt(numbers.getText());
        if(number<0){
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            new JMetro(JMetro.Style.LIGHT).applyTheme(alert.getDialogPane());
            alert.setContentText("药品数量不够，请重新选择 当前库存："+medicine.getTotalNumber());
            alert.showAndWait();
            return;
        }
            medicine.setTotalNumber(number);
            m=medicine;
            m.setNumbers(numbers.getText());
            m.setUsage(usage.getText());
            Stage s= (Stage) name.getScene().getWindow();
            s.close();
    }


}
