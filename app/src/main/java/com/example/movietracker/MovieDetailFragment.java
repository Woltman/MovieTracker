package com.example.movietracker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import Core.Movie;


public class MovieDetailFragment extends Fragment {

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false);
    }

    public void setMovie(Movie movie){
        ImageView iv = getActivity().findViewById(R.id.movieposter);
        TextView title = getActivity().findViewById(R.id.movie_title);
        TextView summary = getActivity().findViewById(R.id.summary);
        title.setText(movie.GetTitle());
        summary.setText(movie.getSummary());
        new DownloadImageFromInternet(iv, movie).execute(movie.GetImageUrl());
    }
}
