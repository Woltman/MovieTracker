package Infrastructure;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.movietracker.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Core.IMovieList;
import Core.Movie;

public class TheMovieDB {
    private final String api_key = BuildConfig.ApiKey;
    private final String base_url = "https://api.themoviedb.org/3";

    public TheMovieDB(){

    }

    public void Discover(Activity activity, final IMovieList iMovieList){

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(activity);
        String url = base_url+"/discover/movie?api_key="+api_key;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("results");

                            ArrayList<Movie> movies = new ArrayList<>();
                            MovieFactory movieFactory = new MovieFactory();

                            for(int i = 0; i < results.length(); i++){
                                JSONObject object = results.getJSONObject(i);
                                movies.add(movieFactory.buildMovie(object));
                            }

                            iMovieList.OnResponse(movies);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            iMovieList.OnException(e);
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        iMovieList.OnErrorResponse(error);
                    }
                });

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }
}
