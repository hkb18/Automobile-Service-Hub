package com.example.auton;

public class AirFreshner_ModelClass {
    String Image, Model, ItemForm, Color, Dimenension, Weight, Manufacturer, Duration, Fragrence, Price, Quantity;

    public AirFreshner_ModelClass() {
    }

    public AirFreshner_ModelClass(String image, String model, String itemForm, String color, String dimenension, String weight, String manufacturer, String duration, String fragrence, String price, String quantity) {
        Image = image;
        Model = model;
        ItemForm = itemForm;
        Color = color;
        Dimenension = dimenension;
        Weight = weight;
        Manufacturer = manufacturer;
        Duration = duration;
        Fragrence = fragrence;
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

    public String getItemForm() {
        return ItemForm;
    }

    public void setItemForm(String itemForm) {
        ItemForm = itemForm;
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

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getFragrence() {
        return Fragrence;
    }

    public void setFragrence(String fragrence) {
        Fragrence = fragrence;
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
