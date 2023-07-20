package com.example.auton;

public class Batteries_ModelClass {
    private String Brand, Image, Key, Price, Voltage, YrsofWarrenty;

    public Batteries_ModelClass() {
    }

    public Batteries_ModelClass(String brand, String image, String key, String price, String voltage, String yrsofWarrenty) {
        Brand = brand;
        Image = image;
        Key = key;
        Price = price;
        Voltage = voltage;
        YrsofWarrenty = yrsofWarrenty;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getVoltage() {
        return Voltage;
    }

    public void setVoltage(String voltage) {
        Voltage = voltage;
    }

    public String getYrsofWarrenty() {
        return YrsofWarrenty;
    }

    public void setYrsofWarrenty(String yrsofWarrenty) {
        YrsofWarrenty = yrsofWarrenty;
    }
}
