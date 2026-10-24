package VehicalManagement;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Rental implements Serializable {

    private static final long serialVersionUID = 1L;

    private int rentalId;

    private int customerId;

    private String customerName;

    private int vehicleId;

    private String vehicleName;

    private int rentalDays;

    private double dailyRent;

    private double rentalAmount;

    private String status;

    public Rental(int rentalId,

                  int customerId,

                  String customerName,

                  int vehicleId,

                  String vehicleName,

                  int rentalDays,

                  double dailyRent) {

        this.rentalId = rentalId;

        this.customerId = customerId;

        this.customerName = customerName;

        this.vehicleId = vehicleId;

        this.vehicleName = vehicleName;

        this.rentalDays = rentalDays;

        this.dailyRent = dailyRent;

        rentalAmount =

                dailyRent * rentalDays;

        status = "ACTIVE";

    }

    public int getRentalId() {

        return rentalId;

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

    public int getRentalDays() {

        return rentalDays;

    }

    public double getDailyRent() {

        return dailyRent;

    }

    public double getRentalAmount() {

        return rentalAmount;

    }

    public String getStatus() {

        return status;

    }

    public void setStatus(String status) {

        this.status = status;

    }

    public void displayRentalDetails() {

        System.out.println(

                "\n========== RENTAL DETAILS =========="

        );

        System.out.println(

                "Rental ID       : " + rentalId

        );

        System.out.println(

                "Customer ID     : " + customerId

        );

        System.out.println(

                "Customer Name   : " + customerName

        );

        System.out.println(

                "Vehicle ID      : " + vehicleId

        );

        System.out.println(

                "Vehicle Name    : " + vehicleName

        );

        System.out.println(

                "Rental Duration : "

                + rentalDays

                + " days"

        );

        System.out.println(

                "Rent / Day      : Rs."

                + dailyRent

        );

        System.out.println(

                "Total Amount    : Rs."

                + rentalAmount

        );

        System.out.println(

                "Status          : " + status

        );

        System.out.println(

                "====================================="

        );

    }

}

class RentalManagement {

    private ArrayList<Rental> rentals;

    private VehicleManagement vehicleManagement;

    private Scanner sc;

    private int nextRentalId = 1001;

    private final String FILE_NAME =

            "rental_details.txt";

    public RentalManagement(

            Scanner sc,

            VehicleManagement vehicleManagement) {

        this.sc = sc;

        this.vehicleManagement =

                vehicleManagement;

        loadRentals();

    }

    private void loadRentals() {

        ArrayList<String> lines =

                DataManager.readFile(FILE_NAME);

        rentals = new ArrayList<>();

        int rentalId = 0;

        int customerId = 0;

        String customerName = "";

        int vehicleId = 0;

        String vehicleName = "";

        int rentalDays = 0;

        double dailyRent = 0;

        String status = "ACTIVE";

        for (String line : lines) {

            try {

                if (line.startsWith("Rental ID:")) {

                    rentalId =

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

                } else if (line.startsWith("Rental Duration:")) {

                    String value =

                            line.substring(

                                    line.indexOf(":") + 1

                            ).trim();

                    value =

                            value.replace(

                                    "days",

                                    ""

                            ).trim();

                    rentalDays =

                            Integer.parseInt(value);

                } else if (line.startsWith("Rent / Day:")) {

                    String value =

                            line.substring(

                                    line.indexOf(":") + 1

                            ).trim();

                    value =

                            value.replace(

                                    "Rs.",

                                    ""

                            ).trim();

                    dailyRent =

                            Double.parseDouble(value);

                } else if (line.startsWith("Status:")) {

                    status =

                            line.substring(

                                    line.indexOf(":") + 1

                            ).trim();

                    Rental rental =

                            new Rental(

                                    rentalId,

                                    customerId,

                                    customerName,

                                    vehicleId,

                                    vehicleName,

                                    rentalDays,

                                    dailyRent

                            );

                    rental.setStatus(status);

                    rentals.add(rental);

                    if (rentalId >= nextRentalId) {

                        nextRentalId =

                                rentalId + 1;

                    }

                }

            } catch (Exception e) {

                // Ignore malformed records

            }

        }

    }

    private void saveRentals() {

        StringBuilder data =

                new StringBuilder();

        for (Rental r : rentals) {

            data.append(

                    "Rental ID: "

                    + r.getRentalId()

                    + "\n"

            );

            data.append(

                    "Customer ID: "

                    + r.getCustomerId()

                    + "\n"

            );

            data.append(

                    "Customer Name: "

                    + r.getCustomerName()

                    + "\n"

            );

            data.append(

                    "Vehicle ID: "

                    + r.getVehicleId()

                    + "\n"

            );

            data.append(

                    "Vehicle Name: "

                    + r.getVehicleName()

                    + "\n"

            );

            data.append(

                    "Rental Duration: "

                    + r.getRentalDays()

                    + " days\n"

            );

            data.append(

                    "Rent / Day: Rs."

                    + r.getDailyRent()

                    + "\n"

            );

            data.append(

                    "Total Amount: Rs."

                    + r.getRentalAmount()

                    + "\n"

            );

            data.append(

                    "Status: "

                    + r.getStatus()

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

    public Rental createRental(Customer customer) {

        if (customer == null) {

            System.out.println(

                    "Please login first."

            );

            return null;

        }

        System.out.println(

                "\n========== CREATE NEW RENTAL =========="

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

                    "Rental Vehicle ID: "

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

                return null;

            }

            if (!vehicle.isAvailable()) {

                System.out.println(

                        "Vehicle is NOT available."

                );

                return null;

            }

            System.out.print(

                    "Enter Rental Duration (days): "

            );

            int days =

                    Integer.parseInt(

                            sc.nextLine()

                    );

            DataManager.saveInput(

                    "Rental Days: " + days

            );

            if (days <= 0) {

                System.out.println(

                        "Rental days must be greater than zero."

                );

                return null;

            }

            double amount =

                    vehicle.getRent() * days;

            System.out.println(

                    "\n========== RENTAL SUMMARY =========="

            );

            System.out.println(

                    "Customer Name : "

                    + customer.getName()

            );

            System.out.println(

                    "Vehicle       : "

                    + vehicle.getName()

            );

            System.out.println(

                    "Vehicle ID    : "

                    + vehicle.getId()

            );

            System.out.println(

                    "Rent / Day    : Rs."

                    + vehicle.getRent()

            );

            System.out.println(

                    "Days          : " + days

            );

            System.out.println(

                    "Total Amount  : Rs."

                    + amount

            );

            System.out.print(

                    "\nConfirm Rental? (Y/N): "

            );

            String choice = sc.nextLine();

            DataManager.saveInput(

                    "Rental Confirmation: "

                    + choice

            );

            if (choice.equalsIgnoreCase("Y")) {

                Rental rental =

                        new Rental(

                                nextRentalId++,

                                customer.getCustomerId(),

                                customer.getName(),

                                vehicle.getId(),

                                vehicle.getName(),

                                days,

                                vehicle.getRent()

                        );

                rentals.add(rental);

                vehicle.setAvailable(false);

                vehicleManagement.saveCurrentData();

                saveRentals();

                System.out.println(

                        "\nRental Confirmed Successfully!"

                );

                System.out.println(

                        "Rental ID: "

                        + rental.getRentalId()

                );

                return rental;

            } else {

                System.out.println(

                        "\nRental Cancelled."

                );

            }

        } catch (NumberFormatException e) {

            System.out.println(

                    "Please enter valid numeric values."

            );

        }

        return null;

    }

    public Rental findRental(int rentalId) {

        for (Rental r : rentals) {

            if (r.getRentalId() == rentalId) {

                return r;

            }

        }

        return null;

    }

    public void viewMyRentals(Customer customer) {

        System.out.println(

                "\n========== MY RENTALS =========="

        );

        boolean found = false;

        for (Rental r : rentals) {

            if (r.getCustomerId()

                    == customer.getCustomerId()) {

                r.displayRentalDetails();

                found = true;

            }

        }

        if (!found) {

            System.out.println(

                    "No rentals found."

            );

        }

    }

    public void viewAllRentals() {

        System.out.println(

                "\n========== ALL RENTALS =========="

        );

        if (rentals.isEmpty()) {

            System.out.println(

                    "No rentals available."

            );

            return;

        }

        for (Rental r : rentals) {

            r.displayRentalDetails();

        }

    }

    public void saveCurrentData() {

        saveRentals();

    }

}
