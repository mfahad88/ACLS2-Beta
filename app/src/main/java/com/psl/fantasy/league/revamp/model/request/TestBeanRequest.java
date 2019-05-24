package com.psl.fantasy.league.revamp.model.request;

public class TestBeanRequest {
    private String request;
    private String key;



    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "TestBeanRequest{" +
                "request='" + request + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
