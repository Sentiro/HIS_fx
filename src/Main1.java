import Data.Hospital.Department;
import Data.Hospital.DiseaseNode;
import Data.Hospital.DiseaseTree;
import Data.HospitalData;
import Data.Others.FileUse;
import Data.Patient.Patient;
import Data.Structure.MyHashMap;
import Data.Patient.Record;
import Data.RecordData;
import Data.Structure.MyQueue;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class Main1 {

   public static void main(String[] args) throws IOException, ClassNotFoundException {
       System.out.println(LocalDateTime.now().getHour());
       /*ArrayList<Department> departments=HospitalData.getInstance().getDepartments();
       ArrayList<String> json=new ArrayList<>();
       for(Department s:departments){
           String str=JSON.toJSONString(s);
           json.add(str);
       }
       FileUse.writeFile("tempDepartment.txt",json);
       ArrayList<String> departments2=FileUse.readFile("tempDepartment.txt");
       for(String a:departments2){
           Department d=JSONObject.parseObject(a,Department.class);
           System.out.println(d.getDoctors());
       }*/
       //DiseaseTree tree=HospitalData.getInstance().getTree();
      /* System.out.println(tree.getRoot().getName());
       String str=JSON.toJSONString(tree);
       ArrayList<String> json=new ArrayList<>();
       json.add(str);
       FileUse.writeFile("tempDisease.txt",json);*/
  /*    traverse(tree.getRoot());
       FileUse.writeFileO("temp.txt",tree);*/
       /*ArrayList<String> newjson=new ArrayList<>();
       newjson.addAll(FileUse.readFile("tempDisease.txt"));

       DiseaseTree newtree=JSONObject.parseObject(newjson.get(0),DiseaseTree.class);
       System.out.println(newtree.getRoot().getName());*/


      // FileUse.writeFile("tempRecord.txt",json);
       /*boolean flag=true;
       DiseaseTree tree=new DiseaseTree(100);
       DiseaseTree anotherTree= HospitalData.getInstance().getTree();
       while (flag) {
           System.out.println("请选择操作");
           System.out.println("1.创建树");
           System.out.println("2.查询新建的树");
           System.out.println("3.查询已有树");
           System.out.println("4.查询已有树病种下的病人");
           System.out.println("5.退出");
           Scanner s = new Scanner(System.in);
           Scanner m = new Scanner(System.in);
           int choice = s.nextInt();

           switch (choice) {
               case 1: {
                   ArrayList<String> treeString = new ArrayList<>();
                   System.out.println("请输入父节点名称，节点名称，逗号分隔，输入exit退出");
                   while (true) {
                       String line = m.nextLine();
                       if (line.equals("exit")) {
                           break;
                       }
                       String[] list = line.split(",");
                       for (int i = 0; i < list.length; i++) {
                           treeString.add(list[i]);
                       }
                   }
                   tree.build(treeString);
                   break;
               }
               case 2: {
                   if(tree.getRoot()==null){
                       System.out.println("请先创建树再查询");
                       break;
                   }
                   tree.inOrder(tree.getRoot());
                   break;
               }
               case 3: {
                   anotherTree.inOrder(anotherTree.getRoot());
                   break;
               }
               case 4: {
                   System.out.println("请输入节点名称");
                   String name = m.nextLine();
                   DiseaseNode node=anotherTree.fetch(name);
                   if(node==null){
                       System.out.println("该节点不存在");
                       break;
                   }
                   traverse(node);
                  *//* Vector<String> p = anotherTree.getNodePatients(name);
                   for (String str : p
                   ) {
                       System.out.println(str + " ");
                       Record record = RecordData.getInstance().getRecords().getV(Integer.parseInt(str));
                       System.out.println(record.getOwner().getName());
                   }*//*
                   break;
               }
               case 5: {
                   flag = false;
                   break;
               }
               default:{
                   System.out.println("请输入范围内的数字");
               }
           }
       }
   }
    public  static void traverse(DiseaseNode node) throws IOException {
       if(node.getPatients().size()>0){
           System.out.println(node.getName());
           System.out.println("----------------------");
           Vector<String> oneRecords=node.getPatients();

           for(String ID:oneRecords){
               String name= RecordData.getInstance().getRecords().getV(Integer.valueOf(ID)).getOwner().getName();
               System.out.println(ID+" "+name);
           }
           System.out.println("----------------------");
       }

        if(node.getSub_diseases()==null) return;

        for(int i=0;i<node.getSub_diseases().size();i++){
            traverse(node.getSub_diseases().get(i));
        }*/
    }
    public  static void traverse(DiseaseNode node) throws IOException {
       int ID= (int) (100000 * Math.random());
        node.setID(String.valueOf(ID));
        System.out.println(node.getName() + " " + node.getID());
        if (node.getSub_diseases() == null) return;

        for (int i = 0; i < node.getSub_diseases().size(); i++) {
            traverse(node.getSub_diseases().get(i));
        }

     /* DiseaseTree tree=new DiseaseTree(10);
       DiseaseNode node=new DiseaseNode("病");
       DiseaseNode node1=new DiseaseNode("内科"); node1.setID("123");node1.setParent(node);
       DiseaseNode node2=new DiseaseNode("外科"); node2.setID("123");node2.setParent(node);
       DiseaseNode node3=new DiseaseNode("心血管内科"); node3.setID("123");node3.setParent(node1);
       DiseaseNode node4=new DiseaseNode("神经内科"); node4.setID("123");node4.setParent(node1);
       Vector v=new Vector<String>();
       v.add("111111");
       node.setPatients(v);
       tree.setRoot(node);
       tree.insert(node1,node);
       tree.insert(node2,node);
       tree.insert(node3,node1);
       tree.insert(node4,node1);
       tree.inOrder(node);
       FileUse.writeFileO("temp.txt",tree);
       System.out.println("新的树");
       DiseaseTree tree2=FileUse.readTreeFromFile("temp.txt");
       tree2.inOrder(tree2.getRoot());
       System.out.println(tree2.getRoot().getPatients());*/
     /*public void down(int i,int j,int value){

         if(nexti>5&&)
     }*/

    }
}
