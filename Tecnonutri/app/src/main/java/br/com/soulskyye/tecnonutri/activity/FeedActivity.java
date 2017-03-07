package br.com.soulskyye.tecnonutri.activity;

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

import java.util.ArrayList;

import br.com.soulskyye.tecnonutri.R;
import br.com.soulskyye.tecnonutri.adapter.FeedListAdapter;
import br.com.soulskyye.tecnonutri.backend.BackendManager;
import br.com.soulskyye.tecnonutri.entity.Item;
import br.com.soulskyye.tecnonutri.model.FeedResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedActivity extends AppCompatActivity {

    private ArrayList<Item> items;
    private RecyclerView feedRecyclerView;
    private FeedListAdapter feedAdapter;
    private SwipeRefreshLayout swipeRefreshFeeds;

    Callback paginationFeedCallback = new Callback<FeedResponse>() {
        @Override
        public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
            // handle success HTTP request
            Log.i("paginatedFeed", call.request().toString());
            items.addAll(response.body().getItems());
            feedAdapter.insertItems(response.body().getItems().size());

            loadItemRecyclerView(response.body().getP(), response.body().getT());

            BackendManager.getInstance().getPaginatedFeedRunning = false;
        }

        @Override
        public void onFailure(Call<FeedResponse> call, Throwable t) {
            // handle failure HTTP request
            Log.i("paginatedFeed", "Failure");
            BackendManager.getInstance().getPaginatedFeedRunning = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initiateViews();
        loadFirstFeed(false);
    }

    private void initiateViews(){

        feedRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_feed);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        feedRecyclerView.setLayoutManager(llm);

        swipeRefreshFeeds = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_feeds);
        swipeRefreshFeeds.setColorSchemeResources(R.color.colorAccent , R.color.colorPrimary);
        swipeRefreshFeeds.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadFirstFeed(true);
            }
        });
    }

    private void loadFirstFeed(final boolean isFromRefresh){

        BackendManager.getInstance().getFirstFeed(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
                // handle success HTTP request
                Log.i("firstFeed", "Success");

                items = new ArrayList<>(response.body().getItems());
                loadItemRecyclerView(response.body().getP(), response.body().getT());
                if (isFromRefresh){
                    swipeRefreshFeeds.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {
                // handle failure HTTP request
                Log.i("firstFeed", "Failure");
            }
        });
    }

    private void loadItemRecyclerView(int p, int t){

        feedAdapter = new FeedListAdapter(items, this, paginationFeedCallback);
        feedRecyclerView.setAdapter(feedAdapter);
        feedAdapter.p = p;
        feedAdapter.t = t;
    }

}
