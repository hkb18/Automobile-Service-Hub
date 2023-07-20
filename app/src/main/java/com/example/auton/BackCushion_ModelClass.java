package com.example.auton;

public class BackCushion_ModelClass {
    String Image, Model, Color, Dimenension, Weight, Manufacturer, Price, Quantity;

    public BackCushion_ModelClass() {
    }

    public BackCushion_ModelClass(String image, String model, String color, String dimenension, String weight, String manufacturer, String price, String quantity) {
        Image = image;
        Model = model;
        Color = color;
        Dimenension = dimenension;
        Weight = weight;
        Manufacturer = manufacturer;
        Price = price;
        Quantity = quantity;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getDimenension() {
        return Dimenension;
    }

    public void setDimenension(String dimenension) {
        Dimenension = dimenension;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getManufacturer() {
        return Manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        Manufacturer = manufacturer;
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
}
