package com.example.android.popularmovies.data.generated.values;

import android.content.ContentValues;

import java.lang.Integer;
import java.lang.Long;
import java.lang.String;

public class Popular_moviesValuesBuilder {
  ContentValues values = new ContentValues();

  public Popular_moviesValuesBuilder Id(Integer value) {
    values.put(ListColumns._ID, value);
    return this;
  }

  public Popular_moviesValuesBuilder Id(Long value) {
    values.put(ListColumns._ID, value);
    return this;
  }

  public Popular_moviesValuesBuilder title(String value) {
    values.put(ListColumns.TITLE, value);
    return this;
  }

  public Popular_moviesValuesBuilder releaseDate(String value) {
    values.put(ListColumns.RELEASE_DATE, value);
    return this;
  }

  public Popular_moviesValuesBuilder rating(String value) {
    values.put(ListColumns.RATING, value);
    return this;
  }

  public Popular_moviesValuesBuilder poster(String value) {
    values.put(ListColumns.POSTER, value);
    return this;
  }

  public Popular_moviesValuesBuilder plot(String value) {
    values.put(ListColumns.PLOT, value);
    return this;
  }

  public Popular_moviesValuesBuilder trailer(String value) {
    values.put(ListColumns.TRAILER, value);
    return this;
  }

  public Popular_moviesValuesBuilder reviews(String value) {
    values.put(ListColumns.REVIEWS, value);
    return this;
  }

  public ContentValues values() {
    return values;
  }
}
