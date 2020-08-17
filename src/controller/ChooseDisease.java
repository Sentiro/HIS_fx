package controller;

import Data.Hospital.DiseaseNode;
import Data.Hospital.DiseaseTree;
import Data.HospitalData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChooseDisease implements Initializable {

    @FXML
    private TreeView<String> treeView;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Button Search;

    @FXML
    private TextField textField;
    TreeItem<String> rootItem;
    DiseaseTree tree;
    ArrayList<DiseaseNode> disease;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        treeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
    }



    public void search(){
//        System.out.println(treeView.getSelectionModel().getSelectedItems().get(0).toString());
          String target=textField.getText();
          searchAndSet(target,rootItem);
    }
//在树中搜索病，选择并对其进行展开
    private void searchAndSet(String target,TreeItem node){
        if(node.getValue().toString().equals(target)) {
            treeView.getSelectionModel().select(node);
            int i=treeView.getSelectionModel().getSelectedIndex();
            node.getParent().setExpanded(true);
            treeView.getFocusModel().focus(i);
            System.out.println(treeView.getSelectionModel().getSelectedItems().toString());
        }
        if(node.getChildren()==null) return;
        for(int i=0; i<node.getChildren().size();i++){
           searchAndSet(target, (TreeItem) node.getChildren().get(i));
        }
    }

    public ArrayList<DiseaseNode> getDisease(){
        return disease;
    }
    //确认选择的疾病 并传回数据
    public void submit(){
        disease=new ArrayList<>();
        // treeView.getSelectionModel().setSelectionMode();
        for(int i =0;i<treeView.getSelectionModel().getSelectedItems().size();i++){
            String d=treeView.getSelectionModel().getSelectedItems().get(i).getValue();
            disease.add(tree.fetch(d));
            Stage current= (Stage) treeView.getScene().getWindow();
            current.close();
        }


    }

}