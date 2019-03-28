package com.example.movietracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Core.Movie;
import Infrastructure.WatchlistStorage;


public class MovieDetailFragment extends Fragment {

    private Movie _movie;
    private SharedPreferences sharedPreferences;

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("shared prefernces", getActivity().MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        return v;
    }

    public void setMovie(Movie movie){
        this._movie = movie;
        ImageView iv = getActivity().findViewById(R.id.movieposter);
        TextView title = getActivity().findViewById(R.id.movie_title);
        TextView summary = getActivity().findViewById(R.id.summary);
        title.setText(movie.GetTitle());
        summary.setText(movie.getSummary());
        new DownloadImageFromInternet(iv, movie).execute(movie.GetImageUrl());

        Button addtowatchlist = getActivity().findViewById(R.id.addtowatchlist);
        ArrayList<Movie> movies = WatchlistStorage.getList();

        for (Movie m: movies) {
            if(m.GetTitle().equals(movie.GetTitle())){
                addtowatchlist.setEnabled(false);
                Button removefromwatchlist = getActivity().findViewById(R.id.removefromwatchlist);
                removefromwatchlist.setVisibility(View.VISIBLE);
                return;
            }
        }
    }

    public Movie getMovie() { return _movie; }
}
