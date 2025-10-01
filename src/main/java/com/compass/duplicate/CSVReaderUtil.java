package com.compass.duplicate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReaderUtil {
    public static List<Contact> readContacts() {
        String filePath = "src/main/resources/duplicates.csv";
        List<Contact> contacts = new ArrayList<>();
        String line;
        String delimiter = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            boolean isHeader = true;
            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                String[] values = line.split(delimiter, -1); //-1 keeps empty values
                String id = values.length > 0 ? values[0] : "";
                String firstName = values.length > 1 ? values[1] : "";
                String lastName = values.length > 2 ? values[2] : "";
                String email = values.length > 3 ? values[3] : "";
                String zip = values.length > 4 ? values[4] : "";
                String address = values.length > 5 ? values[5] : "";

                contacts.add(new Contact(id, firstName, lastName, email, zip, address));
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }

        return contacts;
    }

}
