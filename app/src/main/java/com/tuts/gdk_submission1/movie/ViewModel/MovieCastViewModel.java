package com.tuts.gdk_submission1.movie.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tuts.gdk_submission1.background.ApiRepository;
import com.tuts.gdk_submission1.BuildConfig;
import com.tuts.gdk_submission1.movie.response.MovieCastResponse;

public class MovieCastViewModel extends ViewModel {

    private MutableLiveData<MovieCastResponse> movieCastMutableLiveData;

    public void initializeMutable(int movieId) {
        if (movieCastMutableLiveData != null) {
            return;
        }

        ApiRepository apiRepository = ApiRepository.getInstance();
        movieCastMutableLiveData = apiRepository.getMutableMovieCast(movieId, BuildConfig.TMDB_API_Key);

    }

    public MutableLiveData<MovieCastResponse> getMovieCastMutableLiveData() {
        return movieCastMutableLiveData;
    }
}
