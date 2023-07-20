package com.example.auton;

public class MicroFibres_ModelClass {
    String Brand, Color, Dimension, FabricType, Image, MaterialType, Model, Price, Quantity;

    public MicroFibres_ModelClass() {
    }

    public MicroFibres_ModelClass(String brand, String color, String dimension, String fabricType, String image, String materialType, String model, String price, String quantity) {
        Brand = brand;
        Color = color;
        Dimension = dimension;
        FabricType = fabricType;
        Image = image;
        MaterialType = materialType;
        Model = model;
        Price = price;
        Quantity = quantity;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
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

    public String getFabricType() {
        return FabricType;
    }

    public void setFabricType(String fabricType) {
        FabricType = fabricType;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getMaterialType() {
        return MaterialType;
    }

    public void setMaterialType(String materialType) {
        MaterialType = materialType;
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

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }
}
