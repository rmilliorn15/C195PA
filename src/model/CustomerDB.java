package model;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CustomerDB {



    /**
     * Imports list of customers from DB and stores in array list
     *
     * join is so that division name and country name populate instead of numbers.
     *
     *
     * @return array list of customers
     */
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();


        try {
            String sql = "SELECT cust.Customer_ID, cust.Customer_Name, cust.Address, cust.Postal_Code, cust.Phone, " +
                    " cust.Division_ID, fld.Division, fld.COUNTRY_ID, ctry.Country FROM customers as cust JOIN first_level_divisions " +
                    "as fld on cust.Division_ID = fld.Division_ID JOIN countries as ctry ON fld.COUNTRY_ID = ctry.Country_ID";
            // String sql = "SELECT * FROM CUSTOMERS";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int customerID = resultSet.getInt("Customer_ID");
                String customerName = resultSet.getString("Customer_Name");
                String address = resultSet.getString("Address");
                String zipCode = resultSet.getString("Postal_Code");
                String division = resultSet.getString("Division");
                int divisionID = resultSet.getInt("Division_ID");
                String phoneNumber = resultSet.getString("Phone");
                String customerCountry = resultSet.getString("Country");


                Customer customer = new Customer(customerID, customerName, address, zipCode, phoneNumber, division, divisionID, customerCountry);
                customerList.add(customer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerList;
    }

    /**
     * Creates new customer on the database.
     * Made Void since it doesn't need to return anything except for when checking.
     * FOR THE ADD COMMAND
     *
     * @param customerName  input customer name
     * @param address       input address
     * @param zipCode       input zip
     * @param phoneNumber   input phone number
     * @param createdDate   auto crated timestamp when created.
     * @param createdBy     user who created the record. auto created when login
     * @param lastUpdate    last change made auto gen timestamp
     * @param lastUpdatedBy user auto gen when login
     * @param divisionID    selected division from combo box
     * @throws SQLException .
     */
    public static void insert(String customerName, String address, String zipCode, String phoneNumber, LocalDateTime createdDate,
                              String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int divisionID ) throws SQLException {
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

        ps.executeUpdate();
    }

    /**
     * Cannot be primative type.
     * returns list of customer ID numbers.
     * @return list of customer ID.
     */
    public static ObservableList<Integer> getCustomerID() throws SQLException {
        ObservableList<Integer> customerId=FXCollections.observableArrayList();
        String sql = "SELECT DISTINCT Customer_ID FROM CUSTOMERS";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            customerId.add(resultSet.getInt("Customer_ID"));
        }


        return customerId;
    }

    /**
     * Gets the next id from SQL database and uses it for auto gen id
     * @return Customer ID
     * @throws SQLException
     */
    public static int getMaxID() throws SQLException {
        int nextId = 0;
        String sql = "SELECT max(Customer_ID) AS Max_Cust_ID FROM customers";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            nextId = resultSet.getInt("Max_Cust_ID");
        }

        return nextId;
    }
// get the last record that was inserted and create an object out of that instead  "SELECT max(Customer_ID) AS Max_Cust_ID FROM customers";

    /**
     * Deletes customer from dababase by selected ID number.
     * @param customerID selected from table on main screen.
     * @throws SQLException
     */
    public static void deleteSelectedCustomer(int customerID) throws SQLException {
        String sql = "DELETE FROM CUSTOMERS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        ps.executeUpdate();
    }

    /**
     * Gets selected customer and updates their info on Database
     * RUNTIME ERROR
     * Forgot to add the ps.executeUpdate and couldn't figure out why it wouldn't update.
     * Also needs to have each item with = ? not at the end like with insert.
     * @param customerName name
     * @param address address
     * @param zipCode zip/postal code
     * @param phoneNumber phone number
     * @param lastUpdate timestamp of update
     * @param lastUpdatedBy user who updated lase
     * @param divisionID division id number
     * @param customerId customer id to be changed.
     * @throws SQLException .
     */
    public static void updateCustomer( int customerId, String customerName, String address, String zipCode, String phoneNumber,LocalDateTime lastUpdate, String lastUpdatedBy, int divisionID) throws SQLException {
        String sql = "UPDATE CUSTOMERS SET Customer_Name = ?, Address = ?, Postal_Code = ? ,Phone = ?,Last_Update = ?, Last_Updated_By = ?,Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3,zipCode);
        ps.setString(4,phoneNumber);
        ps.setTimestamp(5,Timestamp.valueOf(lastUpdate) );
        ps.setString(6,lastUpdatedBy);
        ps.setInt(7,divisionID);
        ps.setInt(8,customerId);
        ps.executeUpdate();
    }

    /**
     * Should send command to reset the auto increment on the customers table.
     * @throws SQLException
     */
    public static void resetAutoIncrement() throws SQLException {
        String sql ="ALTER TABLE CUSTOMERS AUTO_INCREMENT = 1";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        ps.executeUpdate();
    }
}



