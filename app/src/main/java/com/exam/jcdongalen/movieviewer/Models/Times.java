package com.exam.jcdongalen.movieviewer.Models;

/**
 * Created by jc on 09/01/2018.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Times {

    @SerializedName("parent")
    @Expose
    private String parent;
    @SerializedName("times")
    @Expose
    private List<Time> times = null;

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public List<Time> getTimes() {
        return times;
    }

    public void setTimes(List<Time> times) {
        this.times = times;
    }

}