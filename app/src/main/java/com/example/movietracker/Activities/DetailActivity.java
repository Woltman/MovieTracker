package com.example.movietracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Visibility;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
