package com.example.movietracker.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.movietracker.MovieAdapter;
import com.example.movietracker.R;

import java.util.ArrayList;

import Core.IListItemSelected;
import Core.Movie;

public class MovieList extends Fragment {
    private MovieAdapter movieAdapter;
    private IListItemSelected iListItemSelected;
    private ListView listView;
    private int page;

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

    public void SetOnScrollListener(AbsListView.OnScrollListener onScrollListener){
        listView = getActivity().findViewById(R.id.listview);
        listView.setOnScrollListener(onScrollListener);
    }

    public void SetList(ArrayList<Movie> movies){

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

    public void AddMovies(ArrayList<Movie> movies){
        movieAdapter.AddMovies(movies);
        movieAdapter.notifyDataSetChanged();
    }

    public ArrayList<Movie> getMovies(){
        return movieAdapter.getMovies();
    }
}
