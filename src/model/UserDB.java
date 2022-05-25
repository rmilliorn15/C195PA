package model;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** * @author Richard Milliorn */
public class UserDB {


    /**
     * Gets users from database
     * @return users
     */
    public static ObservableList<User> getAllUsers(){
        ObservableList<User> userList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM USERS";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                int userID = resultSet.getInt("User_ID");
                String userName = resultSet.getString("User_Name");
                User user = new User(userID,userName);
                userList.add(user);
            }

        }  catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }

    public static String getUserName(int userID) {
        String userName = "Default";
        try{
            String sql = "SELECT * FROM USERS WHERE USER_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1,userID);

            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){

                userName = resultSet.getString("User_Name");


            }

        }  catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userName;
    }

}

