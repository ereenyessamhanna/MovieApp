package com.example.ereenyessam.movieapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ImageParssing implements Parsor {
    @Override
    public String[] parsing(String JSONCode) throws JSONException {
            JSONObject Blog = new JSONObject(JSONCode);
            JSONArray result = Blog.getJSONArray("results");
            String[] posterPath = new String[result.length()];
            for (int i = 0; i < result.length(); i++) {
                posterPath[i]="http://image.tmdb.org/t/p/w185" + result.getJSONObject(i).getString("poster_path");
                Log.i("posterPath", posterPath[i]);

            }


            return posterPath ;
        }
    }

