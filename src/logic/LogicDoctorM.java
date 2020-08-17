package logic;

import Data.Hospital.Department;
import Data.Hospital.Doctor;
import Data.Hospital.Medicine;
import Data.HospitalData;
import Data.Others.FileUse;
import Data.Patient.OneRecord;
import Data.Patient.Patient;
import Data.Patient.Record;
import Data.RecordData;
import Data.Structure.Heap;
import Data.Structure.MyHashMap;
import Data.User.DoctorM;
import Data.User.User;
import Data.UserData;
import com.alibaba.fastjson.JSONObject;

import javax.swing.plaf.basic.BasicScrollPaneUI;
import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class LogicDoctorM {
    HospitalData hospitalData = HospitalData.getInstance();
    UserData userData = UserData.getInstance();
    RecordData recordData = RecordData.getInstance();
    private static LogicDoctorM instance;

    public LogicDoctorM() throws IOException, ClassNotFoundException {
    }

    public static LogicDoctorM getInstance() throws IOException, ClassNotFoundException {
        if (instance == null) {
            instance = new LogicDoctorM();
        }
        return instance;
    }

    //通过登录账号确定医生
    public Doctor getDoctorThroughID(int ID) {
        String name = null;
        //遍历用户里DoctorM的信息 得到对应的名字
        for (User user : userData.getUsers()) {
            if (user instanceof DoctorM && user.getID() == ID) {
                name = user.getName();
            }
        }
        //遍历HospitalData里的医生 返回名字一致的医生
        for (Department d : hospitalData.getDepartments()) {
            for (Doctor doctor : d.getDoctors()) {
                if (doctor.getName().equals(name)) {
                    return doctor;
                }
            }
        }
        return null;
    }

    //通过医生确定该医生待管理的病人 返回一个ArrayList<Record> 即病人的一次看病记录
    public ArrayList<Record> myPatientRecords(Doctor doctor) {
        ArrayList<Record> result = new ArrayList<>();
        //遍历所有的病历本
        for (Integer i : recordData.getRecords().keySet()) {
            //遍历病历本里所有的看病记录
            for (OneRecord oneRecord : recordData.getRecords().getV(i).getMyRecord()) {
                //System.out.println(oneRecord);
                //如果这一次记录里医生的名字和传入的参数医生的名字一样 并且已经缴费 并且还没确诊 就add
                if (oneRecord.getRegister().getDoctor().equals(doctor.getName()) &&oneRecord.getStatus().equals("未分诊")) {
                    result.add(recordData.getRecords().getV(i));
                   // System.out.println(oneRecord);
                }
            }
        }
        return result;
    }

    public ArrayList<OneRecord> getOneRecordsByDepartment(String department){
        ArrayList<OneRecord> result = new ArrayList<>();
        for (Integer i : recordData.getRecords().keySet()) {
            for (OneRecord oneRecord : recordData.getRecords().getV(i).getMyRecord()) {
                if(oneRecord.getRegister().getDepartment().equals(department)&& oneRecord.getEnd() == false&&(oneRecord.getStatus().equals("未分诊")||oneRecord.getStatus().equals("检查中"))){
                    result.add(oneRecord);
                }
            }
        }
        return result;
    }
    //通过医生确定该医生待管理的病人 返回一个ArrayList<OneRecord> 即病人的一次看病记录
    public ArrayList<OneRecord> myPatientOneRecords(Doctor doctor) {
        ArrayList<OneRecord> result = new ArrayList<>();
        //遍历所有的病历本
        for (Integer i : recordData.getRecords().keySet()) {
            //遍历病历本里所有的看病记录
            for (OneRecord oneRecord : recordData.getRecords().getV(i).getMyRecord()) {
                //System.out.println(oneRecord);
                //如果这一次记录里医生的名字和传入的参数医生的名字一样 并且已经缴费 并且还没确诊 就add
                if (oneRecord.getRegister().getDoctor().equals(doctor.getName())&& oneRecord.getStatus().equals("未诊断")) {
                    result.add(oneRecord);
                   //System.out.println(oneRecord);
                }
            }
        }
        return result;
    }
    public ArrayList<OneRecord> makeSureTableOneRecords(Doctor doctor) {
        MyHashMap<Integer,Record> allRecords= new MyHashMap<>();
        ArrayList<String> json=new ArrayList<>();
        json.addAll(FileUse.readFile("Records.txt"));
        for(String s:json){
            Record record= JSONObject.parseObject(s,Record.class);
            int key=record.getID();
            allRecords.put(key,record);
           // System.out.println("Read"+record);
        }
        ArrayList<OneRecord> result = new ArrayList<>();
        //遍历所有的病历本
        for (Integer i : allRecords.keySet()) {
            //遍历病历本里所有的看病记录
            for (OneRecord oneRecord : allRecords.getV(i).getMyRecord()) {
              //  System.out.println(oneRecord);
                //如果这一次记录里医生的名字和传入的参数医生的名字一样 并且已经缴费 并且还没确诊 就add
                if (oneRecord.getRegister().getDoctor().equals(doctor.getName())&& (!oneRecord.getStatus().equals("未诊断"))&& (!oneRecord.getStatus().equals("未分诊"))) {
                    result.add(oneRecord);
                    //System.out.println("!!!!!!!"+oneRecord);
                }
            }
        }
        return result;
    }

    public ArrayList<OneRecord> treatedOneRecords(Doctor doctor) {
        ArrayList<OneRecord> result = new ArrayList<>();
        //遍历所有的病历本
        for (Integer i : recordData.getRecords().keySet()) {
            //遍历病历本里所有的看病记录
            for (OneRecord oneRecord : recordData.getRecords().getV(i).getMyRecord()) {
                System.out.println(oneRecord);
                //如果这一次记录里医生的名字和传入的参数医生的名字一样 并且已经缴费 并且还没确诊 就add
                if (oneRecord.getRegister().getDoctor().equals(doctor.getName())&& (oneRecord.getStatus().equals("未确诊")||oneRecord.getStatus().equals("检查中")||oneRecord.getStatus().equals("已确诊"))) {
                    result.add(oneRecord);
                    //System.out.println(oneRecord);
                }
            }
        }
        return result;
    }
    //通过医生确定该医生可管理的病人 返回一个ArrayList<Patient> 病人的集合
   /* public ArrayList<Patient> myPatients(Doctor doctor) {
        ArrayList<Patient> result = new ArrayList<>();
        for (Integer i : recordData.getRecords().keySet()) {

        }
    }*/

    //通过得到可编辑comboBox的TextField的值，匹配到相应代号，拼音编码的药品，返回一个ArrayList
    public List<Medicine> renewMedicine(String target,String type) {
        List<Medicine> medicines = hospitalData.getMedicines();
        List<Medicine> finalMedicines = new ArrayList<>();
        int length = target.length();
        String pattern = ".*" + target + ".*";
        for (Medicine m : medicines) {
            if (Pattern.matches(pattern, m.getID()) || Pattern.matches(pattern, m.getPinyin()) || Pattern.matches(pattern, m.getName())) {
                if(type.equals(m.getType())){
                    finalMedicines.add(m);
                }
            }
        }
        return finalMedicines;
    }

    //快速排序
     public ArrayList<Patient> qSortByID(ArrayList<Patient> p){
        if(p.size()>0)
        quickSortID(p,0,p.size()-1);
        for(Patient patient: p){
            System.out.println(patient.getId());
        }
        return p;
     }

    private void quickSortID(ArrayList<Patient> patients,int start,int end){
        //终止条件
        if(start>=end){
            return;
        }
        //获取当前支点
        int pivot= Integer.parseInt(patients.get(start).getId());
        int swapPos=start+1;
        //调整顺序 小的放左边 大的放右边
        for(int i=swapPos;i<=end;i++){
            if(Integer.parseInt(patients.get(i).getId())<pivot){//可能有问题
                Patient temp=patients.get(swapPos);
                patients.set(swapPos,patients.get(i));
                patients.set(i,temp);
                swapPos++;
            }
        }
        //更新pivot
        Patient p=patients.get(start);
        patients.set(start,patients.get(swapPos-1));
        patients.set(swapPos-1,p);
        //递归 对两边分别进行排序
        quickSortID(patients,start,swapPos-2);
        quickSortID(patients,swapPos,end);

    }

    public ArrayList<Patient> qSortByName(ArrayList<Patient>p){
        if(p.size()>0)
            quickSortName(p,0,p.size()-1);
        return p;

    }
    private ArrayList<Patient> quickSortNameD(ArrayList<Patient> patients,int start,int end){
        quickSortName(patients,start,end);
        ArrayList<Patient> result=new ArrayList<>();
        for(int i=patients.size()-1;i>=0;i--){
            result.add(patients.get(i));
        }
        return result;
    }
    private void quickSortName(ArrayList<Patient> patients,int start,int end){
        //终止条件
        if(start>end){
            return;
        }
        //获取当前支点
        String pivot=patients.get(start).getName();
        int swapPos=start+1;
        //调整顺序 小的放左边 大的放右边
        for(int i=swapPos;i<=end;i++){
            if(compare(patients.get(i).getName(),pivot)<0){//可能有问题
                Patient temp=patients.get(swapPos);
                patients.set(swapPos,patients.get(i));
                patients.set(i,temp);
                swapPos++;
            }
        }
        //更新pivot
        Patient p=patients.get(start);
        patients.set(start,patients.get(swapPos-1));
        patients.set(swapPos-1,p);
        //继续对左边和右边进行快排
        quickSortName(patients,start,swapPos-2);
        quickSortName(patients,swapPos,end);
    }

    //比较中文的先后顺序
    public int compare(String s1, String s2) {
        String o1 = "";
        String o2 = "";
        if (s1 != null) {
            o1 = s1;
        }
        if (s2 != null) {
            o2 = s2;
        }
        Collator instance = Collator.getInstance(Locale.CHINA);
        return instance.compare(o1, o2);
    }

    //冒泡排序
    public void bubbleSort(ArrayList<Patient> patients){
        for(int i =0;i<patients.size()-1;i++){
           for(int j =0;j<patients.size()-1-i;i++){
               if(Integer.parseInt(patients.get(j).getID())>Integer.parseInt(patients.get(j+1).getID())){
                  Patient temp=patients.get(j);
                  patients.set(j,patients.get(j+1));
                  patients.set(j+1,temp);
               }
           }
        }
    }

    public Medicine binarySearch(ArrayList<Medicine> medicines ,int ID){
        int low=0;
        int high=medicines.size()-1;
        if(ID <Integer.parseInt(medicines.get(low).getID()) ||ID >Integer.parseInt(medicines.get(high).getID()) || low > high){
            return null;
        }
        while(low<=high){
            int middle=(low+high)/2;
            if(ID<Integer.parseInt(medicines.get(middle).getID())){
                high=middle-1;
            }else if(ID>Integer.parseInt(medicines.get(middle).getID())){
                low=middle+1;
            }else{
                return medicines.get(middle);
            }

        }
        return null;
    }
}
