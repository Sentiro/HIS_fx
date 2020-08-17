package Data.Structure;

import Data.Patient.OneRecord;
import Data.Patient.Patient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Heap implements Serializable {
    int currentSize;
    ArrayList<OneRecord> patients;
    int max=1000;

    public Heap(int max) {
        this.max = max;
    }

    public Heap() {
        patients=new ArrayList<>();
    }
//建立大根堆
    public void build(ArrayList<OneRecord> patients){
        this.patients = patients;
        currentSize=patients.size();
        for(int i=0;i<currentSize;i++) { //从0开始
            adjustUp(i,this.patients);
        }


    }

    public void removeAll(){
        patients=null;
    }
    public void adjustUp(int i,ArrayList<OneRecord>patients){
        int currentPriority = patients.get(i).getPriority();
        OneRecord currentP=patients.get(i);
        while((i+1)/2>0){
            int ancestorPriority=patients.get(i/2).getPriority();
            if(currentPriority>ancestorPriority){
                patients.set(i,patients.get(i/2));
                i=i/2;
            }else{
                break;
            }
        }
        patients.set(i,currentP);
    }

    /*public void adjustUp(int i){
        int currentPriority = patients.get(i).getPriority();
        int current=i;
        Patient currentP=patients.get(i);
        int k=0;
        if(i==0) return;
        while(true){
            int ancestorPriority=patients.get((i-1)/2).getPriority();
            if(i==0){
                i=current;
                currentP=patients.get(i);
                currentPriority=patients.get(i).getPriority();
            }
            else if(currentPriority>ancestorPriority){
                patients.set(i,patients.get((i-1)/2));
                patients.set((i-1)/2,currentP);
                i=(i-1)/2;
            }else if(patients.get(k).getPriority()==currentPriority){
                break;
            }else{
                k=i;
                i=current;
            }
        }
        //patients.set(i,currentP);
    }*/

    public void adjustDown(){
      //  int currentPriority = patients.get(0).getPriority();
        OneRecord currentP=patients.get(0);
        int i=1;
        while(i*2<currentSize){
            OneRecord temp=patients.get(i-1);
            int current=i-1;
            int smaller,bigger;
            if(patients.get(2*i-1).getPriority()>patients.get(2*i).getPriority()){
                smaller=2*i; bigger=2*i-1;
            }else {smaller=2*i-1; bigger=2*i;}
            if(currentP.getPriority()<patients.get(bigger).getPriority()){
                patients.set(current,patients.get(bigger));
                i=bigger+1;
            }/*else if(temp.getPriority()<patients.get(bigger).getPriority()){
                patients.set(current,patients.get(bigger));
                i=bigger+1;
            }*/ else{
                break;
            }

        }
        patients.set(i-1,currentP);
    }

    public void remove(){
        if(currentSize==0){
            System.out.println("can not remove");
        }
        patients.set(0,patients.get(currentSize-1));
        patients.set(currentSize-1,null);
        currentSize=currentSize-1;
        adjustDown();
       //currentSize=currentSize-1;
        //patients.remove(currentSize);
       /* if(currentSize==0){
            System.out.println("can not remove");
        }
        patients.remove(0);
        currentSize=currentSize-1;*/
    }
    public void insert(OneRecord p){
        if(max==currentSize){
            System.out.println("cannot insert");
        }
        patients.add(p);
        adjustUp(currentSize,this.patients);
        currentSize++;
    }

    @Override
    public String toString() {
        //sort();
        String str=null;
        for(int i=0;i<currentSize;i++){
            str=str/*+patients.get(i).getID()*/+" "+patients.get(i).getPriority()+"/n";
        }
        return str;
    }

    public ArrayList<OneRecord> getPatientList(){
       /* int size=patients.size();
        ArrayList<OneRecord> firstMax=new ArrayList<>();
        for (int i= 0;i<size;i++) {
            System.out.println(patients.get(size-1-i).getPriority());
            firstMax.add(patients.get(size-1-i));
        }
        return firstMax;*/
      /* ArrayList<OneRecord> result=new ArrayList<>();
       for(int i=0;i<currentSize;i++){
           result.add(patients.get(i));
       }
       return result;*/
      //sort();
      return patients;
    }

    public ArrayList<OneRecord> sort(ArrayList<OneRecord> list){
        int index=list.size();
        while(index>1){
            OneRecord max=list.get(0);
            list.set(0,list.get(index-1));
            list.set(index-1,max);
            index=index-1;
            for(int i=0;i<index;i++) { //从0开始
                adjustUp(i,list);
            }
        }
        ArrayList<OneRecord> result=new ArrayList<>();
        for(int i=list.size()-1;i>=0;i--){
            result.add(list.get(i));
        }
      /*  for(int i=0;i<result.size();i++){
            System.out.println(result.get(i).getPriority()+result.get(i).getId());
        }*/
        return result;
    }
}
