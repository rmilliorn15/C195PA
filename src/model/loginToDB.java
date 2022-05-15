package model;

import helper.JDBC;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;

public class loginToDB {

    private static User loggedInUser;
    private static Locale userLocale;
    private static ZoneId userZoneID;


    /**
     * gets input from login screen and compares to users. if username and password correct created new logged in user.
     * @param userName
     * @param password
     * @return true if login successful else false
     * @throws SQLException
     */
    public static boolean loggedIn(String userName, String password) throws SQLException {
        String sql = ("SELECT * FROM USERS WHERE User_Name = ? AND Password = ?") ;
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, userName);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            String userNameLog = rs.getString("User_Name");
            int userIdLog = rs.getInt("User_ID");
            loggedInUser = new User(userIdLog, userNameLog);
            userLocale = Locale.getDefault();
            userZoneID = ZoneId.systemDefault();

            return true;
        }
        else {
            new Alert(Alert.AlertType.ERROR,"Invaild User Name or Password.");
            return false;
        }
    }

    public static Locale getUserLocale() {
        return userLocale;
    }

    public static ZoneId getUserZoneID() {
        return userZoneID;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }
}
