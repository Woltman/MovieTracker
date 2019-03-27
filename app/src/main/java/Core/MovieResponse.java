package Core;

import android.util.Log;

public abstract class MovieResponse {
    public void onResponse(Movie movie){}
    public void onException(Exception e){

    }
    public void onErrorResponse(Exception e){

    }
}
