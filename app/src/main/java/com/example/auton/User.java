package com.example.auton;

public class User {
    private String Name, Username, EmailId, ContactNo;

    public User(String name, String username, String emailId, String contactNo) {
        Name = name;
        Username = username;
        EmailId = emailId;
        ContactNo = contactNo;
    }

    public User() {
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

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }
}
