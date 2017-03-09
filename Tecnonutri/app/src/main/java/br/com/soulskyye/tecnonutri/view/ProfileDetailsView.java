package br.com.soulskyye.tecnonutri.view;

import android.content.Context;

import br.com.soulskyye.tecnonutri.presenter.adapter.FeedListAdapter;
import br.com.soulskyye.tecnonutri.presenter.adapter.ProfileItemsListAdapter;

/**
 * Created by ulissescurti on 3/8/17.
 */

public interface ProfileDetailsView {

    void hideRefreshDialog();

    void hideProgressDialog();

    void showProgressDialog();

    void failureLoadProfile(Throwable t);

    void setAdapter(ProfileItemsListAdapter adapter);

    void loadViews(final boolean isFromRefresh, int p, int t);
}
