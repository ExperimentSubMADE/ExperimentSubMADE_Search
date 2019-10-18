package com.tuts.gdk_submission1.tvshow.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tuts.gdk_submission1.tvshow.datamodel.TvShowGenreDataModel;

import java.util.ArrayList;

public class TvShowGenreResponse {

    @SerializedName("genres")
    @Expose
    private ArrayList<TvShowGenreDataModel> tvShowGenreDataModels = null;

    public ArrayList<TvShowGenreDataModel> getTvShowGenreDataModels() {
        return tvShowGenreDataModels;
    }
}
