package com.example.auton;

public class TyresWheelcare_ModelClass {
    private String Brand, Image, Features, Model, Price, RimSize, Width;

    public TyresWheelcare_ModelClass() {
    }

    public TyresWheelcare_ModelClass(String brand, String image, String features, String model, String price, String rimSize, String width) {
        Brand = brand;
        Image = image;
        Features = features;
        Model = model;
        Price = price;
        RimSize = rimSize;
        Width = width;
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

    public String getFeatures() {
        return Features;
    }

    public void setFeatures(String features) {
        Features = features;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getRimSize() {
        return RimSize;
    }

    public void setRimSize(String rimSize) {
        RimSize = rimSize;
    }

    public String getWidth() {
        return Width;
    }

    public void setWidth(String width) {
        Width = width;
    }
}
