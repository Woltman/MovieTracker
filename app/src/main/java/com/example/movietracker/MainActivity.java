package com.example.movietracker;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;

import java.util.ArrayList;

import Core.IMovieDetail;
import Core.IMovieList;
import Core.Movie;
import Infrastructure.TheMovieDB;

public class MainActivity extends AppCompatActivity implements IMovieList, IMovieDetail {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get Api key
        String key = BuildConfig.ApiKey;

        TheMovieDB theMovieDB = new TheMovieDB();
        theMovieDB.Discover(this, this);

        //id of how to train your dragon
        theMovieDB.GetMovieById("166428", this,this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            //handle bottom navigation view item clicks
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                String title = menuItem.getTitle().toString();

                String watchlist = getResources().getString(R.string.watchlist);
                String movielist = getResources().getString(R.string.movies);

                if(title.equals(watchlist)){
                    Toast.makeText(getApplicationContext(), watchlist, Toast.LENGTH_SHORT).show();
                }
                else if(title.equals(movielist)){
                    Toast.makeText(getApplicationContext(), movielist, Toast.LENGTH_SHORT).show();
                }
                else {
                    return false;
                }

                return true;
            }
        });
    }

    @Override
    public void OnResponse(ArrayList<Movie> movies) {
        for (Movie movie: movies) {
            Log.i("movie_list_title", movie.GetTitle());
            Log.i("movie_list_imageURL", movie.GetImageUrl());
            Log.i("movie_list_id", String.valueOf(movie.GetId()));
        }
    }

    @Override
    public void OnResponse(Movie movie) {
        Log.i("movie_detail_title", movie.GetTitle());
        Log.i("movie_detail_ID", String.valueOf(movie.GetId()));
        Log.i("movie_detail_imageURL", movie.GetImageUrl());
    }

    @Override
    public void OnException(JSONException e) {

    }

    @Override
    public void OnErrorResponse(VolleyError error) {

    }
}
