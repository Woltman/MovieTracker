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

import Core.IListItemSelected;
import Core.IMovieList;
import Core.Movie;
import Infrastructure.TheMovieDB;

public class movielist extends Fragment implements IMovieList {

    private MovieAdapter movieAdapter;
    private IListItemSelected iListItemSelected;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        iListItemSelected = (IListItemSelected)getActivity();

        View v = inflater.inflate(R.layout.fragment_movielist, container, false);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle bundle){
        super.onActivityCreated(bundle);
    }

    @Override
    public void OnResponse(ArrayList<Movie> movies) {

    }

    public void SetList(ArrayList<Movie> movies){
        if(movieAdapter == null || movieAdapter.ItemCount() < movies.size()){
            movieAdapter = new MovieAdapter(getActivity(), R.layout.movie, new ArrayList<Movie>(movies));

            listView = getActivity().findViewById(R.id.listview);
            listView.setAdapter(movieAdapter);

            AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView parent, View v, int position, long id) {
                    //String message = String.valueOf(movieAdapter.getItem(position).GetId());
                    iListItemSelected.onItemSelected(movieAdapter.getItem(position));
                }
            };
            listView.setOnItemClickListener(mMessageClickedHandler);
        }
        else{
            movieAdapter.SetMovies(movies);
            movieAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void OnException(JSONException e) {

    }

    @Override
    public void OnErrorResponse(VolleyError error) {

    }
}
