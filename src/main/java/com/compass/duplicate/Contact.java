package com.compass.duplicate;

public class Contact {
    private final String contactId;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String zip;
    private final String address;

    public Contact(String contactId, String firstName, String lastName, String email, String zip, String address) {
        this.contactId = contactId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.zip = zip;
        this.address = address;
    }

    public String getContactId() { return contactId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getZip() { return zip; }
    public String getAddress() { return address; }
}