package com.tuts.gdk_submission1.favorite.tvshow;


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
import com.tuts.gdk_submission1.R;
import com.tuts.gdk_submission1.tvshow.adapter.TvShowAdapter;
import com.tuts.gdk_submission1.tvshow.callback.TvShowClickCallback;
import com.tuts.gdk_submission1.tvshow.datamodel.TvShowDataModel;
import com.tuts.gdk_submission1.tvshow.datamodel.TvShowGenreDataModel;
import com.tuts.gdk_submission1.tvshow.ViewModel.TvShowGenreViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFavoriteFragment extends Fragment {

    private TvShowFavoriteViewModel tvShowFavoriteViewModel;
    private RecyclerView recyclerViewTvShowFavoriteItem;
    private ShimmerFrameLayout tvShowFavShimmer;
    private TvShowAdapter tvShowAdapter;
    private ArrayList<TvShowDataModel> tvShowDataList = new ArrayList<>();

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private ArrayList<TvShowGenreDataModel> tvShowGenreDataModelArrayList = new ArrayList<>();

    public TvShowFavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewTvShowFavoriteItem = view.findViewById(R.id.recyclerViewTvShowFavoriteItem);
        tvShowFavShimmer = view.findViewById(R.id.tvShowFavShimmer);
        tvShowFavoriteViewModel = ViewModelProviders.of(this).get(TvShowFavoriteViewModel.class);

        tvShowFavShimmer.startShimmer();
        getMutableTvShowGenres();
        showTvShowsFavorite();

    }

    private void getFavoriteTvShows() {

        tvShowFavoriteViewModel.getTvShowFavoriteLiveData().observe(this, tvShowFavoriteDataModels -> {

            assert tvShowFavoriteDataModels != null;
            List<TvShowDataModel> favTvShows = new ArrayList<>();
            for (TvShowFavoriteDataModel tvShowFavModel : tvShowFavoriteDataModels) {
                TvShowDataModel tvShowModel = new TvShowDataModel(
                        tvShowFavModel.id,
                        tvShowFavModel.tvShowFavTitle,
                        tvShowFavModel.tvShowFavVoteAverage,
                        tvShowFavModel.tvShowFavReleaseDate,
                        tvShowFavModel.tvShowFavPosterPath,
                        tvShowFavModel.tvShowFavBackdropPath,
                        tvShowFavModel.tvShowFavOverview
                );

                favTvShows.add(tvShowModel);
            }

            tvShowAdapter.setListTvShow(new ArrayList<>(favTvShows));
            tvShowFavShimmer.stopShimmer();
            tvShowFavShimmer.setVisibility(View.GONE);

        });

    }

    private void getMutableTvShowGenres() {

        TvShowGenreViewModel tvShowGenreViewModel = ViewModelProviders.of(this).get(TvShowGenreViewModel.class);
        tvShowGenreViewModel.initializeMutable();
        tvShowGenreViewModel.getTvShowGenreMutableLiveData().observe(this, tvShowGenreResponse -> {
            ArrayList<TvShowGenreDataModel> tvShowGenreModel = tvShowGenreResponse.getTvShowGenreDataModels();
            tvShowGenreDataModelArrayList.addAll(tvShowGenreModel);
            tvShowAdapter.notifyDataSetChanged();
            tvShowFavShimmer.stopShimmer();
            tvShowFavShimmer.setVisibility(View.GONE);

        });

    }

    private void showTvShowsFavorite() {
        tvShowAdapter = new TvShowAdapter(tvShowDataList, getActivity(), tvShowClickCallback);

        int screenOrientation = getResources().getConfiguration().orientation;
        if (screenOrientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerViewTvShowFavoriteItem.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        else if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerViewTvShowFavoriteItem.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }

        recyclerViewTvShowFavoriteItem.setAdapter(tvShowAdapter);
    }

    private TvShowClickCallback tvShowClickCallback = tvShowDataModel -> {
        Intent intent = new Intent(getActivity(), ContentDetailActivity.class);
        intent.putExtra(ContentDetailActivity.TVSHOW_ID, tvShowDataModel.getTvShowId());
        intent.putExtra(ContentDetailActivity.EXTRA_INFO_TVSHOW, "");
        startActivity(intent);
    };

    @Override
    public void onResume() {
        tvShowFavoriteViewModel.loadFavoriteTvShows();
        getFavoriteTvShows();
        super.onResume();
    }
}
