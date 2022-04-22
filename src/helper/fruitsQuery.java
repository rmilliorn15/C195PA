package helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class fruitsQuery {

    public static int insert(String fruitName, int colorId) throws SQLException {
        String sql = "INSERT INTO FRUITS (Fruit_name, Color_ID) VALUES (?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, fruitName);
        ps.setInt(2, colorId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int update(int fruitId, String fruitName) throws SQLException {
        String sql = "UPDATE FRUITS SET Fruit_Name = ? WHERE Fruit_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, fruitName);
        ps.setInt(2, fruitId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int delete(int fruitId) throws SQLException {
        String sql = "DELETE FROM FRUITS WHERE Fruit_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, fruitId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }


    public static void select() throws SQLException {
        String sql = " SELECT * FROM FRUITS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int fruitID = rs.getInt("Fruit_ID");
            String fruitName = rs.getString("Fruit_Name");
            System.out.print(fruitID + " | " + fruitName);
        }
    }

    public static void select(int colorId) throws SQLException {
        String sql = " SELECT * FROM FRUITS WHERE Color_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, colorId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int fruitID = rs.getInt("Fruit_ID");
            int colorIdFk = rs.getInt("Color_ID");
            String fruitName = rs.getString("Fruit_Name");
            System.out.println(fruitID + " | " + fruitName + " | " + colorIdFk);
        }
    }
}
