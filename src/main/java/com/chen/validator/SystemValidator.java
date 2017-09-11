package com.chen.validator;

import com.chen.dao.System;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * Created by 陈忠意 on 2017/8/24.
 */
public class SystemValidator {

    @Size(min = 1, max = 44)
    private String code;
    @Size(max = 44)
    private String description;
    @Min(0)
    @Max(1)
    private Integer status;

    public System buildSystem(){
        System system = new System();
        system.setCode(code);
        system.setDescription(description);
        return system;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
