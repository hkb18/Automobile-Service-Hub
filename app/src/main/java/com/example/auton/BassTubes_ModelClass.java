package com.example.auton;

public class BassTubes_ModelClass {
    private String Color, Design, Dimension, Frequency, Image, Manufacturer, Model, PowerOutput, Price, Quantity, SalientFeature, Sensitivity, Weight;

    public BassTubes_ModelClass() {
    }

    public BassTubes_ModelClass(String color, String design, String dimension, String frequency, String image, String manufacturer, String model, String powerOutput, String price, String quantity, String salientFeature, String sensitivity, String weight) {
        Color = color;
        Design = design;
        Dimension = dimension;
        Frequency = frequency;
        Image = image;
        Manufacturer = manufacturer;
        Model = model;
        PowerOutput = powerOutput;
        Price = price;
        Quantity = quantity;
        SalientFeature = salientFeature;
        Sensitivity = sensitivity;
        Weight = weight;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getDesign() {
        return Design;
    }

    public void setDesign(String design) {
        Design = design;
    }

    public String getDimension() {
        return Dimension;
    }

    public void setDimension(String dimension) {
        Dimension = dimension;
    }

    public String getFrequency() {
        return Frequency;
    }

    public void setFrequency(String frequency) {
        Frequency = frequency;
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

    public String getSalientFeature() {
        return SalientFeature;
    }

    public void setSalientFeature(String salientFeature) {
        SalientFeature = salientFeature;
    }

    public String getSensitivity() {
        return Sensitivity;
    }

    public void setSensitivity(String sensitivity) {
        Sensitivity = sensitivity;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }
}
