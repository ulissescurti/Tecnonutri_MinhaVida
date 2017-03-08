package br.com.soulskyye.tecnonutri.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.soulskyye.tecnonutri.R;
import br.com.soulskyye.tecnonutri.adapter.ProfileItemsListAdapter;
import br.com.soulskyye.tecnonutri.backend.BackendManager;
import br.com.soulskyye.tecnonutri.entity.Item;
import br.com.soulskyye.tecnonutri.entity.Profile;
import br.com.soulskyye.tecnonutri.model.ProfileResponse;
import br.com.soulskyye.tecnonutri.util.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileDetailsActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshProfile;
    private RecyclerView profileRecyclerView;
    private ProfileItemsListAdapter profileAdapter;
    public static final String PROFILE_ID = "profile_id";
    private long profileId;
    private Profile profile;
    private ArrayList<Item> items;
    private Context context;

    Callback paginationFeedCallback = new Callback<ProfileResponse>() {
        @Override
        public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
            // handle success HTTP request
            Log.i("ProfileDetailsActivity", "getPaginatedProfile");

            items.addAll(response.body().getItems());
            profileAdapter.insertItems(response.body().getItems().size());

            loadItemRecyclerView(response.body().getP(), response.body().getT());
            Utils.hideProgressDialog();
        }

        @Override
        public void onFailure(Call<ProfileResponse> call, Throwable t) {
            // handle failure HTTP request
            Log.e("ProfileDetailsActivity", "getPaginatedProfile");
            Utils.hideProgressDialog();
            Utils.showErrorPopup(context, t);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_profile_details);
        setSupportActionBar(toolbar);

        context = this;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            profileId = extras.getLong(PROFILE_ID);
        }

        loadProfileDetails(false);
    }

    private void loadProfileDetails(final boolean isFromRefresh){
        if(!isFromRefresh){
            Utils.showProgressDialog(this);
        }
        BackendManager.getInstance().getFirstProfile(profileId, new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                Log.i("ProfileDetailsActivity", "getFirstProfile");
                profile = response.body().getProfile();
                items = new ArrayList<>(response.body().getItems());
                loadViews(isFromRefresh, response.body().getP(), response.body().getT());
                Utils.hideProgressDialog();
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Log.e("ProfileDetailsActivity", "getFirstProfile");
//                loadViews(isFromRefresh, 0, 0);
                Utils.hideProgressDialog();
                swipeRefreshProfile.setRefreshing(false);
                Utils.showErrorPopup(context, t);
            }
        });
    }

    private void loadViews(final boolean isFromRefresh, int p, int t){

        setTitle(profile.getName());

        if(isFromRefresh){
            swipeRefreshProfile.setRefreshing(false);
        }

        profileRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_profile);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        profileRecyclerView.setLayoutManager(llm);

        swipeRefreshProfile = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_profile_details);
        swipeRefreshProfile.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshProfile.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadProfileDetails(true);
            }
        });

        ImageView profileIv = (ImageView) findViewById(R.id.profile_iv);
        TextView profileNameTv = (TextView) findViewById(R.id.profile_name);
        TextView profileGoalTv = (TextView) findViewById(R.id.profile_goal);

        Picasso.with(this)
                .load(profile.getPhoto())
                .placeholder(R.drawable.person_placeholder)
                .error(R.drawable.ic_image_not_found)
                .into(profileIv);

        profileNameTv.setText(profile.getName());
        profileGoalTv.setText(profile.getGoal());

        loadItemRecyclerView(p, t);

    }

    private void loadItemRecyclerView(int p, int t){

        profileAdapter = new ProfileItemsListAdapter(profileId, items, this, paginationFeedCallback);
        profileRecyclerView.setAdapter(profileAdapter);
        profileAdapter.p = p;
        profileAdapter.t = t;
    }

}
