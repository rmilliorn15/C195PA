package model;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDB {

    public static ObservableList<String> getContactName() throws SQLException {
        ObservableList<String> contactList = FXCollections.observableArrayList();

            String sql = "SELECT * FROM CONTACTS";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                int contactID = resultSet.getInt("Contact_ID");
                String contactName = resultSet.getString("Contact_Name");
                String email =  resultSet.getString("Email");

                contactList.add(contactName);
            }

        return contactList;
    }

    public static int getContactId(String contactName) throws SQLException {
        int contactId = 0;
        String sql = "SELECT * FROM CoNTACTS WHERE CONTACT_NAME = ?";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, contactName);
        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
            contactId = resultSet.getInt("Contact_ID");
        }
        return contactId;
    }
}
