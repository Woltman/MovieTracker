package com.example.movietracker.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.example.movietracker.Fragments.MovieDetailFragment;
import com.example.movietracker.R;
import com.example.movietracker.SavePoster;

import Core.Movie;
import Core.MovieResponse;
import Infrastructure.TheMovieDB;
import Infrastructure.WatchlistStorage;

public class DetailActivity extends AppCompatActivity {
    private TheMovieDB theMovieDB = new TheMovieDB();
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        sharedPreferences = getSharedPreferences("shared prefernces", MODE_PRIVATE);

        View movieposter = findViewById(R.id.movieposter);
        registerForContextMenu(movieposter);

        setSupportActionBar((Toolbar)findViewById(R.id.my_toolbar));
        //getSupportActionBar().setDisplayShowTitleEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String id = intent.getStringExtra(MainActivity.ID_MESSAGE);

        theMovieDB.GetMovieById(id, this, new MovieResponse() {
            @Override
            public void onResponse(Movie movie) {
                MovieDetailFragment movieDetailFragment = (MovieDetailFragment)getSupportFragmentManager().findFragmentById(R.id.moviedetailfragment);
                movieDetailFragment.setMovie(movie);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.save_image : {
                Toast.makeText(getApplicationContext(), "Image Saved", Toast.LENGTH_SHORT).show();
                SavePoster savePoster = new SavePoster(this);
                MovieDetailFragment movieDetailFragment = (MovieDetailFragment)getSupportFragmentManager().findFragmentById(R.id.moviedetailfragment);
                savePoster.SaveImage(movieDetailFragment.getMovie().GetBitMap(), movieDetailFragment.getMovie().GetTitle());
                return true;
            }
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void addToWatchList(View v){
        MovieDetailFragment movieDetailFragment = (MovieDetailFragment)getSupportFragmentManager().findFragmentById(R.id.moviedetailfragment);
        Toast.makeText(this, movieDetailFragment.getMovie().GetTitle()+" added to watchlist", Toast.LENGTH_SHORT).show();

        movieDetailFragment = (MovieDetailFragment)getSupportFragmentManager().findFragmentById(R.id.moviedetailfragment);
        WatchlistStorage.addToWatchlist(movieDetailFragment.getMovie(), sharedPreferences);

        Button addtowatchlist = findViewById(R.id.addtowatchlist);
        addtowatchlist.setEnabled(false);

        Button removefromwatchlist = findViewById(R.id.removefromwatchlist);
        removefromwatchlist.setVisibility(View.VISIBLE);
    }

    public void removeFromWatchList(View v){
        MovieDetailFragment movieDetailFragment = (MovieDetailFragment)getSupportFragmentManager().findFragmentById(R.id.moviedetailfragment);
        Toast.makeText(this, movieDetailFragment.getMovie().GetTitle()+" removed from watchlist", Toast.LENGTH_SHORT).show();

        movieDetailFragment = (MovieDetailFragment)getSupportFragmentManager().findFragmentById(R.id.moviedetailfragment);
        WatchlistStorage.removeFromWatchlist(movieDetailFragment.getMovie(), sharedPreferences);

        Button addtowatchlist = findViewById(R.id.addtowatchlist);
        addtowatchlist.setEnabled(true);

        Button removefromwatchlist = findViewById(R.id.removefromwatchlist);
        removefromwatchlist.setVisibility(View.INVISIBLE);
    }
}
