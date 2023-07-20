package com.example.auton;

public class CleaningKit_ModelClass {
    String BoxIncluded, Brand, Dimensions, Image, ItemForm, Model, Price, Quantity, Volume, Weight;

    public CleaningKit_ModelClass() {
    }

    public CleaningKit_ModelClass(String boxIncluded, String brand, String dimensions, String image, String itemForm, String model, String price, String quantity, String volume, String weight) {
        BoxIncluded = boxIncluded;
        Brand = brand;
        Dimensions = dimensions;
        Image = image;
        ItemForm = itemForm;
        Model = model;
        Price = price;
        Quantity = quantity;
        Volume = volume;
        Weight = weight;
    }

    public String getBoxIncluded() {
        return BoxIncluded;
    }

    public void setBoxIncluded(String boxIncluded) {
        BoxIncluded = boxIncluded;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getDimensions() {
        return Dimensions;
    }

    public void setDimensions(String dimensions) {
        Dimensions = dimensions;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getItemForm() {
        return ItemForm;
    }

    public void setItemForm(String itemForm) {
        ItemForm = itemForm;
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

    public String getVolume() {
        return Volume;
    }

    public void setVolume(String volume) {
        Volume = volume;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }
}
