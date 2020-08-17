package Data;

import Data.Hospital.*;
import Data.Others.FileUse;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HospitalData {
    private ArrayList<Department> departments=new ArrayList<>();
    private List<Medicine> medicines=new ArrayList<Medicine>();
    private ArrayList<Examination> examinations=new ArrayList<>();
    private static HospitalData instance;//单例 只能有一个hospital
    private DiseaseTree tree;


    public void insert(String str1,String str2){
        DiseaseNode parent=tree.fetch(str1);
        if(parent==null){
            parent=new DiseaseNode(str1);
        }
        DiseaseNode cursor= new DiseaseNode(str2);
        cursor.setParent(parent);
        tree.insert(cursor,parent);
    }

    private HospitalData() throws IOException, ClassNotFoundException {
       this.tree =FileUse.readTreeFromFile("temp.txt");
       /* this.tree=new DiseaseTree(1000);
        ArrayList<String> treeString=new ArrayList<String>();
        File file = new File("Disease.txt");
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()){
            String line=sc.nextLine();
            String[] list=line.split(",");
            for(int i = 0;i<list.length;i++){
                System.out.println(list[i]);
                treeString.add(list[i]);
            }
        }
        sc.close();
        tree.build(treeString);*/
        /*//信息初始化
        Doctor d1=new Doctor("d1");
        Doctor d2=new Doctor("d2");
        Doctor d3=new Doctor("d3");
        Doctor d4=new Doctor("d4");
        Department department1=new Department();
        department1.setName("内科");
        ArrayList<Doctor> dos1=new ArrayList<>();
        dos1.add(d1);dos1.add(d2);
        department1.setDoctors(dos1);
        Department department2=new Department();
        department2.setName("外科");
        ArrayList<Doctor> dos2=new ArrayList<>();
        dos2.add(d3);dos2.add(d4);
        department2.setDoctors(dos2);
        departments.add(department1);
        departments.add(department2);*/
        /*ArrayList<String> json=new ArrayList<>();
        json.addAll(FileUse.readFile("tempDisease.txt"));
        for(String s:json){
            tree=JSONObject.parseObject(s,DiseaseTree.class);
        }*/
        //departments= (ArrayList<Department>) FileUse.readFileDpm("department.txt");
       ArrayList<String> departments2=FileUse.readFile("department.txt");
       for(String a:departments2){
           Department d=JSONObject.parseObject(a,Department.class);
           departments.add(d);
       }
     //  System.out.println(departments);
         ArrayList<String> medicines2=FileUse.readFile("medicine.txt");
         for(String a:medicines2){
             Medicine m =JSONObject.parseObject(a,Medicine.class);//将建json String转换为Person对象
             medicines.add(m);
         }
        //System.out.println(medicines);*/
        ArrayList<String> examination1=FileUse.readFile("examination.txt");
        for(String a:examination1){
            Examination m =JSONObject.parseObject(a, Examination.class);//将建json String转换为Person对象
            examinations.add(m);
        }
    }

    public static HospitalData getInstance() throws IOException, ClassNotFoundException {
        if(instance==null){
            instance=new HospitalData();
        }
        return instance;
    }

    public ArrayList<Examination> getExaminations() {
        return examinations;
    }

    public void setExaminations(ArrayList<Examination> examinations) {
        this.examinations = examinations;
    }

    public DiseaseTree getTree() {
        return tree;
    }

    public ArrayList<Department> getDepartments() {
        return departments;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    //根据ID返回药物 如果没有返回null
    public Medicine IDReturn(String ID){
        for(Medicine a:medicines){
            if(ID.equals(a.getID())){
                return a;
            }
        }
        return null;
    }
    //根据拼音助记码返回药物
    public Medicine PinyinReturn(String Pinyin){
        for(Medicine a:medicines){
            if(Pinyin.equals(a.getPinyin())){
                return a;
            }
        }
        return null;
    }

    public ArrayList<Department> getDepartments1(){
        ArrayList<Department> d0=new ArrayList<>();
        ArrayList<String> departments2=FileUse.readFile("department1.txt");
        for(String a:departments2){
            Department d=JSONObject.parseObject(a,Department.class);
            d0.add(d);
        }
        return d0;
    }
    public void setDepartments(ArrayList<Department> departments) {
        this.departments = departments;
    }
}
