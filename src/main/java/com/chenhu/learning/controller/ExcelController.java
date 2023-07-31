package com.chenhu.learning.controller;

import com.chenhu.learning.utils.ImageUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author chenhu
 * @description
 * @date 2023-02-28 11:42
 */
@RestController
@RequestMapping("/excel")
public class ExcelController {
    @GetMapping("/image")
    public void addWatermark() throws IOException {
        ImageUtils.createWaterMark("测试水印", "/opt/app/test.png");
    }
}
