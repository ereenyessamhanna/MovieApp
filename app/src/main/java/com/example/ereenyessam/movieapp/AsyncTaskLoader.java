package com.example.ereenyessam.movieapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class AsyncTaskLoader extends AsyncTask<Void,Void,String[]> {
    Context myContext;
    ProgressDialog dialog;
    String dawt;
    Parsor parsor;
    String JSONCode;

    public AsyncTaskLoader(Context context,String Url,Parsor parsor){
        myContext=context;
        dawt=Url;
        this.parsor=parsor;


    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();


        Toast.makeText(myContext, "The Application is started", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        dialog=new ProgressDialog(myContext);
        dialog.setMessage("Loading.....");
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

    }


    @Override
    protected String[] doInBackground(Void... params) {
        publishProgress();
        try {
            URL url=new URL(dawt);
            HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream= urlConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder JsonCodeBuilder=new StringBuilder();
            String JsonLine;
            while((JsonLine =bufferedReader.readLine())!=null)
            {
                JsonCodeBuilder.append(JsonLine+'\n');

            }
            JSONCode=JsonCodeBuilder.toString();
            Log.i("Json", JSONCode);
            return parsor.parsing(JSONCode);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;


    }

    public String getJSONCode() {

        return JSONCode;
    }
}
