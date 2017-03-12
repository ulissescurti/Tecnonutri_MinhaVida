package br.com.soulskyye.tecnonutri.presenter;

import android.content.Context;
import android.os.Bundle;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.google.firebase.analytics.FirebaseAnalytics;

import br.com.soulskyye.tecnonutri.backend.BackendManager;
import br.com.soulskyye.tecnonutri.backend.networkmodel.FeedItemResponse;
import br.com.soulskyye.tecnonutri.model.Item;
import br.com.soulskyye.tecnonutri.view.FeedDetailsView;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ulissescurti on 3/8/17.
 */

public class FeedDetailsPresenter implements BasePresenter<FeedDetailsView>, Logger {

    private FeedDetailsView mFeedDetailsView;
    private Context context;

    private String feedHash;
    private boolean isFromProfile;
    private Item item;

    private Realm realm;

    public FeedDetailsPresenter(Context context){
        this.context = context;
    }

    public void loadFeedDetails(final boolean isFromRefresh){
        if(!isFromRefresh) {
            mFeedDetailsView.showProgressDialog();
            startRealm();
        }
        BackendManager.getInstance().getFeedItem(feedHash, new Callback<FeedItemResponse>() {
            @Override
            public void onResponse(Call<FeedItemResponse> call, Response<FeedItemResponse> response) {
                item = response.body().getItem();

                realm.beginTransaction();
                if(realm.where(Item.class).equalTo("id", item.getId()).findFirst() != null){
                    item.setLiked(true);
                }
                realm.commitTransaction();

                mFeedDetailsView.loadViews(isFromRefresh);
                mFeedDetailsView.hideProgressDialog();
            }

            @Override
            public void onFailure(Call<FeedItemResponse> call, Throwable t) {
                mFeedDetailsView.failureLoadFeedDetails(t);
            }
        });
    }

    private void startRealm(){
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public void changeLikeState(boolean isChecked){
        item.setLiked(isChecked);
        realm.beginTransaction();
        if(item.isLiked()) {
            realm.copyToRealm(item);
        } else{
            realm.where(Item.class).equalTo("id",item.getId()).findAll().deleteAllFromRealm();
        }
        realm.commitTransaction();
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

    public boolean isFromProfile() {
        return isFromProfile;
    }

    public void setFromProfile(boolean isFromProfile) {
        this.isFromProfile = isFromProfile;
    }

    public Item getItem() {
        return item;
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
