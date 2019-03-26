package com.example.movietracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import Core.Movie;

public class MovieAdapter extends ArrayAdapter<Movie> {
    private ArrayList<Movie> _movies;

    public MovieAdapter(Context context, int resource, ArrayList<Movie> movies) {
        super(context, resource, movies);
        _movies = movies;
    }

    public void SetMovies(ArrayList<Movie> movies){
        _movies = movies;
    }

    public int ItemCount(){
        return _movies.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // assign the view we are converting to a local variable
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.movie, null);
        }

        /*
         * Recall that the variable position is sent in as an argument to this method.
         * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
         * iterates through the list we sent it)
         *
         * Therefore, i refers to the current Item object.
         */
        Movie movie = _movies.get(position);

        if (movie != null) {

            // This is how you obtain a reference to the TextViews.
            // These TextViews are created in the XML files we defined.

            TextView tv = (TextView) v.findViewById(R.id.movie_title);
            ImageView iv = (ImageView) v.findViewById(R.id.movieposter);

            // check to see if each individual textview is null.
            // if not, assign some text!
            if (tv != null){
                tv.setText(movie.GetTitle());
            }
            if (iv != null){
                new DownloadImageFromInternet(iv, getContext()).execute(movie.GetImageUrl());
            }
        }

        // the view must be returned to our activity
        return v;
    }
}
