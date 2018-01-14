package com.exam.jcdongalen.movieviewer.Models;

/**
 * Created by jc on 09/01/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cinema {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("cinema_id")
    @Expose
    private String cinemaId;
    @SerializedName("label")
    @Expose
    private String label;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(String cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
