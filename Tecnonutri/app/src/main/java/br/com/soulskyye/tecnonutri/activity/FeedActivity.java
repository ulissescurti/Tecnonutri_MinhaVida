package br.com.soulskyye.tecnonutri.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubView;

import java.util.ArrayList;

import br.com.soulskyye.tecnonutri.R;
import br.com.soulskyye.tecnonutri.adapter.FeedListAdapter;
import br.com.soulskyye.tecnonutri.backend.BackendManager;
import br.com.soulskyye.tecnonutri.entity.Item;
import br.com.soulskyye.tecnonutri.model.FeedResponse;
import br.com.soulskyye.tecnonutri.util.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedActivity extends BaseActivity implements MoPubView.BannerAdListener {

    private ArrayList<Item> items;
    private RecyclerView feedRecyclerView;
    private FeedListAdapter feedAdapter;
    private SwipeRefreshLayout swipeRefreshFeeds;
    private Context context;

    private MoPubView moPubView;

    Callback paginationFeedCallback = new Callback<FeedResponse>() {
        @Override
        public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
            // handle success HTTP request
            Log.i("paginatedFeed", call.request().toString());

            items.addAll(response.body().getItems());
            feedAdapter.insertItems(response.body().getItems().size());

            loadItemRecyclerView(response.body().getP(), response.body().getT());
            Utils.hideProgressDialog();
        }

        @Override
        public void onFailure(Call<FeedResponse> call, Throwable t) {
            // handle failure HTTP request
            Log.i("paginatedFeed", "Failure");
            Utils.hideProgressDialog();
            Utils.showErrorPopup(context, t);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

        moPubView = (MoPubView) findViewById(R.id.adview);
        moPubView.setAdUnitId(SMALL_BANNER_ID);
        moPubView.setBannerAdListener(this);
        moPubView.loadAd();

        initiateViews();
        loadFirstFeed(false);
    }

    private void initiateViews(){

        feedRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_feed);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        feedRecyclerView.setLayoutManager(llm);

        swipeRefreshFeeds = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_feeds);
        swipeRefreshFeeds.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshFeeds.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadFirstFeed(true);
            }
        });
    }

    private void loadFirstFeed(final boolean isFromRefresh){
        if(!isFromRefresh) {
            Utils.showProgressDialog(this);
        }
        BackendManager.getInstance().getFirstFeed(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
                // handle success HTTP request
                Log.i("firstFeed", "Success");

                items = new ArrayList<>(response.body().getItems());
                if (isFromRefresh){
                    swipeRefreshFeeds.setRefreshing(false);
                }
                loadItemRecyclerView(response.body().getP(), response.body().getT());
                Utils.hideProgressDialog();
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {
                // handle failure HTTP request
                Log.i("firstFeed", "Failure");
                Utils.hideProgressDialog();
                swipeRefreshFeeds.setRefreshing(false);
                Utils.showErrorPopup(context, t);
            }
        });
    }

    private void loadItemRecyclerView(int p, int t){

        feedAdapter = new FeedListAdapter(items, this, paginationFeedCallback);
        feedRecyclerView.setAdapter(feedAdapter);
        feedAdapter.p = p;
        feedAdapter.t = t;
    }

    @Override
    public void onDestroy() {
        moPubView.destroy();
        super.onDestroy();
    }

    @Override
    public void onBannerLoaded(MoPubView banner) {
        
    }

    @Override
    public void onBannerFailed(MoPubView banner, MoPubErrorCode errorCode) {

    }

    @Override
    public void onBannerClicked(MoPubView banner) {

    }

    @Override
    public void onBannerExpanded(MoPubView banner) {

    }

    @Override
    public void onBannerCollapsed(MoPubView banner) {

    }
}
