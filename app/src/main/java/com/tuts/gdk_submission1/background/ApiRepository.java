package com.tuts.gdk_submission1.background;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.tuts.gdk_submission1.BuildConfig;
import com.tuts.gdk_submission1.movie.datamodel.MovieDataModel;
import com.tuts.gdk_submission1.movie.callback.MovieGenreCallback;
import com.tuts.gdk_submission1.movie.response.MovieCastResponse;
import com.tuts.gdk_submission1.movie.response.MovieGenreResponse;
import com.tuts.gdk_submission1.movie.response.MovieResponse;
import com.tuts.gdk_submission1.tvshow.datamodel.TvShowDataModel;
import com.tuts.gdk_submission1.tvshow.callback.TvShowGenreCallback;
import com.tuts.gdk_submission1.tvshow.response.TvShowGenreResponse;
import com.tuts.gdk_submission1.tvshow.response.TvShowResponse;
import com.tuts.gdk_submission1.tvshow.response.TvShowCastResponse;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiRepository {

    private static ApiRepository apiRepository;
    private final static String TAG = ApiRepository.class.getSimpleName();
    public final static String POSTER_URL = "https://image.tmdb.org/t/p/w342";
    public final static String BACKDROP_URL = "https://image.tmdb.org/t/p/original";
    private ContentServices contentServices;

    public ApiRepository() {
        contentServices = RetrofitServices.createService();
    }

    public static ApiRepository getInstance() {

        if (apiRepository == null) {
            apiRepository = new ApiRepository();
        }
        return apiRepository;

    }

    ////////////////////////////////////////////////////////////

    public MutableLiveData<ArrayList<MovieDataModel>> getSearchMovie(String apiKey, String language, String query) {

        MutableLiveData<ArrayList<MovieDataModel>> searchMovieLiveData = new MutableLiveData<>();
        contentServices.getSearchMovies(apiKey, language, query).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    MovieResponse movieResponse = response.body();
                    final ArrayList<MovieDataModel> listData = Objects.requireNonNull(movieResponse).movieDataModels;
                    searchMovieLiveData.postValue(listData);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

        return searchMovieLiveData;

    }

    ////////////////////////////////////////////////////////////




    public MutableLiveData<MovieResponse> getMutableMovie(String apiKey, String language) {

        MutableLiveData<MovieResponse> movieResponseMutableLiveData = new MutableLiveData<>();
        contentServices.getPopularMovies(apiKey, language).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    movieResponseMutableLiveData.postValue(response.body());
                }
            }
            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                movieResponseMutableLiveData.postValue(null);
                Log.e(TAG, t.getMessage());
            }
        });
        return movieResponseMutableLiveData;

    }

    public MutableLiveData<MovieGenreResponse> getMutableMovieGenre(String apiKey, String language) {

        MutableLiveData<MovieGenreResponse> movieGenreResponseMutableLiveData = new MutableLiveData<>();
        contentServices.getGenreMovies(apiKey, language).enqueue(new Callback<MovieGenreResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieGenreResponse> call, @NonNull Response<MovieGenreResponse> response) {
                if (response.isSuccessful()) {
                    movieGenreResponseMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieGenreResponse> call, @NonNull Throwable t) {
                movieGenreResponseMutableLiveData.postValue(null);
                Log.e(TAG, t.getMessage());
            }
        });

        return movieGenreResponseMutableLiveData;

    }

    public MutableLiveData<MovieDataModel> getMutableMovieDetail(int movieId, String apiKey, String language) {

        MutableLiveData<MovieDataModel> movieDetailMutableLiveData = new MutableLiveData<>();
        contentServices.getMovieDetail(movieId, apiKey, language).enqueue(new Callback<MovieDataModel>() {
            @Override
            public void onResponse(@NonNull Call<MovieDataModel> call, @NonNull Response<MovieDataModel> response) {
                if (response.isSuccessful()) {
                    movieDetailMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieDataModel> call, @NonNull Throwable t) {
                movieDetailMutableLiveData.postValue(null);
                Log.e(TAG, t.getMessage());
            }
        });

        return movieDetailMutableLiveData;

    }

    public MutableLiveData<MovieCastResponse> getMutableMovieCast(int movieId, String apiKey) {

        MutableLiveData<MovieCastResponse> movieCastResponseMutableLiveData = new MutableLiveData<>();
        contentServices.getMovieCast(movieId, apiKey).enqueue(new Callback<MovieCastResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieCastResponse> call, @NonNull Response<MovieCastResponse> response) {
                if (response.isSuccessful()) {
                    movieCastResponseMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieCastResponse> call, @NonNull Throwable t) {
                movieCastResponseMutableLiveData.postValue(null);
                Log.e(TAG, t.getMessage());
            }
        });

        return movieCastResponseMutableLiveData;

    }

    public void getMovieGenres(final MovieGenreCallback movieGenreCallback) {
        Call<MovieGenreResponse> movieGenreResponseCall = contentServices.getGenreMovies
                (BuildConfig.TMDB_API_Key, "en-US");

        if (Locale.getDefault().getLanguage().equals("en")) {
            movieGenreResponseCall = contentServices.getGenreMovies(BuildConfig.TMDB_API_Key, "en-US");
        }
        if (Locale.getDefault().getLanguage().equals("in")) {
            movieGenreResponseCall = contentServices.getGenreMovies(BuildConfig.TMDB_API_Key, "movieId");
        }
        movieGenreResponseCall.enqueue(new Callback<MovieGenreResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieGenreResponse> call, @NonNull Response<MovieGenreResponse> response) {
                if (response.isSuccessful()) {
                    MovieGenreResponse movieGenreResponse = response.body();
                    if (movieGenreResponse != null && movieGenreResponse.getMovieGenreDataModels() != null) {
                        movieGenreCallback.onSuccess(movieGenreResponse.getMovieGenreDataModels());
                    }
                    else {
                        movieGenreCallback.onError();
                    }
                }
                else {
                    movieGenreCallback.onError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieGenreResponse> call, @NonNull Throwable t) {
                movieGenreCallback.onError();
                Log.e(TAG, t.getMessage());
            }
        });
    }


    public MutableLiveData<TvShowResponse> getMutableTvShow (String apiKey, String language) {

        MutableLiveData<TvShowResponse> tvShowResponseMutableLiveData = new MutableLiveData<>();
        contentServices.getPopularTvShows(apiKey, language).enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowResponse> call, @NonNull Response<TvShowResponse> response) {
                if (response.isSuccessful()) {
                    tvShowResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TvShowResponse> call, @NonNull Throwable t) {
                tvShowResponseMutableLiveData.postValue(null);
                Log.e(TAG, t.getMessage());
            }
        });

        return tvShowResponseMutableLiveData;

    }

    public MutableLiveData<TvShowGenreResponse> getMutableTvShowGenre(String apiKey, String language) {

        MutableLiveData<TvShowGenreResponse> tvShowGenreResponseMutableLiveData = new MutableLiveData<>();
        contentServices.getGenreTvshows(apiKey, language).enqueue(new Callback<TvShowGenreResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowGenreResponse> call, @NonNull Response<TvShowGenreResponse> response) {
                if (response.isSuccessful()) {
                    tvShowGenreResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TvShowGenreResponse> call, @NonNull Throwable t) {
                tvShowGenreResponseMutableLiveData.postValue(null);
                Log.e(TAG, t.getMessage());
            }
        });

        return tvShowGenreResponseMutableLiveData;

    }

    public MutableLiveData<TvShowDataModel> getMutableTvShowDetail (int tvShowId, String apiKey, String language) {

        MutableLiveData<TvShowDataModel> tvShowDetailMutableLiveData = new MutableLiveData<>();
        contentServices.getTvShowDetail(tvShowId, apiKey, language).enqueue(new Callback<TvShowDataModel>() {
            @Override
            public void onResponse(@NonNull Call<TvShowDataModel> call, @NonNull Response<TvShowDataModel> response) {
                if (response.isSuccessful()) {
                    tvShowDetailMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TvShowDataModel> call, @NonNull Throwable t) {
                tvShowDetailMutableLiveData.postValue(null);
                Log.e(TAG, t.getMessage());
            }
        });

        return tvShowDetailMutableLiveData;

    }

    public MutableLiveData<TvShowCastResponse> getMutableTvShowCast(int tvShowId, String apiKey) {

        MutableLiveData<TvShowCastResponse> tvShowCastResponseMutableLiveData = new MutableLiveData<>();
        contentServices.getTvShowCast(tvShowId, apiKey).enqueue(new Callback<TvShowCastResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowCastResponse> call, @NonNull Response<TvShowCastResponse> response) {
                if (response.isSuccessful()) {
                    tvShowCastResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TvShowCastResponse> call, @NonNull Throwable t) {
                tvShowCastResponseMutableLiveData.postValue(null);
                Log.e(TAG, t.getMessage());
            }
        });

        return tvShowCastResponseMutableLiveData;

    }

    public void getTvShowGenres(final TvShowGenreCallback tvShowGenreCallback) {
        Call<TvShowGenreResponse> tvShowGenreResponseCall = contentServices.getGenreTvshows
                                    (BuildConfig.TMDB_API_Key, "en-US");

        if (Locale.getDefault().getLanguage().equals("en")) {
            tvShowGenreResponseCall = contentServices.getGenreTvshows(BuildConfig.TMDB_API_Key, "en-US");
        }
        if (Locale.getDefault().getLanguage().equals("in")) {
            tvShowGenreResponseCall = contentServices.getGenreTvshows(BuildConfig.TMDB_API_Key, "movieId");
        }
                tvShowGenreResponseCall.enqueue(new Callback<TvShowGenreResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<TvShowGenreResponse> call, @NonNull Response<TvShowGenreResponse> response) {
                        if (response.isSuccessful()) {
                            TvShowGenreResponse tvShowGenreResponse = response.body();
                            if (tvShowGenreResponse != null && tvShowGenreResponse.getTvShowGenreDataModels() != null) {
                                tvShowGenreCallback.onSuccess(tvShowGenreResponse.getTvShowGenreDataModels());
                            }
                            else {
                                tvShowGenreCallback.onError();
                            }
                        }
                        else {
                            tvShowGenreCallback.onError();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TvShowGenreResponse> call, @NonNull Throwable t) {
                        tvShowGenreCallback.onError();
                        Log.e(TAG, t.getMessage());
                    }
                });
    }

}
