package com.sankuai.inf.leaf.client;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class OkHttpLeafClientTest {

    private OkHttpLeafClient leafClient;

    @Before
    public void before() {
        leafClient = new OkHttpLeafClient();
        leafClient.setServer("http://localhost:8080");
    }

    @Test
    public void getSegmentId() {
        long before = System.currentTimeMillis();
        System.out.println("before: " + before);
        String id = leafClient.getSegmentId("aaa");
        long after = System.currentTimeMillis();
        System.out.println("after: " + after);
        System.out.println("diff: " + (after - before));
        System.out.println("id: " + id);
    }

    @Test
    public void getSnowflakeId() {
        long before = System.currentTimeMillis();
        System.out.println("before: " + before);
        String id = leafClient.getSnowflakeId("aaa");
        long after = System.currentTimeMillis();
        System.out.println("after: " + after);
        System.out.println("diff: " + (after - before));
        System.out.println("id: " + id);
    }

    @Test
    public void listSegmentId() {
        long before = System.currentTimeMillis();
        System.out.println("before: " + before);
        List<String> aaa = leafClient.listSegmentId("aaa", 1000);
        long after = System.currentTimeMillis();
        System.out.println("after: " + after);
        System.out.println("diff: " + (after - before));
        for (int i = 0; i < aaa.size(); i++) {
            System.out.println("id"+ i + ": " + aaa.get(i));
        }
    }

    @Test
    public void listSnowflakeId() {
        long before = System.currentTimeMillis();
        System.out.println("before: " + before);
        List<String> aaa = leafClient.listSnowflakeId("aaa", 1000);
        long after = System.currentTimeMillis();
        System.out.println("after: " + after);
        System.out.println("diff: " + (after - before));
        for (int i = 0; i < aaa.size(); i++) {
            System.out.println("id"+ i + ": " + aaa.get(i));
        }
    }
}