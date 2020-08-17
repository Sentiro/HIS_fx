package logic;

import Data.Structure.MyHashMap;
import Data.Others.RLevel;
import Data.Patient.OneRecord;
import Data.Patient.Record;
import Data.RecordData;

import java.io.IOException;
import java.util.List;

public class RegisterV {
/*
增删改查挂号信息
 */
private static RegisterV instance;
private Record OneRecord;//某个病历本 下 的某个人的所有看病信息
private MyHashMap<Integer,Record> records; //所有病历本
private RegisterV() throws IOException {
    records=RecordData.getInstance().getRecords();
}
public static RegisterV getInstance() throws IOException {
    if(instance==null){
        instance=new RegisterV();
    }
    return instance;
}
//有问题
//通过病历号定位到某一本病历本
/* public void addRegister(Register register,int ID){
    //如果已经有了这个病历本
    if(RecordData.getInstance().getRecord(ID)!=null){
        //调用方法 输入ID 返回病历本
        OneRecord=RecordData.getInstance().getRecord(ID);
        //新建一个看病记录
        Record newRecord=new Record();
        newRecord.setID(ID);
        //将挂号信息加到记录里
        newRecord.setRegister(register);
    }else{
        //新建一个看病记录
        Record newRecord=new Record();
        newRecord.setID(ID);
        //将挂号信息加到记录里
        newRecord.setRegister(register);
        //新建一个病历本
        OneRecord=new ArrayList<Record>();
        //将看病记录加到病历本里
        OneRecord.add(newRecord);
        //将病历本加到所有病历本里
        records.put(ID,OneRecord);//把病历本加到所有病历本中

     }


 }*/
//计算挂号费
public int  calculatePrice(boolean a, RLevel level){
    int price;
    if (level==null){
        return Integer.parseInt(null);
    }else{
        if(a==true){
            price=1+level.getCharge();
        }else{
            price=level.getCharge();
        }
        return price;
    }

}
//根据value 返回index
    public int getIndex(OneRecord oneRecord,List<OneRecord> myRecord){
       for(int i=0;i<myRecord.size();i++){
           if(myRecord.get(i).getDate().equals(oneRecord.getDate())){
               return i;
           }
       }
       return -1;
    }

}
