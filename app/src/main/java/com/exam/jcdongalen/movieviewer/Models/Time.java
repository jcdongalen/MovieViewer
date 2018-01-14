package com.exam.jcdongalen.movieviewer.Models;

/**
 * Created by jc on 09/01/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Time {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("schedule_id")
    @Expose
    private String scheduleId;
    @SerializedName("popcorn_price")
    @Expose
    private String popcornPrice;
    @SerializedName("popcorn_label")
    @Expose
    private String popcornLabel;
    @SerializedName("seating_type")
    @Expose
    private String seatingType;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("variant")
    @Expose
    private Object variant;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getPopcornPrice() {
        return popcornPrice;
    }

    public void setPopcornPrice(String popcornPrice) {
        this.popcornPrice = popcornPrice;
    }

    public String getPopcornLabel() {
        return popcornLabel;
    }

    public void setPopcornLabel(String popcornLabel) {
        this.popcornLabel = popcornLabel;
    }

    public String getSeatingType() {
        return seatingType;
    }

    public void setSeatingType(String seatingType) {
        this.seatingType = seatingType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Object getVariant() {
        return variant;
    }

    public void setVariant(Object variant) {
        this.variant = variant;
    }

}
