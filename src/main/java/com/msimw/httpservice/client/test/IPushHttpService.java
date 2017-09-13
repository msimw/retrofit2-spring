package com.msimw.httpservice.client.test;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by msimw on 17-9-13.
 */
public interface IPushHttpService<T> {

    @POST("b")
    public Call<String> push();
}
