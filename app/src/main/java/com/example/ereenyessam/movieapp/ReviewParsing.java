package com.example.ereenyessam.movieapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ReviewParsing implements Parsor {
    @Override
    public String[] parsing(String JSONCode) throws JSONException {
        JSONObject Blog = new JSONObject(JSONCode);
        int length=Blog.getInt("total_results");
        JSONArray result = Blog.getJSONArray("results");


       String[] Reviews = new String[length];
        for (int i = 0; i < length; i++) {
            Reviews[i]=result.getJSONObject(i).getString("content");
            Log.i("review", Reviews[i]);

        }


        return Reviews ;
    }
}
