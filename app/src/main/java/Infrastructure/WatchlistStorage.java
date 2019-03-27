package Infrastructure;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import Core.Movie;


public class WatchlistStorage {
    ArrayList<Movie> watchList;

    public void addToWatchlist(Movie movie, SharedPreferences sharedPreferences) {
        watchList.add(movie);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(watchList);
        editor.putString("task list", json);
        editor.apply();
    }

    public void loadData(SharedPreferences sharedPreferences) {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<Movie>() {}.getType();
        watchList = gson.fromJson(json, type);

        if (watchList == null) {
            watchList = new ArrayList<>();
        }
    }
}
