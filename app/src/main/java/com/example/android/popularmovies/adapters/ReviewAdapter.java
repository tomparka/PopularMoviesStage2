package com.example.android.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;

import org.w3c.dom.Text;

/**
 * Created by tp293 on 2/17/2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private Context mContext;
    private String[] mReviews;
    private int mReviewNumber = 0;

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {mContext = parent.getContext();}
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        int viewHolderLayout = R.layout.review_item;

        View view = layoutInflater.inflate(viewHolderLayout, parent, false);
        return new ReviewAdapter.ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        mReviewNumber ++;
        holder.reviewTitleTextView.setText("Review " + (position + 1));
        holder.reviewTextView.setText(mReviews[position]);
    }

    @Override
    public int getItemCount() {
        if(mReviews == null) {
            return 0;
        } else {
            return mReviews.length;
        }
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder{

        TextView reviewTextView;
        TextView reviewTitleTextView;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            reviewTextView = itemView.findViewById(R.id.tv_review);
            reviewTitleTextView = itemView.findViewById(R.id.tv_review_title);
        }
    }

    public void setReviewData(String[] reviewData) {
        mReviews = reviewData;
        notifyDataSetChanged();
    }

}
