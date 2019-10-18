package com.tuts.gdk_submission1.movie.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tuts.gdk_submission1.movie.datamodel.MovieGenreDataModel;

import java.util.ArrayList;

public class MovieGenreResponse {

    @SerializedName("genres")
    @Expose
    private ArrayList<MovieGenreDataModel> movieGenreDataModels = null ;

    public ArrayList<MovieGenreDataModel> getMovieGenreDataModels() {
        return movieGenreDataModels;
    }

}
