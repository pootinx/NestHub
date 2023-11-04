package com.example.nesthub;

public class HouseModel {
    String disponible ,duration,image,localisation,price,title;

    public HouseModel() {
    }

    public HouseModel(String disponible, String duration, String image, String localisation, String price, String title) {
        this.disponible = disponible;
        this.duration = duration;
        this.image = image;
        this.localisation = localisation;
        this.price = price;
        this.title = title;
    }

    public String getDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
