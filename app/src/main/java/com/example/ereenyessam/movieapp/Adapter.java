package com.example.ereenyessam.movieapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Adapter extends ArrayAdapter<String> {
    Context myContext;
    String[] PosterPath;


    public Adapter(Context context, String[] PosterPath) {
        super(context, R.layout.grid_item,R.id.imageView, PosterPath);
        this.PosterPath = PosterPath;
        myContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View convertView1 = convertView;


        if (convertView1 == null) {
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView1 = inflater.inflate(R.layout.grid_item, parent, false);
        }
        ImageView imageView = (ImageView) convertView1.findViewById(R.id.imageView);


        Log.i("ereeny", PosterPath[position]);
        Picasso.with(myContext).load(PosterPath[position]).placeholder(R.mipmap.ic_launcher).into(imageView);

        return convertView1;

    }
}
