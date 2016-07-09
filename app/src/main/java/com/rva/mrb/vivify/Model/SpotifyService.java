package com.rva.mrb.vivify.Model;

import dagger.Provides;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by rigo on 7/8/16.
 */
public interface SpotifyService {

    /**
     * Maximum number of objects to return
     */
    String LIMIT = "limit";

    /**
     * Index of the first item to return (Default = 0)
     * Use with limit to get the next set of objects
     */
    String OFFSET = "offset";

    /**
     * Comma-seperated list of keywords that will be used to filter
     * the response
     * Values are: {@code album},{@code single}
     */
    String ALBUM_TYPE = "album_type";

    /**
     * Country code. Limit response to one country code
     */
    String MARKET = "market";

    /**
     * Language code, consists of language code_country code
     * Example: es_MX meaning "Spanish (Mexico)"
     */
    String LOCALE = "locale";

    /**
     * Filters for the query
     */
    String FIELDS = "fields";

    /***********
     * Tracks
     ***********/

    @GET("/tracks/{id}")
    void getTrack(@Path("id") String trackId);

    /***********
     * User
     ***********/

    @GET("users/{user_id}")
    Call<User> getUser(@Path("user_id") String userId);
}