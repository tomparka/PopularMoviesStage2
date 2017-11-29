// Generated code from Butter Knife. Do not modify!
package com.example.android.popularmovies;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding<T extends MainActivity> implements Unbinder {
  protected T target;

  @UiThread
  public MainActivity_ViewBinding(T target, View source) {
    this.target = target;

    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.rv_movie_posters, "field 'mRecyclerView'", RecyclerView.class);
    target.mErrorMessageDisplay = Utils.findRequiredViewAsType(source, R.id.tv_error_message_display, "field 'mErrorMessageDisplay'", TextView.class);
    target.mProgressBar = Utils.findRequiredViewAsType(source, R.id.pb_loading_indicator, "field 'mProgressBar'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.mRecyclerView = null;
    target.mErrorMessageDisplay = null;
    target.mProgressBar = null;

    this.target = null;
  }
}
