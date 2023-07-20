package com.example.auton;

public class Speaker_ModelClass {
    private String Color, Diameter, Frequency, Image, Manufacturer, PowerOutput, Price, Quality, Sensitivity, SpeakerType, Model;

    public Speaker_ModelClass() {
    }

    public Speaker_ModelClass(String model, String color, String diameter, String frequency, String image, String manufacturer, String powerOutput, String price, String quality, String sensitivity, String speakerType) {
        Model = model;
        Color = color;
        Diameter = diameter;
        Frequency = frequency;
        Image = image;
        Manufacturer = manufacturer;
        PowerOutput = powerOutput;
        Price = price;
        Quality = quality;
        Sensitivity = sensitivity;
        SpeakerType = speakerType;
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

    public String getDiameter() {
        return Diameter;
    }

    public void setDiameter(String diameter) {
        Diameter = diameter;
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

    public String getQuality() {
        return Quality;
    }

    public void setQuality(String quality) {
        Quality = quality;
    }

    public String getSensitivity() {
        return Sensitivity;
    }

    public void setSensitivity(String sensitivity) {
        Sensitivity = sensitivity;
    }

    public String getSpeakerType() {
        return SpeakerType;
    }

    public void setSpeakerType(String speakerType) {
        SpeakerType = speakerType;
    }
}
