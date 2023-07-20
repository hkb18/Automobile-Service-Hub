package com.example.auton;

public class Feedback_ModelClass {
    private String Username, Feedback, Key;

    public Feedback_ModelClass() {
    }

    public Feedback_ModelClass(String username, String feedback, String key) {
        Username = username;
        Feedback = feedback;
        Key = key;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getFeedback() {
        return Feedback;
    }

    public void setFeedback(String feedback) {
        Feedback = feedback;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }
}