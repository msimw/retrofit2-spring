package com.msimw.retrofit2x.spring;

import com.msimw.retrofit2x.annotation.HttpApi;
import com.msimw.retrofit2x.retrofit.Retrofit;
import com.msimw.retrofit2x.retrofit.json.FastJsonConverterFactory;
import com.msimw.retrofit2x.retrofit.log.LoggingInterceptor;
import com.msimw.retrofit2x.util.ResourcesUtil;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.FactoryBean;

import java.util.concurrent.TimeUnit;

/**
 * Created by msimw on 2017/7/12.
 * 工厂类
 */
public class RetrofitBeanFactory implements FactoryBean<Object> {

    private  int readTimeOut = 15;
    private  int writeTimeOut = 15;
    private  int connTimeOut = 15;
    private ConnectionPool connectionPool;

    private Class<?> serviceClass;




    /**
     * 创建service服务实体
     *
     * @param baseUrl
     * @param serviceClass
     */
    public Object createBean(String baseUrl, Class serviceClass, Class... interceptorClass) {
        if (StringUtils.isEmpty(baseUrl)) {
            return null;
        }
            //http client 配置
            OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder()
                    .connectTimeout(readTimeOut, TimeUnit.SECONDS)
                    .writeTimeout(writeTimeOut, TimeUnit.SECONDS)
                    .readTimeout(connTimeOut, TimeUnit.SECONDS)
                    .connectionPool(connectionPool)
                    .addInterceptor(new LoggingInterceptor());
            if (interceptorClass != null && interceptorClass.length > 0) {
                for (Class clazz : interceptorClass) {
                    if (Interceptor.class.isAssignableFrom(clazz)) {
                        try {
                            clientBuilder.addInterceptor((Interceptor) clazz.newInstance());
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HttpUrl.parse(baseUrl))
                    .client(clientBuilder.build())
                    .addConverterFactory(FastJsonConverterFactory.create())
                    .build();
             return retrofit.create(serviceClass);

    }


    public int getReadTimeOut() {
        return readTimeOut;
    }

    public void setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public int getWriteTimeOut() {
        return writeTimeOut;
    }

    public void setWriteTimeOut(int writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
    }

    public int getConnTimeOut() {
        return connTimeOut;
    }

    public void setConnTimeOut(int connTimeOut) {
        this.connTimeOut = connTimeOut;
    }

    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    public void setConnectionPool(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public Class<?> getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(Class<?> serviceClass) {
        this.serviceClass = serviceClass;
    }

    /**
     * 解析url
     * @param url
     * @return
     */
    public static String resolveUrl(String url){
        String host = url;
        if(host.contains("{")){
            String key = host.substring(host.indexOf("{")+1,host.indexOf("}"));
            String value = ResourcesUtil.getValue("httpclient.httpapi",key);
            if(StringUtils.isEmpty(value)){
                return host;
            }
            return host.replaceAll("\\{[\\s\\S]*}",value);
        }

        String value = ResourcesUtil.getValue("httpclient.httpapi",host);
        if (StringUtils.isEmpty(value)) {
            return host;
        }
        return value;
    }


    @Override
    public Object getObject() throws Exception {
        if(this.serviceClass==null){
            return null;
        }
        HttpApi httpApi = this.serviceClass.getAnnotation(HttpApi.class);
        if(httpApi==null){
            return null;
        }
        return createBean(resolveUrl(httpApi.value()),serviceClass,httpApi.interceptor());
    }

    @Override
    public Class<?> getObjectType() {
        return this.serviceClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
