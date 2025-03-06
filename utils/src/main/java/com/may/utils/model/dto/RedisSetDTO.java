package com.may.utils.model.dto;

import java.util.List;

public class RedisSetDTO {
    private String key;
    private String hashKey;
    private Object value;
    private long time;



    private List<Object> values;

    public RedisSetDTO() {
    }

    public RedisSetDTO(String key, String hashKey, Object value, long time) {
        this.key = key;
        this.hashKey = hashKey;
        this.value = value;
        this.time = time;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getHashKey() {
        return hashKey;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public List<Object> getValues() {
        return values;
    }

    public void setValues(List<Object> values) {
        this.values = values;
    }
}
