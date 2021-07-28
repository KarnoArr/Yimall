package com.zackyj.Mmall.service.Impl;

import com.google.common.collect.Lists;
import com.zackyj.Mmall.service.IFileService;
import com.zackyj.Mmall.utils.FTPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.service.Impl ON 2021/7/28-周三.
 */
@Slf4j
@Service
public class FtpFileServiceImpl implements IFileService {

    @Override
    public String upload(MultipartFile file, String path) {
        //获得原始文件名
        String fileName = file.getOriginalFilename();
        //截取扩展名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        //使用 UUID 生成随机文件名
        String uploadFileName = UUID.randomUUID() + "." + fileExtensionName;
        log.info("文件上传操作：上传文件的文件名:{},上传的路径:{},新文件名:{}", fileName, path, uploadFileName);
        //创建文件
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            //新建目录
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        //最终目标文件对象
        File targetFile = new File(path, uploadFileName);

        try {
            //上传文件
            file.transferTo(targetFile);
            //文件已经上传成功了
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //已经上传到ftp服务器上

            targetFile.delete();
        } catch (IOException e) {
            log.error("上传文件异常", e);
            return null;
        }
        return targetFile.getName();
    }
}
