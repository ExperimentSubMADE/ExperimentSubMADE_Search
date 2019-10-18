package com.tuts.gdk_submission1.favorite.tvshow;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tvshows")
public class TvShowFavoriteDataModel {

    @PrimaryKey
    public int id;

    @ColumnInfo(name = "title")
    public String tvShowFavTitle;

    @ColumnInfo(name = "first_air_date")
    public String tvShowFavReleaseDate;

    @ColumnInfo(name = "vote_average")
    public double tvShowFavVoteAverage;

    @ColumnInfo(name = "poster_path")
    public String tvShowFavPosterPath;

    @ColumnInfo(name = "overview")
    public String tvShowFavOverview;

    @ColumnInfo(name = "backdrop_path")
    public String tvShowFavBackdropPath;

    public int getId() {
        return id;
    }
}
