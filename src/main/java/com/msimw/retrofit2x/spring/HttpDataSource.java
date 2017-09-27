package com.msimw.retrofit2x.spring;

import okhttp3.ConnectionPool;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.TimeUnit;

/**
 * Created by msimw on 17-9-26.
 */
public class HttpDataSource  implements InitializingBean{

    /**
     * http 连接池
     */
    private ConnectionPool connectionPool;


    /**
     * 读取超时时间  /秒
     */
    private  int readTimeOut = 15;

    /**
     * 写数据超时时间 /秒
     */
    private  int writeTimeOut = 15;

    /**
     * 连接超时时间 /秒
     */
    private  int connTimeOut = 15;


    /**
     * 最大连接数
     */
    private  int maxIdleConnections;

    /**
     * 保存活跃时间 /秒
     */
    private  long keepAliveDurationNs;

    public ConnectionPool getConnectionPool() {
        return connectionPool;
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

    public int getMaxIdleConnections() {
        return maxIdleConnections;
    }

    public void setMaxIdleConnections(int maxIdleConnections) {
        this.maxIdleConnections = maxIdleConnections;
    }

    public long getKeepAliveDurationNs() {
        return keepAliveDurationNs;
    }

    public void setKeepAliveDurationNs(long keepAliveDurationNs) {
        this.keepAliveDurationNs = keepAliveDurationNs;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
       this.connectionPool = new ConnectionPool(maxIdleConnections,keepAliveDurationNs, TimeUnit.SECONDS);
    }

    protected void setConnectionPool(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }
}
