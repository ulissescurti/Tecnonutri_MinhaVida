package br.com.soulskyye.tecnonutri.backend;

import br.com.soulskyye.tecnonutri.model.FeedItemResponse;
import br.com.soulskyye.tecnonutri.model.FeedResponse;
import br.com.soulskyye.tecnonutri.model.ProfileResponse;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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
    private ProfileApi profileApiImpl;

    private BackendManager(){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        final Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BackendConstants.BASE_URL)
                .client(httpClient.build())
                .build();

        feedApiImpl = retrofit.create(FeedApi.class);
        profileApiImpl = retrofit.create(ProfileApi.class);
    }

    public static BackendManager getInstance(){

        return mInstance == null ? new BackendManager() : mInstance;
    }

    public void getFirstFeed(Callback<FeedResponse> callback){

        Call<FeedResponse> call = feedApiImpl.firstFeed();
        call.enqueue(callback);
    }

    public void getPaginatedFeed(int p, int t, Callback<FeedResponse> callback){
        Call<FeedResponse> call = feedApiImpl.paginatedFeed(p, t);
        call.enqueue(callback);
    }

    public void getFeedItem(String feedHash, Callback<FeedItemResponse> callback){

        Call<FeedItemResponse> call = feedApiImpl.feedItem(feedHash);
        call.enqueue(callback);
    }

    public void getFirstProfile(long id, Callback<ProfileResponse> callback){

        Call<ProfileResponse> call = profileApiImpl.firstProfile(id);
        call.enqueue(callback);
    }

    public void getPaginatedProfile(long id, int p, int t, Callback<ProfileResponse> callback){
        Call<ProfileResponse> call = profileApiImpl.paginatedProfile(id, p, t);
        call.enqueue(callback);
    }

}