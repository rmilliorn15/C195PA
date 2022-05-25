package model;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** * @author Richard Milliorn */
public class ContactDB {

    /**
     * Gets contacts from Database.
     * @return contact name
     * @throws SQLException
     */
    public static ObservableList<String> getContactName() throws SQLException {
        ObservableList<String> contactList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM CONTACTS";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ResultSet resultSet = ps.executeQuery();
        while(resultSet.next()){

            String contactName = resultSet.getString("Contact_Name");

            contactList.add(contactName);
        }

        return contactList;
    }

    public static String getContactNameID(int Id) throws SQLException {
        String contactName ="";
        String sql = "SELECT * FROM CONTACTS WHERE CONTACT_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, Id);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            contactName = resultSet.getString("Contact_Name");
        }
        return contactName;
    }


    /**
     * Gets contact id matching contact name
     * @param contactName selected name
     * @return contact ID.
     * @throws SQLException
     */
    public static int getContactId(String contactName) throws SQLException {
        int contactId = 0;
        String sql = "SELECT * FROM CONTACTS WHERE CONTACT_NAME = ?";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, contactName);
        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
            contactId = resultSet.getInt("Contact_ID");
        }
        return contactId;
    }
}
