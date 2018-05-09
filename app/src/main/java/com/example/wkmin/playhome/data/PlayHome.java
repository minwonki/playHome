package com.example.wkmin.playhome.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PlayHome extends RealmObject {

    @PrimaryKey
    private long    id;

    private String  name;
    private int     starCount;
    private String  imageUrl;
    private String  thumbnailUrl;
    private Float   starAvg;
    private String  address;
    private String  tags;
    private double  latitude;
    private double  longitude;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStarCount() {
        return starCount;
    }

    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Float getStarAvg() {
        return starAvg;
    }

    public void setStarAvg(Float starAvg) {
        this.starAvg = starAvg;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
