package com.example.wkmin.playhome.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlayHomeModel {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("star_count")
    @Expose
    private int starCount;
    @SerializedName("primary_image")
    @Expose
    private String primaryImage;
    @SerializedName("primary_image_thumbnail")
    @Expose
    private String primaryImageThumbnail;
    @SerializedName("star_score_avg")
    @Expose
    private float starScoreAvg;
    @SerializedName("formatted_address")
    @Expose
    private String formattedAddress;
    @SerializedName("name_in_list")
    @Expose
    private String nameInList;
    @SerializedName("tag_names")
    @Expose
    private String tagNames;
    @SerializedName("latitude")
    @Expose
    private double latitude;
    @SerializedName("longitude")
    @Expose
    private double longitude;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStarCount() {
        return starCount;
    }

    public void setStarCount(Integer starCount) {
        this.starCount = starCount;
    }

    public String getPrimaryImage() {
        return primaryImage;
    }

    public void setPrimaryImage(String primaryImage) {
        this.primaryImage = primaryImage;
    }

    public String getPrimaryImageThumbnail() {
        return primaryImageThumbnail;
    }

    public void setPrimaryImageThumbnail(String primaryImageThumbnail) {
        this.primaryImageThumbnail = primaryImageThumbnail;
    }

    public Float getStarScoreAvg() {
        return starScoreAvg;
    }

    public void setStarScoreAvg(Float starScoreAvg) {
        this.starScoreAvg = starScoreAvg;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getNameInList() {
        return nameInList;
    }

    public void setNameInList(String nameInList) {
        this.nameInList = nameInList;
    }

    public String getTagNames() {
        return tagNames;
    }

    public void setTagNames(String tagNames) {
        this.tagNames = tagNames;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

}
