package br.com.soulskyye.tecnonutri.view.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.mopub.mobileads.MoPubView;

import br.com.soulskyye.tecnonutri.R;
import br.com.soulskyye.tecnonutri.presenter.adapter.FeedListAdapter;
import br.com.soulskyye.tecnonutri.presenter.FeedPresenter;
import br.com.soulskyye.tecnonutri.util.Utils;
import br.com.soulskyye.tecnonutri.view.FeedView;

public class FeedActivity extends BaseActivity implements FeedView {

    private FeedPresenter mFeedPresenter;

    private RecyclerView feedRecyclerView;
    private SwipeRefreshLayout swipeRefreshFeeds;
    private Context context;

    private MoPubView moPubView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        context = this;

        mFeedPresenter = new FeedPresenter(context);
        mFeedPresenter.attachView(this);

        configureViews();
    }

    private void configureViews(){

        moPubView = (MoPubView) findViewById(R.id.adview);
        moPubView.setAdUnitId(SMALL_BANNER_ID);
        moPubView.setBannerAdListener(this);
        moPubView.loadAd();

        feedRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_feed);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        feedRecyclerView.setLayoutManager(llm);

        swipeRefreshFeeds = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_feeds);
        swipeRefreshFeeds.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshFeeds.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mFeedPresenter.loadFirstFeed(true);
            }
        });

        mFeedPresenter.loadFirstFeed(false);
    }

    @Override
    public void onDestroy() {
        moPubView.destroy();
        mFeedPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void hideRefreshDialog() {
        if(swipeRefreshFeeds != null) {
            swipeRefreshFeeds.setRefreshing(false);
        }
    }

    @Override
    public void hideProgressDialog() {
        Utils.hideProgressDialog();
    }

    @Override
    public void showProgressDialog() {
        Utils.showProgressDialog(context);
    }

    @Override
    public void failureLoadFeed(Throwable t) {
        Utils.hideProgressDialog();
        hideRefreshDialog();
        Utils.showErrorPopup(context, t);
    }

    @Override
    public void setAdapter(FeedListAdapter adapter) {
        feedRecyclerView.setAdapter(adapter);
    }
}
