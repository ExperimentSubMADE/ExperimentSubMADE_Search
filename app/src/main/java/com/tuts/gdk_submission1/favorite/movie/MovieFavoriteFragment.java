package com.tuts.gdk_submission1.favorite.movie;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.tuts.gdk_submission1.ContentDetailActivity;
import com.tuts.gdk_submission1.movie.adapter.MovieAdapter;
import com.tuts.gdk_submission1.movie.callback.MovieClickCallback;
import com.tuts.gdk_submission1.movie.datamodel.MovieDataModel;
import com.tuts.gdk_submission1.movie.datamodel.MovieGenreDataModel;
import com.tuts.gdk_submission1.movie.ViewModel.MovieGenreViewModel;
import com.tuts.gdk_submission1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavoriteFragment extends Fragment {

    private MovieFavoriteViewModel movieFavoriteViewModel;
    private RecyclerView recyclerViewMovieFavoriteItem;
    private ShimmerFrameLayout movieFavShimmer;
    private MovieAdapter movieAdapter;
    private ArrayList<MovieDataModel> movieDataList = new ArrayList<>();

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private ArrayList<MovieGenreDataModel> movieGenreDataModelArrayList = new ArrayList<>();

    public MovieFavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewMovieFavoriteItem = view.findViewById(R.id.recyclerViewMovieFavoriteItem);
        movieFavShimmer = view.findViewById(R.id.movieFavShimmer);
        movieFavoriteViewModel = ViewModelProviders.of(this).get(MovieFavoriteViewModel.class);

        movieFavShimmer.startShimmer();
        getMutableMovieGenres();
        showMoviesFavorite();

    }

    private void getFavoriteMovies() {

         movieFavoriteViewModel.getMovieFavoriteLiveData().observe(this, movieFavoriteDataModels -> {

            assert movieFavoriteDataModels != null;
            List<MovieDataModel> favMovies = new ArrayList<>();
            for (MovieFavoriteDataModel movieFavModel : movieFavoriteDataModels) {
                MovieDataModel movieModel = new MovieDataModel(
                        movieFavModel.movieId,
                        movieFavModel.movieFavVoteAverage,
                        movieFavModel.movieFavTitle,
                        movieFavModel.movieFavPosterPath,
                        movieFavModel.movieFavBackdropPath,
                        movieFavModel.movieFavOverview,
                        movieFavModel.movieFavReleaseDate
                );

                favMovies.add(movieModel);
            }

            movieAdapter.setListMovie(new ArrayList<>(favMovies));
            movieFavShimmer.stopShimmer();
            movieFavShimmer.setVisibility(View.GONE);

        });

    }

    private void getMutableMovieGenres() {

        MovieGenreViewModel movieGenreViewModel = ViewModelProviders.of(this).get(MovieGenreViewModel.class);
        movieGenreViewModel.initializeMutable();
        movieGenreViewModel.getMovieGenreMutableLiveData().observe(this, movieGenreResponse -> {
            ArrayList<MovieGenreDataModel> movieGenreModel = movieGenreResponse.getMovieGenreDataModels();
            movieGenreDataModelArrayList.addAll(movieGenreModel);
            movieAdapter.notifyDataSetChanged();
            movieFavShimmer.stopShimmer();
            movieFavShimmer.setVisibility(View.GONE);
        });

    }

    private void showMoviesFavorite() {

        movieAdapter = new MovieAdapter(movieDataList, getActivity(), movieClickCallback);

        int screenOrientation = getResources().getConfiguration().orientation;
        if (screenOrientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerViewMovieFavoriteItem.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        else if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerViewMovieFavoriteItem.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }

        recyclerViewMovieFavoriteItem.setAdapter(movieAdapter);

    }

    private MovieClickCallback movieClickCallback = movieDataModel -> {
        Intent intent = new Intent(getActivity(), ContentDetailActivity.class);
        intent.putExtra(ContentDetailActivity.MOVIE_ID, movieDataModel.getMovieId());
        intent.putExtra(ContentDetailActivity.EXTRA_INFO_MOVIE, "");
        startActivity(intent);
    };

    @Override
    public void onResume() {
        movieFavoriteViewModel.loadFavoriteMovies();
        getFavoriteMovies();
        super.onResume();
    }
}
