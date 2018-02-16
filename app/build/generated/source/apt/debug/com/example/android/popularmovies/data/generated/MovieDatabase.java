package com.example.android.popularmovies.data.generated;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.Override;
import java.lang.String;

public class MovieDatabase extends SQLiteOpenHelper {
  private static final int DATABASE_VERSION = 1;

  public static final String TABLE_NAME = "CREATE TABLE popular_movies ("
   + ListColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
   + ListColumns.TITLE + " TEXT NOT NULL,"
   + ListColumns.RELEASE_DATE + " TEXT NOT NULL,"
   + ListColumns.RATING + " TEXT NOT NULL,"
   + ListColumns.POSTER + " TEXT NOT NULL,"
   + ListColumns.PLOT + " TEXT NOT NULL,"
   + ListColumns.TRAILER + " TEXT NOT NULL,"
   + ListColumns.REVIEWS + " TEXT NOT NULL)";

  private static volatile MovieDatabase instance;

  private Context context;

  private MovieDatabase(Context context) {
    super(context.getApplicationContext(), "movieDatabase.db", null, DATABASE_VERSION);
    this.context = context.getApplicationContext();
  }

  public static MovieDatabase getInstance(Context context) {
    if (instance == null) {
      synchronized (MovieDatabase.class) {
        if (instance == null) {
          instance = new MovieDatabase(context);
        }
      }
    }
    return instance;
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(TABLE_NAME);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
  }
}
