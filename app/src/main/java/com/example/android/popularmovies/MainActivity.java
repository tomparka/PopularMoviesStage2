package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.adapters.MovieAdapter;
import com.example.android.popularmovies.data.MovieContract;
import com.example.android.popularmovies.utils.NetworkUtils;
import com.example.android.popularmovies.utils.OpenMovieJsonUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler{

    private MovieAdapter myAdapter;
    private GridLayoutManager movieLayoutManager;

    @BindView(R.id.rv_movie_posters) RecyclerView mRecyclerView;
    @BindView(R.id.tv_error_message_display) TextView mErrorMessageDisplay;
    @BindView(R.id.pb_loading_indicator) ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        int postersPerRow;
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            postersPerRow = 3;
        }
        else{
            postersPerRow = 4;
        }
        movieLayoutManager = new GridLayoutManager(this, postersPerRow);
        mRecyclerView.setLayoutManager(movieLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        myAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(myAdapter);

        loadMovieData("MOST POPULAR");
    }

    public void loadMovieData(String apiRequestType) {
        showMovieDataView();
        if (apiRequestType.equals("FAVORITES")) {
            Cursor dbCursor = getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, null, null, null, null);
            myAdapter.setMovieData(cursorTo2dArray(dbCursor));
            dbCursor.close();
        } else {
            new MovieDataTask().execute(apiRequestType);
        }
    }

    public String[][] cursorTo2dArray(Cursor cursor) {
        if (cursor.getCount() < 1) {return null;}

        ArrayList<String[]> arrayList = new ArrayList<String[]>();
        while (cursor.moveToNext()) {
            arrayList.add(new String[]{
                    cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE)),
                    cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RATING)),
                    cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER)),
                    cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_PLOT)),
                    cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID))});
        }

        return arrayList.toArray(new String[arrayList.size()][5]);
    }

    private void showMovieDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessageView() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(String[] movieInfo) {
        Context context = this;
        Class destinationClass = MovieDetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, movieInfo);
        startActivity(intentToStartDetailActivity);
    }

    public class MovieDataTask extends AsyncTask<String, Void, String[][]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[][] doInBackground(String... apiRequestType) {
            if (apiRequestType.length == 0) {
                return null;
            }

            URL movieRequestUrl = NetworkUtils.buildUrl(apiRequestType[0]);

            try {
                String jsonMovieResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieRequestUrl);

                String[][] simpleJsonMovieData = OpenMovieJsonUtils
                        .getSimpleMovieStringsFromJson(MainActivity.this, jsonMovieResponse);

                return simpleJsonMovieData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[][] movieData) {
            super.onPostExecute(movieData);
            mProgressBar.setVisibility(View.INVISIBLE);
            if (movieData != null) {
                showMovieDataView();
                Log.d("ONPOSTEXECUTE CALLED", "movieData length = " + movieData.length);
                myAdapter.setMovieData(movieData);

            } else {
                showErrorMessageView();
                Log.d("onPostExecute", "errorMessageTriggered");
//                ContentResolver resolver = getContentResolver();
//                Cursor cursor = resolver.query(MovieContract.MovieEntry.CONTENT_URI, null, null, null, null);
//                myAdapter.setMovieData(cursor);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.sort_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sort_by_type) {
            String currentTitle = (String) item.getTitle();
            String[] sortTypeArray = getResources().getStringArray(R.array.sort_by_array);
            int nextIndex =
                    (Arrays.asList(sortTypeArray).indexOf(currentTitle) + 1) % sortTypeArray.length;
            item.setTitle(sortTypeArray[nextIndex]);
            loadMovieData(sortTypeArray[nextIndex]);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
