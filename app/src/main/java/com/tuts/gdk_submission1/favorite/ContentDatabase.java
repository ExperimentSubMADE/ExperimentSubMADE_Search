package com.tuts.gdk_submission1.favorite;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.tuts.gdk_submission1.favorite.movie.MovieFavoriteDao;
import com.tuts.gdk_submission1.favorite.movie.MovieFavoriteDataModel;
import com.tuts.gdk_submission1.favorite.tvshow.TvShowFavoriteDao;
import com.tuts.gdk_submission1.favorite.tvshow.TvShowFavoriteDataModel;

@Database(entities = {MovieFavoriteDataModel.class, TvShowFavoriteDataModel.class}, exportSchema = false, version = 7)
public abstract class ContentDatabase extends RoomDatabase {

    public abstract MovieFavoriteDao movieFavoriteDao();
    public abstract TvShowFavoriteDao tvShowFavoriteDao();

    private static final String DATABASE_NAME = "contentdb";
    private static ContentDatabase instance;

    public static synchronized ContentDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), ContentDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
