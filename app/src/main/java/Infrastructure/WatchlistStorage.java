package Infrastructure;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import Core.Movie;


public class WatchlistStorage {
    private static ArrayList<Movie> watchList = new ArrayList<Movie>();
    private static WatchlistStorage _instance;
    private static ArrayList<IWatchListObserver> _observers = new ArrayList<>();

    public static void addToWatchlist(Movie movie, SharedPreferences sharedPreferences) {
        for (Movie m: watchList) {
            if(m.GetTitle().equals(movie.GetTitle())){ return; }
        }

        watchList.add(movie);
        notifyObservers();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(watchList);
        editor.putString("Movies", json);
        editor.apply();
    }

    public static ArrayList<Movie> loadData(SharedPreferences sharedPreferences) {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Movies", null);
        Type type = new TypeToken<ArrayList<Movie>>() {}.getType();
        watchList = gson.fromJson(json, type);

        for (Movie m: watchList
             ) {
            m.SetBitMap(null);
        }

        if (watchList == null) {
            watchList = new ArrayList<Movie>();
        }
        return watchList;
    }

    public static void removeFromWatchlist(Movie movie, SharedPreferences sharedPreferences) {
        for (Movie m: watchList) {
            if(m.GetTitle().equals(movie.GetTitle())) {
                watchList.remove(m);
                notifyObservers();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(watchList);
                editor.putString("Movies", json);
                editor.apply();
                return;
            }
        }
    }

    public static ArrayList<Movie> getList() {
        return watchList;
    }

    public static WatchlistStorage getInstance(){
        if(_instance == null){
            _instance = new WatchlistStorage();
        }
        return _instance;
    }

    private static void notifyObservers(){
        for (IWatchListObserver observer: _observers) {
            observer.watchListChanged(watchList);
        }
    }

    public static void observeList(IWatchListObserver iWatchListObserver){
        _observers.add(iWatchListObserver);
    }

    public interface IWatchListObserver{
        void watchListChanged(ArrayList<Movie> movies);
    }
}
