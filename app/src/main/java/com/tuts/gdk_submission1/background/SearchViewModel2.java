package com.tuts.gdk_submission1.background;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tuts.gdk_submission1.BuildConfig;
import com.tuts.gdk_submission1.background.ApiRepository;
import com.tuts.gdk_submission1.background.ContentServices;
import com.tuts.gdk_submission1.movie.datamodel.MovieDataModel;
import com.tuts.gdk_submission1.movie.response.MovieResponse;

import java.util.ArrayList;
import java.util.Objects;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchViewModel2 extends ViewModel {

    private final MutableLiveData<ArrayList<MovieDataModel>> listMovies = new MutableLiveData<>();
    private static SearchViewModel2 searchViewModel2;
    private ContentServices  contentServices;

    public SearchViewModel2() {
        contentServices = RetrofitServices.createService();
    }

    public static SearchViewModel2 getInstance() {
        if (searchViewModel2 == null) {
            searchViewModel2 = new SearchViewModel2();
        }
        return searchViewModel2;
    }

    public void getSearch(String query) {
        Call<MovieResponse> call = contentServices.getSearchMovies(BuildConfig.TMDB_API_Key, "en-US", query);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                final ArrayList<MovieDataModel> listData = Objects.requireNonNull(movieResponse).movieDataModels;
                listMovies.postValue(listData);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<ArrayList<MovieDataModel>> getListMovies() {
        return listMovies;
    }

}

