package model;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AppointmentsDB {

    /**
     * Connects to database and gets all appointments.
     *
     * @return all appointments.
     */
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {

            String sql = "SELECT apt.Appointment_ID, apt.Title, apt.Description, apt.Location, apt.Type, apt.Start, apt.End, apt.Customer_ID, u.User_ID, c.Contact_ID, c.Contact_Name" +
                    " FROM APPOINTMENTS as apt JOIN CUSTOMERS as cust on apt.Customer_ID = cust.Customer_ID JOIN USERS as u on apt.User_ID = u.User_ID" +
                    " JOIN CONTACTS as c on apt.Contact_ID = c.Contact_ID";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int appointmentID = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                LocalDateTime startDate = resultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDate = resultSet.getTimestamp("End").toLocalDateTime();
                int customerID = resultSet.getInt("Customer_ID");
                int userID = resultSet.getInt("User_ID");
                int contactID = resultSet.getInt("Contact_ID");
                String contactName = resultSet.getString("Contact_Name");


                Appointment appointment = new Appointment(appointmentID, title, description, location, type
                        , startDate, endDate, customerID, userID, contactID, contactName);
                appointmentList.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }

    /**
     * Adds new appointment to database.
     *
     * @param title         title
     * @param description   description
     * @param location      location
     * @param type          type
     * @param start         start time
     * @param end           end time
     * @param createdDate   created date
     * @param createdBy     user that created appointment
     * @param lastUpdate    last update for appointment. should be the same as created for Insert
     * @param lastUpdatedBy same as created for insert
     * @param customerID    customer ID
     * @param userID        user ID
     * @param contactID     Contact ID
     * @throws SQLException .
     */
    public static void insert(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, LocalDateTime createdDate,
                              String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int customerID, int userID, int contactID) throws SQLException {

        String sql = "INSERT INTO APPOINTMENTS (Title, Description, Location, Type, Start , End, Create_Date, Created_By, " +
                " Last_Update, Last_Updated_BY, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ? ,? ,? , ?, ?, ?, ? )";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6, Timestamp.valueOf(end));
        ps.setString(7, String.valueOf(createdDate));
        ps.setString(8, createdBy);
        ps.setString(9, String.valueOf(lastUpdate));
        ps.setString(10, lastUpdatedBy);
        ps.setInt(11, customerID);
        ps.setInt(12, userID);
        ps.setInt(13, contactID);


        ps.executeUpdate();
    }

    /**
     * Deletes selected item from Database
     *
     * @param appointmentID selected appointment's id
     * @throws SQLException
     */
    public static void deleteSelectedAppointment(int appointmentID) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentID);
        ps.executeUpdate();
    }

    /**
     * Updates the selected appointment
     *
     * @param appointmentId appointment id
     * @param title         title
     * @param description   description
     * @param location      location
     * @param type          type
     * @param start         start time
     * @param end           end time
     * @param lastUpdate    last updated time
     * @param lastUpdatedBy updated by
     * @param customerId    customer id
     * @param userId        user id
     * @param contactId     contact id.
     * @throws SQLException
     */
    public static void updateAppointment(int appointmentId, String title, String description, String location, String type, LocalDateTime start,
                                         LocalDateTime end, LocalDateTime lastUpdate, String lastUpdatedBy, int customerId, int userId, int contactId) throws SQLException {
        String sql = "UPDATE APPOINTMENTS SET Title = ?, Description = ?, Location = ? , Type = ?, Start = ?, End = ?, " +
                "Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6, Timestamp.valueOf(end));
        ps.setString(7, String.valueOf(lastUpdate));
        ps.setString(8, lastUpdatedBy);
        ps.setInt(9, customerId);
        ps.setInt(10, userId);
        ps.setInt(11, contactId);
        ps.setInt(12, appointmentId);

        ps.executeUpdate();
    }

    /**
     * Returns the highest in use Id number from DB
     *
     * @return highest id in use.
     * @throws SQLException
     */
    public static int getMaxID() throws SQLException {
        int nextId = 0;
        String sql = "SELECT max(Appointment_ID) AS Max_Appt_ID FROM appointments";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            nextId = resultSet.getInt("Max_Appt_ID");
        }
        return nextId;
    }

    /**
     * Resets Id auto increment counter when deleting items.
     *
     * @throws SQLException
     */
    public static void resetAutoIncrement() throws SQLException {
        String sql = "ALTER TABLE APPOINTMENTS AUTO_INCREMENT = 1";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        ps.executeUpdate();
    }

    /**
     * Checks appointment list to see if selected customer is assigned to an appointment.
     *
     * @param customerID selcted customer
     * @return true if appointment found with customer id assigned.
     * @throws SQLException
     */
    public static boolean hasAppointment(int customerID) throws SQLException {
        boolean hasAppointment = false;
        String sql = "SELECT * FROM APPOINTMENTS WHERE Customer_ID = ? ";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, customerID);
        ResultSet resultSet = ps.executeQuery();

        if (resultSet.next()) {
            hasAppointment = true;
        }
        return hasAppointment;
    }

    /**
     * Used for appointment overlap
     *
     * @param custID   for new/updated appointment
     * @return list of appointments matching the selected customer on selected day.
     */
    public static ObservableList<Appointment> getApptByID(int custID) throws SQLException {
        ObservableList<Appointment> custAppointmentList = FXCollections.observableArrayList();


        String sql = "SELECT * FROM APPOINTMENTS WHERE Customer_ID = ? ";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, custID);
        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
            int appointmentID = resultSet.getInt("Appointment_ID");
            String title = resultSet.getString("Title");
            String description = resultSet.getString("Description");
            String location = resultSet.getString("Location");
            String type = resultSet.getString("Type");
            LocalDateTime startDate = resultSet.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endDate = resultSet.getTimestamp("End").toLocalDateTime();
            int customerID = resultSet.getInt("Customer_ID");
            int userID = resultSet.getInt("User_ID");
            int contactID = resultSet.getInt("Contact_ID");
            String contactName = ContactDB.getContactNameID(contactID);


            Appointment appointment = new Appointment(appointmentID, title, description, location, type
                    , startDate, endDate, customerID, userID, contactID, contactName);
            custAppointmentList.add(appointment);
        }
        return custAppointmentList;
    }

    /**
     * Gets list of appointments assigned to contact from DB
     * @param contact selected contact
     * @return list of contact appointments.
     * @throws SQLException
     */
    public static ObservableList<Appointment> getApptByContact(int contact) throws SQLException {
        ObservableList<Appointment> contactAppointmentList = FXCollections.observableArrayList();


        String sql = "SELECT * FROM APPOINTMENTS WHERE Contact_ID = ? ";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, contact);
        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
            int appointmentID = resultSet.getInt("Appointment_ID");
            String title = resultSet.getString("Title");
            String description = resultSet.getString("Description");
            String location = resultSet.getString("Location");
            String type = resultSet.getString("Type");
            LocalDateTime startDate = resultSet.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endDate = resultSet.getTimestamp("End").toLocalDateTime();
            int customerID = resultSet.getInt("Customer_ID");
            int userID = resultSet.getInt("User_ID");
            int contactID = resultSet.getInt("Contact_ID");
            String contactName = ContactDB.getContactNameID(contactID);


            Appointment appointment = new Appointment(appointmentID, title, description, location, type
                    , startDate, endDate, customerID, userID, contactID, contactName);
            contactAppointmentList.add(appointment);
        }
        return contactAppointmentList;
    }

    /**
     * Gets all appointment types
     * @return appointment type list
     * @throws SQLException
     */
    public static ObservableList<String> getAllApptType() throws SQLException {
        ObservableList<String> typeAppointmentList = FXCollections.observableArrayList();
        String sql = "SELECT DISTINCT * FROM APPOINTMENTS";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {

            String type = resultSet.getString("Type");
            typeAppointmentList.add(type);
        }

        return typeAppointmentList;
    }

    /**
     * Gets month name from appointments
     * @return Months that have an appointment.
     * @throws SQLException
     */
    public static ObservableList<String> getApptMonths() throws SQLException {
        ObservableList<String> apptMonth = FXCollections.observableArrayList();

        String sql = "SELECT DISTINCT MONTHNAME(START) AS NAME FROM APPOINTMENTS";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            String month =resultSet.getString("NAME");
            apptMonth.add(month);
        }
        return apptMonth;
    }

    /**
     * Gets appointments matching the selected type.
     * @param inputType selected type
     * @return number of appointments with that type
     * @throws SQLException
     */
    public static ObservableList<Appointment> getApptByType(String inputType, String inputMonth) throws SQLException {
        ObservableList<Appointment> typeAppointmentList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM APPOINTMENTS WHERE Type = ? AND MONTHNAME(START) = ? ";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString( 1, inputType);
        ps.setString(2,inputMonth);

        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            int appointmentID = resultSet.getInt("Appointment_ID");
            String title = resultSet.getString("Title");
            String description = resultSet.getString("Description");
            String location = resultSet.getString("Location");
            String type = resultSet.getString("Type");
            LocalDateTime startDate = resultSet.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endDate = resultSet.getTimestamp("End").toLocalDateTime();
            int customerID = resultSet.getInt("Customer_ID");
            int userID = resultSet.getInt("User_ID");
            int contactID = resultSet.getInt("Contact_ID");
            String contactName = ContactDB.getContactNameID(contactID);


            Appointment appointment = new Appointment(appointmentID, title, description, location, type
                    , startDate, endDate, customerID, userID, contactID, contactName);
            typeAppointmentList.add(appointment);
        }

        return typeAppointmentList;
    }

    /**
     * Gets the number of appointments by month
     * @param inputMonth selected month
     * @return number of appointments that month
     * @throws SQLException
     */
    public static ObservableList<Appointment> getApptByMonth(String inputMonth) throws SQLException {
        ObservableList<Appointment> monthAppointmentList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM APPOINTMENTS WHERE MONTHNAME(START) = ? ";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1,inputMonth);

        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            int appointmentID = resultSet.getInt("Appointment_ID");
            String title = resultSet.getString("Title");
            String description = resultSet.getString("Description");
            String location = resultSet.getString("Location");
            String type = resultSet.getString("Type");
            LocalDateTime startDate = resultSet.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endDate = resultSet.getTimestamp("End").toLocalDateTime();
            int customerID = resultSet.getInt("Customer_ID");
            int userID = resultSet.getInt("User_ID");
            int contactID = resultSet.getInt("Contact_ID");
            String contactName = ContactDB.getContactNameID(contactID);


            Appointment appointment = new Appointment(appointmentID, title, description, location, type
                    , startDate, endDate, customerID, userID, contactID, contactName);
            monthAppointmentList.add(appointment);
        }

        return monthAppointmentList;
    }


}
