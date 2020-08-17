import Data.Hospital.DiseaseNode;
import Data.Hospital.DiseaseTree;
import Data.HospitalData;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jfxtras.styles.jmetro8.JMetro;

import java.io.IOException;

import static javafx.fxml.FXMLLoader.load;

public class Main extends Application {

    public static void main(String[] args) throws IOException {
        //RecordData recordData=RecordData.getInstance();

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = load(getClass().getResource("/view/Login.fxml"));
        Scene scene = new Scene(root);
        new JMetro(JMetro.Style.LIGHT).applyTheme(root);
        primaryStage.setTitle("医疗系统");
       primaryStage.setScene(scene);
        primaryStage.show();
    }
}
