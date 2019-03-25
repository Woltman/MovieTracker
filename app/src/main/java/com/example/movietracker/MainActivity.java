package com.example.movietracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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

        TextView tv = findViewById(R.id.Textview);
        tv.setText(key);

        TheMovieDB theMovieDB = new TheMovieDB();
        theMovieDB.Discover(this, this);

        //id of how to train your dragon
        theMovieDB.GetMovieById("166428", this,this);
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

        TextView tv = findViewById(R.id.Textview);
        tv.setText(movie.GetTitle());
    }

    @Override
    public void OnException(JSONException e) {

    }

    @Override
    public void OnErrorResponse(VolleyError error) {

    }
}
