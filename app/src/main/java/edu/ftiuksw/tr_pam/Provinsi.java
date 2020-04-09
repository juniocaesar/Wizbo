package edu.ftiuksw.tr_pam;

import com.google.gson.annotations.SerializedName;

public class Provinsi {

    @SerializedName("id")
    private String id;

    @SerializedName("nama")
    private String nama;

    @SerializedName("image-url")
    private String imageurl;

    public Provinsi(String id, String nama, String imageurl) {
        this.id = id;
        this.nama = nama;
        this.imageurl = imageurl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
