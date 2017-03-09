package br.com.soulskyye.tecnonutri.view;

import android.content.Context;

import br.com.soulskyye.tecnonutri.presenter.adapter.FeedListAdapter;

/**
 * Created by ulissescurti on 3/8/17.
 */

public interface FeedView {

    void hideRefreshDialog();

    void hideProgressDialog();

    void showProgressDialog();

    void failureLoadFeed(Throwable t);

    void setAdapter(FeedListAdapter adapter);
}
