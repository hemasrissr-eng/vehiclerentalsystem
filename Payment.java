package VehicalManagement;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    private int paymentId;

    private int rentalId;

    private String customerName;

    private String vehicleName;

    private double rentalAmount;

    private double discount;

    private double additionalCharges;

    private double finalAmount;

    private String paymentMethod;

    private String status;

    public Payment(int paymentId,

                   int rentalId,

                   String customerName,

                   String vehicleName,

                   double rentalAmount,

                   double discount,

                   double additionalCharges,

                   double finalAmount,

                   String paymentMethod) {

        this.paymentId = paymentId;

        this.rentalId = rentalId;

        this.customerName = customerName;

        this.vehicleName = vehicleName;

        this.rentalAmount = rentalAmount;

        this.discount = discount;

        this.additionalCharges =

                additionalCharges;

        this.finalAmount = finalAmount;

        this.paymentMethod = paymentMethod;

        this.status = "SUCCESS";

    }

    public int getPaymentId() {

        return paymentId;

    }

    public int getRentalId() {

        return rentalId;

    }

    public String getCustomerName() {

        return customerName;

    }

    public String getVehicleName() {

        return vehicleName;

    }

    public double getRentalAmount() {

        return rentalAmount;

    }

    public double getDiscount() {

        return discount;

    }

    public double getAdditionalCharges() {

        return additionalCharges;

    }

    public double getFinalAmount() {

        return finalAmount;

    }

    public String getPaymentMethod() {

        return paymentMethod;

    }

    public String getStatus() {

        return status;

    }

    public void displayPayment() {

        System.out.println(

                "\n========== PAYMENT RECEIPT =========="

        );

        System.out.println(

                "Payment ID       : " + paymentId

        );

        System.out.println(

                "Rental ID        : " + rentalId

        );

        System.out.println(

                "Customer Name    : " + customerName

        );

        System.out.println(

                "Vehicle Name     : " + vehicleName

        );

        System.out.println(

                "Rental Amount    : Rs."

                + rentalAmount

        );

        System.out.println(

                "Discount         : Rs."

                + discount

        );

        System.out.println(

                "Additional Charge: Rs."

                + additionalCharges

        );

        System.out.println(

                "Final Amount     : Rs."

                + finalAmount

        );

        System.out.println(

                "Payment Method   : "

                + paymentMethod

        );

        System.out.println(

                "Payment Status   : " + status

        );

        System.out.println(

                "======================================"

        );

    }

}

class PaymentManagement {

    private ArrayList<Payment> payments;

    private Scanner sc;

    private int nextPaymentId = 5001;

    private final String FILE_NAME =

            "payment_details.txt";

    public PaymentManagement(Scanner sc) {

        this.sc = sc;

        loadPayments();

    }

    private void loadPayments() {

        payments = new ArrayList<>();

        // Payment records remain permanently stored

        // in payment_details.txt

    }

    private void savePayments() {

        StringBuilder data =

                new StringBuilder();

        for (Payment p : payments) {

            data.append(

                    "Payment ID: "

                    + p.getPaymentId()

                    + "\n"

            );

            data.append(

                    "Rental ID: "

                    + p.getRentalId()

                    + "\n"

            );

            data.append(

                    "Customer Name: "

                    + p.getCustomerName()

                    + "\n"

            );

            data.append(

                    "Vehicle Name: "

                    + p.getVehicleName()

                    + "\n"

            );

            data.append(

                    "Rental Amount: Rs."

                    + p.getRentalAmount()

                    + "\n"

            );

            data.append(

                    "Discount: Rs."

                    + p.getDiscount()

                    + "\n"

            );

            data.append(

                    "Additional Charge: Rs."

                    + p.getAdditionalCharges()

                    + "\n"

            );

            data.append(

                    "Final Amount: Rs."

                    + p.getFinalAmount()

                    + "\n"

            );

            data.append(

                    "Payment Method: "

                    + p.getPaymentMethod()

                    + "\n"

            );

            data.append(

                    "Payment Status: "

                    + p.getStatus()

                    + "\n"

            );

            data.append(

                    "======================================\n"

            );

        }

        DataManager.writeFile(

                FILE_NAME,

                data.toString()

        );

    }

    public void processPayment(

            Rental rental,

            Customer customer) {

        if (rental == null) {

            System.out.println(

                    "No rental selected."

            );

            return;

        }

        System.out.println(

                "\n========== PAYMENT =========="

        );

        try {

            double rentalAmount =

                    rental.getRentalAmount();

            System.out.print(

                    "Enter Discount (%): "

            );

            double discountPercent =

                    Double.parseDouble(

                            sc.nextLine()

                    );

            double discountAmount =

                    rentalAmount

                    * discountPercent

                    / 100;

            System.out.print(

                    "Enter Additional Charges: "

            );

            double additionalCharges =

                    Double.parseDouble(

                            sc.nextLine()

                    );

            double finalAmount =

                    rentalAmount

                    - discountAmount

                    + additionalCharges;

            System.out.println(

                    "\nAmount To Pay: Rs."

                    + finalAmount

            );

            System.out.println(

                    "\nSelect Payment Method:"

            );

            System.out.println("1. Cash");

            System.out.println("2. Card");

            System.out.println("3. UPI");

            System.out.print(

                    "Enter Choice: "

            );

            int choice =

                    Integer.parseInt(

                            sc.nextLine()

                    );

            String paymentMethod;

            if (choice == 1) {

                paymentMethod = "Cash";

            } else if (choice == 2) {

                paymentMethod = "Card";

            } else if (choice == 3) {

                paymentMethod = "UPI";

            } else {

                System.out.println(

                        "Invalid Payment Method."

                );

                return;

            }

            Payment payment =

                    new Payment(

                            nextPaymentId++,

                            rental.getRentalId(),

                            customer.getName(),

                            rental.getVehicleName(),

                            rentalAmount,

                            discountAmount,

                            additionalCharges,

                            finalAmount,

                            paymentMethod

                    );

            payments.add(payment);

            savePayments();

            DataManager.saveInput(

                    "Payment Amount: "

                    + finalAmount

            );

            DataManager.saveInput(

                    "Payment Method: "

                    + paymentMethod

            );

            System.out.println(

                    "\nPayment Successful!"

            );

            payment.displayPayment();

        } catch (NumberFormatException e) {

            System.out.println(

                    "Please enter valid values."

            );

        }

    }

    public void viewAllPayments() {

        System.out.println(

                "\n========== ALL PAYMENTS =========="

        );

        if (payments.isEmpty()) {

            System.out.println(

                    "No payments available."

            );

            return;

        }

        for (Payment p : payments) {

            p.displayPayment();

        }

    }

}
