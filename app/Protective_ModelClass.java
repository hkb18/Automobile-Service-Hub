public class Protective_ModelClass {
    String Image, Model, Dimension, Color, Weight, MaterialType, FitType, Feature, Manufacturer, Price, Quantity;

    public Protective_ModelClass() {
    }

    public Protective_ModelClass(String image, String model, String dimension, String color, String weight, String materialType, String fitType, String feature, String manufacturer, String price, String quantity) {
        Image = image;
        Model = model;
        Dimension = dimension;
        Color = color;
        Weight = weight;
        MaterialType = materialType;
        FitType = fitType;
        Feature = feature;
        Manufacturer = manufacturer;
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

    public String getMaterialType() {
        return MaterialType;
    }

    public void setMaterialType(String materialType) {
        MaterialType = materialType;
    }

    public String getFitType() {
        return FitType;
    }

    public void setFitType(String fitType) {
        FitType = fitType;
    }

    public String getFeature() {
        return Feature;
    }

    public void setFeature(String feature) {
        Feature = feature;
    }

    public String getManufacturer() {
        return Manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        Manufacturer = manufacturer;
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
