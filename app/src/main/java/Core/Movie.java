package Core;

public class Movie {
    private String _title;

    public Movie(){

    }

    public Movie(String title){
        _title = title;
    }

    public void SetTitle(String title){
        _title = title;
    }

    public String GetTitle(){
        return _title;
    }
}
