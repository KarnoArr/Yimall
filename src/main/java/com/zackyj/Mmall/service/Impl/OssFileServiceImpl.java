package com.zackyj.Mmall.service.Impl;

import com.zackyj.Mmall.service.IFileService;
import com.zackyj.Mmall.utils.OssUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.service.Impl ON 2021/7/29-周四.
 */
@Service
public class OssFileServiceImpl implements IFileService {
    @Override
    public String upload(MultipartFile file, String path) {
        String fileURL = null;
        try {
            fileURL = OssUtil.upload(file.getOriginalFilename(), file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileURL;
    }
}
