package com.example.ereenyessam.movieapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class TrailerParsing implements Parsor {
    @Override
    public String[] parsing(String JSONCode) throws JSONException {
        JSONObject Blog = new JSONObject(JSONCode);
        JSONArray result  = Blog.getJSONArray("results");
        String[] Trailer = new String[result.length()];
        for (int i = 0; i < result.length(); i++) {
            Trailer[i]=result.getJSONObject(i).getString("key");
            Log.i("trailer", Trailer[i]);

        }


        return Trailer ;


    }
}
