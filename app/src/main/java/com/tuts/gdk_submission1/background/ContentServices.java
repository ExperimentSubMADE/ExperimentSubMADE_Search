package com.tuts.gdk_submission1.background;


import com.tuts.gdk_submission1.movie.datamodel.MovieDataModel;
import com.tuts.gdk_submission1.movie.response.MovieCastResponse;
import com.tuts.gdk_submission1.movie.response.MovieGenreResponse;
import com.tuts.gdk_submission1.movie.response.MovieResponse;
import com.tuts.gdk_submission1.tvshow.datamodel.TvShowDataModel;
import com.tuts.gdk_submission1.tvshow.response.TvShowGenreResponse;
import com.tuts.gdk_submission1.tvshow.response.TvShowResponse;
import com.tuts.gdk_submission1.tvshow.response.TvShowCastResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ContentServices {

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language);

    @GET("genre/movie/list")
    Call<MovieGenreResponse> getGenreMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language);

    @GET("movie/{movie_id}")
    Call<MovieDataModel> getMovieDetail(
            @Path("movie_id") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language);

    @GET("movie/{movie_id}/credits")
    Call<MovieCastResponse> getMovieCast(
            @Path("movie_id") int id,
            @Query("api_key") String apiKey);

    @GET("tv/popular")
    Call<TvShowResponse> getPopularTvShows(
            @Query("api_key") String apiKey,
            @Query("language") String language);

    @GET("genre/tv/list")
    Call<TvShowGenreResponse> getGenreTvshows(
            @Query("api_key") String apiKey,
            @Query("language") String language);

    @GET("tv/{tv_id}")
    Call<TvShowDataModel> getTvShowDetail(
            @Path("tv_id") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language);

    @GET("tv/{tv_id}/credits")
    Call<TvShowCastResponse> getTvShowCast(
            @Path("tv_id") int id,
            @Query("api_key") String apiKey);

    ///////
    @GET("search/movie")
    Call<MovieResponse> getSearchMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("query") String query);

}
