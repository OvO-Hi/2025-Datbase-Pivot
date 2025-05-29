package dao;

import model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {
    private Connection conn;

    public UserDao(Connection conn) {
        this.conn = conn;
    }

    // 회원가입
    public boolean insertUser(User user) {
        String sql = "INSERT INTO user (login_id, pw, customer_name, phone_number) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getLoginId());
            pstmt.setString(2, user.getPw());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getPhoneNumber());
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 로그인
    public User login(String loginId, String pw) {
        String sql = "SELECT * FROM user WHERE login_id = ? AND pw = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, loginId);
            pstmt.setString(2, pw);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("login_id"),
                        rs.getString("pw"),
                        rs.getString("customer_name"),
                        rs.getString("phone_number"),
                        rs.getInt("is_admin")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 회원 탈퇴
    public boolean deleteUserById(int userId) {
        String sql = "DELETE FROM user WHERE user_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
