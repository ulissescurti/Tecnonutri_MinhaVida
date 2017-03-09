package br.com.soulskyye.tecnonutri.presenter;

import android.content.Context;

import br.com.soulskyye.tecnonutri.backend.BackendManager;
import br.com.soulskyye.tecnonutri.backend.networkmodel.FeedItemResponse;
import br.com.soulskyye.tecnonutri.model.Item;
import br.com.soulskyye.tecnonutri.view.FeedDetailsView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ulissescurti on 3/8/17.
 */

public class FeedDetailsPresenter implements BasePresenter<FeedDetailsView> {

    private FeedDetailsView mFeedDetailsView;
    private Context context;

    private String feedHash;
    private Item item;

    public FeedDetailsPresenter(Context context){
        this.context = context;
    }

    public void loadFeedDetails(final boolean isFromRefresh){
        if(!isFromRefresh) {
            mFeedDetailsView.showProgressDialog();
        }
        BackendManager.getInstance().getFeedItem(feedHash, new Callback<FeedItemResponse>() {
            @Override
            public void onResponse(Call<FeedItemResponse> call, Response<FeedItemResponse> response) {
                item = response.body().getItem();
                mFeedDetailsView.loadViews(isFromRefresh);
                mFeedDetailsView.hideProgressDialog();
            }

            @Override
            public void onFailure(Call<FeedItemResponse> call, Throwable t) {
                mFeedDetailsView.failureLoadFeedDetails(t);
            }
        });
    }

    @Override
    public void attachView(FeedDetailsView view) {
        mFeedDetailsView = view;
    }

    @Override
    public void detachView() {
        mFeedDetailsView = null;
    }

    public String getFeedHash() {
        return feedHash;
    }

    public void setFeedHash(String feedHash) {
        this.feedHash = feedHash;
    }

    public Item getItem() {
        return item;
    }
}
