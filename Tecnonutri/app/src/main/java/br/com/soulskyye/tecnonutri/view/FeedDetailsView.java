package br.com.soulskyye.tecnonutri.view;

import br.com.soulskyye.tecnonutri.presenter.adapter.ProfileItemsListAdapter;

/**
 * Created by ulissescurti on 3/8/17.
 */

public interface FeedDetailsView {

    void hideRefreshDialog();

    void hideProgressDialog();

    void showProgressDialog();

    void failureLoadFeedDetails(Throwable t);

    void loadViews(final boolean isFromRefresh);
}
