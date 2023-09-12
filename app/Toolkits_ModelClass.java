public class Toolkits_ModelClass {
    String Image, Model, Dimension, Color, Weight, ItemIncluded, Brand, Price, Quantity;

    public Toolkits_ModelClass() {
    }

    public Toolkits_ModelClass(String image, String model, String dimension, String color, String weight, String itemIncluded, String brand, String price, String quantity) {
        Image = image;
        Model = model;
        Dimension = dimension;
        Color = color;
        Weight = weight;
        ItemIncluded = itemIncluded;
        Brand = brand;
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

    public String getDimension() {
        return Dimension;
    }

    public void setDimension(String dimension) {
        Dimension = dimension;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getItemIncluded() {
        return ItemIncluded;
    }

    public void setItemIncluded(String itemIncluded) {
        ItemIncluded = itemIncluded;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
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
