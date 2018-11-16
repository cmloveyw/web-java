package com.heitian.ssm.base.model;

import java.io.Serializable;

/**
 * Des:输出模型
 * Created by UlverYang on 2016-05-19 15:37.
 */
public class OutModel implements Serializable {
    public static SuccessOutModel instanceSuccessModel() {
        return SuccessOutModel.instance;
    }
}
