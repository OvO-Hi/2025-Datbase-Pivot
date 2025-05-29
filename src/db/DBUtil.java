package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        String url = "jdbc:mysql://www.i-kina.co.kr:23406/ewha?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Seoul";
        String user = "ewha";
        String password = "ewhaPass2025##";

        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, password);
    }
}
