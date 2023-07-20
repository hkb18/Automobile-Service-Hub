package com.example.auton;

public class Mechanic {
    private String ContactNo, Email, Name, Workshop;

    public Mechanic(String contactNo, String email, String name, String workshop) {
        ContactNo = contactNo;
        Email = email;
        Name = name;
        Workshop = workshop;
    }

    public Mechanic() {
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getWorkshop() {
        return Workshop;
    }

    public void setWorkshop(String workshop) {
        Workshop = workshop;
    }
}
