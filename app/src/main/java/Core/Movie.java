package Core;

public class Movie {
    private static final String imageBaseUrl = "http://image.tmdb.org/t/p/w300";

    private String _title;
    private int _id;
    private String _imageUrl = "";

    public Movie(){

    }

    public Movie(String title, int id){
        _title = title;
        _id = id;
    }

    public void SetTitle(String title){
        _title = title;
    }

    public String GetTitle(){
        return _title;
    }

    public void SetId(int id){
        _id = id;
    }

    public int GetId(){
        return _id;
    }

    public void SetImageUrl(String imageUrl){
        _imageUrl = imageBaseUrl + imageUrl;
    }

    public String GetImageUrl(){
        return _imageUrl;
    }
}
