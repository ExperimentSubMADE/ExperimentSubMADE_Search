package com.tuts.gdk_submission1.movie.callback;

import com.tuts.gdk_submission1.movie.datamodel.MovieGenreDataModel;

import java.util.ArrayList;

public interface MovieGenreCallback {

    void onSuccess(ArrayList<MovieGenreDataModel> movieGenreDataModels);

    void onError();

}
