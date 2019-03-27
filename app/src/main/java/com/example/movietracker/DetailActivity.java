package com.example.movietracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import Core.Movie;
import Core.MovieResponse;
import Infrastructure.TheMovieDB;

public class DetailActivity extends AppCompatActivity {
    private TheMovieDB theMovieDB = new TheMovieDB();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setSupportActionBar((Toolbar)findViewById(R.id.my_toolbar));
        //getSupportActionBar().setDisplayShowTitleEnabled(true);

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
}
