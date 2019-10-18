package com.tuts.gdk_submission1.movie.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tuts.gdk_submission1.movie.datamodel.MovieCastDataModel;

import java.util.ArrayList;

public class MovieCastResponse {

    @SerializedName("cast")
    @Expose
    private ArrayList<MovieCastDataModel> movieCastDataModels = null;

    public ArrayList<MovieCastDataModel> getMovieCastDataModels() {
        return movieCastDataModels;
    }
}
