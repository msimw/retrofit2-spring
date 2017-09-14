package com.msimw.retrofit2x.test;


import com.msimw.retrofit2x.retrofit.Call;
import com.msimw.retrofit2x.retrofit.http.POST;

/**
 * Created by msimw on 17-9-13.
 */
public interface IPushHttpService<T> {

    @POST("b")
    public Call<String> push();
}
