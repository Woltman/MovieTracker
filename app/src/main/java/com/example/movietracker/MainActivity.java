package com.example.movietracker;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;

import java.util.ArrayList;

import Core.IListItemSelected;
import Core.IMovieDetail;
import Core.IMovieList;
import Core.Movie;
import Infrastructure.TheMovieDB;

public class MainActivity extends AppCompatActivity implements IMovieList, IMovieDetail, IListItemSelected {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar)findViewById(R.id.my_toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(true);

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

                //get string values from strings.xml
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

        movielist ml = (movielist)getSupportFragmentManager().findFragmentById(R.id.movielist);
        ml.SetList(movies);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tools, menu);

        final Activity activity = this;
        final IMovieList iMovieList = this;

        SearchView searchView = (SearchView)menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                TheMovieDB theMovieDB = new TheMovieDB();
                theMovieDB.Search(query, activity, iMovieList);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")){
                    new TheMovieDB().Discover(activity, iMovieList);
                    return true;
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public void onItemSelected(Movie movie) {
        Toast.makeText(this, movie.GetTitle(), Toast.LENGTH_SHORT).show();
    }
}
