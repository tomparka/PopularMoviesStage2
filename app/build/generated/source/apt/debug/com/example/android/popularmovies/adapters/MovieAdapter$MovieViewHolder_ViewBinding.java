// Generated code from Butter Knife. Do not modify!
package com.example.android.popularmovies.adapters;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.android.popularmovies.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MovieAdapter$MovieViewHolder_ViewBinding<T extends MovieAdapter.MovieViewHolder> implements Unbinder {
  protected T target;

  @UiThread
  public MovieAdapter$MovieViewHolder_ViewBinding(T target, View source) {
    this.target = target;

    target.mPosterImageView = Utils.findRequiredViewAsType(source, R.id.iv_movie_poster, "field 'mPosterImageView'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.mPosterImageView = null;

    this.target = null;
  }
}
