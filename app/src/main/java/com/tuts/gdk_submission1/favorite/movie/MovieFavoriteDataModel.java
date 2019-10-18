package com.tuts.gdk_submission1.favorite.movie;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movies")
public class MovieFavoriteDataModel {

    @PrimaryKey
    public int movieId;

    @ColumnInfo(name = "title")
    public String movieFavTitle;

    @ColumnInfo(name = "release_date")
    public String movieFavReleaseDate;

    @ColumnInfo(name = "vote_average")
    public double movieFavVoteAverage;

    @ColumnInfo(name = "poster_path")
    public String movieFavPosterPath;

    @ColumnInfo(name = "overview")
    public String movieFavOverview;

    @ColumnInfo(name = "backdrop_path")
    public String movieFavBackdropPath;

    public int getMovieId() {
        return movieId;
    }

}
