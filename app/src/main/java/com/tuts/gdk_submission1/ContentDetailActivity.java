package com.tuts.gdk_submission1;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tuts.gdk_submission1.background.ApiRepository;
import com.tuts.gdk_submission1.favorite.ContentDatabase;
import com.tuts.gdk_submission1.favorite.movie.MovieFavoriteDataModel;
import com.tuts.gdk_submission1.favorite.tvshow.TvShowFavoriteDataModel;
import com.tuts.gdk_submission1.movie.adapter.MovieCastAdapter;
import com.tuts.gdk_submission1.movie.datamodel.MovieCastDataModel;
import com.tuts.gdk_submission1.movie.ViewModel.MovieCastViewModel;
import com.tuts.gdk_submission1.movie.ViewModel.MovieDetailViewModel;
import com.tuts.gdk_submission1.tvshow.adapter.TvShowCastAdapter;
import com.tuts.gdk_submission1.tvshow.callback.TvShowGenreCallback;
import com.tuts.gdk_submission1.tvshow.datamodel.TvShowCastDataModel;
import com.tuts.gdk_submission1.tvshow.datamodel.TvShowDataModel;
import com.tuts.gdk_submission1.tvshow.datamodel.TvShowGenreDataModel;
import com.tuts.gdk_submission1.tvshow.ViewModel.TvShowCastViewModel;
import com.tuts.gdk_submission1.tvshow.ViewModel.TvShowDetailViewModel;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class ContentDetailActivity extends AppCompatActivity {

    public static final String EXTRA_INFO_MOVIE = "extra_info_movie";
    public static final String EXTRA_INFO_TVSHOW = "extra_info_tvshow";
    public static final String MOVIE_ID = "movie_id";
    public static final String TVSHOW_ID = "tv_id";

    private ImageView imageContentDetailBackphoto, imageContentDetailPhoto;
    private TextView txtContentDetailTitle, txtContentDetailReleaseDate, txtContentDetailGenre,
            txtContentDetailRuntime, txtContentDetailRating, txtContentDetailOverview;
    private FloatingActionButton fabContentFavorite;

    private AppBarLayout contentAppBarLayout;
    private CardView cardviewContentPhoto;
    private ConstraintLayout layoutContent1;
    private NestedScrollView contentDetailBodyLayout;
    private CollapsingToolbarLayout contentCollapsingToolbarLayout;
    private Toolbar contentToolbar;
    private ShimmerFrameLayout contentDetailShimmerFrameLayout;
    private RecyclerView recyclerViewContentDetailCast;

    private ApiRepository apiRepository;
    MovieDetailViewModel movieDetailViewModel;
    TvShowDetailViewModel tvShowDetailViewModel;
    private int movieId;
    private int tvShowId;

    private ArrayList<MovieCastDataModel> movieCastDataModelArrayList = new ArrayList<>();
    private ArrayList<TvShowCastDataModel> tvShowCastDataModelArrayList = new ArrayList<>();
    private MovieCastAdapter movieCastAdapter;
    private TvShowCastAdapter tvShowCastAdapter;

    private Bundle bundleMovie, bundleTvShow;

    private ContentDatabase contentDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_detail);

        movieId = getIntent().getIntExtra(MOVIE_ID, movieId);
        tvShowId = getIntent().getIntExtra(TVSHOW_ID, tvShowId);
        bundleMovie = getIntent().getExtras();
        bundleTvShow = getIntent().getExtras();

        apiRepository = ApiRepository.getInstance();

        initializeContentDetailView();

        initializeDatabase();
        checkFabClicked();

        setupMovieDetailToolbar();

        if (bundleMovie != null && bundleMovie.containsKey(EXTRA_INFO_MOVIE)) {
            contentDetailShimmerFrameLayout.startShimmer();
            setMutableMovieDetailData();
            setMutableMovieCasts();
        }
        if (bundleTvShow != null && bundleTvShow.containsKey(EXTRA_INFO_TVSHOW)) {
            contentDetailShimmerFrameLayout.startShimmer();
            setMutableTvShowDetailData();
            setMutableTvShowCasts();
        }

        contentAppBarLayout.addOnOffsetChangedListener((appBarLayout, position) -> {

            if (Math.abs(position) - appBarLayout.getTotalScrollRange() == 0) {
                layoutContent1.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                layoutContent1.setElevation(0);
                layoutContent1.setVisibility(View.INVISIBLE);
                cardviewContentPhoto.setVisibility(View.INVISIBLE);
                if (bundleMovie != null && bundleMovie.containsKey(EXTRA_INFO_MOVIE)) {
                    setMutableMovieDetailData();
                }
                if (bundleTvShow != null && bundleTvShow.containsKey(EXTRA_INFO_TVSHOW)) {
                    setMutableTvShowDetailData();
                }

            }
            else {
                layoutContent1.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                contentCollapsingToolbarLayout.setTitle("");
                layoutContent1.setElevation(2);
                layoutContent1.setVisibility(View.VISIBLE);
                cardviewContentPhoto.setVisibility(View.VISIBLE);
            }

        });

    }

    private void initializeContentDetailView() {
        imageContentDetailBackphoto = findViewById(R.id.imageContentDetailBackPhoto);
        imageContentDetailPhoto = findViewById(R.id.imageContentDetailPhoto);
        txtContentDetailTitle = findViewById(R.id.txtContentDetailTitle);
        txtContentDetailReleaseDate = findViewById(R.id.txtContentDetailReleaseDate);
        txtContentDetailGenre = findViewById(R.id.txtContentDetailGenre);
        txtContentDetailRuntime = findViewById(R.id.txtContentDetailRuntime);
        txtContentDetailRating = findViewById(R.id.txtContentDetailRating);
        txtContentDetailOverview = findViewById(R.id.txtContentDetailOverview);
        fabContentFavorite = findViewById(R.id.fabContentFavorite);

        contentToolbar = findViewById(R.id.contentToolbar);
        contentAppBarLayout = findViewById(R.id.contentAppBarLayout);
        contentDetailBodyLayout = findViewById(R.id.contentDetailBodyLayout);
        cardviewContentPhoto = findViewById(R.id.cardviewContentPhoto);
        layoutContent1 = findViewById(R.id.layoutContent1);
        contentDetailShimmerFrameLayout = findViewById(R.id.contentDetailShimmerFrameLayout);
        recyclerViewContentDetailCast = findViewById(R.id.recyclerViewContentDetailCast);

        contentCollapsingToolbarLayout = findViewById(R.id.contentCollapsingToolbarLayout);
        contentCollapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedToolbarTitleStyle);
    }

    private void setupMovieDetailToolbar() {
        contentToolbar = findViewById(R.id.contentToolbar);
        setSupportActionBar(contentToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

    }

    @SuppressLint("SetTextI18n")
    private void setMutableMovieDetailData() {

        movieDetailViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
        movieDetailViewModel.initializeMutable(movieId);
        movieDetailViewModel.getMovieDetailMutableLiveData().observe(this, movieDataModel -> {

            Glide.with(getApplicationContext())
                    .load(ApiRepository.BACKDROP_URL + movieDataModel.getBackdrop_path())
                    .placeholder(R.drawable.backdrop_placeholder_image)
                    .into(imageContentDetailBackphoto);
            Glide.with(getApplicationContext())
                    .load(ApiRepository.POSTER_URL + movieDataModel.getPoster_path())
                    .placeholder(R.drawable.poster_placeholder_image)
                    .into(imageContentDetailPhoto);

            txtContentDetailTitle.setText(movieDataModel.getTitle());
            txtContentDetailReleaseDate.setText(" "+ movieDataModel.getRelease_date());
            txtContentDetailOverview.setText(movieDataModel.getOverview());
            //getMovieDetailGenres(movieDataModel);

            //int movieRuntime = (int) movieDataModel.getMovieDetailRuntime();
            //int movieInHours = movieRuntime/60;
            //int movieInMinutes = movieRuntime%60;
            //txtContentDetailRuntime.setText(" " + movieInHours + "h" + " " + movieInMinutes + "m");

            Double countVoteAverage = movieDataModel.getVote_average()*10;
            String txtCountVoteAverage = new DecimalFormat("##").format(countVoteAverage);
            txtContentDetailRating.setText(txtCountVoteAverage + "%");

            contentCollapsingToolbarLayout.setTitle(movieDataModel.getTitle());
            contentDetailShimmerFrameLayout.stopShimmer();
            contentDetailShimmerFrameLayout.setVisibility(View.GONE);
            contentAppBarLayout.setVisibility(View.VISIBLE);
            contentDetailBodyLayout.setVisibility(View.VISIBLE);
        });

    }

    private void setMutableMovieCasts() {

        MovieCastViewModel movieCastViewModel = ViewModelProviders.of(this).get(MovieCastViewModel.class);
        movieCastViewModel.initializeMutable(movieId);
        movieCastViewModel.getMovieCastMutableLiveData().observe(this, movieCastResponse -> {
            ArrayList<MovieCastDataModel> movieCastModel = movieCastResponse.getMovieCastDataModels();
            movieCastDataModelArrayList.addAll(movieCastModel);
            movieCastAdapter.notifyDataSetChanged();
        });

        setCastRecyclerView();

    }

    /*
    private void getMovieDetailGenres(final MovieDataModel movieDataModel) {

        apiRepository.getMovieGenres(new MovieGenreCallback() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(ArrayList<MovieGenreDataModel> movieGenreDataModels) {
                if (movieDataModel.getMovieGenres() != null) {
                    ArrayList<String> movieGenres = new ArrayList<>();
                    for (MovieGenreDataModel movieGenreDataModel : movieDataModel.getMovieGenres()) {
                        movieGenres.add(movieGenreDataModel.getMovieGenreName());
                    }
                    txtContentDetailGenre.setText(" " + TextUtils.join(" | ", movieGenres));
                }
            }

            @Override
            public void onError() {
                Toast.makeText(getApplicationContext(), "Error Fetch Genres", Toast.LENGTH_LONG).show();
            }
        });
    }
     */

    private void setCastRecyclerView() {
        if (bundleMovie != null && bundleMovie.containsKey(EXTRA_INFO_MOVIE)) {
            movieCastAdapter = new MovieCastAdapter(movieCastDataModelArrayList, getApplicationContext());
            int screenOrientation = getResources().getConfiguration().orientation;
            if (screenOrientation == Configuration.ORIENTATION_PORTRAIT) {
                recyclerViewContentDetailCast.setLayoutManager(new LinearLayoutManager(this));
            }
            else if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                recyclerViewContentDetailCast.setLayoutManager(new GridLayoutManager(this, 2));
            }
            recyclerViewContentDetailCast.setAdapter(movieCastAdapter);
        }
        if (bundleTvShow != null && bundleTvShow.containsKey(EXTRA_INFO_TVSHOW)) {
            tvShowCastAdapter = new TvShowCastAdapter(tvShowCastDataModelArrayList, getApplicationContext());
            int screenOrientation = getResources().getConfiguration().orientation;
            if (screenOrientation == Configuration.ORIENTATION_PORTRAIT) {
                recyclerViewContentDetailCast.setLayoutManager(new LinearLayoutManager(this));
            }
            else if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                recyclerViewContentDetailCast.setLayoutManager(new GridLayoutManager(this, 2));
            }
            recyclerViewContentDetailCast.setAdapter(tvShowCastAdapter);
        }
        else {
            if (bundleMovie != null && bundleMovie.containsKey(EXTRA_INFO_MOVIE)) {
                movieCastAdapter.notifyDataSetChanged();
            }
            else if (bundleTvShow != null && bundleTvShow.containsKey(EXTRA_INFO_TVSHOW)) {
                tvShowCastAdapter.notifyDataSetChanged();
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void setMutableTvShowDetailData() {

        tvShowDetailViewModel = ViewModelProviders.of(this).get(TvShowDetailViewModel.class);
        tvShowDetailViewModel.initializeMutable(tvShowId);
        tvShowDetailViewModel.getTvShowDetailMutableLiveData().observe(this, tvShowDataModel -> {

            Glide.with(getApplicationContext())
                    .load(ApiRepository.BACKDROP_URL + tvShowDataModel.getTvShowBackdropPath())
                    .into(imageContentDetailBackphoto);
            Glide.with(getApplicationContext())
                    .load(ApiRepository.POSTER_URL + tvShowDataModel.getTvShowPosterPath())
                    .into(imageContentDetailPhoto);

            txtContentDetailTitle.setText(tvShowDataModel.getTvShowName());
            txtContentDetailReleaseDate.setText(" " + tvShowDataModel.getTvShowReleaseDate());
            txtContentDetailOverview.setText(tvShowDataModel.getTvShowOverview());
            getTvShowDetailGenres(tvShowDataModel);

            int tvShowRuntime = tvShowDataModel.getTvshowRuntime();
            txtContentDetailRuntime.setText(" " + tvShowRuntime + " episodes");

            Double countVoteAverage = tvShowDataModel.getTvShowVoteAverage()*10;
            String txtCountVoteAverage = new DecimalFormat("##").format(countVoteAverage);
            txtContentDetailRating.setText(txtCountVoteAverage + "%");

            contentCollapsingToolbarLayout.setTitle(tvShowDataModel.getTvShowName());

            contentDetailShimmerFrameLayout.stopShimmer();
            contentDetailShimmerFrameLayout.setVisibility(View.GONE);
            contentAppBarLayout.setVisibility(View.VISIBLE);
            contentDetailBodyLayout.setVisibility(View.VISIBLE);

        });
    }

    private void setMutableTvShowCasts() {

        TvShowCastViewModel tvShowCastViewModel = ViewModelProviders.of(this).get(TvShowCastViewModel.class);
        tvShowCastViewModel.initializeMutable(tvShowId);
        tvShowCastViewModel.getTvShowCastMutableLiveData().observe(this, tvShowCastResponse -> {
            ArrayList<TvShowCastDataModel> tvShowCastModel = tvShowCastResponse.getTvShowCastDataModels();
            tvShowCastDataModelArrayList.addAll(tvShowCastModel);
            tvShowCastAdapter.notifyDataSetChanged();
        });

        setCastRecyclerView();

    }

    private void getTvShowDetailGenres(final TvShowDataModel tvShowDataModel) {
        apiRepository.getTvShowGenres(new TvShowGenreCallback() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(ArrayList<TvShowGenreDataModel> tvShowGenreDataModels) {
                if (tvShowDataModel.getTvShowGenres() != null) {
                    ArrayList<String> tvShowGenres = new ArrayList<>();
                    for (TvShowGenreDataModel tvShowGenreDataModel : tvShowDataModel.getTvShowGenres()) {
                        tvShowGenres.add(tvShowGenreDataModel.getTvShowGenreName());
                    }
                    txtContentDetailGenre.setText(" " + TextUtils.join(" | ", tvShowGenres));
                }
            }

            @Override
            public void onError() {
                Toast.makeText(getApplicationContext(), "Error Fetch Genres", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initializeDatabase() {
        contentDatabase = Room.databaseBuilder(this, ContentDatabase.class, "contentdb")
                .allowMainThreadQueries()
                .build();
    }

    private void checkFabClicked() {

        if (bundleMovie != null && bundleMovie.containsKey(EXTRA_INFO_MOVIE)) {
            MovieFavoriteDataModel movieFavoriteData = contentDatabase.movieFavoriteDao()
                    .getFavoriteMovieById(movieId);
            if (movieFavoriteData == null) {
                fabContentFavorite.setImageResource(R.drawable.ic_content_favorite_border);
            }
            else {
                fabContentFavorite.setImageResource(R.drawable.ic_content_favorite_solid);
            }
        }
        if (bundleTvShow != null && bundleTvShow.containsKey(EXTRA_INFO_TVSHOW)) {
            TvShowFavoriteDataModel tvShowFavoriteData = contentDatabase.tvShowFavoriteDao()
                    .getFavoriteTvShowById(tvShowId);
            if (tvShowFavoriteData == null) {
                fabContentFavorite.setImageResource(R.drawable.ic_content_favorite_border);
            }
            else {
                fabContentFavorite.setImageResource(R.drawable.ic_content_favorite_solid);
            }
        }

    }

    public void saveToDb(View view) {
        if (bundleMovie != null && bundleMovie.containsKey(EXTRA_INFO_MOVIE)) {
            saveMovieToFavorite();
        }
        if (bundleTvShow != null && bundleTvShow.containsKey(EXTRA_INFO_TVSHOW)) {
            saveTvShowToFavorite();
        }
    }

    private void saveMovieToFavorite() {

        MovieDetailViewModel movieDetailViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
        movieDetailViewModel.initializeMutable(movieId);
        movieDetailViewModel.getMovieDetailMutableLiveData().observe(this, movieDataModel1 -> {

            MovieFavoriteDataModel movieFavoriteDatas = contentDatabase.movieFavoriteDao()
                    .getFavoriteMovieById(movieId);

            if (movieFavoriteDatas == null) {
                MovieFavoriteDataModel addMovies = new MovieFavoriteDataModel();

                addMovies.movieId = movieDataModel1.getMovieId();
                addMovies.movieFavTitle = movieDataModel1.getTitle();
                addMovies.movieFavOverview = movieDataModel1.getOverview();
                addMovies.movieFavPosterPath = movieDataModel1.getPoster_path();
                addMovies.movieFavReleaseDate = movieDataModel1.getRelease_date();
                addMovies.movieFavVoteAverage = movieDataModel1.getVote_average();
                addMovies.movieFavBackdropPath = movieDataModel1.getBackdrop_path();

                contentDatabase.movieFavoriteDao().insertMovieToFavorite(addMovies);
                fabContentFavorite.setImageResource(R.drawable.ic_content_favorite_solid);
            }
            else {
                contentDatabase.movieFavoriteDao().deleteFavoriteMovieById(movieId);
                fabContentFavorite.setImageResource(R.drawable.ic_content_favorite_border);
            }

        });

    }

    private void saveTvShowToFavorite() {

        TvShowDetailViewModel tvShowDetailViewModel = ViewModelProviders.of(this).get(TvShowDetailViewModel.class);
        tvShowDetailViewModel.initializeMutable(tvShowId);
        tvShowDetailViewModel.getTvShowDetailMutableLiveData().observe(this, tvShowDataModel -> {

            TvShowFavoriteDataModel tvShowFavoriteDatas = contentDatabase.tvShowFavoriteDao()
                    .getFavoriteTvShowById(tvShowId);

            if (tvShowFavoriteDatas == null) {
                TvShowFavoriteDataModel addTvShows = new TvShowFavoriteDataModel();

                addTvShows.id = tvShowDataModel.getTvShowId();
                addTvShows.tvShowFavTitle = tvShowDataModel.getTvShowName();
                addTvShows.tvShowFavOverview = tvShowDataModel.getTvShowOverview();
                addTvShows.tvShowFavPosterPath = tvShowDataModel.getTvShowPosterPath();
                addTvShows.tvShowFavReleaseDate = tvShowDataModel.getTvShowReleaseDate();
                addTvShows.tvShowFavVoteAverage = tvShowDataModel.getTvShowVoteAverage();
                addTvShows.tvShowFavBackdropPath = tvShowDataModel.getTvShowBackdropPath();

                contentDatabase.tvShowFavoriteDao().insertTvShowToFavorite(addTvShows);
                fabContentFavorite.setImageResource(R.drawable.ic_content_favorite_solid);
            }
            else {
                contentDatabase.tvShowFavoriteDao().deleteFavoriteTvShowById(tvShowId);
                fabContentFavorite.setImageResource(R.drawable.ic_content_favorite_border);
            }

        });

    }


}