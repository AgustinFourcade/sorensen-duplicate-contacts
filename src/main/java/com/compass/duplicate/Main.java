package com.compass.duplicate;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Contact> contacts = CSVReaderUtil.readContacts();
        if (contacts.isEmpty()) {
            System.out.println("No contacts could be read or CSV is empty.");
            return;
        }
        List<String> duplicates = DuplicateFinder.findDuplicates(contacts);
        System.out.println("ContactID Source \t ContactID Match \t Accuracy");
        for (String r : duplicates) {
            System.out.println(r);
        }
    }
}
