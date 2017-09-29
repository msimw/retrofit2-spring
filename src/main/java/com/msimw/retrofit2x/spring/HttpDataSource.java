package com.msimw.retrofit2x.spring;

import okhttp3.*;
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

    /**
     * Sets the authenticator used to respond to challenges from origin servers.
     */
    private Authenticator authenticator;

    /**
     * Sets the certificate pinner that constrains which certificates are trusted. By default HTTPS
     * connections rely on only the {@link #sslSocketFactory SSL socket factory} to establish trust.
     * Pinning certificates avoids the need to trust certificate authorities.
     */
    private CertificatePinner certificatePinner;

    /**
     *
     */
    private CookieJar cookieJar;

    /**
     *
     */
    private Cache cache;

    /**
     *
     */
    private Dns dns;



    public static final String DEFAULT_HTTP_API_RESOURCE_BUNDLE_FILE_NAME = "httpclient.httpapi";


    /**
     * http api resources
     */
    private String httpApiResourceBundleFileName = DEFAULT_HTTP_API_RESOURCE_BUNDLE_FILE_NAME;

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

    public String getHttpApiResourceBundleFileName() {
        return httpApiResourceBundleFileName;
    }

    public void setHttpApiResourceBundleFileName(String httpApiResourceBundleFileName) {
        this.httpApiResourceBundleFileName = httpApiResourceBundleFileName;
    }

    public Authenticator getAuthenticator() {
        return authenticator;
    }

    public void setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    public CertificatePinner getCertificatePinner() {
        return certificatePinner;
    }

    public void setCertificatePinner(CertificatePinner certificatePinner) {
        this.certificatePinner = certificatePinner;
    }

    public CookieJar getCookieJar() {
        return cookieJar;
    }

    public void setCookieJar(CookieJar cookieJar) {
        this.cookieJar = cookieJar;
    }

    public Cache getCache() {
        return cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    public Dns getDns() {
        return dns;
    }

    public void setDns(Dns dns) {
        this.dns = dns;
    }
}
