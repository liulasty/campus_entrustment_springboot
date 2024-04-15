package com.lz.controller;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/14/23:36
 * @Description:
 */

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.lz.pojo.result.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @author lz
 */
@RestController
@RequestMapping("/img")
@Slf4j
@Api(tags = "上传图片")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ImgController {
    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.bucketName}")
    private String bucketName;



    /**
     * 上传图片
     *
     * @param file 文件
     *
     * @return {@code ResponseEntity<String>}
     */
    @PostMapping("/upload")
    public Result<String> uploadImage(@RequestParam("file") MultipartFile file) {
        HttpHeaders headers = new HttpHeaders();
        try {
            String folderName = "photos/";
            // 生成唯一的文件名
            String fileName = folderName + UUID.randomUUID() + ".png";

            // 创建OSSClient实例
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 上传文件到阿里云Bucket
            ossClient.putObject(bucketName, fileName, file.getInputStream());

            // 关闭OSSClient
            ossClient.shutdown();
            log.info("Image uploaded successfully");
            // 返回上传成功的消息
            return Result.success(fileName, "Image_uploaded_successfully");
        } catch (IOException e) {
            log.error("Failed to upload image", e);
            return Result.error("Failed_to_upload_image");
        }
    }
}