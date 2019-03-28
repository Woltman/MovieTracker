package com.example.movietracker.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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


import com.example.movietracker.Fragments.MovieList;
import com.example.movietracker.R;

import java.util.ArrayList;

import Core.IListItemSelected;
import Core.Movie;
import Core.MovieListResponse;
import Infrastructure.TheMovieDB;
import Infrastructure.WatchlistStorage;

public class MainActivity extends AppCompatActivity implements IListItemSelected, WatchlistStorage.IWatchListObserver {
    private TheMovieDB theMovieDB;
    private MovieList movielistFragment;
    private int page = 1;
    private final int moviesPerPage = 20;
    private boolean isLoadingNewMovies = false;
    private boolean isInDiscoverMode = true;
    private boolean isInWatchList = false;
    private ArrayList<Movie> discoverMovies = new ArrayList<>();
    private ArrayList<Movie> searchMovies = new ArrayList<>();
    private String lastQuery = "";

    private SharedPreferences sharedPreferences;

    public static final String ID_MESSAGE = "id_message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar)findViewById(R.id.my_toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        theMovieDB = new TheMovieDB();

        sharedPreferences = getSharedPreferences("shared prefernces", MODE_PRIVATE);
        movielistFragment = (MovieList) getSupportFragmentManager().findFragmentById(R.id.movielist);

        WatchlistStorage.loadData(sharedPreferences);
        WatchlistStorage.observeList(this);

        if(savedInstanceState != null){
            discoverMovies = savedInstanceState.getParcelableArrayList("discovermovies");
            searchMovies = savedInstanceState.getParcelableArrayList("searchmovies");
            isInDiscoverMode = (boolean)savedInstanceState.getSerializable("discovermode");
            lastQuery = savedInstanceState.getString("lastquery");
            isInWatchList = (boolean)savedInstanceState.getSerializable("isinwatchlist");
            if(isInWatchList){
                movielistFragment.SetList(WatchlistStorage.getList());
            }

            else if(isInDiscoverMode){
                movielistFragment.SetList(discoverMovies);
            }
            else{
                movielistFragment.SetList(searchMovies);
            }
        }
        else{
            theMovieDB.Discover(this, page, new MovieListResponse() {
                @Override
                public void onResponse(ArrayList<Movie> movies) {
                    discoverMovies = movies;
                    movielistFragment.SetList(discoverMovies);
                }
            });
        }

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

                    if(isInDiscoverMode && !isInWatchList){
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

                if(title.equals(watchlist) && !isInWatchList){
                    findViewById(R.id.search).setVisibility(View.GONE);

                    isInWatchList = true;

                    if(isInDiscoverMode){
                        discoverMovies = movielistFragment.getMovies();
                    }
                    else{
                        searchMovies = movielistFragment.getMovies();
                    }

                    movielistFragment.SetList(WatchlistStorage.getList());

                }
                else if(title.equals(movielist) && isInWatchList){
                    isInWatchList = false;
                    if(isInDiscoverMode){
                        movielistFragment.SetList(discoverMovies);
                    }
                    else{
                        movielistFragment.SetList(searchMovies);
                    }

                    invalidateOptionsMenu();
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

        if(isInWatchList){
            MenuItem item = menu.findItem(R.id.search);
            item.setVisible(false);
        }

        searchView.setQuery(lastQuery, false);
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
                        searchMovies = movies;
                        movielistFragment.SetList(movies);
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")){
                    lastQuery = newText;
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_settings:
                Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onItemSelected(Movie movie) {
        Intent intent = new Intent(this, DetailActivity.class);

        String id = String.valueOf(movie.GetId());
        intent.putExtra(ID_MESSAGE, id);
        startActivity(intent);
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

    @Override
    public void watchListChanged(ArrayList<Movie> movies) {
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        if(bottomNavigationView.getSelectedItemId() == R.id.navigation_schedule){
            movielistFragment.SetList(WatchlistStorage.getList());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(discoverMovies != null){
            outState.putParcelableArrayList("discovermovies", discoverMovies);
            outState.putSerializable("discovermode", isInDiscoverMode);
            outState.putSerializable("isinwatchlist", isInWatchList);
            outState.putString("lastquery", lastQuery);
            outState.putParcelableArrayList("searchmovies", searchMovies);
        }

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }
}
