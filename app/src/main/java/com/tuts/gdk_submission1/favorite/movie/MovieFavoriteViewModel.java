package com.tuts.gdk_submission1.favorite.movie;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tuts.gdk_submission1.favorite.ContentDatabase;

import java.util.List;

public class MovieFavoriteViewModel extends AndroidViewModel {

    private LiveData<List<MovieFavoriteDataModel>> movieFavoriteLiveData;
    private ContentDatabase contentDatabase;


    public MovieFavoriteViewModel(@NonNull Application application) {
        super(application);
        contentDatabase = ContentDatabase.getInstance(application);

    }

    LiveData<List<MovieFavoriteDataModel>> getMovieFavoriteLiveData() {
        return movieFavoriteLiveData;
    }

    void loadFavoriteMovies() {
        movieFavoriteLiveData = contentDatabase.movieFavoriteDao().getAllMovieFavorite();
    }

}
