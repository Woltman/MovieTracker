package com.example.movietracker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;

import Core.Movie;

public class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
    private ImageView imageView;
    private Movie movie;

    public DownloadImageFromInternet(ImageView imageView, Movie movie) {
        this.imageView = imageView;
        this.movie = movie;
        //Toast.makeText(context, "Please wait, it may take a few minute...", Toast.LENGTH_SHORT).show();
    }

    protected Bitmap doInBackground(String... urls) {
        String imageURL = urls[0];
        if(imageURL == ""){
            return null;
        }
        Bitmap bimage = null;
        try {
            InputStream in = new java.net.URL(imageURL).openStream();
            bimage = BitmapFactory.decodeStream(in);

        } catch (Exception e) {
            Log.e("Error Message", e.getMessage());
            e.printStackTrace();
        }
        //this.movie.SetBitMap(bimage);
        return bimage;
    }

    protected void onPostExecute(Bitmap result) {
        if(result == null) return;
        imageView.setImageBitmap(result);
    }
}
