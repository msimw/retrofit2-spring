package com.msimw.retrofit2x.test;


import com.msimw.retrofit2x.retrofit.http.GET;
import com.msimw.retrofit2x.retrofit.http.Header;

/**
 * Created by msimw on 17-9-13.
 */
public interface IPushHttpApi<T> {

    @GET("/b")
    public String push(@Header("zhost") String host);
}
