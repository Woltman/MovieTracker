package Core;

import com.android.volley.VolleyError;

import org.json.JSONException;

import java.util.ArrayList;

public interface IMovieList {
    public void OnResponse(ArrayList<Movie> movies);
    public void OnException(JSONException e);
    public void OnErrorResponse(VolleyError error);
}
