package com.exam.jcdongalen.movieviewer.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jc on 09/01/2018.
 */

public class Available {

    @SerializedName("seats")
    @Expose
    private List<String> seats = null;
    @SerializedName("seat_count")
    @Expose
    private Integer seatCount;

    public List<String> getSeats() {
        return seats;
    }

    public void setSeats(List<String> seats) {
        this.seats = seats;
    }

    public Integer getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(Integer seatCount) {
        this.seatCount = seatCount;
    }

}
