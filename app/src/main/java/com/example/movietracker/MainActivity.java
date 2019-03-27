package com.example.movietracker;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;


import java.util.ArrayList;

import Core.IListItemSelected;
import Core.Movie;
import Core.MovieListResponse;
import Infrastructure.TheMovieDB;
import Infrastructure.WatchlistStorage;

public class MainActivity extends AppCompatActivity implements IListItemSelected {
    private TheMovieDB theMovieDB;
    private MovieList movielistFragment;
    private int page = 1;
    private final int moviesPerPage = 20;
    private boolean isLoadingNewMovies = false;
    private boolean isInDiscoverMode = true;
    private ArrayList<Movie> discoverMovies = new ArrayList<>();
    private String lastQuery = "";

    private WatchlistStorage watchlistStorage;
    private SharedPreferences sharedPreferences;

    public static final String ID_MESSAGE = "id_message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar)findViewById(R.id.my_toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        theMovieDB = new TheMovieDB();
        watchlistStorage = new WatchlistStorage();
        sharedPreferences = getSharedPreferences("shared prefernces", MODE_PRIVATE);
        movielistFragment = (MovieList) getSupportFragmentManager().findFragmentById(R.id.movielist);

        //watchlistStorage.loadData(sharedPreferences);

        theMovieDB.Discover(this, page, new MovieListResponse() {
            @Override
            public void onResponse(ArrayList<Movie> movies) {
                movielistFragment.SetList(movies);
            }
        });

        final Activity activity = this;

        movielistFragment.SetOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.i("scrolling firstitem", firstVisibleItem+"");
                Log.i("scrolling visibleitem", visibleItemCount+"");
                Log.i("scrolling totalitem", totalItemCount+"");

                //if true load new data
                if(firstVisibleItem + visibleItemCount == totalItemCount && !isLoadingNewMovies && totalItemCount > 0){
                    isLoadingNewMovies = true;
                    Log.i("total", "bottom of page!!");

                    page+=1;
                    Log.i("page number", page+"");

                    if(isInDiscoverMode){
                        theMovieDB.Discover(activity, page, new MovieListResponse() {
                            @Override
                            public void onResponse(ArrayList<Movie> movies) {
                                movielistFragment.AddMovies(movies);
                                isLoadingNewMovies = false;
                            }
                        });
                    }
                    else{
                        isLoadingNewMovies = false;
                    }
                }
            }
        });

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
                    findViewById(R.id.search).setVisibility(View.GONE);
                    discoverMovies = movielistFragment.getMovies();
                    movielistFragment.SetList(new ArrayList<Movie>());
                    //TODO LOAD WatchList arraylist<Movie> into SetList
                }
                else if(title.equals(movielist)){
                    Toast.makeText(getApplicationContext(), movielist, Toast.LENGTH_SHORT).show();
                    findViewById(R.id.search).setVisibility(View.VISIBLE);
                    movielistFragment.SetList(discoverMovies);
                    //TODO SWITCH
                }
                else {
                    return false;
                }

                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tools, menu);

        final Activity activity = this;

        SearchView searchView = (SearchView)menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                page = 1;
                theMovieDB.Search(query, page, activity, new MovieListResponse() {
                    @Override
                    public void onResponse(ArrayList<Movie> movies) {
                        lastQuery = query;
                        if(isInDiscoverMode){
                            discoverMovies = movielistFragment.getMovies();
                            isInDiscoverMode = false;
                        }
                        page = 1;
                        movielistFragment.SetList(movies);
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")){
                    isInDiscoverMode = true;
                    movielistFragment.SetList(discoverMovies);
                    page = discoverMovies.size() / moviesPerPage;
                    return true;
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public void onItemSelected(Movie movie) {
        Toast.makeText(this, "Added " + movie.GetTitle() + " to WatchList", Toast.LENGTH_SHORT).show();

        //watchlistStorage.addToWatchlist(movie, sharedPreferences);
        //watchlistStorage.loadData(sharedPreferences);

        //watchlistStorage.addToWatchlist(movie, sharedPreferences);


        //TODO UITCOMMENT
        Intent intent = new Intent(this, DetailActivity.class);

        String id = String.valueOf(movie.GetId());
        intent.putExtra(ID_MESSAGE, id);
        startActivity(intent);

        //TODO Move this to detail page
//        SavePoster savePoster = new SavePoster(this);
//        Bitmap bimage = movie.GetBitMap();
//        savePoster.SaveImage(bimage, movie.GetTitle());
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.super.onBackPressed();
                    }
                }).create().show();
    }
}
