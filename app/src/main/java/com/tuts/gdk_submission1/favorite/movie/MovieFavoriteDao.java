package com.tuts.gdk_submission1.favorite.movie;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tuts.gdk_submission1.movie.datamodel.MovieDataModel;

import java.util.List;

@Dao
public interface MovieFavoriteDao {

    @Query("SELECT * FROM movies")
    LiveData<List<MovieFavoriteDataModel>> getAllMovieFavorite();

    @Query("SELECT * FROM movies WHERE movieId = :id")
    MovieFavoriteDataModel getFavoriteMovieById(int id);

    @Query("DELETE FROM movies WHERE movieId = :id")
    void deleteFavoriteMovieById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovieToFavorite(MovieFavoriteDataModel movieFavoriteModel);

    @Query("SELECT * FROM movies")
    List<MovieDataModel> getAllMoviesToWidget();

    @Query("SELECT * FROM movies")
    Cursor getAllFavoriteMoviesByCursor();

}
