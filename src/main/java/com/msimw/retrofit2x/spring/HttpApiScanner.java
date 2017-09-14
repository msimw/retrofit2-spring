package com.msimw.retrofit2x.spring;

import com.msimw.retrofit2x.annotation.HttpApi;
import okhttp3.ConnectionPool;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.util.CollectionUtils;

import java.util.Set;

/**
 * Created by msimw on 17-9-12.
 *
 * Http service 扫描
 */
public class HttpApiScanner extends ClassPathBeanDefinitionScanner {


    private  int readTimeOut = 15;
    private  int writeTimeOut = 15;
    private  int connTimeOut = 15;
    private ConnectionPool connectionPool;

    private boolean throwFail = false;

    public HttpApiScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return (beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent())
                &&(beanDefinition.getMetadata().hasAnnotation(HttpApi.class.getName()));
    }



    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        if(CollectionUtils.isEmpty(beanDefinitionHolders)){
            return null;
        }
        for(BeanDefinitionHolder definitionHolder:beanDefinitionHolders){
            GenericBeanDefinition definition = (GenericBeanDefinition) definitionHolder.getBeanDefinition();
                logger.info("Creating MapperFactoryBean with name '" + definitionHolder.getBeanName()
                        + "' and '" + definition.getBeanClassName() + "' mapperInterface");
            definition.getPropertyValues().add("serviceClass", definition.getBeanClassName());
            definition.setBeanClass(RetrofitBeanFactory.class);

            definition.getPropertyValues().add("readTimeOut", this.readTimeOut);
            definition.getPropertyValues().add("writeTimeOut", this.writeTimeOut);
            definition.getPropertyValues().add("connTimeOut", this.connTimeOut);
            definition.getPropertyValues().add("connectionPool", this.connectionPool);
            definition.getPropertyValues().add("throwFail", this.throwFail);
            definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        }
        return beanDefinitionHolders;
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

    public boolean isThrowFail() {
        return throwFail;
    }

    public void setThrowFail(boolean throwFail) {
        this.throwFail = throwFail;
    }
}
