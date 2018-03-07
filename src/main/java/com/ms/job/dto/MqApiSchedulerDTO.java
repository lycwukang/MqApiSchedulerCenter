package com.ms.job.dto;

import java.io.Serializable;
import java.util.Date;

public class MqApiSchedulerDTO implements Serializable {

    private static final Long serialVersionUID = 1L;

    private Long id;
    private String api;
    private Integer partition;
    private String uid;
    private String content;
    private String requestAddress;
    private Date requestTime;
    private Date processTime;
    private Integer requestCount;
    private Date previousRequestTime;
    private Date nextRequestTime;
    private Integer requestStatus;
    private String requestResult;
    private Integer possessCount;
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public Integer getPartition() {
        return partition;
    }

    public void setPartition(Integer partition) {
        this.partition = partition;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRequestAddress() {
        return requestAddress;
    }

    public void setRequestAddress(String requestAddress) {
        this.requestAddress = requestAddress;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public Date getProcessTime() {
        return processTime;
    }

    public void setProcessTime(Date processTime) {
        this.processTime = processTime;
    }

    public Integer getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(Integer requestCount) {
        this.requestCount = requestCount;
    }

    public Date getPreviousRequestTime() {
        return previousRequestTime;
    }

    public void setPreviousRequestTime(Date previousRequestTime) {
        this.previousRequestTime = previousRequestTime;
    }

    public Date getNextRequestTime() {
        return nextRequestTime;
    }

    public void setNextRequestTime(Date nextRequestTime) {
        this.nextRequestTime = nextRequestTime;
    }

    public Integer getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Integer requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getRequestResult() {
        return requestResult;
    }

    public void setRequestResult(String requestResult) {
        this.requestResult = requestResult;
    }

    public Integer getPossessCount() {
        return possessCount;
    }

    public void setPossessCount(Integer possessCount) {
        this.possessCount = possessCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}