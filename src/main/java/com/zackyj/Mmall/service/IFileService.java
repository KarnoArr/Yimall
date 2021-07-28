package com.zackyj.Mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.service ON 2021/7/28-周三.
 */
public interface IFileService {
    String upload(MultipartFile file, String path);
}
