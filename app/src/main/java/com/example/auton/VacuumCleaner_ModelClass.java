package com.example.auton;

public class VacuumCleaner_ModelClass {
    private String Color, Dimension, Image, ItemsIncluded, Manufacturer, Model, OperatingVoltage, Price, Quantity, Weight;

    public VacuumCleaner_ModelClass() {
    }

    public VacuumCleaner_ModelClass(String color, String dimension, String image, String itemsIncluded, String manufacturer, String model, String operatingVoltage, String price, String quantity, String weight) {
        Color = color;
        Dimension = dimension;
        Image = image;
        ItemsIncluded = itemsIncluded;
        Manufacturer = manufacturer;
        Model = model;
        OperatingVoltage = operatingVoltage;
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

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getItemsIncluded() {
        return ItemsIncluded;
    }

    public void setItemsIncluded(String itemsIncluded) {
        ItemsIncluded = itemsIncluded;
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

    public String getOperatingVoltage() {
        return OperatingVoltage;
    }

    public void setOperatingVoltage(String operatingVoltage) {
        OperatingVoltage = operatingVoltage;
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
