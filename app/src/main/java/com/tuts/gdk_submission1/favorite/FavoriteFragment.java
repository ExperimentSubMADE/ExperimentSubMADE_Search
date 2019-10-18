package com.tuts.gdk_submission1.favorite;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.tuts.gdk_submission1.favorite.movie.MovieFavoriteFragment;
import com.tuts.gdk_submission1.favorite.tvshow.TvShowFavoriteFragment;
import com.tuts.gdk_submission1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager viewPagerFavoriteNavigation = view.findViewById(R.id.viewpagerFavoriteNavigation);
        TabLayout tabLayoutFavoriteNavigation = view.findViewById(R.id.tablayoutFavoriteNavigation);

        FavoriteViewpagerAdapter favoriteViewpagerAdapter = new FavoriteViewpagerAdapter
                (getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        favoriteViewpagerAdapter.addFragment(new MovieFavoriteFragment(), getResources().getString(R.string.tablayout_title_movies));
        favoriteViewpagerAdapter.addFragment(new TvShowFavoriteFragment(), getResources().getString(R.string.tablayout_title_tvshow));

        viewPagerFavoriteNavigation.setAdapter(favoriteViewpagerAdapter);
        tabLayoutFavoriteNavigation.setupWithViewPager(viewPagerFavoriteNavigation);

    }

}
