import Data.Others.ConnectDB;
import Data.Others.FileUse;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

public class Conn {
    public static void main(String[] args) throws SQLException {
        String s=" ";
       // s= FileUse.operateDB();
        ConnectDB.connectDB();
        s= ConnectDB.reundRegisterDB(123);
        System.out.println(s);
    }
}
