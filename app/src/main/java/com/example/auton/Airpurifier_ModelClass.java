package com.example.auton;

public class Airpurifier_ModelClass {
    private String Color, Dimensions, ItemsIncluded, Manufacturer, Model, OperatingVoltage, Price, Quantity, Warrenty, Weight, Image;

    public Airpurifier_ModelClass() {
    }

    public Airpurifier_ModelClass(String image, String color, String dimensions, String itemsIncluded, String manufacturer, String model, String operatingVoltage, String price, String quantity, String warrenty, String weight) {
        Color = color;
        Dimensions = dimensions;
        ItemsIncluded = itemsIncluded;
        Manufacturer = manufacturer;
        Model = model;
        OperatingVoltage = operatingVoltage;
        Price = price;
        Quantity = quantity;
        Warrenty = warrenty;
        Weight = weight;
        Image = image;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getDimensions() {
        return Dimensions;
    }

    public void setDimensions(String dimensions) {
        Dimensions = dimensions;
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

    public String getWarrenty() {
        return Warrenty;
    }

    public void setWarrenty(String warrenty) {
        Warrenty = warrenty;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }
}
