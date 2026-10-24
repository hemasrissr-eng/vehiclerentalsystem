package VehicalManagement;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class DataManager {

    public static void saveInput(String input) {

        try {

            FileWriter writer =

                    new FileWriter(

                            "console_input.txt",

                            true

                    );

            writer.write(input);

            writer.write("\n");

            writer.close();

        } catch (IOException e) {

            System.out.println(

                    "Unable to save console input."

            );

        }

    }

    public static ArrayList<String> readFile(String fileName) {

        ArrayList<String> lines = new ArrayList<>();

        File file = new File(fileName);

        if (!file.exists()) {

            return lines;

        }

        try {

            BufferedReader reader =

                    new BufferedReader(

                            new FileReader(file)

                    );

            String line;

            while ((line = reader.readLine()) != null) {

                lines.add(line);

            }

            reader.close();

        } catch (IOException e) {

            System.out.println(

                    "Error while loading " + fileName

            );

        }

        return lines;

    }

    public static void writeFile(String fileName, String data) {

        try {

            FileWriter writer =

                    new FileWriter(fileName);

            writer.write(data);

            writer.close();

        } catch (IOException e) {

            System.out.println(

                    "Error while saving " + fileName

            );

        }

    }

}
