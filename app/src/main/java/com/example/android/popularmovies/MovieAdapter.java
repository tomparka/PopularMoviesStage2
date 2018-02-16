package com.example.android.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tp293 on 10/5/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private String[][] mMovieData;
    private final MovieAdapterOnClickHandler mClickHandler;
    private Context mContext;
    private Cursor mCursor;

    public interface MovieAdapterOnClickHandler {
        void onClick (String[] movieInfo);
    }

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        LayoutInflater layoutInflater = LayoutInflater.from(mContext); //why get layoutInflater from mContext?
        int viewHolderLayout = R.layout.movie_item;

        View view = layoutInflater.inflate(viewHolderLayout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        int posterPathIndex = 3;
        String fullPosterPath = mMovieData[position][posterPathIndex];
        Picasso.with(mContext)
                .load(fullPosterPath)
                .into(holder.mPosterImageView);
    }

    @Override
    public int getItemCount() {
        if (mMovieData == null) {
            return 0;
        } else {
            return mMovieData.length;
        }
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv_movie_poster) ImageView mPosterImageView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            String[] movieArray = mMovieData[adapterPosition];
            mClickHandler.onClick(movieArray);
        }
    }

    public void setMovieData(String[][] movieData) {
        mMovieData = movieData;
        notifyDataSetChanged();
    }

}
