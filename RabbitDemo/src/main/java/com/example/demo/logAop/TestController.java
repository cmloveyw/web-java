package com.example.demo.logAop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @类名: TestController.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2019-1-24
 */
@RestController
public class TestController {
    @SysLog("测试")
    @GetMapping("/test")
    public String test(@RequestParam("name") String name) {
        return name;
    }
}
