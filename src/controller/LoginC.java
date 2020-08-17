package controller;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import jfxtras.styles.jmetro8.JMetro;
import logic.Login;

import static javafx.fxml.FXMLLoader.load;
import static javafx.geometry.HPos.LEFT;
import static javafx.geometry.HPos.RIGHT;
import static javafx.stage.StageStyle.TRANSPARENT;

import java.io.IOException;

public class LoginC  {

    private Login login=Login.getInstance();

    @FXML
    private GridPane grid;


    @FXML
    private TextField loginID;

    @FXML
    private PasswordField loginPassword;
    static int ID;


    public void login(ActionEvent event) throws IOException {
        ID= Integer.parseInt(loginID.getText());
        String password=loginPassword.getText();
        int n=login.checkLogin(ID,password);
        if (n==0){
            //登录失败界面
            /*Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("登陆失败 用户名或密码错误");
            alert.showAndWait();*/
            final Text actionTarget = new Text();
            grid.add(actionTarget, 0, 2);
            grid.setColumnSpan(actionTarget, 2);
            grid.setHalignment(actionTarget, LEFT);
            actionTarget.setId("actionTarget");
            actionTarget.setText("登陆失败 用户名或密码错误");
            loginID.setText(null);
            loginPassword.setText(null);
        }else if (n==1){
            //药房医生
            Stage sign=(Stage) grid.getScene().getWindow();
            sign.setResizable(false);
            Parent in = null;
            try {
                in = load(getClass().getResource("/view/doctorM/MedicineDistribution.fxml"));
              //  new JMetro(JMetro.Style.LIGHT).applyTheme(in);
                Scene scene=new Scene(in, 1208, 800);
                sign.setScene(scene);
                sign.setTitle("东软云HIS系统");
                sign.show();
            }catch (IOException e){
                e.printStackTrace();
            }

        }else if(n==2){
            //挂号医生界面菜单
            Stage sign=(Stage) grid.getScene().getWindow();
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

        }else if(n==3){
            //门诊医生界面
            Stage sign=(Stage) grid.getScene().getWindow();
            Parent in = null;
            try {
                in = load(getClass().getResource("/view/doctorC/Doctorchufang.fxml"));
                new JMetro(JMetro.Style.LIGHT).applyTheme(in);
                Scene scene=new Scene(in, 1208, 800);
                sign.setScene(scene);
                sign.setTitle("东软云HIS系统");
                sign.show();
            }catch (IOException e){
                e.printStackTrace();
            }

        }else{
            Stage sign=(Stage) grid.getScene().getWindow();
            Parent in = null;
            try {
                in = load(getClass().getResource("/view/Nurse.fxml"));
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


}
