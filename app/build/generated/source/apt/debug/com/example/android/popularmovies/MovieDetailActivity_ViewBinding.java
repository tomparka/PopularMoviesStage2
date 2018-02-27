// Generated code from Butter Knife. Do not modify!
package com.example.android.popularmovies;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MovieDetailActivity_ViewBinding<T extends MovieDetailActivity> implements Unbinder {
  protected T target;

  @UiThread
  public MovieDetailActivity_ViewBinding(T target, View source) {
    this.target = target;

    target.scrollView = Utils.findRequiredViewAsType(source, R.id.detail_scroll_view, "field 'scrollView'", ScrollView.class);
    target.mProgressBar = Utils.findRequiredViewAsType(source, R.id.pb_loading_indicator, "field 'mProgressBar'", ProgressBar.class);
    target.mPosterImage = Utils.findRequiredViewAsType(source, R.id.detail_poster, "field 'mPosterImage'", ImageView.class);
    target.mMovieTitle = Utils.findRequiredViewAsType(source, R.id.detail_title, "field 'mMovieTitle'", TextView.class);
    target.mMovieReleaseDate = Utils.findRequiredViewAsType(source, R.id.detail_release_date, "field 'mMovieReleaseDate'", TextView.class);
    target.mMovieRating = Utils.findRequiredViewAsType(source, R.id.detail_rating, "field 'mMovieRating'", TextView.class);
    target.mMoviePlot = Utils.findRequiredViewAsType(source, R.id.detail_plot, "field 'mMoviePlot'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.scrollView = null;
    target.mProgressBar = null;
    target.mPosterImage = null;
    target.mMovieTitle = null;
    target.mMovieReleaseDate = null;
    target.mMovieRating = null;
    target.mMoviePlot = null;

    this.target = null;
  }
}
