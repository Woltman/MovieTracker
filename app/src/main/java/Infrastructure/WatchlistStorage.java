package Infrastructure;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import Core.Movie;


public class WatchlistStorage {
    ArrayList<Movie> watchList = new ArrayList<Movie>();

    public void addToWatchlist(Movie movie, SharedPreferences sharedPreferences) {
        if (!watchList.contains(movie)) {
            watchList.add(movie);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(watchList);
            editor.putString("Movies", json);
            editor.apply();
        }
    }

    public ArrayList<Movie> loadData(SharedPreferences sharedPreferences) {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Movies", null);
        Type type = new TypeToken<ArrayList<Movie>>() {}.getType();
        watchList = gson.fromJson(json, type);

        if (watchList == null) {
            watchList = new ArrayList<Movie>();
        }
        return watchList;
    }

    public ArrayList<Movie> getList() {
        return watchList;
    }
}
