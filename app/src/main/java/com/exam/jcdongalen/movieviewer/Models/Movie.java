package com.exam.jcdongalen.movieviewer.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jc on 09/01/2018.
 */

public class Movie {

    @SerializedName("movie_id")
    @Expose
    private String movieId;
    @SerializedName("advisory_rating")
    @Expose
    private String advisoryRating;
    @SerializedName("canonical_title")
    @Expose
    private String canonicalTitle;
    @SerializedName("cast")
    @Expose
    private List<String> cast = null;
    @SerializedName("genre")
    @Expose
    private String genre;
    @SerializedName("has_schedules")
    @Expose
    private Integer hasSchedules;
    @SerializedName("is_inactive")
    @Expose
    private Integer isInactive;
    @SerializedName("is_showing")
    @Expose
    private Integer isShowing;
    @SerializedName("link_name")
    @Expose
    private String linkName;
    @SerializedName("poster")
    @Expose
    private String poster;
    @SerializedName("poster_landscape")
    @Expose
    private String posterLandscape;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("runtime_mins")
    @Expose
    private String runtimeMins;
    @SerializedName("synopsis")
    @Expose
    private String synopsis;
    @SerializedName("trailer")
    @Expose
    private String trailer;
    @SerializedName("average_rating")
    @Expose
    private Object averageRating;
    @SerializedName("total_reviews")
    @Expose
    private Object totalReviews;
    @SerializedName("variants")
    @Expose
    private List<String> variants = null;
    @SerializedName("theater")
    @Expose
    private String theater;
    @SerializedName("order")
    @Expose
    private Integer order;
    @SerializedName("is_featured")
    @Expose
    private Integer isFeatured;
    @SerializedName("watch_list")
    @Expose
    private Boolean watchList;
    @SerializedName("your_rating")
    @Expose
    private Integer yourRating;

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getAdvisoryRating() {
        return advisoryRating;
    }

    public void setAdvisoryRating(String advisoryRating) {
        this.advisoryRating = advisoryRating;
    }

    public String getCanonicalTitle() {
        return canonicalTitle;
    }

    public void setCanonicalTitle(String canonicalTitle) {
        this.canonicalTitle = canonicalTitle;
    }

    public List<String> getCast() {
        return cast;
    }

    public void setCast(List<String> cast) {
        this.cast = cast;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getHasSchedules() {
        return hasSchedules;
    }

    public void setHasSchedules(Integer hasSchedules) {
        this.hasSchedules = hasSchedules;
    }

    public Integer getIsInactive() {
        return isInactive;
    }

    public void setIsInactive(Integer isInactive) {
        this.isInactive = isInactive;
    }

    public Integer getIsShowing() {
        return isShowing;
    }

    public void setIsShowing(Integer isShowing) {
        this.isShowing = isShowing;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPosterLandscape() {
        return posterLandscape;
    }

    public void setPosterLandscape(String posterLandscape) {
        this.posterLandscape = posterLandscape;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRuntimeMins() {
        return runtimeMins;
    }

    public void setRuntimeMins(String runtimeMins) {
        this.runtimeMins = runtimeMins;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public Object getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Object averageRating) {
        this.averageRating = averageRating;
    }

    public Object getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(Object totalReviews) {
        this.totalReviews = totalReviews;
    }

    public List<String> getVariants() {
        return variants;
    }

    public void setVariants(List<String> variants) {
        this.variants = variants;
    }

    public String getTheater() {
        return theater;
    }

    public void setTheater(String theater) {
        this.theater = theater;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(Integer isFeatured) {
        this.isFeatured = isFeatured;
    }

    public Boolean getWatchList() {
        return watchList;
    }

    public void setWatchList(Boolean watchList) {
        this.watchList = watchList;
    }

    public Integer getYourRating() {
        return yourRating;
    }

    public void setYourRating(Integer yourRating) {
        this.yourRating = yourRating;
    }
}
