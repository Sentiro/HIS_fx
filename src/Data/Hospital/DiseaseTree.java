package Data.Hospital;

import Data.Patient.OneRecord;
import Data.Patient.Patient;
import Data.RecordData;
import Data.Structure.TreeOverFlowException;

import javax.swing.tree.TreeNode;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

public class DiseaseTree implements Serializable {
    private static final int DEFAULT_MAX = 10;
    private DiseaseNode root;
    Vector<String> tempPatients=new Vector<>();
    /*private DiseaseNode[] nodes;
    private DiseaseNode root;
    private int firstFree = 0;*/
    private int size = DEFAULT_MAX;

    public DiseaseTree(int size) {
        this.size = size;
    }

    public DiseaseNode getRoot() {
        return root;
    }

    public void setRoot(DiseaseNode root) {
        this.root = root;
    }

    public void build(ArrayList<String> tree){
        root=new DiseaseNode(tree.get(0));
        for(int i = 1;i<tree.size();i=i+2){
            DiseaseNode parent;
            DiseaseNode current=new DiseaseNode(tree.get(i+1));
            parent=fetch(tree.get(i),root);
            if(parent==null){
                parent=new DiseaseNode(tree.get(i));
            }
            current.setParent(parent);
            insert(current,parent);
        }
    }

    public void insert(DiseaseNode tempNode,DiseaseNode now){
        if(now==null) return;
        Vector<DiseaseNode> list;
        if(now.getSub_diseases()!=null){
            list=now.getSub_diseases();
        }else{
            list=new Vector<>();
        }
        if(now.getName().equals(tempNode.getParent().getName())){

            list.add(tempNode);
            now.setSub_diseases(list);
            return;
        }
        for(int i=0;i<list.size();i++){
            DiseaseNode cursor=list.get(i);
            insert(tempNode,cursor);
        }
        return;
    }
    public void inOrder(DiseaseNode node) {

        System.out.println(node.getName());
       if(node.getSub_diseases()==null) return;

       for(int i=0;i<node.getSub_diseases().size();i++){
           inOrder(node.getSub_diseases().get(i));
       }
    }

    /*public void traverse(DiseaseNode node) throws IOException {
        System.out.println(node.getName());
        System.out.println("----------------------");
        Vector<String> oneRecords=node.getPatients();
        for(String ID:oneRecords){
            String name= RecordData.getInstance().getRecords().getV(Integer.valueOf(ID)).getOwner().getName();
            System.out.println(ID+" "+name);
        }
        System.out.println("----------------------");
        if(node.getSub_diseases()==null) return;

        for(int i=0;i<node.getSub_diseases().size();i++){
            inOrder(node.getSub_diseases().get(i));
        }
    }
*/
    /*public void insert(DiseaseNode tempNode) throws TreeOverFlowException {
        DiseaseNode parent =tempNode.getParent();
        if (firstFree == size) {
            throw new TreeOverFlowException("OverFlow!");
        }
        nodes[firstFree] = tempNode;
        // index++;
        if (root == null) {
            root = nodes[firstFree];
        } else {
            DiseaseNode node=fetch(parent,root);
            node.getSub_diseases().add(tempNode);
        }
        firstFree++;
    }*/

    public DiseaseNode fetch(String target)
    {
        return  fetch(target,root);
    }
    public DiseaseNode fetch(String target,DiseaseNode cursor) {

        if(cursor==null) return null;
        if(cursor.getName().equals(target)) return cursor;
        Vector<DiseaseNode> diseaseNodes = cursor.getSub_diseases();

        for (int i = 0; i < diseaseNodes.size(); i++) {
            cursor=diseaseNodes.get(i);
            if(cursor.getName().equals(target)) return cursor;
            if(cursor.getSub_diseases()!=null){
                DiseaseNode cnt=fetch(target,cursor);
                if(cnt!=null) return cnt;
            }
            }
        return null;
        }

        public Vector<String> getNodePatients(String name){
            DiseaseNode node=fetch(name);
            Vector<String> tempPatients=new Vector<>();
            fetchPatients(node,tempPatients);
            //System.out.println(tempPatients);
            return tempPatients;
        }

    private void fetchPatients(DiseaseNode node,Vector<String> patients){

       // System.out.println(node.getName()+node.getPatients());
        patients.addAll(node.getPatients());
        if(node.getSub_diseases()==null) return;

        for(int i=0;i<node.getSub_diseases().size();i++){
            fetchPatients(node.getSub_diseases().get(i),patients);
        }
    }
    @Override
    public String toString() {
        return "DiseaseTree{" +
                "root=" + root +
                ", size=" + size +
                '}';
    }
}
