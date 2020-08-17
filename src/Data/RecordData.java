package Data;

import Data.Others.FileUse;
import Data.Patient.OneRecord;
import Data.Patient.Patient;
import Data.Patient.Record;
import Data.Structure.MyHashMap;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecordData {

    private MyHashMap<Integer,Record> records=new MyHashMap();//所有病历本
    private static RecordData instance;

    private RecordData() throws IOException {

        //String name, int age, String gender, String ageType, LocalDate birthDate, String ID, String address
        Record r1=new Record();
        Patient p1=new Patient("111111","yyc",19,"女","岁", LocalDate.of(2000,6,9),"sssssssss");
        r1.setOwner(p1);
        r1.setID(1234);
        List<OneRecord> myRecord=new ArrayList<>();
        /*OneRecord o1=new OneRecord();
        Register
        o1.setType("西医");o1.setRegister();
        myRecord.add(new OneRecord())
        r1.setMyRecord(new OneRecord())*/
     // records.put(1234,r1);
       /* ArrayList<String> records2=F.readFile("Records.txt");
        System.out.println(records2.toString());
        System.out.println("!!!!!!!");*/

      /*  for(String a:records2){
            Record d= JSONObject.parseObject(a,Record.class);
            records.put(d.getID(),d);
        }
        System.out.println(records);*/
   // records= (MyHashMap<Integer, Record>) FileUse.readFileO("Records.txt");
        ArrayList<String> json=new ArrayList<>();
        json.addAll(FileUse.readFile("Records.txt"));
        for(String s:json){
            Record record= JSONObject.parseObject(s,Record.class);
            int key=record.getID();
            records.put(key,record);
             System.out.println(record);
        }
    }
    public static RecordData getInstance() throws IOException {
        if(instance==null){
            instance=new RecordData();
        }
        return instance;
    }

    public MyHashMap<Integer, Record> getRecords() {
        return records;
    }

}
