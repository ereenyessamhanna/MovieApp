package com.example.ereenyessam.movieapp;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;


import org.json.JSONException;




public class MainFragment extends Fragment {


    View rootView;
    GridView gridView;
    AsyncTaskLoader asyncTaskLoader;
    Adapter adapter;
    MovieInfo movieInfo;


    public interface Callback{
        void onItemSelected(MovieInfo movieInfo);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        gridView = (GridView) rootView.findViewById(R.id.mainGridView);
        setHasOptionsMenu(true);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {




                /*try {
                    movieInfo = MovieParsing.parseEachFilm(asyncTaskLoader.getJSONCode(), position);
                    Log.v("hello", String.valueOf(movieInfo.getId()));
                    Intent intent = new Intent(getContext(), DetailActivity.class);
                    intent.putExtra("Tittle", movieInfo.getTittle());
                    intent.putExtra("Date", movieInfo.getDate());
                    intent.putExtra("OverView", movieInfo.getOverview());
                    intent.putExtra("Image", movieInfo.getImageurl());
                    intent.putExtra("average", (Double.toString(movieInfo.getAverage())));
                    intent.putExtra("id", (movieInfo.getId()));

                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Log.e("Tittle", movieInfo.getTittle());*/
                try {
                    movieInfo = MovieParsing.parseEachFilm(asyncTaskLoader.getJSONCode(), position);
                    ((Callback) getActivity()).onItemSelected(movieInfo);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });



        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();


        String popularURL = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + "ab7f35103dac044075517a15da238890";

          asyncTaskLoader = new AsyncTaskLoader(getContext(), popularURL,new ImageParssing()) {
            @Override
            protected void onPostExecute(String[] PosterPath) {
                super.onPostExecute(PosterPath);
                Toast.makeText(myContext, "Completed Loading", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                adapter = new Adapter(myContext, PosterPath);
                gridView.setAdapter(adapter);
            }

        };
        asyncTaskLoader.execute();


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu, menu);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_popular) {
            String popularUrl = "http://api.themoviedb.org/3/movie/popular?api_key=" + "ab7f35103dac044075517a15da238890";

             asyncTaskLoader = new AsyncTaskLoader(getContext(), popularUrl,new ImageParssing()) {
                @Override
                protected void onPostExecute(String[] PosterPath) {
                    super.onPostExecute(PosterPath);

                    Toast.makeText(myContext, "Completed Loading", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();


                    adapter = new Adapter(myContext, PosterPath);

                    gridView.setAdapter(adapter);
                }
            };

            asyncTaskLoader.execute();
            return true;

        }


        if (item.getItemId() == R.id.action_topRated) {
            String ratedURL = "http://api.themoviedb.org/3/movie/top_rated?api_key=" + "ab7f35103dac044075517a15da238890";
             asyncTaskLoader = new AsyncTaskLoader(getContext(), ratedURL ,new ImageParssing()) {
                @Override
                protected void onPostExecute(String[] PosterPath) {
                    super.onPostExecute(PosterPath);

                    Toast.makeText(myContext, "Completed Loading", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                     adapter = new Adapter(myContext, PosterPath);
                    gridView.setAdapter(adapter);
                }
            };

            asyncTaskLoader.execute();
            return true;


        }
        if (item.getItemId() == R.id.action_favourite) {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
            Cursor cursor = dataBaseHelper.getAllData();

            if (cursor.getCount() == 0)
            {
                Toast.makeText(getContext(),"nothing in database",Toast.LENGTH_SHORT).show();

            }
            else {

                MovieInfo temp[] = new MovieInfo[40];
                for (int i=0; i< 40;i++)
                {
                    temp[i] = new MovieInfo();
                }


                if (cursor.moveToFirst())
                { int i=0;
                    do {
                        temp[i].setId(cursor.getInt(0));
                        temp[i].setTittle(cursor.getString(1));
                        temp[i].setImageurl(cursor.getString(2));
                        temp[i].setDate(cursor.getString(3));
                        temp[i].setOverview(cursor.getString(4));
                        temp[i].setAverage(cursor.getDouble(5));


                        Log.i("Habibie: ", String.valueOf(temp[i].getId()));
                        Log.i("Habibie: ", temp[i].getTittle());
                        Log.i("Habibie: ", temp[i].getImageurl());
                        Log.i("Habibie: ", temp[i].getDate());
                        Log.i("Habibie: ", temp[i].getOverview());
                        Log.i("Habibie: ", String.valueOf(temp[i].getAverage()));
                        i++;
                    }while (cursor.moveToNext());

                    final MovieInfo movieInfo[]= new MovieInfo[i];
                    final String FavImage[] = new String [i];
                    for (int j=0; j<i ;j++)
                    {
                        movieInfo[j]= new MovieInfo();
                    }

                    for (int k=0; k<i; k++)
                    {
                        movieInfo[k].setId(temp[k].getId());
                        movieInfo[k].setTittle(temp[k].getTittle());
                        movieInfo[k].setImageurl(temp[k].getImageurl());
                        FavImage[k]= movieInfo[k].getImageurl();
                        movieInfo[k].setDate(temp[k].getDate());
                        movieInfo[k].setOverview(temp[k].getOverview());
                        movieInfo[k].setAverage(temp[k].getAverage());


                    }
                    adapter=new Adapter(getContext(),FavImage);
                   gridView.setAdapter(adapter);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            ((Callback)getActivity()).onItemSelected(movieInfo[position]);


                        }
                    });


                    }


            }



            return true;

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


}


