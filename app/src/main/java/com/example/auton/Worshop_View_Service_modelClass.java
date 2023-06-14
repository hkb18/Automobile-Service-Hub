package com.example.auton;

public class Worshop_View_Service_modelClass {
    private String CarBrand, CarModel, Date, Latitude, Longitude, PaymentMode, ServiceTime, ServiceType, Username, SYSTIME;
    boolean ACCEPT_SERVICE=true;

    public Worshop_View_Service_modelClass() {
    }

    public Worshop_View_Service_modelClass(String carBrand, String carModel, String date, String latitude, String longitude, String paymentMode, String serviceTime, String serviceType, String username, String SYSTIME ,boolean ACCEPT_SERVICE) {
        CarBrand = carBrand;
        CarModel = carModel;
        Date = date;
        Latitude = latitude;
        Longitude = longitude;
        PaymentMode = paymentMode;
        ServiceTime = serviceTime;
        ServiceType = serviceType;
        Username = username;
        this.SYSTIME = SYSTIME;
        this.ACCEPT_SERVICE=ACCEPT_SERVICE;
    }

    public boolean isACCEPT_SERVICE() {
        return ACCEPT_SERVICE;
    }

    public void setACCEPT_SERVICE(boolean ACCEPT_SERVICE) {
        this.ACCEPT_SERVICE = ACCEPT_SERVICE;
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

    public String getPaymentMode() {
        return PaymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        PaymentMode = paymentMode;
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

    public String getSYSTIME() {
        return SYSTIME;
    }

    public void setSYSTIME(String SYSTIME) {
        this.SYSTIME = SYSTIME;
    }
}
