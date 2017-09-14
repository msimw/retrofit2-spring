package com.msimw.retrofit2x.interceptor;


import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;


/**
 * Created by msimw on 17-8-25.
 *
 * 实现某些请求 地址根据某些业务逻辑实现  替换 header 的内容替换{}
 */
public class HostReplaceInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        return chain.proceed(newRequest(getHost(resolveUrl(request)),request));
    }




    private String resolveUrl(Request request){
        String host = request.url().host();
        if(host.contains("{")){
          String key = host.substring(host.indexOf("{")+1,host.indexOf("}"));
          return request.header(key);
        }
        return host;
    }



    /**
     * 获取request 中的用户自定义host
     * @param host
     * @return
     */
    private Host getHost(String host) throws IOException {
        String address = null;
        int port = 80;
        if(host!=null){
            if(host.contains(":")){
                String[] split = host.split(":");
                address = split[0];
                port = Integer.valueOf(split[1]);
            } else{
                address = host;
            }
        }
       return new Host(address,port);
    }




    private Request newRequest(Host host, Request request){

        HttpUrl newUrl = request.url().newBuilder().host(host.getHost()).port(host.getPort()).build();
        Request.Builder builder = new Request.Builder();
        builder.url(newUrl);
        builder.method(request.method(),request.body());
        builder.tag(request.tag());
        builder.headers(request.headers());
        builder.cacheControl(request.cacheControl());
        return builder.build();
    }
}
