TITLE: Customer Appointment Scheduling Application. WGU C195 Java
PURPOSE: To access existing DataBase and add, update, delete Appointments and Customers.

Author : Richard Milliorn WGU ID#1325689, Application Version 1.0, 05/19/2022.


IDE Version: 2022.1 (Ultimate Edition) Runtime Version 11.0.14.1+, JDK Version 17.0.2, JavaFX Version 17.0.2.

DIRECTIONS: Launch program, enter username and password on login screen. Acknowledge any upcoming appointments. From selection screen choose to view Customers , Appointments, or Reports.

Customers Screen: List of current customers from Database displayed on table. You can choose to add a new customer or select one from the table and update or delete their information.
	Add screen: Fill out customers information and click save. All fields are Required and will not save if left blank. If added correctly you will return to the customers screen. 
	Delete Button: Highlight a customer from the table and select delete. If the customer has Appointments assigned to them you will have to go to the Appointments screen and remove them before you will be allowed to delete the Customer.
	Update Button: Select a customer from the table and click update. You will be taken to a page that populates with their current information. Change any fields to new values and click save. All fields are Required. 
	Go To Appointments Button: Click to go directly to appointments without having to retun to selection screen. 
	Back Button: Click back to return to the Selection screen. 

Appointments Screen: List of current appointments displayed on table and you can choose to Add, Update, or Delete an appointment from this screen.
	Add Screen: Fill out new Appointment information and click save. All fields required. If correctly added it will return to the appointments main screen and new appointment should show on the table. 
	Delete Button: Highlight appointlent from the list and click Delete. Once prmpted if you want to delete click ok and it will remove the selected appointment. 
	Update Button: Highlight appointment from the list and click Update. It will take you to a page similar to adding an appointment with the fields populated with existing information. Change the fields you want and Click save. All fields are Required. 
	Go To Customer Button: Click to go directly to Customers without having to retun to selection screen. 
	Back Button: Click back to return to the Selection screen.


Reports Screen: Opens a page with 3 tabs for different reports that can be run.
	Report 1: Contact schedule- select a contact using the drop down list. once selected the table will display all appointments assigned to that contact. 
	Report 2: Total appointments in selected month- Select a month from the drop down list. If a month is not shown, there are no appointments for that month. It will display the total number of appointments for the selected month.
	Report 3: Appointments by Type and Month: Choose an appointment Type from drop down and a month from the second drop down. If a month is not shown, there are no appointments for that month. Then click search It will show the number of appointments of the matching tyoe during the selected month. 
	Back Button: Click back to return to the Selection screen.


MySql Connector driver mysql-connector-java-8.0.28 & mysql-connector-java-8.0.29. (i used multiple computers and didnt realize i downloaded different versions.)
