package com.ms.job.dto;

import java.io.Serializable;

public class MqApiConfigDTO implements Serializable {

    private static final Long serialVersionUID = 1L;

    private String api;
    private String name;
    private String remark;
    private String requestAddress;

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRequestAddress() {
        return requestAddress;
    }

    public void setRequestAddress(String requestAddress) {
        this.requestAddress = requestAddress;
    }
}
