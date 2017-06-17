package com.bachors.carilirik.api;

import com.bachors.carilirik.model.Judul;
import com.bachors.carilirik.model.Lirik;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Bachors on 6/16/2017.
 */

public interface LirikInterface {

    @GET("exec")
    Call<Judul> getJudul(
        @Query("service") String service,
        @Query("judul") String judul
    );

    @GET("exec")
    Call<Lirik> getLirik(
            @Query("service") String service,
            @Query("lirik") String lirik
    );

}