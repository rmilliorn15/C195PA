package model;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CustomerDB {

    int customerID;
    String customerName;
    String address;
    String zipCode;
    Timestamp createdDate;
    String createdBy;
    Timestamp lastUpdate;
    String lastUpdatedBy;
    String phoneNumber;
    int divisionID;

    /**
     * imports list of customers from DB and stores in arrray list
     *
     * @return array list of customers
     */
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM CUSTOMERS";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int customerID = resultSet.getInt("Customer_ID");
                String customerName = resultSet.getString("Customer_Name");
                String address = resultSet.getString("Address");
                String zipCode = resultSet.getString("Postal_Code");
                Timestamp createdDate = resultSet.getTimestamp("Create_Date");
                String createdBy = resultSet.getString("Created_By");
                Timestamp lastUpdate = resultSet.getTimestamp("Last_Update");
                String lastUpdatedBy = resultSet.getString("Last_Updated_By");
                String phoneNumber = resultSet.getString("Phone");
                int divisionID = resultSet.getInt("Division_ID");

                Customer customer = new Customer(customerID, customerName, address, zipCode, phoneNumber, createdDate, createdBy, lastUpdate, lastUpdatedBy, divisionID);
                customerList.add(customer);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerList;
    }

    public static int insert(String customerName,String address,String zipCode, String phoneNumber,Timestamp createdDate,String createdBy, Timestamp lastUpdate,String lastUpdatedBy,int divisionID ) throws SQLException {
        String sql = "INSERT INTO CUSTOMERS (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By,Last_Update, Last_Updated_By, Division_ID) VALUES (?, ?, ?, ?, ?, ?, ? ,? ,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3,zipCode);
        ps.setString(4,phoneNumber);
        ps.setTimestamp(5, createdDate);
        ps.setString(6,createdBy);
        ps.setTimestamp(7,lastUpdate);
        ps.setString(8,lastUpdatedBy);
        ps.setInt(9,divisionID);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
}

