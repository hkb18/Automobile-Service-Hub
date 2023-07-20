package com.example.auton;

public class WiperBlades_ModelClass {
    String Brand, Dimension, Image, Material, Model, Position, Price, Quantity, Weight;

    public WiperBlades_ModelClass() {
    }

    public WiperBlades_ModelClass(String brand, String dimension, String image, String material, String model, String position, String price, String quantity, String weight) {
        Brand = brand;
        Dimension = dimension;
        Image = image;
        Material = material;
        Model = model;
        Position = position;
        Price = price;
        Quantity = quantity;
        Weight = weight;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
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

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String material) {
        Material = material;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
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
