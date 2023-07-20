package com.example.auton;

public class Washers_ModelClass {
    String Color, Dimension, HoseLength, Image, Manufacturer, MaxPressure, Model, PowerOutput, Price, Quantity, Weight;

    public Washers_ModelClass() {
    }

    public Washers_ModelClass(String color, String dimension, String hoseLength, String image, String manufacturer, String maxPressure, String model, String powerOutput, String price, String quantity, String weight) {
        Color = color;
        Dimension = dimension;
        HoseLength = hoseLength;
        Image = image;
        Manufacturer = manufacturer;
        MaxPressure = maxPressure;
        Model = model;
        PowerOutput = powerOutput;
        Price = price;
        Quantity = quantity;
        Weight = weight;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getDimension() {
        return Dimension;
    }

    public void setDimension(String dimension) {
        Dimension = dimension;
    }

    public String getHoseLength() {
        return HoseLength;
    }

    public void setHoseLength(String hoseLength) {
        HoseLength = hoseLength;
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

    public String getMaxPressure() {
        return MaxPressure;
    }

    public void setMaxPressure(String maxPressure) {
        MaxPressure = maxPressure;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getPowerOutput() {
        return PowerOutput;
    }

    public void setPowerOutput(String powerOutput) {
        PowerOutput = powerOutput;
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

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }
}
