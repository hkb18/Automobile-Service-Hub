package com.example.auton;

public class Detailing_ModelClass {
    String BoxIncluded,Brand,Dimension,Image,ItemForm,Price,Quantity,Volume,Weight;

    public Detailing_ModelClass() {
    }

    public Detailing_ModelClass(String boxIncluded, String brand, String dimension, String image, String itemForm, String price, String quantity, String volume, String weight) {
        BoxIncluded = boxIncluded;
        Brand = brand;
        Dimension = dimension;
        Image = image;
        ItemForm = itemForm;
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

    public String getItemForm() {
        return ItemForm;
    }

    public void setItemForm(String itemForm) {
        ItemForm = itemForm;
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
