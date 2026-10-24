package VehicalManagement;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class VehicleReturn implements Serializable {

    private static final long serialVersionUID = 1L;

    private int returnId;

    private int rentalId;

    private int vehicleId;

    private String vehicleName;

    private int lateDays;

    private double lateCharges;

    private double finalRent;

    private boolean vehicleAvailable;

    public VehicleReturn(int returnId,

                         int rentalId,

                         int vehicleId,

                         String vehicleName,

                         int lateDays,

                         double lateCharges,

                         double finalRent) {

        this.returnId = returnId;

        this.rentalId = rentalId;

        this.vehicleId = vehicleId;

        this.vehicleName = vehicleName;

        this.lateDays = lateDays;

        this.lateCharges = lateCharges;

        this.finalRent = finalRent;

        this.vehicleAvailable = true;

    }

    public int getReturnId() {

        return returnId;

    }

    public int getRentalId() {

        return rentalId;

    }

    public int getVehicleId() {

        return vehicleId;

    }

    public String getVehicleName() {

        return vehicleName;

    }

    public int getLateDays() {

        return lateDays;

    }

    public double getLateCharges() {

        return lateCharges;

    }

    public double getFinalRent() {

        return finalRent;

    }

    public boolean isVehicleAvailable() {

        return vehicleAvailable;

    }

    public void displayReturn() {

        System.out.println(

                "\n========== RETURN DETAILS =========="

        );

        System.out.println(

                "Return ID       : " + returnId

        );

        System.out.println(

                "Rental ID       : " + rentalId

        );

        System.out.println(

                "Vehicle ID      : " + vehicleId

        );

        System.out.println(

                "Vehicle Name    : " + vehicleName

        );

        System.out.println(

                "Late Days       : " + lateDays

        );

        System.out.println(

                "Late Charges    : Rs."

                + lateCharges

        );

        System.out.println(

                "Final Rent      : Rs."

                + finalRent

        );

        System.out.println(

                "Vehicle Available: "

                + vehicleAvailable

        );

        System.out.println(

                "===================================="

        );

    }

}

class ReturnManagement {

    private ArrayList<VehicleReturn> returns;

    private VehicleManagement vehicleManagement;

    private RentalManagement rentalManagement;

    private Scanner sc;

    private int nextReturnId = 7001;

    private final String FILE_NAME =

            "return_details.txt";

    public ReturnManagement(

            Scanner sc,

            VehicleManagement vehicleManagement,

            RentalManagement rentalManagement) {

        this.sc = sc;

        this.vehicleManagement =

                vehicleManagement;

        this.rentalManagement =

                rentalManagement;

        loadReturns();

    }

    private void loadReturns() {

        returns = new ArrayList<>();

        ArrayList<String> lines =

                DataManager.readFile(FILE_NAME);

        // Existing return records remain stored

        // permanently in return_details.txt

    }

    private void saveReturns() {

        StringBuilder data =

                new StringBuilder();

        for (VehicleReturn vr : returns) {

            data.append(

                    "Return ID: "

                    + vr.getReturnId()

                    + "\n"

            );

            data.append(

                    "Rental ID: "

                    + vr.getRentalId()

                    + "\n"

            );

            data.append(

                    "Vehicle ID: "

                    + vr.getVehicleId()

                    + "\n"

            );

            data.append(

                    "Vehicle Name: "

                    + vr.getVehicleName()

                    + "\n"

            );

            data.append(

                    "Late Days: "

                    + vr.getLateDays()

                    + "\n"

            );

            data.append(

                    "Late Charges: Rs."

                    + vr.getLateCharges()

                    + "\n"

            );

            data.append(

                    "Final Rent: Rs."

                    + vr.getFinalRent()

                    + "\n"

            );

            data.append(

                    "Vehicle Available: "

                    + vr.isVehicleAvailable()

                    + "\n"

            );

            data.append(

                    "====================================\n"

            );

        }

        DataManager.writeFile(

                FILE_NAME,

                data.toString()

        );

    }

    public void returnVehicle() {

        System.out.println(

                "\n========== VEHICLE RETURN =========="

        );

        try {

            System.out.print(

                    "Enter Rental ID: "

            );

            int rentalId =

                    Integer.parseInt(

                            sc.nextLine()

                    );

            Rental rental =

                    rentalManagement.findRental(

                            rentalId

                    );

            if (rental == null) {

                System.out.println(

                        "Rental Not Found."

                );

                return;

            }

            if (rental.getStatus()

                    .equalsIgnoreCase("RETURNED")) {

                System.out.println(

                        "Vehicle already returned."

                );

                return;

            }

            System.out.print(

                    "Enter Late Days: "

            );

            int lateDays =

                    Integer.parseInt(

                            sc.nextLine()

                    );

            if (lateDays < 0) {

                System.out.println(

                        "Late days cannot be negative."

                );

                return;

            }

            double chargePerDay = 500;

            double lateCharges =

                    lateDays * chargePerDay;

            double finalRent =

                    rental.getRentalAmount()

                    + lateCharges;

            System.out.println(

                    "\n========== RETURN SUMMARY =========="

            );

            System.out.println(

                    "Rental ID    : " + rentalId

            );

            System.out.println(

                    "Vehicle      : "

                    + rental.getVehicleName()

            );

            System.out.println(

                    "Late Days    : " + lateDays

            );

            System.out.println(

                    "Late Charges : Rs."

                    + lateCharges

            );

            System.out.println(

                    "Final Rent   : Rs."

                    + finalRent

            );

            System.out.print(

                    "\nConfirm Return? (Y/N): "

            );

            String choice = sc.nextLine();

            if (!choice.equalsIgnoreCase("Y")) {

                System.out.println(

                        "Return Cancelled."

                );

                return;

            }

            Vehicle vehicle =

                    vehicleManagement.getVehicle(

                            rental.getVehicleId()

                    );

            if (vehicle != null) {

                vehicle.setAvailable(true);

                vehicleManagement.saveCurrentData();

            }

            rental.setStatus("RETURNED");

            rentalManagement.saveCurrentData();

            VehicleReturn vehicleReturn =

                    new VehicleReturn(

                            nextReturnId++,

                            rentalId,

                            rental.getVehicleId(),

                            rental.getVehicleName(),

                            lateDays,

                            lateCharges,

                            finalRent

                    );

            returns.add(vehicleReturn);

            saveReturns();

            DataManager.saveInput(

                    "Return Rental ID: "

                    + rentalId

            );

            DataManager.saveInput(

                    "Late Days: "

                    + lateDays

            );

            System.out.println(

                    "\nVehicle Returned Successfully!"

            );

            vehicleReturn.displayReturn();

        } catch (NumberFormatException e) {

            System.out.println(

                    "Please enter valid values."

            );

        }

    }

    public void viewAllReturns() {

        System.out.println(

                "\n========== ALL RETURNS =========="

        );

        if (returns.isEmpty()) {

            System.out.println(

                    "No return records available."

            );

            return;

        }

        for (VehicleReturn vr : returns) {

            vr.displayReturn();

        }

    }

}
