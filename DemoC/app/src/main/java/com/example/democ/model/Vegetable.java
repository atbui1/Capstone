package com.example.democ.model;

public class Vegetable {

    private String title;
    private String description;
    private String featture;
    private String images;

    public Vegetable(String title, String description, String featture, String images) {
        this.title = title;
        this.description = description;
        this.featture = featture;
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFeatture() {
        return featture;
    }

    public void setFeatture(String featture) {
        this.featture = featture;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
