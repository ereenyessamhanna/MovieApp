package com.example.ereenyessam.movieapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseHelper extends SQLiteOpenHelper {
    static final String MDB_POSTER_PATH = "poster_path";
    static final String MDB_OVERVIEW = "overview";
    static final String MDB_RELEASE_DATE = "release_date";
    static final String MDB_ORIGINAL_TITLE = "original_title";
    static final String MDB_ID = "id";
        static final String MDB_VOTE_AVERAGE = "vote_average";

    static final String DATABASE_NAME = "UsersDB";
    static final String TABLE_NAME = "USERS";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE="CREATE TABLE "+TABLE_NAME+" ("+MDB_ID+" VARCHAR(225), "+MDB_ORIGINAL_TITLE+" VARCHAR(225), "+MDB_POSTER_PATH
            +" VARCHAR(225), "+MDB_RELEASE_DATE+" VARCHAR(225), "+MDB_OVERVIEW+" VARCHAR(225), "+MDB_VOTE_AVERAGE+" VARCHAR(225));";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }


    public boolean insertRow(MovieInfo movieInfo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MDB_ID,movieInfo.getId());
        values.put(MDB_ORIGINAL_TITLE,movieInfo.getTittle());
        values.put(MDB_RELEASE_DATE,movieInfo.getDate());
        values.put(MDB_OVERVIEW,movieInfo.getOverview());
        values.put(MDB_POSTER_PATH,movieInfo.getImageurl());
        values.put(MDB_VOTE_AVERAGE, movieInfo.getAverage());

        long result =  db.insert(TABLE_NAME,null,values);
        db.close();
        if (result == -1)
        {
            return false;

        }
        else{
            return true;
        }

    }

    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return cursor;
    }
}

