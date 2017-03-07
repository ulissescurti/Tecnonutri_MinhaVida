package br.com.soulskyye.tecnonutri.backend;

import br.com.soulskyye.tecnonutri.model.FeedItemResponse;
import br.com.soulskyye.tecnonutri.model.FeedResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ulissescurti on 3/6/17.
 */

public class BackendManager {

    private static BackendManager mInstance = null;

    private FeedApi feedApiImpl;

    public boolean getPaginatedFeedRunning = false;

    private BackendManager(){

        final Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BackendConstants.BASE_URL)
                .build();

        feedApiImpl = retrofit.create(FeedApi.class);
    }

    public static BackendManager getInstance(){

        return mInstance == null ? new BackendManager() : mInstance;
    }

    public void getFirstFeed(Callback<FeedResponse> callback){

        Call<FeedResponse> call = feedApiImpl.firstFeed();
        call.enqueue(callback);
    }

    public void getPaginatedFeed(int p, int t, Callback<FeedResponse> callback){
        if(!getPaginatedFeedRunning) {
            Call<FeedResponse> call = feedApiImpl.paginatedFeed(p, t);
            call.enqueue(callback);
            getPaginatedFeedRunning = true;
        }
    }

    public void getFeedItem(String feedHash, Callback<FeedItemResponse> callback){

        Call<FeedItemResponse> call = feedApiImpl.feedItem(feedHash);
        call.enqueue(callback);
    }
}