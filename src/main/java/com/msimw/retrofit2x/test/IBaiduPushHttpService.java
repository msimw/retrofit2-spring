package com.msimw.retrofit2x.test;


import com.msimw.retrofit2x.annotation.HttpApi;

/**
 * Created by msimw on 17-9-13.
 */
@HttpApi("http://www.baidu.com/")
public interface IBaiduPushHttpService extends IPushHttpService<String> {

}
