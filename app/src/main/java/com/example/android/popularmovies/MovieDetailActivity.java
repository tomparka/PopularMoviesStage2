package com.example.android.popularmovies;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.DropBoxManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.data.MovieContract;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    String[] mMovieArray;

    ImageView mPosterImage;
    TextView mMovieTitle;
    TextView mMovieReleaseDate;
    TextView mMovieRating;
    TextView mMoviePlot;
    String mPosterImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

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
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                ContentResolver resolver = getContentResolver();
                Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();

                Drawable.ConstantState currentIconConstantState = item.getIcon().getConstantState();
                Drawable.ConstantState resourceConstantState = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_unfavorited_star_24px, null).getConstantState();
                if(currentIconConstantState.equals(resourceConstantState)) {
                    item.setIcon(R.drawable.ic_favorited_star_24px);
                    //TODO: add movie to favorites
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(MovieContract.MovieEntry.COLUMN_TITLE, mMovieTitle.getText().toString());
                    contentValues.put(MovieContract.MovieEntry.COLUMN_RATING, mMovieRating.getText().toString());
                    contentValues.put(MovieContract.MovieEntry.COLUMN_PLOT, mMoviePlot.getText().toString());
                    contentValues.put(MovieContract.MovieEntry.COLUMN_POSTER, mPosterImageUrl);
                    contentValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, mMovieReleaseDate.getText().toString());
                    //contentValues.put(MovieContract.MovieEntry.COLUMN_REVIEWS, );
                    //contentValues.put(MovieContract.MovieEntry.COLUMN_TRAILER, );


                    resolver.insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);
                } else {
                    item.setIcon(R.drawable.ic_unfavorited_star_24px);
                    //TODO: remove movie from favorites
                    String currentMovieTitle = mMovieTitle.getText().toString();
                    int rowsDeleted = resolver.delete(MovieContract.MovieEntry.CONTENT_URI, MovieContract.MovieEntry.COLUMN_TITLE + "=?" , new String[] {currentMovieTitle});
                    Toast.makeText(this, "Rows deleted: " + Integer.toString(rowsDeleted), Toast.LENGTH_SHORT).show();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
