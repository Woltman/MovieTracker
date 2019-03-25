package com.example.movietracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONException;

import java.util.ArrayList;

import Core.IMovieList;
import Core.Movie;
import Infrastructure.TheMovieDB;

public class MainActivity extends AppCompatActivity implements IMovieList {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get Api key
        String key = BuildConfig.ApiKey;

        TheMovieDB theMovieDB = new TheMovieDB();
        theMovieDB.Discover(this, this);
    }

    @Override
    public void OnResponse(ArrayList<Movie> movies) {
        for (Movie movie: movies) {
            Log.i("movie_title", movie.GetTitle());
        }
    }

    @Override
    public void OnException(JSONException e) {

    }

    @Override
    public void OnErrorResponse(VolleyError error) {

    }
}
