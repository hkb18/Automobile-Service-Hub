package com.example.auton;

public class Worshop_View_Service_modelClass {
    int ACCEPT_SERVICE = 3;
    private String AssignedMechanic, CarBrand, CarModel, Date, Image, Latitude, Longitude, Price, ServiceTime, ServiceType, Username, Key, ServiceName, Workshop, ServiceStatus, ContactNo;

    public Worshop_View_Service_modelClass() {
    }

    public Worshop_View_Service_modelClass(String ContactNo, String ServiceStatus, String assignedMechanic, String carBrand, String carModel, String date, String image, String latitude, String longitude, String price, String serviceTime, String serviceType, String username, String key, String serviceName, String workshop, int ACCEPT_SERVICE) {
        AssignedMechanic = assignedMechanic;
        CarBrand = carBrand;
        CarModel = carModel;
        Date = date;
        Image = image;
        Latitude = latitude;
        Longitude = longitude;
        Price = price;
        ServiceTime = serviceTime;
        ServiceType = serviceType;
        Username = username;
        Key = key;
        ServiceName = serviceName;
        Workshop = workshop;
        this.ACCEPT_SERVICE = ACCEPT_SERVICE;
        this.ServiceStatus = ServiceStatus;
        this.ContactNo = ContactNo;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public int getACCEPT_SERVICE() {
        return ACCEPT_SERVICE;
    }

    public void setACCEPT_SERVICE(int ACCEPT_SERVICE) {
        this.ACCEPT_SERVICE = ACCEPT_SERVICE;
    }

    public String getServiceStatus() {
        return ServiceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        ServiceStatus = serviceStatus;
    }

    public String getAssignedMechanic() {
        return AssignedMechanic;
    }

    public void setAssignedMechanic(String assignedMechanic) {
        AssignedMechanic = assignedMechanic;
    }

    public String getCarBrand() {
        return CarBrand;
    }

    public void setCarBrand(String carBrand) {
        CarBrand = carBrand;
    }

    public String getCarModel() {
        return CarModel;
    }

    public void setCarModel(String carModel) {
        CarModel = carModel;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getServiceTime() {
        return ServiceTime;
    }

    public void setServiceTime(String serviceTime) {
        ServiceTime = serviceTime;
    }

    public String getServiceType() {
        return ServiceType;
    }

    public void setServiceType(String serviceType) {
        ServiceType = serviceType;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }

    public String getWorkshop() {
        return Workshop;
    }

    public void setWorkshop(String workshop) {
        Workshop = workshop;
    }


}
