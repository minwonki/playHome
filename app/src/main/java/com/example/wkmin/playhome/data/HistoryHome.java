package com.example.wkmin.playhome.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class HistoryHome extends RealmObject {

    @PrimaryKey
    private long    id;

    private String  name;
    private String  address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
