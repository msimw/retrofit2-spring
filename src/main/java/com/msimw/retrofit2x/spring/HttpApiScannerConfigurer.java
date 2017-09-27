package com.msimw.retrofit2x.spring;

import com.msimw.retrofit2x.annotation.HttpApi;
import okhttp3.ConnectionPool;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.StringUtils;

/**
 * Created by msimw on 17-9-12.
 *
 * Http service 扫描配置
 */
public class HttpApiScannerConfigurer implements BeanDefinitionRegistryPostProcessor , InitializingBean,ApplicationContextAware {

    private String basePackage;// * one,** many

    private  int readTimeOut = 15;
    private  int writeTimeOut = 15;
    private  int connTimeOut = 15;
    private ConnectionPool connectionPool;
    private boolean throwFail = false;

    private String httpDataSourceBeanName;


    private ApplicationContext applicationContext;


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        //ConnectionPool bean = this.applicationContext.getBean(ConnectionPool.class);

        HttpApiScanner scanner = new HttpApiScanner(registry);
        scanner.setWriteTimeOut(this.writeTimeOut);
        scanner.setReadTimeOut(this.readTimeOut);
        scanner.setConnTimeOut(this.connTimeOut);
        scanner.setConnectionPool(this.connectionPool);
        scanner.setThrowFail(this.throwFail);
        scanner.setHttpDataSourceBeanName(this.httpDataSourceBeanName);
        scanner.setResourceLoader(this.applicationContext);
        scanner.addIncludeFilter(new AnnotationTypeFilter(HttpApi.class));
        scanner.setBeanNameGenerator(null);
        scanner.scan(StringUtils.tokenizeToStringArray(this.basePackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));

    }


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
       //left intentionally blank
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //check basePackage
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public int getReadTimeOut() {
        return readTimeOut;
    }

    @Deprecated
    public void setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public int getWriteTimeOut() {
        return writeTimeOut;
    }

    @Deprecated
    public void setWriteTimeOut(int writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
    }

    public int getConnTimeOut() {
        return connTimeOut;
    }

    @Deprecated
    public void setConnTimeOut(int connTimeOut) {
        this.connTimeOut = connTimeOut;
    }

    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    public boolean isThrowFail() {
        return throwFail;
    }

    public void setThrowFail(boolean throwFail) {
        this.throwFail = throwFail;
    }

    @Deprecated
    public void setConnectionPool(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
      this.applicationContext =applicationContext;
    }


    public String getHttpDataSourceBeanName() {
        return httpDataSourceBeanName;
    }

    public void setHttpDataSourceBeanName(String httpDataSourceBeanName) {
        this.httpDataSourceBeanName = httpDataSourceBeanName;
    }
}
