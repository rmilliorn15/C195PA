package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;

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
     * Holds the created appointments.
     */
    private static ObservableList<Appointment> appointments = AppointmentsDB.getAllAppointments();

    /**
     * Creates new appointment object
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
     * Getter for appointment ID
     * @return appointment ID
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * Getter for contact id
     * @return contact id
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Getter for customer id
     * @return customer ID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Getter for user ID
     * @return user id
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Getter for contact name
     * @return contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Getter for description
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for location
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Getter for Title
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for type
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Getter for start time
     *
     * @return start time
     */
    public Timestamp getStartTime() {
        ZonedDateTime zonedDateTime = startTime.atZone(ZoneOffset.UTC);
        zonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of(String.valueOf(loginToDB.getUserZoneID())));
        LocalDateTime zonedStartTime = zonedDateTime.toLocalDateTime();
        return Timestamp.valueOf(zonedStartTime);
    }

    /**
     * Getter for end time
     *
     * @return end time
     */
    public Timestamp getEndTime() {
        ZonedDateTime zonedDateTime = endTime.atZone(ZoneOffset.UTC);
        zonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of(String.valueOf(loginToDB.getUserZoneID())));
        LocalDateTime zonedStartTime = zonedDateTime.toLocalDateTime();
        return Timestamp.valueOf(zonedStartTime);
    }

    /**
     * Adds new appointment to appointment array.
     * @param newAppointment new appointment created.
     */
    public static void addAppointment(Appointment newAppointment){
        appointments.add(newAppointment);
    }

    /**
     * Removes the appointment from array.
     * @param selectedAppointment selected on appointment screen.
     */
    public static void removeAppointment(Appointment selectedAppointment){
        appointments.remove(selectedAppointment);
    }

    /**
     * Updates existing appointment in array
     * @param Index from selected appointment
     * @param appointment changes made.
     */
    public static void updateAppointment(int Index, Appointment appointment){
        appointments.set(Index, appointment);
    }

    /**
     * Appointment array list.
     * @return list of all appointments
     */
    public static ObservableList<Appointment> getAllAppointments() {
        return appointments;
    }

    /**
     * Checks business hours and makes sure appointment is during those hours
     * @param apptTime appointment time when adding/ changing appointment
     * @return
     */
    public static boolean checkBusinessHours(LocalDateTime apptTime) {
        ZonedDateTime zoneAppt = apptTime.atZone(ZoneId.systemDefault());
        zoneAppt = zoneAppt.withZoneSameInstant(ZoneId.of("US/Eastern"));
        apptTime = zoneAppt.toLocalDateTime();

        LocalTime open = LocalTime.of(8,0);
        LocalTime close = LocalTime.of(22,0);
        return (apptTime.toLocalTime().isAfter(open) || apptTime.toLocalTime().equals(open)) && apptTime.toLocalTime().isBefore(close);

    }

    /**
     * Returns appointments within the next week
     * @return  array of appointments in the next week
     */
    public static ObservableList<Appointment> getWeekAppointments() {


        LocalDateTime beginTime = LocalDateTime.now(loginToDB.getUserZoneID());
        LocalDateTime endTime = beginTime.plusWeeks(1);
        ObservableList<Appointment> weeklyAppointments = FXCollections.observableArrayList();


        for (Appointment searchAppt : appointments) {
            LocalDateTime searchTime = searchAppt.getStartTime().toLocalDateTime();

            if (searchTime.isAfter(beginTime) && searchTime.isBefore(endTime)) {
                weeklyAppointments.add(searchAppt);
            }
        }
        return weeklyAppointments;
    }

    /**
     * LAMBDA EXPRESSION: iterates through appointments and compares time to filter by month.
     * Returns an array of appointments in the next month
     * @return array of appointments in the next month
     */
    public static ObservableList<Appointment> getMonthAppointments() {
        ObservableList<Appointment> monthlyAppointments = FXCollections.observableArrayList();

        LocalDateTime beginTime = LocalDateTime.now(loginToDB.getUserZoneID());
        LocalDateTime endTime = beginTime.plusMonths(1);

        appointments.forEach(appointment -> {
                LocalDateTime searchTime = appointment.getStartTime().toLocalDateTime();

                if (searchTime.isAfter(beginTime) && searchTime.isBefore(endTime)) {
                    monthlyAppointments.add(appointment);
                }
            });
        return monthlyAppointments;
    }

    /**
     * Checks appointments for any in the next 15 minutes.
     * @return appointments in the next 15 minutes.
     */
    public static ObservableList<Appointment> upcomingAppointment(){


        LocalDateTime beginTime = LocalDateTime.now(loginToDB.getUserZoneID());
        LocalDateTime endTime = beginTime.plusMinutes(15);
        ObservableList<Appointment> comingAppointment = FXCollections.observableArrayList();
        for (Appointment searchAppt : appointments) {
            ZonedDateTime zonedSearch = ZonedDateTime.from(searchAppt.getStartTime().toLocalDateTime().atZone(loginToDB.getUserZoneID()));
            zonedSearch = zonedSearch.withZoneSameInstant(loginToDB.getUserZoneID());
            LocalDateTime searchTime = zonedSearch.toLocalDateTime();
            if (searchTime.isAfter(beginTime) && searchTime.isBefore(endTime)) {
                comingAppointment.add(searchAppt);
                return comingAppointment;
            }
        }
        return null;
    }

    /**
     * Shows alert for the upcoming appointment when logged in.
     */
    public static void nextAppt(){
        if( Appointment.upcomingAppointment() != null){
            for(int i =0; i < upcomingAppointment().size(); i++) {
                Appointment nextAppt = upcomingAppointment().get(i);
                Alert warning = new Alert(Alert.AlertType.WARNING);
                warning.setTitle("Upcoming Appointment!");
                warning.setHeaderText("Appointment in the next 15 minutes!");
                warning.setContentText("Appointment ID " + nextAppt.getAppointmentID() + " Date and Time " + nextAppt.getStartTime());
                warning.show();
            }

        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Appointments Upcoming");
            alert.setHeaderText("No appointments in next 15 minutes.");
            alert.show();
        }

    }

    /**
     * Checks for appointment overlap
     * @param custID customer id
     * @param newDate new appointment date
     * @param newStart new start time
     * @param newEnd new end time
     * @return true if appointment overlap.
     * @throws SQLException
     */
    public static boolean checkOverlap(int custID, LocalDate newDate, LocalTime newStart, LocalTime newEnd) throws SQLException {
        ObservableList<Appointment> custAppts = AppointmentsDB.getApptByID(custID);
        boolean overlap = false;
        LocalDate startDate;
        LocalTime apptStime;
        LocalTime apptEtime;
        for (int i = 0;i < custAppts.size(); i++) {
            Appointment appt = custAppts.get(i);
            startDate = appt.getStartTime().toLocalDateTime().toLocalDate();
            if( custID == appt.getAppointmentID()){
                return false;
            }
            if (startDate.equals(newDate)) {
                apptStime = appt.getStartTime().toLocalDateTime().toLocalTime();
                apptEtime = appt.getEndTime().toLocalDateTime().toLocalTime();
                if ((newStart.isAfter(apptStime) || newStart.equals(apptStime)) && newStart.isBefore(apptEtime)) {
                    overlap = true;
                } else if (newEnd.isAfter(apptStime) && (newStart.isBefore(apptEtime) || newEnd.equals(apptEtime))) {
                    overlap = true;
                } else if ((newStart.isBefore(apptStime) || newStart.equals(apptStime)) && (newEnd.isAfter(apptEtime) || newEnd.equals(apptEtime))) {
                    overlap = true;
                }
            }

        }
        return overlap;
    }

}


