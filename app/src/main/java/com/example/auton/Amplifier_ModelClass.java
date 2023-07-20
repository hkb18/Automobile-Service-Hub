package com.example.auton;

public class Amplifier_ModelClass {
    private String Channel, Dimension, Image, Manufacturer, MaxVoltage, Model, MountingHardware, Price, Quantity, Weight;

    public Amplifier_ModelClass() {
    }

    public Amplifier_ModelClass(String channel, String dimension, String image, String manufacturer, String maxVoltage, String model, String mountingHardware, String price, String quantity, String weight) {
        Channel = channel;
        Dimension = dimension;
        Image = image;
        Manufacturer = manufacturer;
        MaxVoltage = maxVoltage;
        Model = model;
        MountingHardware = mountingHardware;
        Price = price;
        Quantity = quantity;
        Weight = weight;
    }

    public String getChannel() {
        return Channel;
    }

    public void setChannel(String channel) {
        Channel = channel;
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

    public String getManufacturer() {
        return Manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        Manufacturer = manufacturer;
    }

    public String getMaxVoltage() {
        return MaxVoltage;
    }

    public void setMaxVoltage(String maxVoltage) {
        MaxVoltage = maxVoltage;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getMountingHardware() {
        return MountingHardware;
    }

    public void setMountingHardware(String mountingHardware) {
        MountingHardware = mountingHardware;
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
