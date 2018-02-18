package com.example.android.popularmovies;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.DropBoxManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.data.MovieContract;
import com.example.android.popularmovies.utils.NetworkUtils;
import com.example.android.popularmovies.utils.OpenMovieJsonUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener{

    String[] mMovieArray;

    ProgressBar mProgressBar;

    ImageView mPosterImage;
    TextView mMovieTitle;
    TextView mMovieReleaseDate;
    TextView mMovieRating;
    TextView mMoviePlot;
    String mPosterImageUrl;
    String mMovieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        //TODO: check if movie is in favorites database and display appropriate favorites drawable
        // checks whether movie title is already in the database

        mProgressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        mPosterImage = (ImageView) findViewById(R.id.detail_poster);
        mMovieTitle = (TextView) findViewById(R.id.detail_title);
        mMovieReleaseDate = (TextView) findViewById(R.id.detail_release_date);
        mMovieRating = (TextView) findViewById(R.id.detail_rating);
        mMoviePlot = (TextView) findViewById(R.id.detail_plot);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                mMovieArray = intentThatStartedThisActivity.getStringArrayExtra(Intent.EXTRA_TEXT);

                mMovieTitle.setText(mMovieArray[0]);
                mMovieReleaseDate.setText(mMovieArray[1]);
                mMovieRating.setText(mMovieArray[2]);
                mPosterImageUrl = mMovieArray[3];
                Picasso.with(this).load(mPosterImageUrl).into(mPosterImage);
                mMoviePlot.setText(mMovieArray[4]);
                mMovieId = mMovieArray[5];
            }
        }

        new MovieTrailerTask().execute("VIDEOS");
    }

    public void addTrailersToLayout(String[] trailers) {
        //TODO: add click listener with intent to open video in youtube

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup linearLayout = (ViewGroup) findViewById(R.id.detail_linear_layout);
        for (int i = 0; i < trailers.length; i++) {
            View view = inflater.inflate(R.layout.trailer_item, linearLayout, false);
            view.setOnClickListener(this);
            view.setTag(trailers[i]);
            linearLayout.addView(view);
        }
    }

    @Override
    public void onClick(View view) {
        String url = (String) view.getTag();
        Intent youtubeIntent = new Intent(Intent.ACTION_VIEW);
        youtubeIntent.setData(Uri.parse(url));
        startActivity(youtubeIntent);
    }

    public class MovieTrailerTask extends AsyncTask<String, Void, String[]> {
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

            URL movieTrailerRequestUrl = NetworkUtils.buildUrl(apiRequestType[0], mMovieId);

            try {
                String jsonMovieTrailerResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieTrailerRequestUrl);

                String[] jsonMovieTrailerData = OpenMovieJsonUtils
                        .getMovieTrailerStringsFromJson(MovieDetailActivity.this, jsonMovieTrailerResponse);

                return jsonMovieTrailerData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] movieTrailerData) {
            super.onPostExecute(movieTrailerData);
            mProgressBar.setVisibility(View.INVISIBLE);
            if (movieTrailerData != null) {
                addTrailersToLayout(movieTrailerData);
                Log.d("ONPOSTEXECUTE CALLED", "movieData length = " + movieTrailerData.length);

            } else {
                Log.d("onPostExecute", "errorMessageTriggered");
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);

        if (getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                new String[]{MovieContract.MovieEntry.COLUMN_TITLE},
                MovieContract.MovieEntry.COLUMN_TITLE + "='" + mMovieArray[0] +"'",
                null, null).getCount() > 0 ) {

            MenuItem starItem = menu.findItem(R.id.action_favorite);
            starItem.setIcon(R.drawable.ic_favorited_star_24px);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                ContentResolver resolver = getContentResolver();


                Drawable.ConstantState currentIconConstantState = item.getIcon().getConstantState();
                Drawable.ConstantState resourceConstantState = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_unfavorited_star_24px, null).getConstantState();
                if(currentIconConstantState.equals(resourceConstantState)) {
                    item.setIcon(R.drawable.ic_favorited_star_24px);

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(MovieContract.MovieEntry.COLUMN_TITLE, mMovieTitle.getText().toString());
                    contentValues.put(MovieContract.MovieEntry.COLUMN_RATING, mMovieRating.getText().toString());
                    contentValues.put(MovieContract.MovieEntry.COLUMN_PLOT, mMoviePlot.getText().toString());
                    contentValues.put(MovieContract.MovieEntry.COLUMN_POSTER, mPosterImageUrl);
                    contentValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, mMovieReleaseDate.getText().toString());
                    contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, mMovieArray[5]);
                    //contentValues.put(MovieContract.MovieEntry.COLUMN_REVIEWS, );
                    //contentValues.put(MovieContract.MovieEntry.COLUMN_TRAILER, );

                    resolver.insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);
                    Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
                } else {
                    item.setIcon(R.drawable.ic_unfavorited_star_24px);
                    String currentMovieTitle = mMovieTitle.getText().toString();
                    int rowsDeleted = resolver.delete(MovieContract.MovieEntry.CONTENT_URI, MovieContract.MovieEntry.COLUMN_TITLE + "=?" , new String[] {currentMovieTitle});
                    Toast.makeText(this, "Rows deleted: " + Integer.toString(rowsDeleted), Toast.LENGTH_SHORT).show();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
