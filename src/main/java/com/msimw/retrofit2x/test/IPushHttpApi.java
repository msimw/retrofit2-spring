package com.msimw.retrofit2x.test;


import com.msimw.retrofit2x.retrofit.Call;
import com.msimw.retrofit2x.retrofit.http.Header;
import com.msimw.retrofit2x.retrofit.http.POST;

/**
 * Created by msimw on 17-9-13.
 */
public interface IPushHttpApi<T> {

    @POST("b")
    public Call<String> push(@Header("zhost") String host);
}
