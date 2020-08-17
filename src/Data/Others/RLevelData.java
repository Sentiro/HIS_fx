package Data.Others;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RLevelData implements Serializable {
    private List<RLevel> RLevels=new ArrayList<>();
    private static RLevelData instance;

    public static RLevelData getInstance(){
        if(instance==null){
            instance=new RLevelData();
        }
        return instance;
    }

    private  RLevelData() {
        RLevel r1=new RLevel("专家号",1,20);
        RLevel r2=new RLevel("普通号",1,5);
        RLevel r3=new RLevel("急诊号",1,30);
        RLevels.add(r1);
        RLevels.add(r2);
        RLevels.add(r3);
       /*ArrayList<String> rLevel=f.readFile("RLevel.txt");
       for(String a: rLevel){

       }*/
    }

    public List<RLevel> getRLevels() {
        return RLevels;
    }
}
