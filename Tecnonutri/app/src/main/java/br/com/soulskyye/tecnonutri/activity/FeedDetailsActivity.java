package br.com.soulskyye.tecnonutri.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.soulskyye.tecnonutri.R;
import br.com.soulskyye.tecnonutri.adapter.FeedListAdapter;
import br.com.soulskyye.tecnonutri.backend.BackendManager;
import br.com.soulskyye.tecnonutri.entity.Food;
import br.com.soulskyye.tecnonutri.entity.Item;
import br.com.soulskyye.tecnonutri.model.FeedItemResponse;
import br.com.soulskyye.tecnonutri.model.FeedResponse;
import br.com.soulskyye.tecnonutri.util.DateUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedDetailsActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshFeeds;
    public static final String FEED_HASH = "feedhash";
    private String feedHash;
    private Item item;

    private ImageView authorPhotoIv;
    private TextView authorNameTv;
    private TextView authorGoalTv;
    private ImageView mealPhotoIv;

    private LinearLayout itemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_details);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            feedHash = extras.getString(FEED_HASH);
        }

        loadFeedDetails(false);
    }

    private void loadFeedDetails(final boolean isFromRefresh){
        BackendManager.getInstance().getFeedItem(feedHash, new Callback<FeedItemResponse>() {
            @Override
            public void onResponse(Call<FeedItemResponse> call, Response<FeedItemResponse> response) {
                item = response.body().getItem();
                loadViews(isFromRefresh);
            }

            @Override
            public void onFailure(Call<FeedItemResponse> call, Throwable t) {
                loadViews(isFromRefresh);
            }
        });
    }

    private void loadViews(final boolean isFromRefresh){

        if(isFromRefresh){
            swipeRefreshFeeds.setRefreshing(false);
        }

        swipeRefreshFeeds = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_feed_details);
        authorPhotoIv = (ImageView) findViewById(R.id.feed_item_detail_author_iv);
        authorNameTv = (TextView) findViewById(R.id.feed_item_detail_author_name_tv);
        authorGoalTv = (TextView) findViewById(R.id.feed_item_detail_goal_tv);
        mealPhotoIv = (ImageView) findViewById(R.id.feed_item_detail_meal_iv);
        itemsList = (LinearLayout) findViewById(R.id.feed_item_detail_food_list);

        itemsList.removeAllViews();

        setTitle("Refeição de "+ DateUtils.getFormattedDate(item.getDate()));

        swipeRefreshFeeds.setColorSchemeResources(R.color.colorAccent , R.color.colorPrimary);
        swipeRefreshFeeds.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadFeedDetails(true);
            }
        });

        Picasso.with(this)
                .load(item.getProfile().getPhoto())
                .placeholder(R.drawable.person_placeholder)
                .error(R.drawable.person_placeholder)
                .into(authorPhotoIv);
        Picasso.with(this)
                .load(item.getImage())
                .placeholder(R.drawable.meal_placeholder)
                .error(R.drawable.no_image_found)
                .into(mealPhotoIv);

        authorNameTv.setText(item.getProfile().getName());
        authorGoalTv.setText(item.getProfile().getGoal());

        if(item.getFoods() != null){
            for(Food food : item.getFoods()){

                LinearLayout foodItem;
                LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                foodItem = (LinearLayout) inflater.inflate(R.layout.food_item, null);

                TextView foodNameTv = (TextView)foodItem.findViewById(R.id.food_name);
                TextView foodAmountTv = (TextView)foodItem.findViewById(R.id.food_amount);
                TextView calTv = (TextView)foodItem.findViewById(R.id.food_item_cal);
                TextView cal2Tv = (TextView)foodItem.findViewById(R.id.food_item_cal2);
                TextView carbTv = (TextView)foodItem.findViewById(R.id.food_item_carb);
                TextView carb2Tv = (TextView)foodItem.findViewById(R.id.food_item_carb2);
                TextView protTv = (TextView)foodItem.findViewById(R.id.food_item_prot);
                TextView prot2Tv = (TextView)foodItem.findViewById(R.id.food_item_prot2);
                TextView fatTv = (TextView)foodItem.findViewById(R.id.food_item_fat);
                TextView fat2Tv = (TextView)foodItem.findViewById(R.id.food_item_fat2);

                foodNameTv.setText(food.getDescription());
                foodAmountTv.setText(food.getAmount()+" ("+food.getMeasure()+")");

                cal2Tv.setText(food.getEnergy()+"cal");
                carb2Tv.setText(food.getCarbohydrate()+"g");
                prot2Tv.setText(food.getProtein()+"g");
                fat2Tv.setText(food.getFat()+"g");

                itemsList.addView(foodItem);
            }
        }
    }

}
