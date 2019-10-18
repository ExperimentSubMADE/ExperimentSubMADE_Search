package com.tuts.gdk_submission1.tvshow.datamodel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TvShowDataModel {

    @SerializedName("movieId")
    private Integer tvShowId;

    @SerializedName("name")
    private String tvShowName;

    @SerializedName("vote_average")
    private Double tvShowVoteAverage;

    @SerializedName("first_air_date")
    private String tvShowReleaseDate;

    @SerializedName("poster_path")
    private String tvShowPosterPath;

    @SerializedName("genre_ids")
    private ArrayList<Integer> tvShowGenreIds = null;

    @SerializedName("backdrop_path")
    private String tvShowBackdropPath;

    @SerializedName("overview")
    private String tvShowOverview;

    @SerializedName("genres")
    private ArrayList<TvShowGenreDataModel> tvShowGenres = null;

    @SerializedName("number_of_episodes")
    private Integer tvshowRuntime = null;

    public Integer getTvShowId() {
        return tvShowId;
    }

    public String getTvShowName() {
        return tvShowName;
    }

    public Double getTvShowVoteAverage() {
        return tvShowVoteAverage;
    }

    public String getTvShowReleaseDate() {
        return tvShowReleaseDate;
    }

    public String getTvShowPosterPath() {
        return tvShowPosterPath;
    }

    public String getTvShowBackdropPath() {
        return tvShowBackdropPath;
    }

    public String getTvShowOverview() {
        return tvShowOverview;
    }

    public ArrayList<TvShowGenreDataModel> getTvShowGenres() {
        return tvShowGenres;
    }

    public int getTvshowRuntime() {
        return tvshowRuntime;
    }

    public TvShowDataModel(Integer tvShowId, String tvShowName, Double tvShowVoteAverage, String tvShowReleaseDate, String tvShowPosterPath, String tvShowBackdropPath, String tvShowOverview) {
        this.tvShowId = tvShowId;
        this.tvShowName = tvShowName;
        this.tvShowVoteAverage = tvShowVoteAverage;
        this.tvShowReleaseDate = tvShowReleaseDate;
        this.tvShowPosterPath = tvShowPosterPath;
        this.tvShowBackdropPath = tvShowBackdropPath;
        this.tvShowOverview = tvShowOverview;
    }
}
