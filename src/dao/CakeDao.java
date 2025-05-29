package dao;

import model.Cake;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CakeDao {
    private Connection conn;

    public CakeDao(Connection conn) {
        this.conn = conn;
    }

    public List<Cake> getAllCakes() {
        List<Cake> list = new ArrayList<>();
        String sql = "SELECT * FROM cake";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Cake cake = new Cake(
                        rs.getInt("cake_id"),
                        rs.getString("cake_name"),
                        rs.getInt("price")
                );
                list.add(cake);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
