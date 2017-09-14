package com.msimw.retrofit2x.test;


import com.msimw.retrofit2x.annotation.HttpApi;

/**
 * Created by msimw on 17-9-13.
 */
@HttpApi("http://{zhost}/")
public interface IBaiduPushHttpService extends IPushHttpService<String> {

}
