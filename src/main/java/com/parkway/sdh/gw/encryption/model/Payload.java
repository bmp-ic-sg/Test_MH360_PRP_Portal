package com.parkway.sdh.gw.encryption.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Payload {

    @JsonProperty("content")
    private String content;
    @JsonProperty("iv")
    private String iv;
    @JsonProperty("signature")
    private String signature;
    @JsonProperty("key")
    private String key;

    public Payload() {
        // Empty Constructor
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
