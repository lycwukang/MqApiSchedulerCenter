package com.ms.job.model;

import io.swagger.annotations.ApiModelProperty;

public class MpApiRequestResult {

    @ApiModelProperty(notes = "是否成功")
    private boolean success;
    @ApiModelProperty(notes = "操作编码 0成功 其他失败")
    private int result;
    @ApiModelProperty(notes = "刚才操作api的名称")
    private String api;
    @ApiModelProperty(notes = "信息")
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}