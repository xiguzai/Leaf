package com.sankuai.inf.leaf.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class OkHttpLeafClient implements LeafClient {

    private Logger logger = LoggerFactory.getLogger(OkHttpLeafClient.class);

    private Gson gson = new GsonBuilder()
            .disableHtmlEscaping()
            .serializeNulls()
            .create();

    private String server;

    @Override
    public String getSegmentId(String key) {
        String url = String.format(server + LeafClient.GET_SEGMENT_ID_URI, key);
        return request(url);
    }

    @Override
    public String getSnowflakeId(String key) {
        String url = String.format(server + LeafClient.GET_SNOWFLAKE_ID_URI, key);
        return request(url);
    }

    @Override
    public List<String> listSegmentId(String key, int length) {
        String url = String.format(server + LeafClient.LIST_SEGMENT_ID_URI, key, length);
        return listId(url);
    }

    @Override
    public List<String> listSnowflakeId(String key, int length) {
        String url = String.format(server + LeafClient.LIST_SNOWFLAKE_ID_URI, key, length);
        return listId(url);
    }

    private List<String> listId(String url) {
        String resp = request(url);
        try {
            return gson.fromJson(resp, new TypeToken<List<String>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            logger.error(e.getMessage(), e);
            throw new LeafClientException();
        }
    }

    private String request(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()){
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                logger.error(response.toString());
                throw new LeafClientException();
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new LeafClientException();
        }
    }

    public void setServer(String server) {
        this.server = server;
    }
}
