package com.example.auton;

public class Mats_ModelClass {
    String Image, Model, Material, Color, Pattern, Feature, Manufacturer, Price, Quantity;

    public Mats_ModelClass() {
    }

    public Mats_ModelClass(String image, String model, String material, String color, String pattern, String feature, String manufacturer, String price, String quantity) {
        Image = image;
        Model = model;
        Material = material;
        Color = color;
        Pattern = pattern;
        Feature = feature;
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

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String material) {
        Material = material;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getPattern() {
        return Pattern;
    }

    public void setPattern(String pattern) {
        Pattern = pattern;
    }

    public String getFeature() {
        return Feature;
    }

    public void setFeature(String feature) {
        Feature = feature;
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
