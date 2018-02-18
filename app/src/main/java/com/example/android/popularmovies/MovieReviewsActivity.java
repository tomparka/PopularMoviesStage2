package com.example.android.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.popularmovies.adapters.ReviewAdapter;
import com.example.android.popularmovies.utils.NetworkUtils;
import com.example.android.popularmovies.utils.OpenMovieJsonUtils;

import java.net.URL;

public class MovieReviewsActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ReviewAdapter myAdapter;
    ProgressBar mProgressBar;

    String mMovieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_reviews);

        mMovieId = getIntent().getStringExtra(Intent.EXTRA_TEXT);

        mProgressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_reviews);
        myAdapter = new ReviewAdapter();
        mRecyclerView.setAdapter(myAdapter);

        new MovieReviewsTask().execute("REVIEWS");
    }

    public class MovieReviewsTask extends AsyncTask<String, Void, String[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... apiRequestType) {
            if (apiRequestType.length == 0) {
                return null;
            }

            URL movieReviewsRequestUrl = NetworkUtils.buildUrl(apiRequestType[0], mMovieId);

            try {
                String jsonMovieReviewsResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieReviewsRequestUrl);

                String[] jsonMovieReviewsData = OpenMovieJsonUtils
                        .getMovieReviewStringsFromJson(MovieReviewsActivity.this, jsonMovieReviewsResponse);

                return jsonMovieReviewsData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] movieReviewsData) {
            super.onPostExecute(movieReviewsData);
            mProgressBar.setVisibility(View.INVISIBLE);
            if (movieReviewsData != null) {
                myAdapter.setReviewData(movieReviewsData);
                Log.d("ONPOSTEXECUTE CALLED", "movieData length = " + movieReviewsData.length);

            } else {
                Log.d("onPostExecute", "errorMessageTriggered");
            }
        }
    }

}
