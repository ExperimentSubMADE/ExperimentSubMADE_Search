package com.tuts.gdk_submission1.tvshow;


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
import com.tuts.gdk_submission1.tvshow.ViewModel.TvShowViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    private RecyclerView recyclerViewTvShowItem;
    private TvShowAdapter tvShowAdapter;
    private ShimmerFrameLayout tvShowShimmerFrameLayout;

    private ArrayList<TvShowDataModel> tvShowDataModelArrayList = new ArrayList<>();

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private ArrayList<TvShowGenreDataModel> tvShowGenreDataModelArrayList = new ArrayList<>();

    public TvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewTvShowItem = view.findViewById(R.id.recylerviewTvShowItem);
        tvShowShimmerFrameLayout = view.findViewById(R.id.tvShowShimmerFrameLayout);

        showTvShows();
    }

    private void showTvShows() {
        tvShowShimmerFrameLayout.startShimmer();
        getMutableTvShows();
    }

    private void getMutableTvShows() {

        TvShowViewModel tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        tvShowViewModel.initializeMutable();
        tvShowViewModel.getTvShowMutableLiveData().observe(this, tvShowResponse -> {
            ArrayList<TvShowDataModel> tvShowModel = tvShowResponse.getTvShowDataModels();
            tvShowDataModelArrayList.addAll(tvShowModel);
            tvShowAdapter.notifyDataSetChanged();
            tvShowShimmerFrameLayout.stopShimmer();
            tvShowShimmerFrameLayout.setVisibility(View.GONE);
        });

        getMutableTvShowGenres();

    }

    private void getMutableTvShowGenres() {

        TvShowGenreViewModel tvShowGenreViewModel = ViewModelProviders.of(this).get(TvShowGenreViewModel.class);
        tvShowGenreViewModel.initializeMutable();
        tvShowGenreViewModel.getTvShowGenreMutableLiveData().observe(this, tvShowGenreResponse -> {
            ArrayList<TvShowGenreDataModel> tvShowGenreModel = tvShowGenreResponse.getTvShowGenreDataModels();
            tvShowGenreDataModelArrayList.addAll(tvShowGenreModel);
            tvShowAdapter.notifyDataSetChanged();
            tvShowShimmerFrameLayout.stopShimmer();
            tvShowShimmerFrameLayout.setVisibility(View.GONE);

        });

        setTvShowRecyclerView();

    }

    private void setTvShowRecyclerView() {
        if (tvShowAdapter == null) {
            tvShowAdapter = new TvShowAdapter(tvShowDataModelArrayList, getActivity(), tvShowClickCallback);
            int screenOrientation = getResources().getConfiguration().orientation;
            if (screenOrientation == Configuration.ORIENTATION_PORTRAIT) {
                recyclerViewTvShowItem.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
            else if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                recyclerViewTvShowItem.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            }
            recyclerViewTvShowItem.setAdapter(tvShowAdapter);
        }
        else {
            tvShowAdapter.notifyDataSetChanged();
        }
    }


    private TvShowClickCallback tvShowClickCallback = tvShowDataModel -> {
        Intent intent = new Intent(getActivity(), ContentDetailActivity.class);
        intent.putExtra(ContentDetailActivity.TVSHOW_ID, tvShowDataModel.getTvShowId());
        intent.putExtra(ContentDetailActivity.EXTRA_INFO_TVSHOW, "");
        startActivity(intent);
    };

}
