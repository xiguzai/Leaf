package com.sankuai.inf.leaf.client;

import java.util.List;

public interface LeafClient {
    String GET_SEGMENT_ID_URI = "/api/segment/get/%s";
    String GET_SNOWFLAKE_ID_URI = "/api/snowflake/get/%s";
    String LIST_SEGMENT_ID_URI = "/api/segment/list/%s/%d";
    String LIST_SNOWFLAKE_ID_URI = "/api/snowflake/list/%s/%d";

    String getSegmentId(String key);

    String getSnowflakeId(String key);

    List<String> listSegmentId(String key, int length);

    List<String> listSnowflakeId(String key, int length);
}
