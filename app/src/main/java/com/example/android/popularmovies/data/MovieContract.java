package com.example.android.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by tp293 on 11/29/2017.
 */

public class MovieContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_FAVORITES = "favorites";

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_FAVORITES)
                .build();

        public static final String TABLE_NAME = "popular_movies";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RELEASE_DATE = "release";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_POSTER = "poster";
        public static final String COLUMN_PLOT = "plot";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TRAILER = "trailer";
        public static final String COLUMN_REVIEWS = "reviews";
    }

}

