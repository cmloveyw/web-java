package com.example.demo.logAop;

import lombok.Data;

/**
 * @version 1.0
 * @类名: SysLogBo.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2019-1-24
 */
@Data
public class SysLogBo {
    private String className;
    private String methodName;
    private String params;
    private Long exeuTime;
    private String remark;
    private String createData;
}
