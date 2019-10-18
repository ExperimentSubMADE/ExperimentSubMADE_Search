package com.tuts.gdk_submission1.tvshow.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tuts.gdk_submission1.background.ApiRepository;
import com.tuts.gdk_submission1.BuildConfig;
import com.tuts.gdk_submission1.tvshow.datamodel.TvShowDataModel;

import java.util.Locale;

public class TvShowDetailViewModel extends ViewModel {

    private MutableLiveData<TvShowDataModel> tvShowDetailMutableLiveData;

    public void initializeMutable(int tvShowId) {
        if (tvShowDetailMutableLiveData != null) {
            return;
        }

        ApiRepository apiRepository = ApiRepository.getInstance();
        tvShowDetailMutableLiveData = apiRepository.getMutableTvShowDetail(tvShowId, BuildConfig.TMDB_API_Key, "en-US");

        if (Locale.getDefault().getLanguage().equals("en")) {
            tvShowDetailMutableLiveData = apiRepository.getMutableTvShowDetail(tvShowId, BuildConfig.TMDB_API_Key, "en-US");
        }
        else if (Locale.getDefault().getLanguage().equals("in")) {
            tvShowDetailMutableLiveData = apiRepository.getMutableTvShowDetail(tvShowId, BuildConfig.TMDB_API_Key, "movieId");
        }

    }

    public MutableLiveData<TvShowDataModel> getTvShowDetailMutableLiveData() {
        return tvShowDetailMutableLiveData;
    }
}
