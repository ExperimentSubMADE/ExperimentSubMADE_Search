package com.tuts.gdk_submission1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;
import com.tuts.gdk_submission1.background.SearchViewModel2;
import com.tuts.gdk_submission1.movie.adapter.MovieAdapter;
import com.tuts.gdk_submission1.movie.callback.MovieClickCallback;
import com.tuts.gdk_submission1.movie.datamodel.MovieDataModel;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements MaterialSearchBar.OnSearchActionListener {

    private String movieQuery;
    private MaterialSearchBar searchBar;
    private RecyclerView recyclerViewItem;
    private MovieAdapter movieAdapter;
    private ArrayList<MovieDataModel> movieDataModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerViewItem = findViewById(R.id.recyclerViewItem);
        initializeSearch();
    }

    private void initializeSearch() {
        searchBar = findViewById(R.id.searchBar);
        searchBar.getPlaceHolderView().setTextSize(14);
        searchBar.setCardViewElevation(0);
        searchBar.setOnSearchActionListener(this);
    }

    private void getMutableSearchMovies(final String movieQuery) {
        SearchViewModel2 searchViewModel2 = ViewModelProviders.of(this).get(SearchViewModel2.class);
        searchViewModel2.getSearch(movieQuery);
        searchViewModel2.getListMovies().observe(this, movieDataModels -> {
            movieAdapter.setListMovie(movieDataModels);
        });
    }



    private void showRecyclerView(final String movieQuery) {
        movieAdapter = new MovieAdapter(movieDataModelArrayList, this, movieClickCallback);
        movieAdapter.setListMovie(movieDataModelArrayList);
        recyclerViewItem.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewItem.setAdapter(movieAdapter);

        getMutableSearchMovies(movieQuery);

    }

    private MovieClickCallback movieClickCallback = movieDataModel -> {
        Intent intent = new Intent(this, ContentDetailActivity.class);
        intent.putExtra(ContentDetailActivity.MOVIE_ID, movieDataModel.getMovieId());
        intent.putExtra(ContentDetailActivity.EXTRA_INFO_MOVIE, "");
        startActivity(intent);
    };

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {

        movieQuery = text.toString();
        //getMutableSearchMovies(movieQuery);
        showRecyclerView(movieQuery);
    }

    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode) {
            case MaterialSearchBar.BUTTON_SPEECH:
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
                try {
                    startActivityForResult(intent, 1);
                    searchBar.setText("");
                }
                catch (ActivityNotFoundException error) {
                    Toast.makeText(this, "Your smartphone doesn't support this feature",
                            Toast.LENGTH_SHORT).show();
                }
            case MaterialSearchBar.BUTTON_BACK:
                searchBar.disableSearch();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                searchBar.enableSearch();
                searchBar.setText(text.get(0));
                //getMutableSearchMovies(text.get(0));
                showRecyclerView(text.get(0));
            }
        }

    }
}
