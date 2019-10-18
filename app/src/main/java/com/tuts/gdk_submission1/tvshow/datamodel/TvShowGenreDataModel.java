package com.tuts.gdk_submission1.tvshow.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TvShowGenreDataModel {

    @SerializedName("movieId")
    @Expose
    private Integer tvShowGenreId = null;

    @SerializedName("name")
    @Expose
    private String tvShowGenreName = null;

    public String getTvShowGenreName() {
        return tvShowGenreName;
    }
}
