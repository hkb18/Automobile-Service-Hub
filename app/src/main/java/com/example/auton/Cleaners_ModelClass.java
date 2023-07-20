package com.example.auton;

public class Cleaners_ModelClass {
    String BoxIncludes, Brand, Category, Dimension, Image, ItemForm, Price, Quantity, Volume, Weight, key;

    public Cleaners_ModelClass() {
    }

    public Cleaners_ModelClass(String key, String boxIncludes, String brand, String category, String dimension, String image, String itemForm, String price, String quantity, String volume, String weight) {
        BoxIncludes = boxIncludes;
        Brand = brand;
        Category = category;
        Dimension = dimension;
        Image = image;
        ItemForm = itemForm;
        Price = price;
        Quantity = quantity;
        Volume = volume;
        Weight = weight;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getBoxIncludes() {
        return BoxIncludes;
    }

    public void setBoxIncludes(String boxIncludes) {
        BoxIncludes = boxIncludes;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
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
