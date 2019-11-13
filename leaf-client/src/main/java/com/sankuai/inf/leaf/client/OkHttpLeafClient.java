package com.sankuai.inf.leaf.client;

import java.util.List;

public class OkHttpLeafClient implements LeafClient {

    private String host;

    @Override
    public String getSegmentId(String key) {
        return null;
    }

    @Override
    public String getSnowflakeId(String key) {
        return null;
    }

    @Override
    public List<String> listSegmentId(String key, int length) {
        return null;
    }

    @Override
    public List<String> listSnowflakeId(String key, int length) {
        return null;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
