package com.tuts.gdk_submission1.movie;


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
import com.tuts.gdk_submission1.movie.ViewModel.MovieViewModel;
import com.tuts.gdk_submission1.R;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private RecyclerView recyclerViewMovieItem;
    private MovieAdapter movieAdapter;
    private ShimmerFrameLayout movieShimmerFrameLayout;
    private ArrayList<MovieDataModel> movieDataModelArrayList = new ArrayList<>();

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private ArrayList<MovieGenreDataModel> movieGenreDataModelArrayList = new ArrayList<>();

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewMovieItem = view.findViewById(R.id.recyclerViewMovieItem);
        movieShimmerFrameLayout = view.findViewById(R.id.movieShimmer);

        showMovies();

    }

    private void showMovies() {
        movieShimmerFrameLayout.startShimmer();
        getMutableMovies();
    }

    private void getMutableMovies() {

        MovieViewModel movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.initializeMutable();
        movieViewModel.getMovieMutableLiveData().observe(this, movieResponse -> {
            ArrayList<MovieDataModel> movieModel = movieResponse.getMovieDataModels();
            movieDataModelArrayList.addAll(movieModel);
            movieAdapter.notifyDataSetChanged();
            movieShimmerFrameLayout.stopShimmer();
            movieShimmerFrameLayout.setVisibility(View.GONE);
        });

        getMutableMovieGenres();
    }

    private void getMutableMovieGenres() {

        MovieGenreViewModel movieGenreViewModel = ViewModelProviders.of(this).get(MovieGenreViewModel.class);
        movieGenreViewModel.initializeMutable();
        movieGenreViewModel.getMovieGenreMutableLiveData().observe(this, movieGenreResponse -> {
            ArrayList<MovieGenreDataModel> movieGenreModel = movieGenreResponse.getMovieGenreDataModels();
            movieGenreDataModelArrayList.addAll(movieGenreModel);
            movieAdapter.notifyDataSetChanged();
            movieShimmerFrameLayout.stopShimmer();
            movieShimmerFrameLayout.setVisibility(View.GONE);
        });

        setMovieRecyclerView();

    }

    private void setMovieRecyclerView() {
        if (movieAdapter == null) {
            movieAdapter = new MovieAdapter(movieDataModelArrayList, getActivity(), movieClickCallback);
            int screenOrientation = getResources().getConfiguration().orientation;
            if (screenOrientation == Configuration.ORIENTATION_PORTRAIT) {
                recyclerViewMovieItem.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
            else if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                recyclerViewMovieItem.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            }
            recyclerViewMovieItem.setAdapter(movieAdapter);
        }
        else {
            movieAdapter.notifyDataSetChanged();
        }
    }

    private MovieClickCallback movieClickCallback = movieDataModel -> {
        Intent intent = new Intent(getActivity(), ContentDetailActivity.class);
        intent.putExtra(ContentDetailActivity.MOVIE_ID, movieDataModel.getMovieId());
        intent.putExtra(ContentDetailActivity.EXTRA_INFO_MOVIE, "");
        startActivity(intent);
    };

}
