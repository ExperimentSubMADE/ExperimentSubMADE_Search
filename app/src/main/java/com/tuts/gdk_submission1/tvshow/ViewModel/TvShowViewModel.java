package com.tuts.gdk_submission1.tvshow.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tuts.gdk_submission1.background.ApiRepository;
import com.tuts.gdk_submission1.BuildConfig;
import com.tuts.gdk_submission1.tvshow.response.TvShowResponse;

import java.util.Locale;

public class TvShowViewModel extends ViewModel {

    private MutableLiveData<TvShowResponse> tvShowMutableLiveData;

    public void initializeMutable() {
        if (tvShowMutableLiveData != null) {
            return;
        }

        ApiRepository apiRepository = ApiRepository.getInstance();

        tvShowMutableLiveData = apiRepository.getMutableTvShow(BuildConfig.TMDB_API_Key, "en-US");

        if (Locale.getDefault().getLanguage().equals("en")) {
            tvShowMutableLiveData = apiRepository.getMutableTvShow(BuildConfig.TMDB_API_Key, "en-US");
        }
        else if (Locale.getDefault().getLanguage().equals("in")) {
            tvShowMutableLiveData = apiRepository.getMutableTvShow(BuildConfig.TMDB_API_Key, "movieId");
        }

    }

    public MutableLiveData<TvShowResponse> getTvShowMutableLiveData() {
        return tvShowMutableLiveData;
    }
}
