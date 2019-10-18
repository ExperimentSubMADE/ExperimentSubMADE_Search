package com.tuts.gdk_submission1.main;

import android.content.Intent;
import android.provider.Settings;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.tuts.gdk_submission1.SearchActivity;
import com.tuts.gdk_submission1.favorite.FavoriteFragment;
import com.tuts.gdk_submission1.movie.MovieFragment;
import com.tuts.gdk_submission1.R;
import com.tuts.gdk_submission1.tvshow.TvShowFragment;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String FRAGMENT_STATE = "fragment_state";
    private Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mainToolbar = findViewById(R.id.mainToolbar);
        BottomNavigationView mainBotnavCategory = findViewById(R.id.mainBotnavCategory);

        setSupportActionBar(mainToolbar);
        mainBotnavCategory.setOnNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            loadFragment(fragment);
        }
        else {
            fragment = getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_STATE);
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameContainer, fragment).commit();
        }
        if (savedInstanceState == null) {
            mainBotnavCategory.setSelectedItemId(R.id.botnav_menu_movie);
        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        getSupportFragmentManager().putFragment(outState, FRAGMENT_STATE, fragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.toolbar_localization_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_language) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.action_search_content) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private boolean loadFragment (Fragment fragment) {
        if(fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainFrameContainer, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.botnav_menu_movie:
                fragment = new MovieFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainFrameContainer, fragment)
                        .commit();
                break;

            case R.id.botnav_menu_favorite:
                fragment = new FavoriteFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainFrameContainer, fragment)
                        .commit();
                break;

            case R.id.botnav_menu_tvshow:
                fragment = new TvShowFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainFrameContainer, fragment)
                        .commit();
                break;

        }

        return loadFragment(fragment);
    }
}
