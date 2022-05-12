package model;

import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Appointment {

    int appointmentID;
    String title;
    String description;
    String location;
    String type;
    LocalDateTime  startTime;
    LocalDateTime endTime;
    int customerID;
    int userID;
    int contactID;
    String contactName;

    /**
     * holds the created appointments.
     */
    private static ObservableList<Appointment> appointments = AppointmentsDB.getAllAppointments();

    /**
     * creates new appointment object
     * @param appointmentID id
     * @param title title
     * @param description description
     * @param location location
     * @param type type
     * @param startTime start time
     * @param endTime end time
     * @param customerID customer Id
     * @param userID user ID
     * @param contactID contact ID
     * @param contactName contact Name
     */
    public Appointment(int appointmentID, String title, String description, String location, String type, LocalDateTime startTime, LocalDateTime endTime, int customerID, int userID, int contactID, String contactName) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.location = location;
        this.description = description;
        this.type = type;

        this.startTime = startTime;

        this.endTime = endTime;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        this.contactName = contactName;
    }



    /**
     * getter for appointment ID
     * @return appointment ID
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * getter for contact id
     * @return contact id
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * gettter for customer id
     * @return customer ID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * getter for user ID
     * @return user id
     */
    public int getUserID() {
        return userID;
    }

    /**
     * getter for contact name
     * @return contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * getter for description
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * getter for location
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * getter for Title
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * getter for type
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * gertter for start time
     *
     * @return start time
     */
    public Timestamp getStartTime() {
        return Timestamp.valueOf(startTime);
    }


    /**
     * getter for end time
     *
     * @return end time
     */
    public Timestamp getEndTime() {
        return Timestamp.valueOf(endTime);
    }

    /**
     * adds new appointment to appointment array.
     * @param newAppointment new appointment created.
     */
    public static void addAppointment(Appointment newAppointment){
        appointments.add(newAppointment);
    }

    /**
     * removes the appointment from array.
     * @param selectedAppointment selected on appointment screen.
     */
    public static void removeAppointment(Appointment selectedAppointment){
        appointments.remove(selectedAppointment);
    }

    /**
     * updates existing appointment in array
     * @param Index from selected appointment
     * @param appointment changes made.
     */
    public static void updateAppointment(int Index, Appointment appointment){
        appointments.set(Index, appointment);
    }

    /**
     * appointment array list.
     * @return list of all appointments
     */
    public static ObservableList<Appointment> getAllAppointments() {
        return appointments;
    }


    /**
     * checks business hours and makes sure appointment is during those hours
     * @param apptTime
     * @return
     */
    public static boolean checkBusinessHours(LocalTime apptTime) {

        LocalTime open = LocalTime.of(8,0);
        LocalTime close = LocalTime.of(22,0);
        if (apptTime.isAfter(open) && apptTime.isBefore(close)){
            return true;
        }
        else {
            return false;
        }

    }

    public static LocalDateTime convertToUserTime(LocalDateTime enteredTime){
        LocalDateTime userTime = null;

       ZonedDateTime zonedDateTime = enteredTime.atZone(ZoneId.of("UTC"));
       zonedDateTime.withZoneSameInstant(ZoneId.systemDefault());
       userTime = zonedDateTime.toLocalDateTime();

        return userTime;
    }



}


