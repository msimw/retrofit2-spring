package com.msimw.retrofit2x.spring;

import com.msimw.retrofit2x.annotation.HttpApi;
import com.msimw.retrofit2x.interceptor.HostReplaceInterceptor;
import com.msimw.retrofit2x.retrofit.Retrofit;
import com.msimw.retrofit2x.retrofit.json.FastJsonConverterFactory;
import com.msimw.retrofit2x.retrofit.log.LoggingInterceptor;
import com.msimw.retrofit2x.util.ResourcesUtil;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.concurrent.TimeUnit;

/**
 * Created by msimw on 2017/7/12.
 * 工厂类
 */
public class RetrofitBeanFactory  implements ApplicationContextAware,FactoryBean<Object> {

    private HttpDataSource dataSource;

    private Class<?> serviceClass;

    private boolean throwFail = false;

    private ApplicationContext applicationContext;




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
            this.checkDatasource();
            //http client 配置
            OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder()
                    .connectTimeout(dataSource.getConnTimeOut(), TimeUnit.SECONDS)
                    .writeTimeout(dataSource.getWriteTimeOut(), TimeUnit.SECONDS)
                    .readTimeout(dataSource.getReadTimeOut(), TimeUnit.SECONDS)
                    .connectionPool(dataSource.getConnectionPool())
                    .addInterceptor(new HostReplaceInterceptor())
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
                    .build()
                    .throwFail(this.throwFail);
             return retrofit.create(serviceClass);

    }

    public boolean isThrowFail() {
        return throwFail;
    }

    public void setThrowFail(boolean throwFail) {
        this.throwFail = throwFail;
    }

    public HttpDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(HttpDataSource dataSource) {
        this.dataSource = dataSource;
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
        if(host.contains("${")){
            String key = host.substring(host.indexOf("${")+2,host.indexOf("}"));
            String value = ResourcesUtil.getValue("httpclient.httpapi",key);
            if(StringUtils.isEmpty(value)){
                return host;
            }
            return host.replaceAll("\\$\\{[\\s\\S]*}",value);
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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
      this.applicationContext = applicationContext;
    }



    protected void checkDatasource(){
        if(this.dataSource==null){
            this.dataSource =  this.applicationContext.getBean(HttpDataSource.class);
        }
    }
}
