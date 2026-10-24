package VehicalManagement;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private String name;

    private String type;

    private String model;

    private double rent;

    private boolean available;

    public Vehicle(int id, String name, String type,

                   String model, double rent) {

        this.id = id;

        this.name = name;

        this.type = type;

        this.model = model;

        this.rent = rent;

        this.available = true;

    }

    public int getId() {

        return id;

    }

    public String getName() {

        return name;

    }

    public String getType() {

        return type;

    }

    public String getModel() {

        return model;

    }

    public double getRent() {

        return rent;

    }

    public boolean isAvailable() {

        return available;

    }

    public void setName(String name) {

        this.name = name;

    }

    public void setType(String type) {

        this.type = type;

    }

    public void setModel(String model) {

        this.model = model;

    }

    public void setRent(double rent) {

        this.rent = rent;

    }

    public void setAvailable(boolean available) {

        this.available = available;

    }

    public void display() {

        System.out.println(

                "----------------------------------------"

        );

        System.out.println("Vehicle ID   : " + id);

        System.out.println("Vehicle Name : " + name);

        System.out.println("Vehicle Type : " + type);

        System.out.println("Model        : " + model);

        System.out.println("Rent / Day   : Rs." + rent);

        System.out.println(

                "Available    : " +

                (available ? "Yes" : "No")

        );

        System.out.println(

                "----------------------------------------"

        );

    }

}

class VehicleManagement {

    private ArrayList<Vehicle> vehicles;

    private Scanner sc;

    private final String FILE_NAME =

            "vehicle_list.txt";

    public VehicleManagement(Scanner sc) {

        this.sc = sc;

        loadVehicles();

    }

    private void loadVehicles() {

        ArrayList<String> lines =

                DataManager.readFile(FILE_NAME);

        vehicles = new ArrayList<>();

        if (lines.isEmpty()) {

            addDefaultVehicles();

            saveVehicles();

            return;

        }

        int id = 0;

        String name = "";

        String type = "";

        String model = "";

        double rent = 0;

        boolean available = true;

        boolean validRecord = false;

        for (String line : lines) {

            try {

                if (line.startsWith("Vehicle ID:")) {

                    id = Integer.parseInt(

                            line.substring(

                                    line.indexOf(":") + 1

                            ).trim()

                    );

                } else if (line.startsWith("Vehicle Name:")) {

                    name =

                            line.substring(

                                    line.indexOf(":") + 1

                            ).trim();

                } else if (line.startsWith("Vehicle Type:")) {

                    type =

                            line.substring(

                                    line.indexOf(":") + 1

                            ).trim();

                } else if (line.startsWith("Model:")) {

                    model =

                            line.substring(

                                    line.indexOf(":") + 1

                            ).trim();

                } else if (line.startsWith("Rent / Day:")) {

                    String value =

                            line.substring(

                                    line.indexOf(":") + 1

                            ).trim();

                    value = value.replace("Rs.", "").trim();

                    rent = Double.parseDouble(value);

                } else if (line.startsWith("Available:")) {

                    available =

                            line.substring(

                                    line.indexOf(":") + 1

                            ).trim()

                            .equalsIgnoreCase("Yes");

                    Vehicle vehicle =

                            new Vehicle(

                                    id,

                                    name,

                                    type,

                                    model,

                                    rent

                            );

                    vehicle.setAvailable(available);

                    vehicles.add(vehicle);

                    validRecord = true;

                }

            } catch (Exception e) {

                // Ignore malformed lines

            }

        }

        if (!validRecord) {

            vehicles.clear();

            addDefaultVehicles();

            saveVehicles();

        }

    }

    private void saveVehicles() {

        StringBuilder data =

                new StringBuilder();

        for (Vehicle v : vehicles) {

            data.append(

                    "Vehicle ID: "

                    + v.getId()

                    + "\n"

            );

            data.append(

                    "Vehicle Name: "

                    + v.getName()

                    + "\n"

            );

            data.append(

                    "Vehicle Type: "

                    + v.getType()

                    + "\n"

            );

            data.append(

                    "Model: "

                    + v.getModel()

                    + "\n"

            );

            data.append(

                    "Rent / Day: Rs."

                    + v.getRent()

                    + "\n"

            );

            data.append(

                    "Available: "

                    + (v.isAvailable() ? "Yes" : "No")

                    + "\n"

            );

            data.append(

                    "----------------------------------------\n"

            );

        }

        DataManager.writeFile(

                FILE_NAME,

                data.toString()

        );

    }

    private void addDefaultVehicles() {

        vehicles.add(

                new Vehicle(

                        1,

                        "Swift",

                        "Car",

                        "2022",

                        1500

                )

        );

        vehicles.add(

                new Vehicle(

                        2,

                        "Honda City",

                        "Car",

                        "2023",

                        2000

                )

        );

        vehicles.add(

                new Vehicle(

                        3,

                        "Activa",

                        "Bike",

                        "2021",

                        700

                )

        );

        vehicles.add(

                new Vehicle(

                        4,

                        "Royal Enfield",

                        "Bike",

                        "2023",

                        1200

                )

        );

        vehicles.add(

                new Vehicle(

                        5,

                        "Creta",

                        "SUV",

                        "2024",

                        2500

                )

        );

    }

    public void addVehicle() {

        System.out.println(

                "\n========== ADD VEHICLE =========="

        );

        try {

            System.out.print("Enter Vehicle ID: ");

            int id =

                    Integer.parseInt(

                            sc.nextLine()

                    );

            DataManager.saveInput(

                    "Vehicle ID: " + id

            );

            for (Vehicle v : vehicles) {

                if (v.getId() == id) {

                    System.out.println(

                            "Vehicle ID already exists."

                    );

                    return;

                }

            }

            System.out.print(

                    "Enter Vehicle Name: "

            );

            String name = sc.nextLine();

            DataManager.saveInput(

                    "Vehicle Name: " + name

            );

            System.out.println("Vehicle Types:");

            System.out.println("1. Car");

            System.out.println("2. Bike");

            System.out.println("3. SUV");

            System.out.println("4. Van");

            System.out.println("5. Bus");

            System.out.print(

                    "Enter Vehicle Type: "

            );

            String type = sc.nextLine();

            DataManager.saveInput(

                    "Vehicle Type: " + type

            );

            System.out.print(

                    "Enter Vehicle Model: "

            );

            String model = sc.nextLine();

            DataManager.saveInput(

                    "Vehicle Model: " + model

            );

            System.out.print(

                    "Enter Rent Per Day: "

            );

            double rent =

                    Double.parseDouble(

                            sc.nextLine()

                    );

            DataManager.saveInput(

                    "Vehicle Rent: " + rent

            );

            if (rent <= 0) {

                System.out.println(

                        "Rent must be greater than zero."

                );

                return;

            }

            Vehicle vehicle =

                    new Vehicle(

                            id,

                            name,

                            type,

                            model,

                            rent

                    );

            vehicles.add(vehicle);

            saveVehicles();

            System.out.println(

                    "Vehicle Added Successfully."

            );

        } catch (NumberFormatException e) {

            System.out.println(

                    "Please enter valid values."

            );

        }

    }

    public void viewAllVehicles() {

        System.out.println(

                "\n========== ALL VEHICLES =========="

        );

        if (vehicles.isEmpty()) {

            System.out.println(

                    "No Vehicles Available."

            );

            return;

        }

        for (Vehicle v : vehicles) {

            v.display();

        }

    }

    public void searchById() {

        try {

            System.out.print(

                    "Enter Vehicle ID: "

            );

            int id =

                    Integer.parseInt(

                            sc.nextLine()

                    );

            boolean found = false;

            for (Vehicle v : vehicles) {

                if (v.getId() == id) {

                    v.display();

                    found = true;

                    break;

                }

            }

            if (!found) {

                System.out.println(

                        "Vehicle Not Found."

                );

            }

        } catch (NumberFormatException e) {

            System.out.println(

                    "Invalid Vehicle ID."

            );

        }

    }

    public void searchByName() {

        System.out.print(

                "Enter Vehicle Name: "

        );

        String name = sc.nextLine();

        boolean found = false;

        for (Vehicle v : vehicles) {

            if (v.getName()

                    .equalsIgnoreCase(name)) {

                v.display();

                found = true;

            }

        }

        if (!found) {

            System.out.println(

                    "Vehicle Not Found."

            );

        }

    }

    public void searchByType() {

        System.out.print(

                "Enter Vehicle Type: "

        );

        String type = sc.nextLine();

        boolean found = false;

        for (Vehicle v : vehicles) {

            if (v.getType()

                    .equalsIgnoreCase(type)) {

                v.display();

                found = true;

            }

        }

        if (!found) {

            System.out.println(

                    "Vehicle Not Found."

            );

        }

    }

    public void searchByModel() {

        System.out.print(

                "Enter Vehicle Model: "

        );

        String model = sc.nextLine();

        boolean found = false;

        for (Vehicle v : vehicles) {

            if (v.getModel()

                    .equalsIgnoreCase(model)) {

                v.display();

                found = true;

            }

        }

        if (!found) {

            System.out.println(

                    "Vehicle Not Found."

            );

        }

    }

    public void searchByRent() {

        try {

            System.out.print(

                    "Enter Maximum Rent Amount: "

            );

            double amount =

                    Double.parseDouble(

                            sc.nextLine()

                    );

            boolean found = false;

            for (Vehicle v : vehicles) {

                if (v.getRent() <= amount) {

                    v.display();

                    found = true;

                }

            }

            if (!found) {

                System.out.println(

                        "No vehicles found."

                );

            }

        } catch (NumberFormatException e) {

            System.out.println(

                    "Invalid rent amount."

            );

        }

    }

    public void searchAvailableVehicles() {

        boolean found = false;

        System.out.println(

                "\n========== AVAILABLE VEHICLES =========="

        );

        for (Vehicle v : vehicles) {

            if (v.isAvailable()) {

                v.display();

                found = true;

            }

        }

        if (!found) {

            System.out.println(

                    "No vehicles available."

            );

        }

    }

    public void updateVehicle() {

        try {

            System.out.print(

                    "Enter Vehicle ID to Update: "

            );

            int id =

                    Integer.parseInt(

                            sc.nextLine()

                    );

            for (Vehicle v : vehicles) {

                if (v.getId() == id) {

                    System.out.print(

                            "Enter New Name: "

                    );

                    String name = sc.nextLine();

                    System.out.print(

                            "Enter New Type: "

                    );

                    String type = sc.nextLine();

                    System.out.print(

                            "Enter New Model: "

                    );

                    String model = sc.nextLine();

                    System.out.print(

                            "Enter New Rent: "

                    );

                    double rent =

                            Double.parseDouble(

                                    sc.nextLine()

                            );

                    v.setName(name);

                    v.setType(type);

                    v.setModel(model);

                    v.setRent(rent);

                    saveVehicles();

                    System.out.println(

                            "Vehicle Updated Successfully."

                    );

                    return;

                }

            }

            System.out.println(

                    "Vehicle Not Found."

            );

        } catch (NumberFormatException e) {

            System.out.println(

                    "Invalid Input."

            );

        }

    }

    public void removeVehicle() {

        try {

            System.out.print(

                    "Enter Vehicle ID to Remove: "

            );

            int id =

                    Integer.parseInt(

                            sc.nextLine()

                    );

            for (int i = 0;

                 i < vehicles.size();

                 i++) {

                if (vehicles.get(i).getId() == id) {

                    vehicles.remove(i);

                    saveVehicles();

                    System.out.println(

                            "Vehicle Removed Successfully."

                    );

                    return;

                }

            }

            System.out.println(

                    "Vehicle Not Found."

            );

        } catch (NumberFormatException e) {

            System.out.println(

                    "Invalid Vehicle ID."

            );

        }

    }

    public Vehicle getVehicle(int id) {

        for (Vehicle v : vehicles) {

            if (v.getId() == id) {

                return v;

            }

        }

        return null;

    }

    public void saveCurrentData() {

        saveVehicles();

    }

}
