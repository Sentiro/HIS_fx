package Data.Others;

import java.sql.*;
import java.time.LocalDate;

public class ConnectDB {
    private static Connection con;
    //jdbc驱动
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/hospital?&serverTimezone=UTC";
    private static String user = "root";
    private static String password = "19880818yyc";

    public static void connectDB() {
        String result=" ";
        try {
            //注册JDBC驱动程序
            Class.forName(driver);
            //建立连接
            con = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            System.out.println("数据库驱动没有安装");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库连接失败");
        }

    }

    /*
        functions
    * */
    public String distributeMedicineDB(int medRecordID) throws SQLException {
        String result="";
        if (!con.isClosed()) {
            System.out.println("数据库连接成功");
            // sql
            String sql = "{call distribute_medicine(?,?)}";
            CallableStatement stmt = con.prepareCall(sql);

            stmt.setObject(1, 600624);

            stmt.registerOutParameter(2, Types.VARCHAR);

            stmt.execute();

            result = stmt.getString(2);

        }
        con.close();
        return result;
    }

    public static String registerDB(String p_shenfenID, String p_name, String p_sex, LocalDate p_birth , int p_age , String p_ageType ,String  p_addr ,
    LocalDate p_date ,String p_wubie ,int p_doctorID ,int p_regLevel ,String p_regType,
    String bingli ,int p_registrarID  ) throws SQLException {
        String result="";
        if (!con.isClosed()) {
            System.out.println("数据库连接成功");
            // sql
            String sql = "{call register(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            CallableStatement stmt = con.prepareCall(sql);

            stmt.setObject(2, p_shenfenID);
            stmt.setObject(3, p_name);
            stmt.setObject(4, p_sex);
            stmt.setObject(5, p_birth);
            stmt.setObject(6, p_age);
            stmt.setObject(7, p_ageType);
            stmt.setObject(8, p_addr);
            stmt.setObject(9, p_date);
            stmt.setObject(10, p_wubie);
            stmt.setObject(11, p_doctorID);
            stmt.setObject(12, p_regLevel);
            stmt.setObject(13, p_regType);
            stmt.setObject(14, bingli);
            stmt.setObject(15, p_registrarID);

            stmt.registerOutParameter(1, Types.VARCHAR);

            stmt.execute();

            Object objRtn = stmt.getObject(1);
        }
        con.close();
        return result;
    }

    public static String reundRegisterDB(int medRecordID) throws SQLException {
        String result="";
        if (!con.isClosed()) {
            System.out.println("数据库连接成功");
            // sql
            String sql = "{call distribute_medicine(?,?)}";
            CallableStatement stmt = con.prepareCall(sql);

            stmt.setObject(1, 600624);

            stmt.registerOutParameter(2, Types.VARCHAR);

            stmt.execute();

            result = stmt.getString(2);

        }
        con.close();
        return result;
    }

}
