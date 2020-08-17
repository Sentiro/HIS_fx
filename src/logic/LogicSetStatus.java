package logic;

import Data.Hospital.Medicine;
import Data.Others.item;
import Data.Patient.OneRecord;
import Data.RecordData;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class LogicSetStatus {
    RecordData recordData;
    {
        try {
            recordData = RecordData.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static LogicSetStatus instance;

    public static LogicSetStatus getInstance() throws IOException {
        if (instance == null) {
            instance = new LogicSetStatus();
        }
        return instance;
    }


    //根据药品名称和数量定位到所有看病记录里面特定一条的特定开药记录,并将这条药的记录的状态设为已缴费
    public void setSpecificMedicinePayedInOneRecord(int ID,String name,String time, String status){
        List<OneRecord> tempOneRecordList=recordData.getRecords().getV(ID).getMyRecord();
        Iterator<OneRecord> iterator=tempOneRecordList.iterator();
        while(iterator.hasNext()){
            OneRecord tempOneRecord=iterator.next();
            if(tempOneRecord.getTime().toString().equals(time)){
                List<Medicine> tempMedicines=tempOneRecord.getMedicines();
                Iterator<Medicine> medicineIterator=tempMedicines.iterator();
                while(medicineIterator.hasNext()){
                    Medicine tempMedicineItem=medicineIterator.next();
                    if(tempMedicineItem.getName().equals(name)){
                        tempMedicineItem.setMedicineStatus(status);
                    }
                }
            }
        }


    }


}

