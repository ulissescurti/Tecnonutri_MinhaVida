package br.com.soulskyye.tecnonutri.backend;

import br.com.soulskyye.tecnonutri.backend.networkmodel.ProfileResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ulissescurti on 3/6/17.
 */

public interface ProfileApi {

    @GET(BackendConstants.PROFILE)
    Call<ProfileResponse> firstProfile(@Path("id") long id);

    @GET(BackendConstants.PROFILE)
    Call<ProfileResponse> paginatedProfile(@Path("id") long id, @Query("p") int p, @Query("t") int t);
}
