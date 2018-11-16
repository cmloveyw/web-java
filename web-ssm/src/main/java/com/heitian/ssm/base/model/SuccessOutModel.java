package com.heitian.ssm.base.model;

/**
 * Des:成功，输出模型
 * Created by UlverYang on 2016-05-19 15:37.
 */
public class SuccessOutModel {

    public static SuccessOutModel instance = new SuccessOutModel();

    private String success = "成功";

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
