package Infrastructure;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import Core.Preference;


public class Preferences {

    private ArrayList<Preference> preferences = new ArrayList<Preference>();

    public Preferences() {
        if (preferences.isEmpty()) {
            preferences.add(new Preference("showMovieTitle", true));
        }
    }

    public void changePreference(Preference preference, boolean value) {
        if (preferences.contains(preference)) {
            int index = preferences.indexOf(preference);
            preferences.get(index).SetValue(value);
        }
    }

    public void savePreference(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(preferences);
        editor.putString("Preferences", json);
        editor.apply();
    }

    public void loadPreferences(SharedPreferences sharedPreferences) {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Preferences", null);
        Type type = new TypeToken<ArrayList<Preference>>() {}.getType();
        preferences = gson.fromJson(json, type);

        if (preferences == null) {
            preferences = new ArrayList<Preference>();
        }
    }
}
