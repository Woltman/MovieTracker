package Infrastructure;

import org.json.JSONException;
import org.json.JSONObject;

import Core.Movie;

public class MovieFactory {
    public Movie buildMovie(JSONObject jsonObject){
        Movie movie = new Movie();
        try {
            movie.SetTitle((String)jsonObject.get("title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movie;
    }
}
