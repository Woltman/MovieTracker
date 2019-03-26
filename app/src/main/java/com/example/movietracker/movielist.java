package com.example.movietracker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;

import java.util.ArrayList;

import Core.IMovieList;
import Core.Movie;
import Infrastructure.TheMovieDB;

public class movielist extends Fragment implements IMovieList {

    ListView listView;

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        listView=(ListView)findViewById(R.id.listview);

        TheMovieDB theMovieDB = new TheMovieDB();
        theMovieDB.Discover(getActivity(), this);

//        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

//        listView.setAdapter(arrayAdapter);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movielist, container, false);
    }

    @Override
    public void OnResponse(ArrayList<Movie> movies) {
        final MovieAdapter movieAdapter = new MovieAdapter(getActivity(), R.layout.movie, movies);

        ListView listView = (ListView) getActivity().findViewById(R.id.listview);
        listView.setAdapter(movieAdapter);

        AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                String message = String.valueOf(movieAdapter.getItem(position).GetId());
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        };
        listView.setOnItemClickListener(mMessageClickedHandler);
    }

    @Override
    public void OnException(JSONException e) {

    }

    @Override
    public void OnErrorResponse(VolleyError error) {

    }
}
