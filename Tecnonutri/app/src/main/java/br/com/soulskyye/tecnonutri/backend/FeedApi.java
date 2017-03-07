package br.com.soulskyye.tecnonutri.backend;

import br.com.soulskyye.tecnonutri.model.FeedResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ulissescurti on 3/6/17.
 */

public interface FeedApi {

    @GET(BackendConstants.FEED)
    Call<FeedResponse> firstFeed();

    @GET(BackendConstants.FEED)
    Call<FeedResponse> paginatedFeed(@Query("p") int p, @Query("t") int t);
}
