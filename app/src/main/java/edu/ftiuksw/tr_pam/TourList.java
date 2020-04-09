package edu.ftiuksw.tr_pam;

import com.google.gson.annotations.SerializedName;

public class TourList {
    @SerializedName("title")
    private String title;
    @SerializedName("date")
    private String date;
    @SerializedName("lat")
    private double latitude;
    @SerializedName("lon")
    private double longitude;

    public TourList(String title, String date, double latitude, double longitude) {
        this.title = title;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public TourList() {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
