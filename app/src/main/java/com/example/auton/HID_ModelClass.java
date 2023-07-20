package com.example.auton;

public class HID_ModelClass {
    String Image, Model, Dimension, Watttage, Weight, BulbType, Position, Feature, Brand, Price, Quantity;

    public HID_ModelClass() {
    }

    public HID_ModelClass(String image, String model, String dimension, String watttage, String weight, String bulbType, String position, String feature, String brand, String price, String quantity) {
        Image = image;
        Model = model;
        Dimension = dimension;
        Watttage = watttage;
        Weight = weight;
        BulbType = bulbType;
        Position = position;
        Feature = feature;
        Brand = brand;
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

    public String getDimension() {
        return Dimension;
    }

    public void setDimension(String dimension) {
        Dimension = dimension;
    }

    public String getWatttage() {
        return Watttage;
    }

    public void setWatttage(String watttage) {
        Watttage = watttage;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getBulbType() {
        return BulbType;
    }

    public void setBulbType(String bulbType) {
        BulbType = bulbType;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getFeature() {
        return Feature;
    }

    public void setFeature(String feature) {
        Feature = feature;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
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
