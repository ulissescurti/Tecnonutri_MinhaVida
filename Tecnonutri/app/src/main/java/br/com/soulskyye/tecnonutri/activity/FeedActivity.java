package br.com.soulskyye.tecnonutri.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import br.com.soulskyye.tecnonutri.R;
import br.com.soulskyye.tecnonutri.backend.BackendManager;
import br.com.soulskyye.tecnonutri.entity.Item;
import br.com.soulskyye.tecnonutri.model.FeedResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedActivity extends AppCompatActivity {

    private ArrayList<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadFirstFeed();
    }

    private void loadFirstFeed(){

        BackendManager.getInstance().getFirstFeed(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
                // handle success HTTP request

                items = new ArrayList<>(response.body().getItems());
                Log.i("firstFeed", "Success");
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {
                // handle failure HTTP request
                Log.i("firstFeed", "Failure");
            }
        });
    }

}
