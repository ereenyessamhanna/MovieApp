package com.example.ereenyessam.movieapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by user on 12/1/2016.
 */
public class DetailAdapter extends ArrayAdapter<String>{
    Context myContext;
    String[] Trailer;


    public DetailAdapter(Context context, String[] Trailer) {
        super(context, R.layout.list_item, Trailer);
        this.Trailer = Trailer;
        myContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View convertView1 = convertView;


        if (convertView1 == null) {
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView1 = inflater.inflate(R.layout.list_item, parent, false);
        }
        ImageView imageView = (ImageView) convertView1.findViewById(R.id.imageView3);
        TextView textView= (TextView) convertView1.findViewById(R.id.textView4);
        textView.setText("Trailer "+ (position+1));


        Log.i("ereeny", Trailer[position]);
        Picasso.with(myContext).load(Trailer[position]).placeholder(R.mipmap.ic_launcher).into(imageView);

        return convertView1;

    }
}
