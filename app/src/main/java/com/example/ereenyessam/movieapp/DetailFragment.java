package com.example.ereenyessam.movieapp;


import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


public class DetailFragment extends Fragment implements View.OnClickListener{

    View rootview;
    Intent intent;
     ListView listview ;
     AsyncTaskLoader asyncTaskLoader;
    Bundle bundle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        bundle = args;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_detail, container, false);

        RelativeLayout header = (RelativeLayout) inflater.inflate(R.layout.header_detail,null,false);
        listview =(ListView) rootview.findViewById(R.id.detail_list_View);

        listview.addHeaderView(header,null,false);

        TextView year = (TextView) rootview.findViewById(R.id.details_year);
        TextView rate = (TextView) rootview.findViewById(R.id.details_rating);
        TextView description = (TextView) rootview.findViewById(R.id.details_description);
        ImageView imageView = (ImageView) rootview.findViewById(R.id.imageView);
        Button favourite = (Button) rootview.findViewById(R.id.button_favorite);
        Button reviews = (Button) rootview.findViewById(R.id.button_reviews);
        favourite.setOnClickListener(this);
        reviews.setOnClickListener(this);





        if (bundle == null) {

            intent = getActivity().getIntent();
            getActivity().setTitle(intent.getStringExtra("Tittle"));
            year.setText(intent.getStringExtra("Date"));
            rate.setText(intent.getStringExtra("average") + "/10");
            description.setText(intent.getStringExtra("OverView"));

            Picasso.with(getContext()).load(intent.getStringExtra("Image")).placeholder(R.mipmap.ic_launcher).into(imageView);
        }
        else {
            getActivity().setTitle(bundle.getString("Tittle"));
            year.setText(bundle.getString("Date"));
            rate.setText(bundle.getString("average") + "/10");
            description.setText(bundle.getString("OverView"));
            Picasso.with(getContext()).load(bundle.getString("Image")).placeholder(R.mipmap.ic_launcher).into(imageView);
        }

        return rootview;
    }


    @Override
    public void onStart() {
        super.onStart();
        int MovieId;
        if (bundle == null)
        {
            MovieId = intent.getIntExtra("id",0);
            Log.v("movieID", String.valueOf(MovieId));

        }
        else {
            MovieId = bundle.getInt("id",0);
            Log.v("movieID", String.valueOf(MovieId));
        }

       final String TrailerUrl = "http://api.themoviedb.org/3/movie/" +MovieId + "/videos?api_key=ab7f35103dac044075517a15da238890";
        asyncTaskLoader = new AsyncTaskLoader(getContext(), TrailerUrl, new TrailerParsing()) {


            @Override
            protected void onPostExecute(String[] key) {
                super.onPostExecute(key);
              int count=0;
                for (int i=0;i<key.length;i++) {
                    if (key[i] == null) {
                        break;
                    }
                    count++;
                }


                final String Videos[] = new String[count+1];
                final String images[] = new String[count];
                for (int j = 0; j<images.length; j++) {
                    images[j] = ("http://img.youtube.com/vi/" + key[j] + "/0.jpg");
                    Videos[j+1] = ("https://www.youtube.com/watch?v=" + key[j]);

                }


                DetailAdapter  detailAdapter= new DetailAdapter(getContext(), images);
                listview.setAdapter(detailAdapter);
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Videos[position])));
                    }
                });

                dialog.dismiss();




            }
        };
        asyncTaskLoader.execute();



    }


    @Override
    public void onClick(View v) {


        if(v.getId() == R.id.button_favorite)
        {   MovieInfo movieInfo = new MovieInfo();

            if(bundle == null) {
                movieInfo.setImageurl(intent.getStringExtra("Image"));
                movieInfo.setId(intent.getIntExtra("id", 0));
                movieInfo.setAverage(intent.getDoubleExtra("average", 0.00));
                movieInfo.setDate(intent.getStringExtra("Date"));
                movieInfo.setTittle(intent.getStringExtra("Tittle"));
                movieInfo.setOverview(intent.getStringExtra("OverView"));
            }
            else {
                movieInfo.setImageurl(bundle.getString("Image"));
                movieInfo.setId(bundle.getInt("id", 0));
                movieInfo.setAverage(bundle.getDouble("average", 0.00));
                movieInfo.setDate(bundle.getString("Date"));
                movieInfo.setTittle(bundle.getString("Tittle"));
                movieInfo.setOverview(bundle.getString("OverView"));
            }
            DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
            boolean isInserted = dataBaseHelper.insertRow(movieInfo);
            if (isInserted)
            {
                Toast.makeText(getContext(),"data inserted",Toast.LENGTH_SHORT).show();
            } else
            {
                Toast.makeText(getContext(),"data not inserted",Toast.LENGTH_SHORT).show();

            }
        }

        if(v.getId() == R.id.button_reviews)
        {
            int MovieId;

            if (bundle == null)
            {

                MovieId = intent.getIntExtra("id",0);

            }
            else {
                MovieId = bundle.getInt("id",0);
            }
            String Reviewurl="http://api.themoviedb.org/3/movie/"+MovieId+"/reviews?api_key=ab7f35103dac044075517a15da238890";
            asyncTaskLoader=new AsyncTaskLoader(getContext(),Reviewurl,new ReviewParsing())
            {
                @Override
                protected void onPostExecute(String[] Reviews) {
                    super.onPostExecute(Reviews);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Reviews");
                    dialog.dismiss();

                    String ReviewStyle=new String("");
                   for (int i=0; i<Reviews.length; i++)
                    {
                        if(Reviews[i] == null)
                        {
                            break;
                        }
                        else {
                          ReviewStyle+="Review "+(i+1)+ "\n\n\n" + Reviews[i] + "\n\n\n";

                        }
                    }

                    builder.setMessage(ReviewStyle);
                    builder.setPositiveButton("Exit", null);
                    builder.show();


                }
            };
            asyncTaskLoader.execute();
        }

    }
}