package com.example.android.popularmovies.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by tp293 on 10/10/2017.
 */

public final class OpenMovieJsonUtils {
    /*
     * @param forecastJsonStr JSON response from server
     *
     * @return Array of Strings describing weather data
     *
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static String[][] getSimpleMovieStringsFromJson(Context context, String movieJsonStr)
            throws JSONException {

        /* Movie info found in Array called "results" */
        final String BASEPOSTERURL = "http://image.tmdb.org/t/p/w185";
        final String OMJ_RESULTS = "results";

        /* String array to hold each movie's info String */
        String[][] parsedMovieData = null;

        JSONObject movieJson = new JSONObject(movieJsonStr);
        JSONArray movieArray = movieJson.getJSONArray(OMJ_RESULTS);

        parsedMovieData = new String[movieArray.length()][5];

        for (int i = 0; i < movieArray.length(); i++) {

            String title;
            String releaseDate;
            String rating;
            String posterPath;
            String plot;
            String id;

            /* Get the JSON object representing a movie */
            JSONObject movieInfo = movieArray.getJSONObject(i);

            title = movieInfo.getString("original_title");
            releaseDate = movieInfo.getString("release_date");
            rating = movieInfo.getString("vote_average");

            posterPath = movieInfo.getString("poster_path");
            String posterFullPath = BASEPOSTERURL + posterPath;

            plot = movieInfo.getString("overview");
            id = movieInfo.getString("id");

            String[] movieInfoArray = {title, releaseDate, rating, posterFullPath, plot, id};
            parsedMovieData[i] = movieInfoArray;
        }

        return parsedMovieData;
    }

    public static String[] getMovieTrailerStringsFromJson(Context context, String movieTrailerJsonString)
            throws JSONException {

        final String YOUTUBE = "YouTube";
        final String BASEYOUTUBEURL = "https://www.youtube.com/watch?v=";
        /* Movie info found in Array called "results" */
        final String OMJ_RESULTS = "results";

        /* String array to hold each movie's info String */
        ArrayList<String> movieTrailerData = new ArrayList<>();

        JSONObject movieTrailerJson = new JSONObject(movieTrailerJsonString);
        JSONArray movieTrailersArray = movieTrailerJson.getJSONArray(OMJ_RESULTS);

        for (int i = 0; i < movieTrailersArray.length(); i++) {

            String key;

            /* Get the JSON object representing a movie */
            JSONObject videoItem = movieTrailersArray.getJSONObject(i);

            if (videoItem.getString("site").equals(YOUTUBE)) {
                key = videoItem.getString("key");
                movieTrailerData.add(BASEYOUTUBEURL + key);
            }
        }

        return movieTrailerData.toArray(new String[movieTrailerData.size()]);
    }


    public static String[] getMovieReviewStringsFromJson(Context context, String movieReviewJsonString)
            throws JSONException {

        /* Movie info found in Array called "results" */
        final String OMJ_RESULTS = "results";

        /* String array to hold each movie's info String */
        String[] movieReviewData = null;

        JSONObject movieReviewJson = new JSONObject(movieReviewJsonString);
        JSONArray movieReviewsArray = movieReviewJson.getJSONArray(OMJ_RESULTS);

        movieReviewData = new String[movieReviewsArray.length()];

        for (int i = 0; i < movieReviewsArray.length(); i++) {

            String author;
            String content;

            /* Get the JSON object representing a movie */
            JSONObject reviewItem = movieReviewsArray.getJSONObject(i);

            author = reviewItem.getString("author");
            content = reviewItem.getString("content");

            movieReviewData[i] = content;
        }

        return movieReviewData;
    }

}
