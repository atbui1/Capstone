package com.example.democ.model;

import java.util.List;

import okhttp3.MultipartBody;

public class UpdateVegetableRequest {
    private String id;
    private String title;
    private String description;
    private String featture;
    private int quantity;
    private int gardenId;
    private List<MultipartBody.Part> newImages;

    public UpdateVegetableRequest(String id, String title, String description, String featture,
                                  int quantity, int gardenId, List<MultipartBody.Part> newImages) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.featture = featture;
        this.quantity = quantity;
        this.gardenId = gardenId;
        this.newImages = newImages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getGardenId() {
        return gardenId;
    }

    public void setGardenId(int gardenId) {
        this.gardenId = gardenId;
    }

    public List<MultipartBody.Part> getNewImages() {
        return newImages;
    }

    public void setNewImages(List<MultipartBody.Part> newImages) {
        this.newImages = newImages;
    }
}
