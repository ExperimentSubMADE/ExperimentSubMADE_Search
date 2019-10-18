package com.tuts.gdk_submission1.movie.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tuts.gdk_submission1.background.ApiRepository;
import com.tuts.gdk_submission1.BuildConfig;
import com.tuts.gdk_submission1.movie.response.MovieResponse;

import java.util.Locale;


public class MovieViewModel extends ViewModel {

    private MutableLiveData<MovieResponse> movieMutableLiveData;

    public void initializeMutable() {
        if (movieMutableLiveData != null) {
            return;
        }

        ApiRepository apiRepository = ApiRepository.getInstance();

        if (Locale.getDefault().getLanguage().equals("en")) {
            movieMutableLiveData = apiRepository.getMutableMovie(BuildConfig.TMDB_API_Key, "en-US");
        }
        if (Locale.getDefault().getLanguage().equals("in")) {
            movieMutableLiveData = apiRepository.getMutableMovie(BuildConfig.TMDB_API_Key, "movieId");
        }
        else {
            movieMutableLiveData = apiRepository.getMutableMovie(BuildConfig.TMDB_API_Key, "en-US");
        }

    }

    public MutableLiveData<MovieResponse> getMovieMutableLiveData() {
        return movieMutableLiveData;
    }
}
