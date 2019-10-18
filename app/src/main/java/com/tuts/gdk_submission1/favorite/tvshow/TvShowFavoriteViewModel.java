package com.tuts.gdk_submission1.favorite.tvshow;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tuts.gdk_submission1.favorite.ContentDatabase;

import java.util.List;

public class TvShowFavoriteViewModel extends AndroidViewModel {

    private LiveData<List<TvShowFavoriteDataModel>> tvShowFavoriteLiveData;
    private ContentDatabase contentDatabase;

    public TvShowFavoriteViewModel(@NonNull Application application) {
        super(application);
        contentDatabase = ContentDatabase.getInstance(application);
    }

    LiveData<List<TvShowFavoriteDataModel>> getTvShowFavoriteLiveData() {
        return tvShowFavoriteLiveData;
    }

    void loadFavoriteTvShows() {
        tvShowFavoriteLiveData = contentDatabase.tvShowFavoriteDao().getAllTvShowFavorite();
    }

}
