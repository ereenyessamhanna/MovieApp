package com.example.ereenyessam.movieapp;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieParsing {
    public MovieParsing (){

    }

    public static MovieInfo parseEachFilm(String JSONCode, int Position) throws JSONException {

        MovieInfo movieInfo=new MovieInfo();
        JSONObject Blog = new JSONObject(JSONCode);
        JSONArray result = Blog.getJSONArray("results");
        JSONObject object = result.getJSONObject(Position);
        movieInfo.setImageurl("http://image.tmdb.org/t/p/w185" + object.getString("poster_path"));
        movieInfo.setTittle(object.getString("original_title"));
        movieInfo.setDate(object.getString("release_date"));
        movieInfo.setOverview(object.getString("overview"));
        movieInfo.setAverage(object.getDouble("vote_average"));
        movieInfo.setId(object.getInt("id"));

        Log.i("movie info", movieInfo.getTittle());
        Log.i("movie info", String.valueOf(movieInfo.getId()));


        return movieInfo ;
    }


}
