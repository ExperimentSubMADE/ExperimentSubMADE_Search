package com.tuts.gdk_submission1.tvshow.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tuts.gdk_submission1.background.ApiRepository;
import com.tuts.gdk_submission1.BuildConfig;
import com.tuts.gdk_submission1.tvshow.response.TvShowGenreResponse;

import java.util.Locale;

public class TvShowGenreViewModel extends ViewModel {

    private MutableLiveData<TvShowGenreResponse> tvShowGenreMutableLiveData;

    public void initializeMutable() {
        if (tvShowGenreMutableLiveData != null) {
            return;
        }

        ApiRepository apiRepository = ApiRepository.getInstance();
        tvShowGenreMutableLiveData = apiRepository.getMutableTvShowGenre(BuildConfig.TMDB_API_Key, "en-US");

        if (Locale.getDefault().getLanguage().equals("en")) {
            tvShowGenreMutableLiveData = apiRepository.getMutableTvShowGenre(BuildConfig.TMDB_API_Key, "en-US");
        }
        else if (Locale.getDefault().getLanguage().equals("in")) {
            tvShowGenreMutableLiveData = apiRepository.getMutableTvShowGenre(BuildConfig.TMDB_API_Key, "movieId");
        }

    }

    public MutableLiveData<TvShowGenreResponse> getTvShowGenreMutableLiveData() {
        return tvShowGenreMutableLiveData;
    }
}
