package com.msimw.retrofit2x.interceptor;

import java.io.Serializable;

/**
 * Created by msimw on 17-8-28.
 */
public class Host implements Serializable{

    private String host;
    private Integer port;

    public Host(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public Host() {
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
