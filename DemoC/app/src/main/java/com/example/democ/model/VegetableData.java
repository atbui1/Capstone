package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class VegetableData implements Serializable {

    @SerializedName("no")
    private int stt;
    @SerializedName("id")
    private String id;
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

    // image wiki
    @SerializedName("newImages")
    private List<ImageWiki> imageWikis;
    //
    @SerializedName("gardenId")
    private int gardenId;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("isFixed")
    private boolean isFixed;
    @SerializedName("nameSearch")
    private String nameSearch;
    @SerializedName("synonymOfFeature")
    private String synonymOfFeature;


    /** CTS new create vegetable - response - 12 */
    public VegetableData(String id, String idDescription, String title, String description, String featture,
                         List<ImageVegetable> imageVegetables, List<ImageWiki> imageWikis, int gardenId,
                         int quantity, boolean isFixed, String nameSearch, String synonymOfFeature) {
        this.id = id;
        this.idDescription = idDescription;
        this.title = title;
        this.description = description;
        this.featture = featture;
        this.imageVegetables = imageVegetables;
        this.imageWikis = imageWikis;
        this.gardenId = gardenId;
        this.quantity = quantity;
        this.isFixed = isFixed;
        this.nameSearch = nameSearch;
        this.synonymOfFeature = synonymOfFeature;
    }

    //CST vegetable by garden id
    public VegetableData(int stt, String id, String idDescription, String name, String feature,
                         List<ImageVegetable> imageVegetables, int gardenId, int quantity) {
        this.stt = stt;
        this.id = id;
        this.idDescription = idDescription;
        this.name = name;
        this.feature = feature;
        this.imageVegetables = imageVegetables;
        this.gardenId = gardenId;
        this.quantity = quantity;
    }

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
                         String feature, List<ImageVegetable> imageVegetables, int gardenId, int quantity) {
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

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<ImageWiki> getImageWikis() {
        return imageWikis;
    }

    public void setImageWikis(List<ImageWiki> imageWikis) {
        this.imageWikis = imageWikis;
    }

    public int getGardenId() {
        return gardenId;
    }

    public void setGardenId(int gardenId) {
        this.gardenId = gardenId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
