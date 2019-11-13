package com.sankuai.inf.leaf.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class HttpClientLeafClient implements LeafClient {

    private Logger logger = LoggerFactory.getLogger(HttpClientLeafClient.class);

    private Gson gson = new GsonBuilder()
            .disableHtmlEscaping()
            .serializeNulls()
            .create();

    private String host;

    @Override
    public String getSegmentId(String key) {
        String uri = host + String.format(LeafClient.GET_SEGMENT_ID_URI, key);
        return getId(uri);
    }

    @Override
    public String getSnowflakeId(String key) {
        String uri = host + String.format(LeafClient.GET_SNOWFLAKE_ID_URI, key);
        return getId(uri);
    }

    @Override
    public List<String> listSegmentId(String key, int length) {
        String uri = String.format(LeafClient.LIST_SEGMENT_ID_URI, key, length);
        return listId(uri);
    }

    @Override
    public List<String> listSnowflakeId(String key, int length) {
        String uri = String.format(LeafClient.LIST_SNOWFLAKE_ID_URI, key, length);
        return listId(uri);
    }

    private String getId(String uri) {
        return request(uri);
    }

    private List<String> listId(String uri) {
        String resp = request(uri);
        try {
            List<String> list = gson.fromJson(resp, new TypeToken<List<String>>() {
            }.getType());
            return list;
        } catch (JsonSyntaxException e) {
            logger.error(e.getMessage(), e);
            throw new LeafClientException();
        }
    }

    private String request(String uri) {
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse execute = null;
        HttpGet httpGet = new HttpGet(uri);
        try {
            execute = client.execute(httpGet);
            int statusCode = execute.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = execute.getEntity();
            return EntityUtils.toString(entity, StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new LeafClientException();
        } finally {
            clean(client, execute);
        }
    }

    private void clean(CloseableHttpClient client, CloseableHttpResponse response) {
        try {
            if (response != null) {
                response.close();
            }
            if (client != null) {
                client.close();
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void setHost(String host) {
        this.host = host;
    }
}
