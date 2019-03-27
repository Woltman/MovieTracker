package Core;

import android.util.Log;

import java.util.ArrayList;

public abstract class MovieListResponse {
    public void onResponse(ArrayList<Movie> movies){};
    public void onException(Exception e){
        Log.e("MovieListResponse exception", e.getMessage());
    };
    public void onErrorResponse(Exception e){
        Log.e("MovieListResponse error", e.getMessage());
    }
}
