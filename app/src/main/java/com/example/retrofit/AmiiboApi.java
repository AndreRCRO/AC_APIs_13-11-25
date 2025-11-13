package com.example.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AmiiboApi {

    @GET("api/amiibo/")
    Call<AmiiboResponse> getAmiibos();

}
