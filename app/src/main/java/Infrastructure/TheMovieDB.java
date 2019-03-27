package Infrastructure;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.movietracker.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Core.Movie;
import Core.MovieListResponse;
import Core.MovieResponse;

public class TheMovieDB {
    private final String api_key = BuildConfig.ApiKey;
    private final String base_url = "https://api.themoviedb.org/3";

    public TheMovieDB(){

    }

    public void Discover(Activity activity, final int page, final MovieListResponse movieListResponse){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(activity);
        String url = base_url+"/discover/movie?api_key="+api_key+"&page="+page;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("results");

                            MovieFactory movieFactory = new MovieFactory();

                            ArrayList<Movie> movies = movieFactory.buildMovieArray(results);

                            movieListResponse.onResponse(movies);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            movieListResponse.onException(e);
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        movieListResponse.onErrorResponse(error);
                    }
                });

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    public void GetMovieById(String movie_id, Activity activity, final MovieResponse movieResponse){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(activity);
        String url = base_url+"/movie/"+movie_id+"?api_key="+api_key;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        MovieFactory movieFactory = new MovieFactory();
                        Movie movie = movieFactory.buildMovie(response);

                        movieResponse.onResponse(movie);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        movieResponse.onErrorResponse(error);
                    }
                });

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    public void Search(String query, final int page, Activity activity, final MovieListResponse movieListResponse){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(activity);
        String url = base_url+"/search/movie?api_key="+api_key+"&query="+query+"&page="+page;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("results");

                            MovieFactory movieFactory = new MovieFactory();

                            ArrayList<Movie> movies = movieFactory.buildMovieArray(results);

                            movieListResponse.onResponse(movies);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            movieListResponse.onException(e);
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        movieListResponse.onErrorResponse(error);
                    }
                });

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }
}
