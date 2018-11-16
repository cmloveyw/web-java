/*
 *  Copyright (c) 2014-2017. 墨博云舟 All Rights Reserved. 
 */

package com.heitian.ssm.exceptions;


/**
 * CommException : 自定义异常公共父类
 *
 * @author UlverYang
 * @version 1.00
 * @since 2017-03-22 10:51
 */
public class PlatformException extends RuntimeException {

    /**
     * 错误码
     */
    private String errorCode = "0x80000000"; // 默认值0x80000000

    /**
     * 自定义错误消息
     */
    private String errorMessage;

    /**
     * 是否展示
     */
    private boolean showFlag;

    /**
     * 异常对象
     */
    private Throwable throwable;

    private String clientExecuteMethod;


    /**
     * 构造方法
     *
     * @param errorCode 自定义错误消息
     */
    public PlatformException(String errorCode) {

        super("errorCode:[" + errorCode + "]");
        this.errorCode = errorCode;
    }

    /**
     * 构造方法
     *
     * @param errorCode    错误码
     * @param errorMessage 自定义错误消息
     */
    public PlatformException(String errorCode, String errorMessage) {

        super("errorCode:[" + errorCode + "]" + errorMessage);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    /**
     * 构造方法
     *
     * @param errorCode    错误码
     * @param errorMessage 自定义错误消息
     * @param showFlag     是否显示
     */
    public PlatformException(String errorCode, String errorMessage, boolean showFlag) {

        super("errorCode:[" + errorCode + "]" + errorMessage);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.showFlag = showFlag;
    }


    /**
     * 构造方法
     *
     * @param errorCode    错误码
     * @param errorMessage 自定义错误消息
     * @param cause        异常源
     */
    public PlatformException(String errorCode, String errorMessage, Throwable cause) {

        super("errorCode:[" + errorCode + "]" + errorMessage, cause);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    /**
     * 构造方法
     *
     * @param errorCode 错误码
     * @param cause     异常源
     */
    public PlatformException(String errorCode, Throwable cause) {

        super("errorCode:[" + errorCode + "]", cause);
        this.errorCode = errorCode;
    }

    /**
     * 构造方法
     *
     * @param cause 异常源
     */
    public PlatformException(Throwable cause) {

        super(cause);
    }

    public String getErrorCode() {

        return errorCode;
    }

    public void setErrorCode(String errorCode) {

        this.errorCode = errorCode;
    }

    public String getErrorMessage() {

        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {

        this.errorMessage = errorMessage;
    }

    public boolean isShowFlag() {

        return showFlag;
    }

    public void setShowFlag(boolean showFlag) {

        this.showFlag = showFlag;
    }

    public String getClientExecuteMethod() {

        return clientExecuteMethod;
    }

    public void setClientExecuteMethod(String clientExecuteMethod) {

        this.clientExecuteMethod = clientExecuteMethod;
    }
}
