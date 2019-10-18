package com.tuts.gdk_submission1.tvshow.datamodel;

import com.google.gson.annotations.SerializedName;

public class TvShowCastDataModel {

    @SerializedName("character")
    private String tvShowCastCharacter = null;

    @SerializedName("name")
    private String tvShowCastRealName = null;

    @SerializedName("profile_path")
    private String tvShowCastProfilePath = null;

    public String getTvShowCastCharacter() {
        return tvShowCastCharacter;
    }

    public String getTvShowCastRealName() {
        return tvShowCastRealName;
    }

    public String getTvShowCastProfilePath() {
        return "https://image.tmdb.org/t/p/original" + tvShowCastProfilePath;
    }
}
