package com.example.ereenyessam.movieapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  implements MainFragment.Callback {
    boolean mTwoPane ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(isOnline())
        {


            if (findViewById(R.id.flayout) != null) {
                mTwoPane = true;

            } else {
                mTwoPane = false;


                getSupportFragmentManager().beginTransaction().add(R.id.layoutMain, new MainFragment()).commit();

            }

        }else
        {
            Toast.makeText(getApplicationContext(),"Check your internet Connection",Toast.LENGTH_LONG).show();
        }
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();



    }


    @Override
    public void onItemSelected(MovieInfo movieInfo) {
        if(!mTwoPane) {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("Tittle", movieInfo.getTittle());
            intent.putExtra("Date", movieInfo.getDate());
            intent.putExtra("OverView", movieInfo.getOverview());
            intent.putExtra("Image", movieInfo.getImageurl());
            intent.putExtra("average", (Double.toString(movieInfo.getAverage())));
            intent.putExtra("id", (movieInfo.getId()));
            Toast.makeText(getApplicationContext(),"Mobile Running",Toast.LENGTH_LONG).show();


            startActivity(intent);
        }

       else {
            Toast.makeText(getApplicationContext(),"Tablet Running",Toast.LENGTH_LONG).show();
            DetailFragment detailFragment = new DetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("Tittle",movieInfo.getTittle());
            bundle.putString("Date",movieInfo.getDate());
            bundle.putString("OverView",movieInfo.getOverview());
            bundle.putString("Image", movieInfo.getImageurl());
            bundle.putString("average", String.valueOf(movieInfo.getAverage()));
            bundle.putInt("id", movieInfo.getId());
            detailFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.flayout,detailFragment).commit();


        }

    }


}

