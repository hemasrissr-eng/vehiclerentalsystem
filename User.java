package VehicalManagement;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class User implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String username;

    protected String password;

    public User() {

    }

    public void registerUser(String username, String password) {

        this.username = username;

        this.password = password;

        System.out.println("User registered successfully.");

    }

    public boolean loginUser(String username, String password) {

        if (this.username != null &&

            this.password != null &&

            this.username.equals(username) &&

            this.password.equals(password)) {

            System.out.println("Login Successful!");

            return true;

        }

        System.out.println("Invalid Username or Password!");

        return false;

    }

    public void updateUser(String newUsername, String newPassword) {

        this.username = newUsername;

        this.password = newPassword;

        System.out.println("User details updated.");

    }

    public void viewUser() {

        System.out.println("Username: " + username);

    }

    public boolean validateUser() {

        return username != null && password != null;

    }

    public void logoutUser() {

        System.out.println("User logged out successfully.");

    }

}

class Customer extends User {

    private static final long serialVersionUID = 1L;

    private int customerId;

    private String name;

    private String phone;

    public Customer() {

        super();

    }

    public Customer(int customerId, String name, String phone,

                    String username, String password) {

        super();

        this.customerId = customerId;

        this.name = name;

        this.phone = phone;

        registerUser(username, password);

    }

    public int getCustomerId() {

        return customerId;

    }

    public String getName() {

        return name;

    }

    public String getPhone() {

        return phone;

    }

    public String getUsername() {

        return username;

    }

    public String getPassword() {

        return password;

    }

    public void registerCustomer(String name, String phone,

                                  String username, String password) {

        this.name = name;

        this.phone = phone;

        registerUser(username, password);

    }

    public boolean loginCustomer(String username, String password) {

        return loginUser(username, password);

    }

    public void updateCustomerDetails(String name, String phone) {

        if (!name.isEmpty()) {

            this.name = name;

        }

        if (!phone.isEmpty()) {

            this.phone = phone;

        }

        System.out.println(

                "Customer details updated successfully."

        );

    }

    public void viewCustomerDetails() {

        System.out.println(

                "\n========== CUSTOMER DETAILS =========="

        );

        System.out.println("Customer ID : " + customerId);

        System.out.println("Name        : " + name);

        System.out.println("Phone       : " + phone);

        viewUser();

        System.out.println(

                "======================================"

        );

    }

}

class Admin extends User {

    private static final long serialVersionUID = 1L;

    public Admin() {

        super();

        username = "admin";

        password = "admin123";

    }

    public boolean adminLogin(String username, String password) {

        return loginUser(username, password);

    }

    public void manageUsers() {

        System.out.println("Admin is managing users.");

    }

}
