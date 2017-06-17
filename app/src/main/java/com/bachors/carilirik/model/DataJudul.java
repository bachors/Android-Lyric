package com.bachors.carilirik.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Bachors on 6/16/2017.
 */

public class DataJudul {

    @SerializedName("judul")
    @Expose
    private String judul;
    @SerializedName("lirik")
    @Expose
    private String lirik;

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getLirik() {
        return lirik;
    }

    public void setLirik(String lirik) {
        this.lirik = lirik;
    }

}
