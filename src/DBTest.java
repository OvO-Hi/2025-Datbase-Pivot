import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBTest {
    // 내부에 getConnection 메서드 정의
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://www.i-kina.co.kr:23406/ewha";
        // DB 이름
        String user = "ewha";      // 예: root
        String password = "ewhaPass2025##";  // 예: 1234

        return DriverManager.getConnection(url, user, password);
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("✅ DB 연결 성공!");
        } catch (Exception e) {
            System.out.println("❌ 연결 실패: " + e.getMessage());
        }
    }
}
