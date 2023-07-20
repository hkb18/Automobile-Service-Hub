package com.example.auton;

public class ScreensSpeakers_ModelClass {
    private String Dimension, DisplayType, Image, Manufacturer, Model, OSType, Price, Quantity, RAM, ROM, ScreenSize, Weight;


    public ScreensSpeakers_ModelClass() {
    }

    public ScreensSpeakers_ModelClass(String dimension, String displayType, String image, String manufacturer, String model, String OSType, String price, String quantity, String RAM, String ROM, String screenSize, String weight) {
        Dimension = dimension;
        DisplayType = displayType;
        Image = image;
        Manufacturer = manufacturer;
        Model = model;
        this.OSType = OSType;
        Price = price;
        Quantity = quantity;
        this.RAM = RAM;
        this.ROM = ROM;
        ScreenSize = screenSize;
        Weight = weight;
    }

    public String getDimension() {
        return Dimension;
    }

    public void setDimension(String dimension) {
        Dimension = dimension;
    }

    public String getDisplayType() {
        return DisplayType;
    }

    public void setDisplayType(String displayType) {
        DisplayType = displayType;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getManufacturer() {
        return Manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        Manufacturer = manufacturer;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getOSType() {
        return OSType;
    }

    public void setOSType(String OSType) {
        this.OSType = OSType;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getRAM() {
        return RAM;
    }

    public void setRAM(String RAM) {
        this.RAM = RAM;
    }

    public String getROM() {
        return ROM;
    }

    public void setROM(String ROM) {
        this.ROM = ROM;
    }

    public String getScreenSize() {
        return ScreenSize;
    }

    public void setScreenSize(String screenSize) {
        ScreenSize = screenSize;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }
}
