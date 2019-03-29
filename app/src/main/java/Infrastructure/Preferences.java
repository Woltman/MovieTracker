package Infrastructure;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import Core.Preference;


public class Preferences {

    private static ArrayList<Preference> preferences = new ArrayList<Preference>();
    public static final String SHOWMOVIETITLES = "showMovieTitle";

    public Preferences() {

    }

    public static void changePreference(Preference preference) {
        for (Preference p: preferences) {
            if(p.getSetting().equals(preference.getSetting())){
                int index = preferences.indexOf(p);
                preferences.get(index).SetValue(preference.GetValue());
                return;
            }
        }

        preferences.add(preference);
    }

    public static void savePreference(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(preferences);
        editor.putString("Preferences", json);
        editor.apply();
    }

    public static void loadPreferences(SharedPreferences sharedPreferences) {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Preferences", null);
        Type type = new TypeToken<ArrayList<Preference>>() {}.getType();
        preferences = gson.fromJson(json, type);

        if (preferences == null) {
            preferences = new ArrayList<Preference>();
            preferences.add(new Preference(SHOWMOVIETITLES, true));
        }
    }

    public static ArrayList<Preference> getPreferences(){
        return preferences;
    }

    public static Preference getPreference(String setting){
        for (Preference p: preferences) {
            if(p.getSetting().equals(setting)){
                return p;
            }
        }
        return null;
    }
}
