package com.tuts.gdk_submission1.tvshow.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tuts.gdk_submission1.background.ApiRepository;
import com.tuts.gdk_submission1.BuildConfig;
import com.tuts.gdk_submission1.tvshow.response.TvShowCastResponse;

public class TvShowCastViewModel extends ViewModel {

    private MutableLiveData<TvShowCastResponse> tvShowCastMutableLiveData;

    public void initializeMutable(int tvShowId) {
        if (tvShowCastMutableLiveData != null) {
            return;
        }

        ApiRepository apiRepository = ApiRepository.getInstance();
        tvShowCastMutableLiveData = apiRepository.getMutableTvShowCast(tvShowId, BuildConfig.TMDB_API_Key);

    }

    public MutableLiveData<TvShowCastResponse> getTvShowCastMutableLiveData() {
        return tvShowCastMutableLiveData;
    }
}
