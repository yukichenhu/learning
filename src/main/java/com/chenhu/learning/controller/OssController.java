package com.chenhu.learning.controller;

import com.chenhu.learning.utils.OssUtils;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 陈虎
 * @description
 * @date 2022-09-16 10:25
 */
@RestController
@RequestMapping("/oss")
@AllArgsConstructor
public class OssController {
    private final OssUtils ossUtils;

    @PostMapping("/upload")
    public String upload(MultipartFile file){
        return ossUtils.upload(file);
    }
}
