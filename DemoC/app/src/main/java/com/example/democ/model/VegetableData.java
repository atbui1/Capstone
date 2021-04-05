package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class VegetableData implements Serializable {

    @SerializedName("no")
    private int stt;
    @SerializedName("idName")
    private String idName;
    @SerializedName("idDescription")
    private String idDescription;
    @SerializedName("idFeature")
    private String idFeature;
    @SerializedName("idImage")
    private String idImage;
    @SerializedName("title")
    private String title;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("featture")
    private String featture;
    @SerializedName("feature")
    private String feature;
    @SerializedName("newFeatture")
    private String newFeatture;
    @SerializedName("images")
    private List<ImageVegetable> imageVegetables;
    @SerializedName("gardenId")
    private int gardenId;
    @SerializedName("quantity")
    private String quantity;
    //new 4 idDetail
    @SerializedName("idDetailName")
    private String idDetailName;
    @SerializedName("idDetailDescription")
    private String idDetailDescription;
    @SerializedName("idDetailFeature")
    private String idDetailFeature;
    @SerializedName("idDetailImage")
    private String idDetailImage;


    //CST all - name
    public VegetableData(int stt, String idName, String idDescription, String idFeature, String idImage, String title, String description,
                         String featture, String newFeatture, List<ImageVegetable> imageVegetables, int gardenId) {
        this.stt = stt;
        this.idName = idName;
        this.idDescription = idDescription;
        this.idFeature = idFeature;
        this.idImage = idImage;
        this.title = title;
        this.description = description;
        this.featture = featture;
        this.newFeatture = newFeatture;
        this.imageVegetables = imageVegetables;
        this.gardenId = gardenId;
    }

    //CST - newFeature, title
    //CST nhan response
    public VegetableData(int stt, String idName, String idDescription, String idFeature, String idImage, String name, String description,
                         String feature, List<ImageVegetable> imageVegetables, int gardenId) {
        this.stt = stt;
        this.idName = idName;
        this.idDescription = idDescription;
        this.idFeature = idFeature;
        this.idImage = idImage;
        this.name = name;
        this.description = description;
        this.feature = feature;
        this.imageVegetables = imageVegetables;
        this.gardenId = gardenId;
    }

    //CST search
    public VegetableData(int stt, String idName, String idDescription, String idFeature, String idImage, String name, String description,
                         String feature, List<ImageVegetable> imageVegetables, int gardenId, String quantity) {
        this.stt = stt;
        this.idName = idName;
        this.idDescription = idDescription;
        this.idFeature = idFeature;
        this.idImage = idImage;
        this.name = name;
        this.description = description;
        this.feature = feature;
        this.imageVegetables = imageVegetables;
        this.gardenId = gardenId;
        this.quantity = quantity;
    }

    //CST searchNew - 15
    public VegetableData(int stt, String idName, String idDescription, String idFeature, String idImage, String name,
                         String description, String feature, List<ImageVegetable> imageVegetables, int gardenId, String quantity,
                         String idDetailName, String idDetailDescription, String idDetailFeature, String idDetailImage) {
        this.stt = stt;
        this.idName = idName;
        this.idDescription = idDescription;
        this.idFeature = idFeature;
        this.idImage = idImage;
        this.name = name;
        this.description = description;
        this.feature = feature;
        this.imageVegetables = imageVegetables;
        this.gardenId = gardenId;
        this.quantity = quantity;
        this.idDetailName = idDetailName;
        this.idDetailDescription = idDetailDescription;
        this.idDetailFeature = idDetailFeature;
        this.idDetailImage = idDetailImage;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public String getIdDescription() {
        return idDescription;
    }

    public void setIdDescription(String idDescription) {
        this.idDescription = idDescription;
    }

    public String getIdFeature() {
        return idFeature;
    }

    public void setIdFeature(String idFeature) {
        this.idFeature = idFeature;
    }

    public String getIdImage() {
        return idImage;
    }

    public void setIdImage(String idImage) {
        this.idImage = idImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getNewFeatture() {
        return newFeatture;
    }

    public void setNewFeatture(String newFeatture) {
        this.newFeatture = newFeatture;
    }

    public List<ImageVegetable> getImageVegetables() {
        return imageVegetables;
    }

    public void setImageVegetables(List<ImageVegetable> imageVegetables) {
        this.imageVegetables = imageVegetables;
    }

    public int getGardenId() {
        return gardenId;
    }

    public void setGardenId(int gardenId) {
        this.gardenId = gardenId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getIdDetailName() {
        return idDetailName;
    }

    public void setIdDetailName(String idDetailName) {
        this.idDetailName = idDetailName;
    }

    public String getIdDetailDescription() {
        return idDetailDescription;
    }

    public void setIdDetailDescription(String idDetailDescription) {
        this.idDetailDescription = idDetailDescription;
    }

    public String getIdDetailFeature() {
        return idDetailFeature;
    }

    public void setIdDetailFeature(String idDetailFeature) {
        this.idDetailFeature = idDetailFeature;
    }

    public String getIdDetailImage() {
        return idDetailImage;
    }

    public void setIdDetailImage(String idDetailImage) {
        this.idDetailImage = idDetailImage;
    }
}
