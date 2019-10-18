package com.tuts.gdk_submission1.movie.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tuts.gdk_submission1.movie.datamodel.MovieDataModel;

import java.util.ArrayList;

public class MovieResponse {

    @SerializedName("results")
    @Expose
    public ArrayList<MovieDataModel> movieDataModels;

    public ArrayList<MovieDataModel> getMovieDataModels() {
        return movieDataModels;
    }


}
