package Data.Others;

import Data.Hospital.Department;
import Data.Hospital.DiseaseTree;
import Data.Structure.MyHashMap;
import Data.Patient.Record;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.print.DocFlavor;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class FileUse {


//    public void readFile(File file) throws IOException, BiffException {
//        // 创建输入流，读取Excel
//        FileInputStream is = new FileInputStream(file);
//        // jxl提供的Workbook类
//        Workbook wb = Workbook.getWorkbook(is);
//        // 只有一个sheet,直接处理
//        //创建一个Sheet对象
//        Sheet sheet = wb.getSheet(0);
//        // 得到所有的行数
//        int rows = sheet.getRows();
//        // 所有的数据
//        List<List<String>> allData = new ArrayList<List<String>>();
//        // 越过第一行 它是列名称
//        for (int j = 1; j < rows; j++) {
//
//            List<String> oneData = new ArrayList<String>();
//            // 得到每一行的单元格的数据
//            Cell[] cells = sheet.getRow(j);
//            for (int k = 0; k < cells.length; k++) {
//
//                oneData.add(cells[k].getContents().trim());
//            }
//            // 存储每一条数据
//            allData.add(oneData);
//            // 打印出每一条数据
//            //System.out.println(oneData);
//
//        }
//
//    }

    /*
    读取json文件中的内容 传入文件名 返回一个ArrayList<String>
     */
    public static ArrayList<String> readFile(String fileName) {
        ArrayList<String> result = new ArrayList<>();
        File file = new File(fileName);
        try {
            FileReader reader = new FileReader(file);
            BufferedReader bf = new BufferedReader(reader);
            String s;
            while ((s = bf.readLine()) != null) {
                result.add(s);
                //System.out.println(s);
            }
            bf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void writeFile(String fileName, ArrayList<String> list) {
        File file = new File(fileName);
        try {
            FileWriter writer = new FileWriter(file);
            BufferedWriter bf = new BufferedWriter(writer);
            for (int i = 0; i < list.size(); i++) {
                bf.write(list.get(i) + "\n");
            }
            bf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeDepartments(String fileName, ArrayList<Department> departments) {
        ArrayList<String> json = new ArrayList<>();
        for (Department s : departments) {
            String str = JSON.toJSONString(s);
            json.add(str);
        }
        FileUse.writeFile(fileName, json);
    }

    public static void writeRecords(String fileName, MyHashMap<Integer, Record> records) {
        ArrayList<String> json = new ArrayList<>();
        for (Integer index : records.keySet()) {
            String s = JSON.toJSONString(records.getV(index));
            json.add(s);
        }
        writeFile(fileName, json);

    }

    public ArrayList getObjects(Object object, ArrayList<String> strings) {
        ArrayList result = null;
        for (String a : strings) {
            Object o1 = JSONObject.parseObject(a, object.getClass());
            result.add(o1);
        }
        return result;
    }

    //序列化读写对象
    static public void writeFileO(String name, Object a) throws IOException {
        FileOutputStream fos = new FileOutputStream(name);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        //如果传入的是List
        if (a instanceof List) {
            List list = (List) a;
            try {
                for (Object m : list) {
                    oos.writeObject(m);
                }
                fos.flush();
                fos.close();
                oos.close();
            } catch (NullPointerException e) {

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (a instanceof MyHashMap) {
            try {
                for (Object o : ((MyHashMap<Object, Object>) a).keySet()) {
                    oos.writeObject(((MyHashMap) a).getV(o));
                    //System.out.println(o);
                }
                fos.flush();
                fos.close();
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            oos.writeObject(a);
            fos.flush();
            fos.close();
            oos.close();
        }
    }

    //反序列化 读取records
    public static MyHashMap<Integer, Record> readFileO(String name) throws IOException {
        MyHashMap<Integer, Record> result = new MyHashMap<>();
        FileInputStream fis = new FileInputStream(name);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object m;
        //读取对象

        try {
            while ((m = ois.readObject()) != null) {

                Record r = (Record) m;
                int ID = r.getID();
                result.put(ID, r);

                System.out.println("读取信息： " + r);
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException e) {

        }
        fis.close();
        ois.close();
        return result;
    }

    public static DiseaseTree readTreeFromFile(String name) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(name);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object m;
        DiseaseTree tree = null;
        try {
            while ((m = ois.readObject()) != null) {
                tree = (DiseaseTree) m;
            }
        } catch (EOFException e) {

        }

        fis.close();
        ois.close();
        return tree;
    }

    //读取department
    public static ArrayList readFileDpm(String name) throws IOException {
        ArrayList<Department> result = new ArrayList<>();
        FileInputStream fis = new FileInputStream(name);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object m;
        //读取对象

        try {
            while ((m = ois.readObject()) != null) {
                Department r = (Department) m;
                result.add(r);
                // System.out.println(r);
            }
            //得到当前时间

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException e) {

        }
        fis.close();
        ois.close();
        return result;
    }






}
