package model;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomerDB {



    /**
     * imports list of customers from DB and stores in array list
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
                String createdBy = resultSet.getString("Created_By");
                String lastUpdatedBy = resultSet.getString("Last_Updated_By");
                String phoneNumber = resultSet.getString("Phone");
                int divisionID = resultSet.getInt("Division_ID");

                Customer customer = new Customer(customerID, customerName, address, zipCode, phoneNumber, createdBy, lastUpdatedBy, divisionID);
                customerList.add(customer);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerList;
    }

    /**
     * creates new customer on the database.
     *
     * FOR THE ADD COMMAND
     *
     *
      * @param customerName input customer name
     * @param address input address
     * @param zipCode input zip
     * @param phoneNumber input phone number
     * @param createdDate auto crated timestamp when created.
     * @param createdBy user who created the record. auto created when login
     * @param lastUpdate last change made auto gen timestamp
     * @param lastUpdatedBy user auto gen when login
     * @param divisionID selected division from combo box
     * @return returns number of rows affected once the change is complete
     * @throws SQLException
     */
    public static int insert(String customerName,String address,String zipCode, String phoneNumber,LocalDateTime createdDate,String createdBy, LocalDateTime lastUpdate,String lastUpdatedBy,int divisionID ) throws SQLException {
        String sql = "INSERT INTO CUSTOMERS (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By,Last_Update, Last_Updated_By, Division_ID) VALUES (?, ?, ?, ?, ?, ?, ? ,? ,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3,zipCode);
        ps.setString(4,phoneNumber);
        ps.setTimestamp(5, Timestamp.valueOf(createdDate));
        ps.setString(6,createdBy);
        ps.setTimestamp(7,Timestamp.valueOf(lastUpdate));
        ps.setString(8,lastUpdatedBy);
        ps.setInt(9,divisionID);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
}

