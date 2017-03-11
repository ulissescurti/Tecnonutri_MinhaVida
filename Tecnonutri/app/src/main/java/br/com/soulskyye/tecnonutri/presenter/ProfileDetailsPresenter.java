package br.com.soulskyye.tecnonutri.presenter;

import android.content.Context;

import java.util.ArrayList;

import br.com.soulskyye.tecnonutri.backend.BackendManager;
import br.com.soulskyye.tecnonutri.backend.networkmodel.ProfileResponse;
import br.com.soulskyye.tecnonutri.model.Item;
import br.com.soulskyye.tecnonutri.model.Profile;
import br.com.soulskyye.tecnonutri.presenter.adapter.ProfileItemsListAdapter;
import br.com.soulskyye.tecnonutri.util.Utils;
import br.com.soulskyye.tecnonutri.view.ProfileDetailsView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ulissescurti on 3/8/17.
 */

public class ProfileDetailsPresenter implements BasePresenter<ProfileDetailsView> {

    private ProfileDetailsView mProfileView;
    private Context context;

    private Profile profile;
    private ArrayList<Item> items;

    private ProfileItemsListAdapter profileAdapter;

    private long profileId;

    public ProfileDetailsPresenter(Context context){
        this.context = context;
    }

    public void loadProfileDetails(final boolean isFromRefresh){
        if(!isFromRefresh){
            mProfileView.showProgressDialog();
        }
        BackendManager.getInstance().getFirstProfile(profileId, new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                profile = response.body().getProfile();
                items = new ArrayList<>(response.body().getItems());
                mProfileView.loadViews(isFromRefresh, response.body().getP(), response.body().getT());
                mProfileView.hideProgressDialog();
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                mProfileView.failureLoadProfile(t);
            }
        });
    }

    public void loadItemRecyclerView(boolean paginationEnded, int p, int t){

        profileAdapter = new ProfileItemsListAdapter(profileId, items, context, getPaginationFeedCallback());
        profileAdapter.loadFinished = paginationEnded;
        mProfileView.setAdapter(profileAdapter);
        profileAdapter.p = p;
        profileAdapter.t = t;
    }

    public Callback<ProfileResponse> getPaginationFeedCallback(){
        return new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {

                items.addAll(response.body().getItems());
                profileAdapter.insertItems(response.body().getItems().size());
                if(response.body().getItems() == null || response.body().getItems().size() == 0){
                    loadItemRecyclerView(true, response.body().getP(), response.body().getT());
                } else{
                    loadItemRecyclerView(false, response.body().getP(), response.body().getT());
                }
                Utils.hideProgressDialog();
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                mProfileView.failureLoadProfile(t);
            }
        };
    }

    @Override
    public void attachView(ProfileDetailsView view) {
        mProfileView = view;
    }

    @Override
    public void detachView() {
        mProfileView = null;
    }

    public long getProfileId() {
        return profileId;
    }

    public void setProfileId(long profileId) {
        this.profileId = profileId;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
