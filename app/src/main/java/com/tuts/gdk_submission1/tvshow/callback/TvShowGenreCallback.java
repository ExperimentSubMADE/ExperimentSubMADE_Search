package com.tuts.gdk_submission1.tvshow.callback;

import com.tuts.gdk_submission1.tvshow.datamodel.TvShowGenreDataModel;

import java.util.ArrayList;

public interface TvShowGenreCallback {

    void onSuccess(ArrayList<TvShowGenreDataModel> tvShowGenreDataModels);

    void onError();

}
