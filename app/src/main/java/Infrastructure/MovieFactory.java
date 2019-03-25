package Infrastructure;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Core.Movie;

public class MovieFactory {
    public Movie buildMovie(JSONObject jsonObject){
        Movie movie = new Movie();
        try {
            movie.SetTitle((String)jsonObject.get("title"));
            movie.SetId((int)jsonObject.get("id"));
            JSONObject obj = (JSONObject)jsonObject.get("belongs_to_collection");
            movie.SetImageUrl((String)obj.get("poster_path"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movie;
    }

    //result from api if list is requested is different then single movie
    public ArrayList<Movie> buildMovieArray(JSONArray jsonArray){
        ArrayList<Movie> movies = new ArrayList<>();

        try{
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);
                Movie movie = new Movie();
                try {
                    movie.SetTitle((String)object.get("title"));
                    movie.SetId((int)object.get("id"));
                    movie.SetImageUrl((String)object.get("poster_path"));
                    movies.add(movie);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        catch(JSONException e){

        }

        return movies;
    }
}
