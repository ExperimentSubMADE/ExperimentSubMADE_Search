package com.tuts.gdk_submission1.movie.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tuts.gdk_submission1.background.ApiRepository;
import com.tuts.gdk_submission1.BuildConfig;
import com.tuts.gdk_submission1.movie.datamodel.MovieDataModel;

import java.util.Locale;

public class MovieDetailViewModel extends ViewModel {

    private MutableLiveData<MovieDataModel> movieDetailMutableLiveData;

    public void initializeMutable(int movieId) {
        if (movieDetailMutableLiveData != null) {
            return;
        }

        ApiRepository apiRepository = ApiRepository.getInstance();

        if (Locale.getDefault().getLanguage().equals("en")) {
            movieDetailMutableLiveData = apiRepository.getMutableMovieDetail(movieId, BuildConfig.TMDB_API_Key, "en-US");
        }
        if (Locale.getDefault().getLanguage().equals("in")) {
            movieDetailMutableLiveData = apiRepository.getMutableMovieDetail(movieId, BuildConfig.TMDB_API_Key, "movieId");
        }
        else {
            movieDetailMutableLiveData = apiRepository.getMutableMovieDetail(movieId, BuildConfig.TMDB_API_Key, "en-US");
        }

    }

    public MutableLiveData<MovieDataModel> getMovieDetailMutableLiveData() {
        return movieDetailMutableLiveData;
    }
}
