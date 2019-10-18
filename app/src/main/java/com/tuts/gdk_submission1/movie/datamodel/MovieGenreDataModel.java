package com.tuts.gdk_submission1.movie.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieGenreDataModel {

    @SerializedName("movieId")
    @Expose
    private Integer movieGenreId = null;

    @SerializedName("name")
    @Expose
    private String movieGenreName = null ;

    public String getMovieGenreName() {
        return movieGenreName;
    }
}
