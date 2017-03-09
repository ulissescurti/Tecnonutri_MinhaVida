package br.com.soulskyye.tecnonutri.view.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.soulskyye.tecnonutri.R;
import br.com.soulskyye.tecnonutri.presenter.ProfileDetailsPresenter;
import br.com.soulskyye.tecnonutri.presenter.adapter.ProfileItemsListAdapter;
import br.com.soulskyye.tecnonutri.model.Profile;
import br.com.soulskyye.tecnonutri.util.Utils;
import br.com.soulskyye.tecnonutri.view.ProfileDetailsView;

public class ProfileDetailsActivity extends AppCompatActivity implements ProfileDetailsView{

    private ProfileDetailsPresenter mProfileDetailsPresenter;

    private SwipeRefreshLayout swipeRefreshProfile;
    private RecyclerView profileRecyclerView;
    public static final String PROFILE_ID = "profile_id";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_profile_details);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        context = this;

        mProfileDetailsPresenter = new ProfileDetailsPresenter(context);
        mProfileDetailsPresenter.attachView(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mProfileDetailsPresenter.setProfileId(extras.getLong(PROFILE_ID));
        }

        setUpSwipeToRefresh();

        mProfileDetailsPresenter.loadProfileDetails(false);
    }

    private void setUpSwipeToRefresh(){
        swipeRefreshProfile = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_profile_details);
        swipeRefreshProfile.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshProfile.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mProfileDetailsPresenter.loadProfileDetails(true);
            }
        });
    }

    @Override
    public void loadViews(final boolean isFromRefresh, int p, int t){

        Profile profile = mProfileDetailsPresenter.getProfile();

        setTitle(profile.getName());

        profileRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_profile);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        profileRecyclerView.setLayoutManager(llm);

        if(isFromRefresh){
            swipeRefreshProfile.setRefreshing(false);
        }
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

        mProfileDetailsPresenter.loadItemRecyclerView(p, t);

    }

    @Override
    public void hideRefreshDialog() {
        if(swipeRefreshProfile != null) {
            swipeRefreshProfile.setRefreshing(false);
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
    public void failureLoadProfile(Throwable t) {
        Utils.hideProgressDialog();
        hideRefreshDialog();
        Utils.showErrorPopup(context, t);
    }

    @Override
    public void setAdapter(ProfileItemsListAdapter adapter) {
        profileRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        mProfileDetailsPresenter.detachView();
        super.onDestroy();
    }
}
