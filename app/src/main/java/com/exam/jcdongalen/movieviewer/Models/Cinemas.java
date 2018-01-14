package com.exam.jcdongalen.movieviewer.Models;

/**
 * Created by jc on 09/01/2018.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cinemas {

    @SerializedName("parent")
    @Expose
    private String parent;
    @SerializedName("cinemas")
    @Expose
    private List<Cinema> cinemas = null;

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public List<Cinema> getCinemas() {
        return cinemas;
    }

    public void setCinemas(List<Cinema> cinemas) {
        this.cinemas = cinemas;
    }

}