package com.tuts.gdk_submission1.movie.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tuts.gdk_submission1.background.ApiRepository;
import com.tuts.gdk_submission1.BuildConfig;
import com.tuts.gdk_submission1.movie.response.MovieGenreResponse;

import java.util.Locale;

public class MovieGenreViewModel extends ViewModel {

    private MutableLiveData<MovieGenreResponse> movieGenreMutableLiveData;

    public void initializeMutable() {
        if (movieGenreMutableLiveData != null) {
            return;
        }

        ApiRepository apiRepository = ApiRepository.getInstance();

        if (Locale.getDefault().getLanguage().equals("en")) {
            movieGenreMutableLiveData = apiRepository.getMutableMovieGenre(BuildConfig.TMDB_API_Key, "en-US");
        }
        if (Locale.getDefault().getLanguage().equals("in")) {
            movieGenreMutableLiveData = apiRepository.getMutableMovieGenre(BuildConfig.TMDB_API_Key, "movieId");
        }
        else {
            movieGenreMutableLiveData = apiRepository.getMutableMovieGenre(BuildConfig.TMDB_API_Key, "en-US");
        }

    }

    public MutableLiveData<MovieGenreResponse> getMovieGenreMutableLiveData() {
        return movieGenreMutableLiveData;
    }
}
