package com.chen.dao;

import java.io.Serializable;

/**
 * Created by 陈忠意 on 2017/8/24.
 */
public class System implements Serializable{

    private long id;
    private String code;
    private String description;
    private int status;

    public System(){};

    public System(long id, String code, String description, int status){
        this.id = id;
        this.code = code;
        this.description = description;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
