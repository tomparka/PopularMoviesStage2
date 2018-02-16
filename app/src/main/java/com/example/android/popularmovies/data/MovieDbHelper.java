package com.example.android.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by tp293 on 11/29/2017.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "favorite_movies.db";
    public static final int DATABASE_VERSION = 1;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_MOVIE_TABLE =

                "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " (" +
                        MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        MovieContract.MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                        MovieContract.MovieEntry.COLUMN_POSTER + " TEXT, " +
                        MovieContract.MovieEntry.COLUMN_RATING + " TEXT, " +
                        MovieContract.MovieEntry.COLUMN_RELEASE_DATE + " TEXT, " +
                        MovieContract.MovieEntry.COLUMN_PLOT + " TEXT, " +
                        MovieContract.MovieEntry.COLUMN_TRAILER + " TEXT, " +
                        MovieContract.MovieEntry.COLUMN_REVIEWS + " TEXT, " +

                        " UNIQUE (" + MovieContract.MovieEntry.COLUMN_TITLE + ") ON CONFLICT REPLACE);";

        Log.d("SQLITE TABLE", SQL_CREATE_MOVIE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}