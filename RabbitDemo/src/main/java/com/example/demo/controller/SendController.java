package com.example.demo.controller;

import com.example.demo.sender.FirstSender;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @version 1.0
 * @类名: SendController.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2019-1-18
 */
@RestController
public class SendController {
    @Autowired
    private FirstSender firstSender;

    @RequestMapping("/send")
    public String send(String message) {
        String uuid = UUID.randomUUID().toString();
        firstSender.send(uuid, message);
        System.out.println("uuid:##" + uuid);
        return uuid;
    }
}
