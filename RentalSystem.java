package VehicalManagement;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Booking implements Serializable {

    private static final long serialVersionUID = 1L;

    private int bookingId;

    private int customerId;

    private String customerName;

    private int vehicleId;

    private String vehicleName;

    private String bookingDate;

    private String pickupDate;

    private String status;

    public Booking(int bookingId,

                   int customerId,

                   String customerName,

                   int vehicleId,

                   String vehicleName,

                   String bookingDate,

                   String pickupDate) {

        this.bookingId = bookingId;

        this.customerId = customerId;

        this.customerName = customerName;

        this.vehicleId = vehicleId;

        this.vehicleName = vehicleName;

        this.bookingDate = bookingDate;

        this.pickupDate = pickupDate;

        this.status = "CONFIRMED";

    }

    public int getBookingId() {

        return bookingId;

    }

    public int getCustomerId() {

        return customerId;

    }

    public String getCustomerName() {

        return customerName;

    }

    public int getVehicleId() {

        return vehicleId;

    }

    public String getVehicleName() {

        return vehicleName;

    }

    public String getBookingDate() {

        return bookingDate;

    }

    public String getPickupDate() {

        return pickupDate;

    }

    public String getStatus() {

        return status;

    }

    public void setStatus(String status) {

        this.status = status;

    }

    public void displayBooking() {

        System.out.println(

                "\n========== BOOKING DETAILS =========="

        );

        System.out.println(

                "Booking ID    : " + bookingId

        );

        System.out.println(

                "Customer ID   : " + customerId

        );

        System.out.println(

                "Customer Name : " + customerName

        );

        System.out.println(

                "Vehicle ID    : " + vehicleId

        );

        System.out.println(

                "Vehicle Name  : " + vehicleName

        );

        System.out.println(

                "Booking Date  : " + bookingDate

        );

        System.out.println(

                "Pickup Date   : " + pickupDate

        );

        System.out.println(

                "Booking Status: " + status

        );

        System.out.println(

                "======================================"

        );

    }

}

class BookingManagement {

    private ArrayList<Booking> bookings;

    private VehicleManagement vehicleManagement;

    private Scanner sc;

    private int nextBookingId = 9001;

    private final String FILE_NAME =

            "booking_details.txt";

    public BookingManagement(

            Scanner sc,

            VehicleManagement vehicleManagement) {

        this.sc = sc;

        this.vehicleManagement =

                vehicleManagement;

        loadBookings();

    }

    private void loadBookings() {

        bookings = new ArrayList<>();

        ArrayList<String> lines =

                DataManager.readFile(FILE_NAME);

        int bookingId = 0;

        int customerId = 0;

        String customerName = "";

        int vehicleId = 0;

        String vehicleName = "";

        String bookingDate = "";

        String pickupDate = "";

        String status = "CONFIRMED";

        for (String line : lines) {

            try {

                if (line.startsWith("Booking ID:")) {

                    bookingId =

                            Integer.parseInt(

                                    line.substring(

                                            line.indexOf(":") + 1

                                    ).trim()

                            );

                } else if (line.startsWith("Customer ID:")) {

                    customerId =

                            Integer.parseInt(

                                    line.substring(

                                            line.indexOf(":") + 1

                                    ).trim()

                            );

                } else if (line.startsWith("Customer Name:")) {

                    customerName =

                            line.substring(

                                    line.indexOf(":") + 1

                            ).trim();

                } else if (line.startsWith("Vehicle ID:")) {

                    vehicleId =

                            Integer.parseInt(

                                    line.substring(

                                            line.indexOf(":") + 1

                                    ).trim()

                            );

                } else if (line.startsWith("Vehicle Name:")) {

                    vehicleName =

                            line.substring(

                                    line.indexOf(":") + 1

                            ).trim();

                } else if (line.startsWith("Booking Date:")) {

                    bookingDate =

                            line.substring(

                                    line.indexOf(":") + 1

                            ).trim();

                } else if (line.startsWith("Pickup Date:")) {

                    pickupDate =

                            line.substring(

                                    line.indexOf(":") + 1

                            ).trim();

                } else if (line.startsWith("Booking Status:")) {

                    status =

                            line.substring(

                                    line.indexOf(":") + 1

                            ).trim();

                    Booking booking =

                            new Booking(

                                    bookingId,

                                    customerId,

                                    customerName,

                                    vehicleId,

                                    vehicleName,

                                    bookingDate,

                                    pickupDate

                            );

                    booking.setStatus(status);

                    bookings.add(booking);

                    if (bookingId >= nextBookingId) {

                        nextBookingId =

                                bookingId + 1;

                    }

                }

            } catch (Exception e) {

                // Ignore malformed records

            }

        }

    }

    private void saveBookings() {

        StringBuilder data =

                new StringBuilder();

        for (Booking b : bookings) {

            data.append(

                    "Booking ID: "

                    + b.getBookingId()

                    + "\n"

            );

            data.append(

                    "Customer ID: "

                    + b.getCustomerId()

                    + "\n"

            );

            data.append(

                    "Customer Name: "

                    + b.getCustomerName()

                    + "\n"

            );

            data.append(

                    "Vehicle ID: "

                    + b.getVehicleId()

                    + "\n"

            );

            data.append(

                    "Vehicle Name: "

                    + b.getVehicleName()

                    + "\n"

            );

            data.append(

                    "Booking Date: "

                    + b.getBookingDate()

                    + "\n"

            );

            data.append(

                    "Pickup Date: "

                    + b.getPickupDate()

                    + "\n"

            );

            data.append(

                    "Booking Status: "

                    + b.getStatus()

                    + "\n"

            );

            data.append(

                    "=====================================\n"

            );

        }

        DataManager.writeFile(

                FILE_NAME,

                data.toString()

        );

    }

    public void createBooking(Customer customer) {

        if (customer == null) {

            System.out.println(

                    "Please login first."

            );

            return;

        }

        System.out.println(

                "\n========== CREATE NEW BOOKING =========="

        );

        vehicleManagement.searchAvailableVehicles();

        try {

            System.out.print(

                    "Enter Vehicle ID: "

            );

            int vehicleId =

                    Integer.parseInt(

                            sc.nextLine()

                    );

            DataManager.saveInput(

                    "Booking Vehicle ID: "

                    + vehicleId

            );

            Vehicle vehicle =

                    vehicleManagement.getVehicle(

                            vehicleId

                    );

            if (vehicle == null) {

                System.out.println(

                        "Vehicle Not Found."

                );

                return;

            }

            if (!vehicle.isAvailable()) {

                System.out.println(

                        "Vehicle is NOT available."

                );

                return;

            }

            System.out.print(

                    "Enter Booking Date: "

            );

            String bookingDate =

                    sc.nextLine();

            DataManager.saveInput(

                    "Booking Date: "

                    + bookingDate

            );

            System.out.print(

                    "Enter Pickup Date: "

            );

            String pickupDate =

                    sc.nextLine();

            DataManager.saveInput(

                    "Pickup Date: "

                    + pickupDate

            );

            System.out.println(

                    "\n========== BOOKING SUMMARY =========="

            );

            System.out.println(

                    "Customer Name : "

                    + customer.getName()

            );

            System.out.println(

                    "Vehicle ID    : "

                    + vehicle.getId()

            );

            System.out.println(

                    "Vehicle Name  : "

                    + vehicle.getName()

            );

            System.out.println(

                    "Booking Date  : "

                    + bookingDate

            );

            System.out.println(

                    "Pickup Date   : "

                    + pickupDate

            );

            System.out.print(

                    "\nConfirm Booking? (Y/N): "

            );

            String choice = sc.nextLine();

            DataManager.saveInput(

                    "Booking Confirmation: "

                    + choice

            );

            if (choice.equalsIgnoreCase("Y")) {

                Booking booking =

                        new Booking(

                                nextBookingId++,

                                customer.getCustomerId(),

                                customer.getName(),

                                vehicle.getId(),

                                vehicle.getName(),

                                bookingDate,

                                pickupDate

                        );

                bookings.add(booking);

                saveBookings();

                System.out.println(

                        "\nBooking Confirmed Successfully!"

                );

                System.out.println(

                        "Booking ID: "

                        + booking.getBookingId()

                );

                booking.displayBooking();

            } else {

                System.out.println(

                        "\nBooking Cancelled."

                );

            }

        } catch (NumberFormatException e) {

            System.out.println(

                    "Please enter a valid Vehicle ID."

            );

        }

    }

    public void viewMyBookings(Customer customer) {

        System.out.println(

                "\n========== MY BOOKINGS =========="

        );

        boolean found = false;

        for (Booking b : bookings) {

            if (b.getCustomerId()

                    == customer.getCustomerId()) {

                b.displayBooking();

                found = true;

            }

        }

        if (!found) {

            System.out.println(

                    "No bookings found."

            );

        }

    }

    public void viewAllBookings() {

        System.out.println(

                "\n========== ALL BOOKINGS =========="

        );

        if (bookings.isEmpty()) {

            System.out.println(

                    "No bookings available."

            );

            return;

        }

        for (Booking b : bookings) {

            b.displayBooking();

        }

    }

    public void checkBookingStatus() {

        try {

            System.out.print(

                    "Enter Booking ID: "

            );

            int bookingId =

                    Integer.parseInt(

                            sc.nextLine()

                    );

            for (Booking b : bookings) {

                if (b.getBookingId()

                        == bookingId) {

                    System.out.println(

                            "\nBooking Status: "

                            + b.getStatus()

                    );

                    return;

                }

            }

            System.out.println(

                    "Booking Not Found."

            );

        } catch (NumberFormatException e) {

            System.out.println(

                    "Invalid Booking ID."

            );

        }

    }

    public void cancelBooking(Customer customer) {

        try {

            System.out.print(

                    "Enter Booking ID to Cancel: "

            );

            int bookingId =

                    Integer.parseInt(

                            sc.nextLine()

                    );

            for (Booking b : bookings) {

                if (b.getBookingId()

                        == bookingId) {

                    if (b.getCustomerId()

                            != customer.getCustomerId()) {

                        System.out.println(

                                "This booking does not belong to you."

                        );

                        return;

                    }

                    if (b.getStatus()

                            .equalsIgnoreCase("CANCELLED")) {

                        System.out.println(

                                "Booking is already cancelled."

                        );

                        return;

                    }

                    b.setStatus("CANCELLED");

                    saveBookings();

                    System.out.println(

                            "Booking Cancelled Successfully."

                    );

                    return;

                }

            }

            System.out.println(

                    "Booking Not Found."

            );

        } catch (NumberFormatException e) {

            System.out.println(

                    "Invalid Booking ID."

            );

        }

    }

    public void saveCurrentData() {

        saveBookings();

    }

}

public class RentalSystem {

    static Scanner sc =

            new Scanner(System.in);

    static ArrayList<Customer> customers =

            new ArrayList<>();

    static Admin admin =

            new Admin();

    static VehicleManagement vehicleManagement =

            new VehicleManagement(sc);

    static RentalManagement rentalManagement =

            new RentalManagement(

                    sc,

                    vehicleManagement

            );

    static PaymentManagement paymentManagement =

            new PaymentManagement(sc);

    static ReturnManagement returnManagement =

            new ReturnManagement(

                    sc,

                    vehicleManagement,

                    rentalManagement

            );

    // ========================================================

    // BOOKING MANAGEMENT OBJECT

    // ========================================================

    static BookingManagement bookingManagement =

            new BookingManagement(

                    sc,

                    vehicleManagement

            );

    static int nextCustomerId = 1;

    // ========================================================

    // MAIN

    // ========================================================

    public static void main(String[] args) {

        loadCustomers();

        int choice = 0;

        do {

            System.out.println(

                    "\n=========================================="

            );

            System.out.println(

                    "              RENTRIDE"

            );

            System.out.println(

                    "        VEHICLE RENTAL SYSTEM"

            );

            System.out.println(

                    "=========================================="

            );

            System.out.println(

                    "1. Admin Login"

            );

            System.out.println(

                    "2. Customer Registration"

            );

            System.out.println(

                    "3. Customer Login"

            );

            System.out.println(

                    "4. Exit"

            );

            System.out.println(

                    "=========================================="

            );

            System.out.print(

                    "Enter your choice: "

            );

            try {

                choice =

                        Integer.parseInt(

                                sc.nextLine()

                        );

                switch (choice) {

                    case 1:

                        adminLogin();

                        break;

                    case 2:

                        customerRegistration();

                        break;

                    case 3:

                        customerLogin();

                        break;

                    case 4:

                        saveAllData();

                        System.out.println(

                                "\nThank You for using RentRide!"

                        );

                        break;

                    default:

                        System.out.println(

                                "Invalid Choice!"

                        );

                }

            } catch (NumberFormatException e) {

                System.out.println(

                        "Please enter a valid choice."

                );

                choice = 0;

            }

        } while (choice != 4);

        sc.close();

    }

    // ========================================================

    // LOAD CUSTOMERS

    // ========================================================

    static void loadCustomers() {

        ArrayList<String> lines =

                DataManager.readFile(

                        "customer_details.txt"

                );

        customers = new ArrayList<>();

        int customerId = 0;

        String name = "";

        String phone = "";

        String username = "";

        String password = "";

        for (String line : lines) {

            try {

                if (line.startsWith("Customer ID:")) {

                    customerId =

                            Integer.parseInt(

                                    line.substring(

                                            line.indexOf(":") + 1

                                    ).trim()

                            );

                } else if (line.startsWith("Name:")) {

                    name =

                            line.substring(

                                    line.indexOf(":") + 1

                            ).trim();

                } else if (line.startsWith("Phone:")) {

                    phone =

                            line.substring(

                                    line.indexOf(":") + 1

                            ).trim();

                } else if (line.startsWith("Username:")) {

                    username =

                            line.substring(

                                    line.indexOf(":") + 1

                            ).trim();

                } else if (line.startsWith("Password:")) {

                    password =

                            line.substring(

                                    line.indexOf(":") + 1

                            ).trim();

                    Customer customer =

                            new Customer(

                                    customerId,

                                    name,

                                    phone,

                                    username,

                                    password

                            );

                    customers.add(customer);

                    if (customerId >= nextCustomerId) {

                        nextCustomerId =

                                customerId + 1;

                    }

                }

            } catch (Exception e) {

                // Ignore malformed records

            }

        }

    }

    // ========================================================

    // SAVE CUSTOMERS

    // ========================================================

    static void saveCustomers() {

        StringBuilder data =

                new StringBuilder();

        for (Customer c : customers) {

            data.append(

                    "Customer ID: "

                    + c.getCustomerId()

                    + "\n"

            );

            data.append(

                    "Name: "

                    + c.getName()

                    + "\n"

            );

            data.append(

                    "Phone: "

                    + c.getPhone()

                    + "\n"

            );

            data.append(

                    "Username: "

                    + c.getUsername()

                    + "\n"

            );

            data.append(

                    "Password: "

                    + c.getPassword()

                    + "\n"

            );

            data.append(

                    "----------------------------------------\n"

            );

        }

        DataManager.writeFile(

                "customer_details.txt",

                data.toString()

        );

    }

    // ========================================================

    // SAVE ALL DATA

    // ========================================================

    static void saveAllData() {

        saveCustomers();

        vehicleManagement.saveCurrentData();

        rentalManagement.saveCurrentData();

        bookingManagement.saveCurrentData();

    }

    // ========================================================

    // ADMIN LOGIN

    // ========================================================

    static void adminLogin() {

        System.out.println(

                "\n========== ADMIN LOGIN =========="

        );

        System.out.print(

                "Enter Admin Username: "

        );

        String username = sc.nextLine();

        System.out.print(

                "Enter Admin Password: "

        );

        String password = sc.nextLine();

        DataManager.saveInput(

                "Admin Username: " + username

        );

        if (admin.adminLogin(

                username,

                password)) {

            adminDashboard();

        } else {

            System.out.println(

                    "Admin Login Failed."

            );

        }

    }

    // ========================================================

    // ADMIN DASHBOARD

    // ========================================================

    static void adminDashboard() {

        int choice = 0;

        do {

            System.out.println(

                    "\n=========================================="

            );

            System.out.println(

                    "             ADMIN DASHBOARD"

            );

            System.out.println(

                    "=========================================="

            );

            System.out.println(

                    "1. Add Vehicle"

            );

            System.out.println(

                    "2. View All Vehicles"

            );

            System.out.println(

                    "3. Search Vehicle"

            );

            System.out.println(

                    "4. Update Vehicle"

            );

            System.out.println(

                    "5. Remove Vehicle"

            );

            System.out.println(

                    "6. View All Rentals"

            );

            System.out.println(

                    "7. View All Payments"

            );

            System.out.println(

                    "8. View All Returns"

            );

            System.out.println(

                    "9. View All Bookings"

            );

            System.out.println(

                    "10. Logout"

            );

            System.out.println(

                    "=========================================="

            );

            System.out.print(

                    "Enter your choice: "

            );

            try {

                choice =

                        Integer.parseInt(

                                sc.nextLine()

                        );

                switch (choice) {

                    case 1:

                        vehicleManagement.addVehicle();

                        break;

                    case 2:

                        vehicleManagement.viewAllVehicles();

                        break;

                    case 3:

                        searchMenu();

                        break;

                    case 4:

                        vehicleManagement.updateVehicle();

                        break;

                    case 5:

                        vehicleManagement.removeVehicle();

                        break;

                    case 6:

                        rentalManagement.viewAllRentals();

                        break;

                    case 7:

                        paymentManagement.viewAllPayments();

                        break;

                    case 8:

                        returnManagement.viewAllReturns();

                        break;

                    case 9:

                        bookingManagement.viewAllBookings();

                        break;

                    case 10:

                        admin.logoutUser();

                        break;

                    default:

                        System.out.println(

                                "Invalid Choice!"

                        );

                }

            } catch (NumberFormatException e) {

                System.out.println(

                        "Please enter a valid choice."

                );

                choice = 0;

            }

        } while (choice != 10);

    }

    // ========================================================

    // CUSTOMER REGISTRATION

    // ========================================================

    static void customerRegistration() {

        System.out.println(

                "\n========== CUSTOMER REGISTRATION =========="

        );

        System.out.print(

                "Enter Name: "

        );

        String name = sc.nextLine();

        System.out.print(

                "Enter Phone: "

        );

        String phone = sc.nextLine();

        System.out.print(

                "Enter Username: "

        );

        String username = sc.nextLine();

        System.out.print(

                "Enter Password: "

        );

        String password = sc.nextLine();

        DataManager.saveInput(

                "Customer Name: " + name

        );

        DataManager.saveInput(

                "Customer Phone: " + phone

        );

        DataManager.saveInput(

                "Customer Username: " + username

        );

        for (Customer c : customers) {

            if (c.getUsername()

                    .equals(username)) {

                System.out.println(

                        "Username already exists."

                );

                return;

            }

        }

        Customer customer =

                new Customer(

                        nextCustomerId++,

                        name,

                        phone,

                        username,

                        password

                );

        customers.add(customer);

        saveCustomers();

        System.out.println(

                "\nCustomer Registration Successful!"

        );

        System.out.println(

                "Your Customer ID is: "

                + customer.getCustomerId()

        );

    }

    // ========================================================

    // CUSTOMER LOGIN

    // ========================================================

    static void customerLogin() {

        System.out.println(

                "\n========== CUSTOMER LOGIN =========="

        );

        System.out.print(

                "Enter Username: "

        );

        String username = sc.nextLine();

        System.out.print(

                "Enter Password: "

        );

        String password = sc.nextLine();

        for (Customer customer : customers) {

            if (customer.loginCustomer(

                    username,

                    password)) {

                System.out.println(

                        "Welcome "

                        + customer.getName()

                        + "!"

                );

                customerDashboard(customer);

                return;

            }

        }

        System.out.println(

                "Customer Login Failed."

        );

    }

    // ========================================================

    // CUSTOMER DASHBOARD

    // ========================================================

    static void customerDashboard(

            Customer customer) {

        int choice = 0;

        do {

            System.out.println(

                    "\n=========================================="

            );

            System.out.println(

                    "           CUSTOMER DASHBOARD"

            );

            System.out.println(

                    "=========================================="

            );

            System.out.println(

                    "1. View Available Vehicles"

            );

            System.out.println(

                    "2. Search Vehicle"

            );

            System.out.println(

                    "3. Rent Vehicle"

            );

            System.out.println(

                    "4. View My Rentals"

            );

            System.out.println(

                    "5. Make Payment"

            );

            System.out.println(

                    "6. Return Vehicle"

            );

            System.out.println(

                    "7. View Customer Details"

            );

            System.out.println(

                    "8. Update Customer Details"

            );

            System.out.println(

                    "9. Booking Management"

            );

            System.out.println(

                    "10. Logout"

            );

            System.out.println(

                    "=========================================="

            );

            System.out.print(

                    "Enter your choice: "

            );

            try {

                choice =

                        Integer.parseInt(

                                sc.nextLine()

                        );

                switch (choice) {

                    case 1:

                        vehicleManagement

                                .searchAvailableVehicles();

                        break;

                    case 2:

                        searchMenu();

                        break;

                    case 3:

                        rentalManagement

                                .createRental(

                                        customer

                                );

                        break;

                    case 4:

                        rentalManagement

                                .viewMyRentals(

                                        customer

                                );

                        break;

                    case 5:

                        makePayment(customer);

                        break;

                    case 6:

                        returnManagement

                                .returnVehicle();

                        break;

                    case 7:

                        customer

                                .viewCustomerDetails();

                        break;

                    case 8:

                        updateCustomer(customer);

                        break;

                    case 9:

                        bookingMenu(customer);

                        break;

                    case 10:

                        customer.logoutUser();

                        break;

                    default:

                        System.out.println(

                                "Invalid Choice!"

                        );

                }

            } catch (NumberFormatException e) {

                System.out.println(

                        "Please enter a valid choice."

                );

                choice = 0;

            }

        } while (choice != 10);

    }

    // ========================================================

    // BOOKING MENU

    // ========================================================

    static void bookingMenu(Customer customer) {

        int choice = 0;

        do {

            System.out.println(

                    "\n========== BOOKING MANAGEMENT =========="

            );

            System.out.println(

                    "1. Create New Booking"

            );

            System.out.println(

                    "2. View My Bookings"

            );

            System.out.println(

                    "3. Check Booking Status"

            );

            System.out.println(

                    "4. Cancel Booking"

            );

            System.out.println(

                    "5. Back"

            );

            System.out.print(

                    "Enter your choice: "

            );

            try {

                choice =

                        Integer.parseInt(

                                sc.nextLine()

                        );

                switch (choice) {

                    case 1:

                        bookingManagement

                                .createBooking(

                                        customer

                                );

                        break;

                    case 2:

                        bookingManagement

                                .viewMyBookings(

                                        customer

                                );

                        break;

                    case 3:

                        bookingManagement

                                .checkBookingStatus();

                        break;

                    case 4:

                        bookingManagement

                                .cancelBooking(

                                        customer

                                );

                        break;

                    case 5:

                        System.out.println(

                                "Returning..."

                        );

                        break;

                    default:

                        System.out.println(

                                "Invalid Choice!"

                        );

                }

            } catch (NumberFormatException e) {

                System.out.println(

                        "Please enter a valid choice."

                );

                choice = 0;

            }

        } while (choice != 5);

    }

    // ========================================================

    // MAKE PAYMENT

    // ========================================================

    static void makePayment(

            Customer customer) {

        try {

            System.out.print(

                    "Enter Rental ID: "

            );

            int rentalId =

                    Integer.parseInt(

                            sc.nextLine()

                    );

            Rental rental =

                    rentalManagement

                            .findRental(

                                    rentalId

                            );

            if (rental == null) {

                System.out.println(

                        "Rental Not Found."

                );

                return;

            }

            if (rental.getCustomerId()

                    != customer.getCustomerId()) {

                System.out.println(

                        "This rental does not belong to you."

                );

                return;

            }

            paymentManagement

                    .processPayment(

                            rental,

                            customer

                    );

        } catch (NumberFormatException e) {

            System.out.println(

                    "Invalid Rental ID."

            );

        }

    }

    // ========================================================

    // UPDATE CUSTOMER

    // ========================================================

    static void updateCustomer(

            Customer customer) {

        System.out.println(

                "\n========== UPDATE CUSTOMER =========="

        );

        System.out.print(

                "Enter New Name: "

        );

        String name = sc.nextLine();

        System.out.print(

                "Enter New Phone: "

        );

        String phone = sc.nextLine();

        customer.updateCustomerDetails(

                name,

                phone

        );

        saveCustomers();

    }

    // ========================================================

    // SEARCH MENU

    // ========================================================

    static void searchMenu() {

        int choice = 0;

        do {

            System.out.println(

                    "\n========== VEHICLE SEARCH =========="

            );

            System.out.println(

                    "1. Search By Vehicle ID"

            );

            System.out.println(

                    "2. Search By Vehicle Name"

            );

            System.out.println(

                    "3. Search By Vehicle Type"

            );

            System.out.println(

                    "4. Search By Model"

            );

            System.out.println(

                    "5. Search By Rent Amount"

            );

            System.out.println(

                    "6. Display Available Vehicles"

            );

            System.out.println(

                    "7. Back"

            );

            System.out.print(

                    "Enter your choice: "

            );

            try {

                choice =

                        Integer.parseInt(

                                sc.nextLine()

                        );

                switch (choice) {

                    case 1:

                        vehicleManagement.searchById();

                        break;

                    case 2:

                        vehicleManagement.searchByName();

                        break;

                    case 3:

                        vehicleManagement.searchByType();

                        break;

                    case 4:

                        vehicleManagement.searchByModel();

                        break;

                    case 5:

                        vehicleManagement.searchByRent();

                        break;

                    case 6:

                        vehicleManagement

                                .searchAvailableVehicles();

                        break;

                    case 7:

                        System.out.println(

                                "Returning..."

                        );

                        break;

                    default:

                        System.out.println(

                                "Invalid Choice!"

                        );

                }

            } catch (NumberFormatException e) {

                System.out.println(

                        "Please enter a valid choice."

                );

                choice = 0;

            }

        } while (choice != 7);

    }

}
