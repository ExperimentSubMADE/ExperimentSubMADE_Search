package com.tuts.gdk_submission1.movie.datamodel;

import com.google.gson.annotations.SerializedName;

public class MovieCastDataModel {

    @SerializedName("character")
    private String movieCastCharacter = null ;

    @SerializedName("name")
    private String movieCastRealName = null ;

    @SerializedName("profile_path")
    private String movieCastProfilePath = null ;

    public String getMovieCastCharacter() {
        return movieCastCharacter;
    }

    public String getMovieCastRealName() {
        return movieCastRealName;
    }

    public String getMovieCastProfilePath() {
        return "https://image.tmdb.org/t/p/original" + movieCastProfilePath;
    }
}
