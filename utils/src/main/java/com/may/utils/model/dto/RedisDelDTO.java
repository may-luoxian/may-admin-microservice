package com.may.utils.model.dto;

import java.util.List;

public class RedisDelDTO {
    private String key;
    private List<Object> hashKey;

    public RedisDelDTO() {
    }

    public RedisDelDTO(String key, List<Object> hashKey) {
        this.key = key;
        this.hashKey = hashKey;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Object> getHashKey() {
        return hashKey;
    }

    public void setHashKey(List<Object> hashKey) {
        this.hashKey = hashKey;
    }
}
