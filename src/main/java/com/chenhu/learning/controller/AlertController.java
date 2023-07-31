package com.chenhu.learning.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author chenhu
 * @description
 * @date 2023-03-10 13:43
 */
@RestController
@RequestMapping("/alert")
public class AlertController {

    @PostMapping("/webhook")
    public void webhook(@RequestBody Map<String,Object> msg){
        System.out.println(msg.toString());
    }
}
