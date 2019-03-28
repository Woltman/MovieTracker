package Core;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private static final String imageBaseUrl = "http://image.tmdb.org/t/p/w300";

    private String _title;
    private int _id;
    private String _imageUrl = "";
    private Bitmap poster;
    private String _summmary;

    public Movie(){

    }

    public Movie(String title, int id){
        _title = title;
        _id = id;
    }

    protected Movie(Parcel in) {
        _title = in.readString();
        _id = in.readInt();
        _imageUrl = in.readString();
        poster = in.readParcelable(Bitmap.class.getClassLoader());
        _summmary = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

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

    public void SetBitMap(Bitmap bitmap) { this.poster = bitmap; }

    public Bitmap GetBitMap() { return this.poster; }

    public String getSummary() {
        return _summmary;
    }

    public void setSummary(String summmary) {
        this._summmary = summmary;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_title);
        dest.writeInt(_id);
        dest.writeString(_imageUrl);
        dest.writeParcelable(poster, flags);
        dest.writeString(_summmary);
    }
}
