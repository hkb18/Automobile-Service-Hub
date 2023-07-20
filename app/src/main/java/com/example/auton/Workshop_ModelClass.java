package com.example.auton;

public class Workshop_ModelClass {
    private String Name, Username, Email_Id, ContactNo;

    public Workshop_ModelClass() {
    }

    public Workshop_ModelClass(String name, String username, String email_Id, String contactNo) {
        Name = name;
        Username = username;
        Email_Id = email_Id;
        ContactNo = contactNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail_Id() {
        return Email_Id;
    }

    public void setEmail_Id(String email_Id) {
        Email_Id = email_Id;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }
}
