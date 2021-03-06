package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;


/** * @author Richard Milliorn */
public class Customer {

    private int id;
    private String name;
    private String address;
    private String zipCode;
    private String country;
    private String phoneNumber;
    private String customerDivision;
    private int divisionID;
    private String customerCountry;


    private static ObservableList<Customer> customers = CustomerDB.getAllCustomers();


    /**
     *
     * Constructor for customer object.
     * @param id customer Id
     * @param name Customer Name
     * @param address Customer Address
     * @param zipCode Customer Zip
     * @param phoneNumber Customer Phone number
     * @param customerDivision Customer first level division.(state/provinces)
     * @param customerCountry country from selection box
     */
    public Customer(int id, String name, String address, String zipCode,
                    String phoneNumber, String customerDivision, int divisionID, String customerCountry){
        this.id = id;
        this.name = name;
        this.address = address;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.divisionID = divisionID;
        this.customerDivision = customerDivision;
        this.customerCountry = customerCountry;

    }

    /**
     * Getter for customer ID
     * @return customer ID
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for customer name
     * @return customer name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for customer address
     * @return customer address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Getter for customer country
     * @return customer country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Getter for first level division
     * @return customer division
     */
    public String getCustomerDivision() {
        return customerDivision;
    }

    /**
     * Getter for customer Phone number
     * @return string containing phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Getter for customer zip/Postal code
     * @return customer postal code
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Setter for country
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Getter for country
     * @return customer country
     *
     */
    public String getCustomerCountry() {
        return customerCountry;
    }

    /**
     *Setter for customer address
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *Setter for customer division.
     * @param customerDivision
     */
    public void setCustomerDivision(String customerDivision) {
        this.customerDivision = customerDivision;
    }

    /**
     * Setter for customer ID
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter for customer Name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter for customer phone number
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Setter for customer Zipcode
     * @param zipCode
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * Setter for division ID
     * @param divisionID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * Getter for division
     * @return division ID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Setter for cust country.
     * @param customerCountry
     */
    public void setCustomercountry(String customerCountry) {
        this.customerCountry = customerCountry;
    }

    /**
     * Getter for country
     * @return customer country.
     */
    public String getCustomercountry() {
        return customerCountry;
    }

    /**
     * Adds customer to customer array List
     * @param newCustomer
     */
    public static void addCustomers(Customer newCustomer){
        customers.add(newCustomer);
    }

    /**
     * Removes customer from customer array list.
     * @param selectedCustomer
     */
    public static void removeCustomer(Customer selectedCustomer){
        customers.remove(selectedCustomer);
    }

    /**
     * Shows list of all customers in system currently
     * @return customer list array
     *
     */
    public static ObservableList<Customer> getAllCustomers(){
        return customers;
    }

    /**
     * Used for searching customer by ID.
     * @param Id customer ID number
     * @return matching customer
     */
    public static Customer lookupCustomer(int Id) {
        for (Customer searchCust : customers) {
            if (searchCust.getId() == Id) {
                return searchCust;
            }
        }
        return null;
    }

    /**
     * Searches customer by name.
     * @param partialName text being compared to existing items.
     * @return matching customers
     */
    public static ObservableList<Customer> lookupCustomer(String partialName) {
        ObservableList<Customer> namedCust = FXCollections.observableArrayList();

        for (Customer searchCust : customers) {
            if (searchCust.getName().contains(partialName)) {
                namedCust.add(searchCust);
            }
        }
        return namedCust;
    }

    /**
     * Used to update the tableview displaying current customer information
     * @param Index sent from main customer page when selected.
     * @param customer the new customer info being entered.
     */
    public static void updateCustomer(int Index, Customer customer){
        customers.set(Index, customer);


    }

}
