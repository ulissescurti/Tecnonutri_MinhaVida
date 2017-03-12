package br.com.soulskyye.tecnonutri.presenter;

import android.content.Context;
import android.os.Bundle;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

import br.com.soulskyye.tecnonutri.backend.BackendManager;
import br.com.soulskyye.tecnonutri.backend.networkmodel.FeedResponse;
import br.com.soulskyye.tecnonutri.model.Item;
import br.com.soulskyye.tecnonutri.presenter.adapter.FeedListAdapter;
import br.com.soulskyye.tecnonutri.view.FeedView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ulissescurti on 3/8/17.
 */

public class FeedPresenter implements BasePresenter<FeedView>, Logger {

    private FeedView mFeedView;
    private ArrayList<Item> items;
    private Context context;
    private FeedListAdapter feedAdapter;

    public FeedPresenter(Context context){
        this.context = context;
    }

    public void loadFirstFeed(final boolean isFromRefresh){
        if(!isFromRefresh) {
            mFeedView.showProgressDialog();
        }
        BackendManager.getInstance().getFirstFeed(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {

                items = new ArrayList<>(response.body().getItems());
                if (isFromRefresh){
                    mFeedView.hideRefreshDialog();
                }
                loadItemRecyclerView(false, response.body().getP(), response.body().getT());
                mFeedView.hideProgressDialog();
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {
                mFeedView.failureLoadFeed(t);
            }
        });
    }

    private Callback<FeedResponse> getPaginationFeedCallback(){
        return new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {

                items.addAll(response.body().getItems());
                feedAdapter.insertItems(response.body().getItems().size());
                if(response.body().getItems() == null || response.body().getItems().size() == 0){
                    loadItemRecyclerView(true, response.body().getP(), response.body().getT());
                } else{
                    loadItemRecyclerView(false, response.body().getP(), response.body().getT());
                }
                mFeedView.hideProgressDialog();
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {
                mFeedView.failureLoadFeed(t);
            }
        };
    }

    private void loadItemRecyclerView(boolean paginationEnded, int p, int t){

        feedAdapter = new FeedListAdapter(items, context, getPaginationFeedCallback(), this);
        feedAdapter.loadFinished = paginationEnded;
        mFeedView.setAdapter(feedAdapter);
        feedAdapter.p = p;
        feedAdapter.t = t;
    }

    @Override
    public void attachView(FeedView view) {
        mFeedView = view;
    }

    @Override
    public void detachView() {
        mFeedView = null;
    }

    public void reloadItems(){
        if(feedAdapter != null) feedAdapter.notifyDataSetChanged();
    }

    @Override
    public void logAnalyticsItemLikeChanged(Item item, boolean isLiked) {
        Bundle bundle = new Bundle();
        bundle.putLong(FirebaseAnalytics.Param.ITEM_ID, item.getId());
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, item.getMealName());
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, item.getImage());
        bundle.putBoolean("liked", isLiked);
        FirebaseAnalytics.getInstance(context).logEvent("meal_likes", bundle);
    }

    @Override
    public void logAnswersItemLikeChanged(Item item, boolean isLiked) {
        Answers.getInstance().logCustom(new CustomEvent("Meal Likes")
                .putCustomAttribute("Liked", isLiked ? 1 : 0)
                .putCustomAttribute("Item Id", item.getId())
                .putCustomAttribute("Item Name", item.getMealName())
                .putCustomAttribute("Item Image", item.getImage()));
    }

}
