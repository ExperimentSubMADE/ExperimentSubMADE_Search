package com.tuts.gdk_submission1.tvshow.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tuts.gdk_submission1.tvshow.datamodel.TvShowCastDataModel;

import java.util.ArrayList;

public class TvShowCastResponse {

    @SerializedName("cast")
    @Expose
    private ArrayList<TvShowCastDataModel> tvShowCastDataModels = null;

    public ArrayList<TvShowCastDataModel> getTvShowCastDataModels() {
        return tvShowCastDataModels;
    }
}
