package com.bachors.carilirik.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Bachors on 6/16/2017.
 */

public class Judul {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<DataJudul> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataJudul> getData() {
        return data;
    }

    public void setData(List<DataJudul> data) {
        this.data = data;
    }

}
