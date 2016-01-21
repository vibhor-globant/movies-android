package com.globant.movies.network;

import com.globant.movies.model.Page;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by vibhor on 13/01/16.
 */
public interface NetworkApi {
    @GET("fetch")
        Observable<Page> getPage(@Query("page_num") int pageNum);
}
