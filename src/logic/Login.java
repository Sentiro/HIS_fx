package logic;

import Data.User.*;
import Data.UserData;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;
import static javafx.stage.StageStyle.TRANSPARENT;

public class Login {
    /*
    登录检验账户密码
     */
    private UserData userData=UserData.getInstance();
    private List<User> users;
    private static Login instance;

    public static Login getInstance(){
        if(instance==null){
            instance=new Login();
        }
        return instance;
    }

    private Login() {
        users= userData.getUsers();
    }
    //输入 ID 和password 进行检验
    public int checkLogin(int ID,String password){
        for(User user:users){
            if(user.getID()==ID&&user.getPassword().equals(password)){
                if(user instanceof Administrator){
                    return 1;//药房医生
                }else if(user instanceof DoctorG){
                    return 2;//挂号医生
                }else if(user instanceof DoctorM){
                    return 3;//门诊医生
                }else if(user instanceof Nurse){
                    return 4;
                }
            }
        }
        return 0;//账户或密码错误
    }
    public void mySleep(Stage a, long m){
        //定时关闭
        Thread thread = new Thread(() -> {
            try {
                sleep(m);
                if (a.isShowing()) {
                    Platform.runLater(() -> a.close());
                }
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void showSuccessfulPage() throws IOException {
        Stage success=new Stage();
        Parent rootSucceed = FXMLLoader.load(getClass().getResource("/view/Successful.fxml"));
        success.setScene(new Scene(rootSucceed, 600, 400));
        success.initStyle(TRANSPARENT);
        //
        success.show();
        mySleep(success,3000);
    }

    public void showFailPage(String path) throws IOException {
        Stage fail=new Stage();
        Parent rootSucceed = FXMLLoader.load(getClass().getResource(path));
        fail.setScene(new Scene(rootSucceed, 600, 400));
        fail.initStyle(TRANSPARENT);
        fail.show();
        mySleep(fail,3000);
    }
}
