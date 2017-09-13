package com.msimw.httpservice.client.test;

import com.msimw.httpservice.client.annotation.HttpApi;

/**
 * Created by msimw on 17-9-13.
 */
@HttpApi("http://www.baidu.com/")
public interface IBaiduPushHttpService extends IPushHttpService<String> {

}
