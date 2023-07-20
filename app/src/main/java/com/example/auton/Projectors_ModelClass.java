package com.example.auton;

public class Projectors_ModelClass {
    String Image, Model, Dimension, Watttage, Weight, BulbType, Lumens, Category, Feature, Brand, Price, Quantity;

    public Projectors_ModelClass() {
    }

    public Projectors_ModelClass(String image, String model, String dimension, String watttage, String weight, String bulbType, String lumens, String category, String feature, String brand, String price, String quantity) {
        Image = image;
        Model = model;
        Dimension = dimension;
        Watttage = watttage;
        Weight = weight;
        BulbType = bulbType;
        Lumens = lumens;
        Category = category;
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

    public String getLumens() {
        return Lumens;
    }

    public void setLumens(String lumens) {
        Lumens = lumens;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
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
