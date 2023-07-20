package com.example.auton;

public class AndroidScreen_Model {
    private String Image;

    public AndroidScreen_Model() {
    }

    public AndroidScreen_Model(String image) {
        Image = image;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
