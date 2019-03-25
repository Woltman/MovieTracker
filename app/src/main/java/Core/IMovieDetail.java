package Core;

import com.android.volley.VolleyError;

import org.json.JSONException;

import java.util.ArrayList;

public interface IMovieDetail {
    public void OnResponse(Movie movie);
    public void OnException(JSONException e);
    public void OnErrorResponse(VolleyError error);
}
