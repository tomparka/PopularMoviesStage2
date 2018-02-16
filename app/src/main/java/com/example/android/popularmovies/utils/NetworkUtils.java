package com.example.android.popularmovies.utils;

import android.net.Uri;
import android.util.Log;
import android.util.MutableInt;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by tp293 on 10/9/2017.
 */

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie";
    private static final String API_KEY = ""; //TODO: ENTER MOVIEDB APIKEY HERE

    /* The format we want our API to return */
    private static final String sortByRatTrigger = "HIGHEST RATED";
    private static final String sortByPopTrigger = "MOST POPULAR";
    private static final String getVideosTrigger = "VIDEOS";
    private static final String getReviewsTrigger = "REVIEWS";

    private static final String requestMostPopular = "popular";
    private static final String requestTopRated = "top_rated";
    private static final String requestVideos = "videos";
    private static final String requestReviews = "reviews";


    // /movie/{id}/videos
    // /movie/{id}/reviews

    //final static String SORTBY_PARAM = "sort_by";
    final static String APIKEY_PARAM = "api_key";

    public static URL buildUrl(String apiRequestType){
        return buildUrl(apiRequestType, null);
    }

    public static URL buildUrl(String apiRequestType, String movieId) {

        Log.d(TAG, "passed in string is " +apiRequestType +": sort by most popular trigger is " + sortByPopTrigger);
        Log.d(TAG, "equals method: " + apiRequestType.equals(sortByPopTrigger) + " == method: " + (apiRequestType == "MOST_POPULAR"));
        Uri.Builder uriBuilder = Uri.parse(MOVIE_BASE_URL).buildUpon();

        switch(apiRequestType) {
            case sortByPopTrigger: //most popular
                uriBuilder.appendEncodedPath(requestMostPopular);
                break;

            case sortByRatTrigger: //top rated
                uriBuilder.appendEncodedPath(requestTopRated);
                break;

            case getVideosTrigger: //videos
                uriBuilder.appendEncodedPath(movieId)
                        .appendEncodedPath(requestVideos);
                break;

            case getReviewsTrigger: //reviews
                uriBuilder.appendEncodedPath(movieId)
                        .appendEncodedPath(requestReviews);
                break;

            default:
                Log.d(TAG, "Method buildUrl did not match api request type");
        }

        Uri builtUri = uriBuilder
                .appendQueryParameter(APIKEY_PARAM, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }

    }

}
