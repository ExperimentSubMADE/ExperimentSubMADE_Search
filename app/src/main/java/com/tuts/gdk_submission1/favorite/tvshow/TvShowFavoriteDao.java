package com.tuts.gdk_submission1.favorite.tvshow;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TvShowFavoriteDao {

    @Query("SELECT * FROM tvshows")
    LiveData<List<TvShowFavoriteDataModel>> getAllTvShowFavorite();

    @Query("SELECT * FROM tvshows WHERE id = :id")
    TvShowFavoriteDataModel getFavoriteTvShowById(int id);

    @Query("DELETE FROM tvshows WHERE id = :id")
    void deleteFavoriteTvShowById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTvShowToFavorite(TvShowFavoriteDataModel tvShowFavoriteModel);

}
