package com.heitian.ssm.test;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * @version 1.0
 * @类名: SendMsg.java
 * @描述: 发短信
 * @创建人: CM
 * @创建时间: 2018-11-15
 */
public class SendMsg {
    public static void main(String[] args) throws Exception {
        String s = "961210";
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod("http://utf8.sms.webchinese.cn/");
        post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");// 在头文件中设置转码  
        NameValuePair[] data = {new NameValuePair("Uid", "ANDM"), // 注册的用户名  
                new NameValuePair("Key", "d41d8cd98f00b204e980"), // 注册成功后,登录网站使用的密钥  
                new NameValuePair("smsMob", "15951702372"), // 手机号码  
                new NameValuePair("smsText", "I am your father")};//设置短信内容          
        post.setRequestBody(data);
        client.executeMethod(post);
        Header[] headers = post.getResponseHeaders();
        int statusCode = post.getStatusCode();
        System.out.println("statusCode:" + statusCode);
        for (Header h : headers) {
            System.out.println(h.toString());
        }
        String result = new String(post.getResponseBodyAsString().getBytes("utf-8"));
        System.out.println(result);
        post.releaseConnection();
    }
}

