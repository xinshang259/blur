package com.chris.blue.meta;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MetaService {

    @GET("/")
    Single<List<ImageBean>> getImages(@Query("name") String name,@Query("sn") int sn,@Query("pn") int pn);

}
