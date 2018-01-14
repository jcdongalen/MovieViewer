package com.exam.jcdongalen.movieviewer.Models;

/**
 * Created by jc on 09/01/2018.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SeatMap {

    @SerializedName("seatmap")
    @Expose
    private List<List<String>> seatmap = null;
    @SerializedName("available")
    @Expose
    private Available available;

    public List<List<String>> getSeatmap() {
        return seatmap;
    }

    public void setSeatmap(List<List<String>> seatmap) {
        this.seatmap = seatmap;
    }

    public Available getAvailable() {
        return available;
    }

    public void setAvailable(Available available) {
        this.available = available;
    }

}
