package com.tuts.gdk_submission1.movie.datamodel;

import com.google.gson.annotations.SerializedName;

public class MovieDataModel {

    @SerializedName("id")
    private int movieId;

    @SerializedName("title")
    private String title;

    @SerializedName("release_date")
    private String release_date;

    @SerializedName("vote_average")
    private double vote_average;

    @SerializedName("poster_path")
    private String poster_path;

    @SerializedName("backdrop_path")
    private String backdrop_path;

    @SerializedName("overview")
    private String overview;

    /*
    @SerializedName("genre_ids")
    private ArrayList<Integer> movieGenreIds;
     */

    /*
    @SerializedName("genres")
    private ArrayList<MovieGenreDataModel> movieGenres = null;

    @SerializedName("runtime")
    private double movieDetailRuntime;
     */

    public int getMovieId() {
        return movieId;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    /*
    public ArrayList<MovieGenreDataModel> getMovieGenres() {
        return movieGenres;
    }

    public double getMovieDetailRuntime() {
        return movieDetailRuntime;
    }
     */

    public MovieDataModel(Integer movieId, Double vote_average, String title, String poster_path, String backdrop_path, String overview, String release_date) {
        this.movieId = movieId;
        this.vote_average = vote_average;
        this.title = title;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.release_date = release_date;
    }

}
