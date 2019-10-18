package com.tuts.gdk_submission1.tvshow.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tuts.gdk_submission1.tvshow.datamodel.TvShowDataModel;

import java.util.ArrayList;

public class TvShowResponse {

    @SerializedName("results")
    @Expose
    private ArrayList<TvShowDataModel> tvShowDataModels = null;

    public ArrayList<TvShowDataModel> getTvShowDataModels() {
        return tvShowDataModels;
    }

}
