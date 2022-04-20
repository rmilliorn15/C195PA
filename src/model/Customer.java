package model;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;

public class Customer {

    private int id;
    private String name;
    private String address;
    private String zipCode;
    private String country;
    private String phoneNumber;
    private int customerDivision;

    private Timestamp created;

    private String createdBy;
    private Timestamp lastUpdate;
    private String updatedBy;


    private static ObservableList<Customer> customers = CustomerDB.getAllCustomers();


    /**
     * constructor for customer object.
     * @param id customer Id
     * @param name Customer Name
     * @param address Customer Address
     * @param zipCode Customer Zip
     * @param phoneNumber Customer Phone number
     * @param customerDivision Customer first level division.(state/provinces)
     */
    public Customer(int id, String name, String address, String zipCode,
                    String phoneNumber, Timestamp created, String createdBy,Timestamp lastUpdate, String updatedBy, int customerDivision){
        this.id = id;
        this.name = name;
        this.address = address;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.created = created;
        this.createdBy=createdBy;
        this.lastUpdate=lastUpdate;
        this.updatedBy=updatedBy;
        this.customerDivision = customerDivision;

    }

    /**
     * getter for customer ID
     * @return customer ID
     */
    public int getId() {
        return id;
    }

    /**
     * getter for customer name
      * @return customer name
     */
    public String getName() {
        return name;
    }

    /**
     * getter for customer address
      * @return customer address
     */
    public String getAddress() {
        return address;
    }

    /**
     * getter for customer country
      * @return customer country
     */
    public String getCountry() {
        return country;
    }

    /**
     * getter for first level division
      * @return customer division
     */
    public int getCustomerDivision() {
        return customerDivision;
    }

    /**
     * getter for customer Phone number
     * @return string containing phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * getter for customer country
     * @return customer country
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * setter for country
      * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * setter for customer address
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * setter for customer division.
      * @param customerDivision
     */
    public void setCustomerDivision(int customerDivision) {
        this.customerDivision = customerDivision;
    }

    /**
     * setter for customer ID
      * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * setter for customer Name
      * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * setter for customer phone number
      * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * setter for customer Zipcode
      * @param zipCode
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * adds customer to customer array List
     * @param newCustomer
     */
    public static void addCustomers(Customer newCustomer){
        customers.add(newCustomer);
    }

    /**
     * removes customer from customer array list.
     * @param selectedCustomer
     */
    public static void removeCustomer(Customer selectedCustomer){
        customers.remove(selectedCustomer);
    }

    /**
     * shows list of all customers in system currently
     * @return customer list array
     *
     */
    public static ObservableList<Customer> getAllCustomers(){
        return customers;
    }
}
