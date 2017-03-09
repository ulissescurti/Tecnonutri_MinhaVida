package br.com.soulskyye.tecnonutri.view.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.soulskyye.tecnonutri.R;
import br.com.soulskyye.tecnonutri.model.Food;
import br.com.soulskyye.tecnonutri.model.Item;
import br.com.soulskyye.tecnonutri.model.Profile;
import br.com.soulskyye.tecnonutri.presenter.FeedDetailsPresenter;
import br.com.soulskyye.tecnonutri.util.Utils;
import br.com.soulskyye.tecnonutri.view.FeedDetailsView;

public class FeedDetailsActivity extends BaseActivity implements FeedDetailsView {

    public static final String FEED_HASH = "feedhash";
    private FeedDetailsPresenter mFeedDetailsPresenter;

    private Context context;

    private SwipeRefreshLayout swipeRefreshFeeds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_details);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        context = this;

        mFeedDetailsPresenter = new FeedDetailsPresenter(context);
        mFeedDetailsPresenter.attachView(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mFeedDetailsPresenter.setFeedHash(extras.getString(FEED_HASH));
        }

        showInterstitialAd();
        setUpSwipeToRefresh();

        mFeedDetailsPresenter.loadFeedDetails(false);
    }

    private void setUpSwipeToRefresh(){
        swipeRefreshFeeds = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_feed_details);
        swipeRefreshFeeds.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshFeeds.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mFeedDetailsPresenter.loadFeedDetails(true);
            }
        });
    }

    @Override
    public void loadViews(final boolean isFromRefresh){

        final Item item = mFeedDetailsPresenter.getItem();

        LinearLayout profileLayout = (LinearLayout) findViewById(R.id.feed_item_detail_first_layout);
        ImageView authorPhotoIv = (ImageView) findViewById(R.id.feed_item_detail_author_iv);
        TextView authorNameTv = (TextView) findViewById(R.id.feed_item_detail_author_name_tv);
        TextView authorGoalTv = (TextView) findViewById(R.id.feed_item_detail_goal_tv);
        ImageView mealPhotoIv = (ImageView) findViewById(R.id.feed_item_detail_meal_iv);
        LinearLayout itemsList = (LinearLayout) findViewById(R.id.feed_item_detail_food_list);

        if(isFromRefresh){
            hideRefreshDialog();
        }

        itemsList.removeAllViews();

        setTitle(getString(R.string.meal_of)+Utils.getFormattedDate(item.getDate()));

        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile profile = item.getProfile();
                Intent intent = new Intent(context, ProfileDetailsActivity.class);
                intent.putExtra(ProfileDetailsActivity.PROFILE_ID, profile.getId());
                startActivity(intent);
            }
        });

        Picasso.with(this)
                .load(item.getProfile().getPhoto())
                .placeholder(R.drawable.person_placeholder)
                .error(R.drawable.ic_image_not_found)
                .into(authorPhotoIv);
        Picasso.with(this)
                .load(item.getImage())
                .placeholder(R.drawable.meal_placeholder)
                .error(R.drawable.ic_image_not_found)
                .into(mealPhotoIv);

        authorNameTv.setText(item.getProfile().getName());
        authorGoalTv.setText(item.getProfile().getGoal());

        LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(item.getFoods() != null){
            for(Food food : item.getFoods()){

                LinearLayout foodItem;
                foodItem = (LinearLayout) inflater.inflate(R.layout.food_item, null);

                TextView foodNameTv = (TextView)foodItem.findViewById(R.id.food_name);
                TextView foodAmountTv = (TextView)foodItem.findViewById(R.id.food_amount);
                TextView cal2Tv = (TextView)foodItem.findViewById(R.id.food_item_cal2);
                TextView carb2Tv = (TextView)foodItem.findViewById(R.id.food_item_carb2);
                TextView prot2Tv = (TextView)foodItem.findViewById(R.id.food_item_prot2);
                TextView fat2Tv = (TextView)foodItem.findViewById(R.id.food_item_fat2);

                foodNameTv.setText(food.getDescription());
                foodAmountTv.setText(Utils.getFormattedNumber(food.getAmount())+" ("+food.getMeasure()+")");

                cal2Tv.setText(getString(R.string.measure_text, Utils.getFormattedNumber(food.getEnergy()), getString(R.string.kcal)));
                carb2Tv.setText(getString(R.string.measure_text, Utils.getFormattedNumber(food.getCarbohydrate()), getString(R.string.g)));
                prot2Tv.setText(getString(R.string.measure_text, Utils.getFormattedNumber(food.getProtein()), getString(R.string.g)));
                fat2Tv.setText(getString(R.string.measure_text, Utils.getFormattedNumber(food.getFat()), getString(R.string.g)));

                itemsList.addView(foodItem);
            }
        }

        LinearLayout foodItemTotal;
        foodItemTotal = (LinearLayout) inflater.inflate(R.layout.food_item_total, null);

        TextView foodTotalTv = (TextView)foodItemTotal.findViewById(R.id.total_text_view);
        TextView cal2Tv = (TextView)foodItemTotal.findViewById(R.id.food_item_cal2);
        TextView carb2Tv = (TextView)foodItemTotal.findViewById(R.id.food_item_carb2);
        TextView prot2Tv = (TextView)foodItemTotal.findViewById(R.id.food_item_prot2);
        TextView fat2Tv = (TextView)foodItemTotal.findViewById(R.id.food_item_fat2);

        foodTotalTv.setText(R.string.total_consumed);
        cal2Tv.setText(getString(R.string.measure_text, Utils.getFormattedNumber(item.getEnergy()), getString(R.string.kcal)));
        carb2Tv.setText(getString(R.string.measure_text, Utils.getFormattedNumber(item.getCarbohydrate()), getString(R.string.g)));
        prot2Tv.setText(getString(R.string.measure_text, Utils.getFormattedNumber(item.getProtein()), getString(R.string.g)));
        fat2Tv.setText(getString(R.string.measure_text, Utils.getFormattedNumber(item.getFat()), getString(R.string.g)));

        itemsList.addView(foodItemTotal);
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
    public void failureLoadFeedDetails(Throwable t) {
        Utils.hideProgressDialog();
        hideRefreshDialog();
        Utils.showErrorPopup(context, t);
    }
}
