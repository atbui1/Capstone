package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UpdateVegetableResponse implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("featture")
    private String featture;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("gardenId")
    private int gardenId;
    @SerializedName("images")
    private String images;
    @SerializedName("newImages")
    private List<ImageWiki> listImageWikis;
    @SerializedName("idDescription")
    private String idDescription;
    @SerializedName("isFixed")
    private boolean isFixed;
    @SerializedName("nameSearch")
    private String nameSearch;
    @SerializedName("synonymOfFeature")
    private String synonymOfFeature;

    public UpdateVegetableResponse(String id, String title, String description, String featture, int quantity,
                                   int gardenId, String images, List<ImageWiki> listImageWikis,
                                   String idDescription, boolean isFixed, String nameSearch, String synonymOfFeature) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.featture = featture;
        this.quantity = quantity;
        this.gardenId = gardenId;
        this.images = images;
        this.listImageWikis = listImageWikis;
        this.idDescription = idDescription;
        this.isFixed = isFixed;
        this.nameSearch = nameSearch;
        this.synonymOfFeature = synonymOfFeature;
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

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public List<ImageWiki> getListImageWikis() {
        return listImageWikis;
    }

    public void setListImageWikis(List<ImageWiki> listImageWikis) {
        this.listImageWikis = listImageWikis;
    }

    public String getIdDescription() {
        return idDescription;
    }

    public void setIdDescription(String idDescription) {
        this.idDescription = idDescription;
    }

    public boolean isFixed() {
        return isFixed;
    }

    public void setFixed(boolean fixed) {
        isFixed = fixed;
    }

    public String getNameSearch() {
        return nameSearch;
    }

    public void setNameSearch(String nameSearch) {
        this.nameSearch = nameSearch;
    }

    public String getSynonymOfFeature() {
        return synonymOfFeature;
    }

    public void setSynonymOfFeature(String synonymOfFeature) {
        this.synonymOfFeature = synonymOfFeature;
    }
}
