package com.qingfeng.cms.service.impl;

import cn.hutool.core.date.DateTime;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.qingfeng.cms.service.FileService;
import com.qingfeng.cms.utils.FileOssProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/10/8
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileOssProperties fileOssProperties;

    /**
     * 上传头像文件
     * @param file
     * @return
     */
    @Override
    public String upload(MultipartFile file) throws IOException {
        OSS ossClient = getClient();

        //构建日期路径：avatar/2019/02/26/文件名
        String folder = new DateTime().toString("yyyy/MM/dd");

        //文件名：uuid.扩展名
        String fileName = UUID.randomUUID().toString();
        String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String key = "avatar/" + folder + "/" + fileName + fileExtension;

        //文件上传至阿里云
        ossClient.putObject(fileOssProperties.getBucket(), key, file.getInputStream());

        // 关闭OSSClient。
        ossClient.shutdown();

        //返回url地址
        return "https://" + fileOssProperties.getBucket() + "." + fileOssProperties.getEndpoint() + "/" + key;
    }

    /**
     * 上传文件
     * @param file
     * @return
     */
    @Override
    public String fileUpload(MultipartFile file) throws IOException {
        //判断oss实例是否存在：如果不存在则创建，如果存在则获取
        return getImgUrl(file, "file");
    }

    /**
     * 删除已上传的文件
     * @param fileUrl
     */
    @Override
    public void removeFile(String fileUrl) {
        //判断oss实例是否存在：如果不存在则创建，如果存在则获取
        OSS ossClient = getOssClient();
        String host = "https://" + fileOssProperties.getBucket() + "." + fileOssProperties.getEndpoint() + "/";

        // 删除文件。
        ossClient.deleteObject(fileOssProperties.getBucket(),
                fileUrl.substring(host.length()));

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Override
    public String uploadImg(MultipartFile file) throws IOException {
        return getImgUrl(file, "img");
    }

    private String getImgUrl(MultipartFile file, String name) throws IOException {
        //判断oss实例是否存在：如果不存在则创建，如果存在则获取
        OSS ossClient = getClient();


        //文件名：uuid.扩展名
        String fileName = UUID.randomUUID().toString().replaceAll("-", "");
        String key = name + "/" + fileName + "-" + file.getOriginalFilename();

        //文件上传至阿里云
        ossClient.putObject(fileOssProperties.getBucket(), key, file.getInputStream());

        // 关闭OSSClient。
        ossClient.shutdown();

        //返回url地址
        return "https://" + fileOssProperties.getBucket() + "." + fileOssProperties.getEndpoint() + "/" + key;
    }

    private OSS getClient() {
        OSS ossClient = getOssClient();
        if (!ossClient.doesBucketExist(fileOssProperties.getBucket())) {
            //创建bucket
            ossClient.createBucket(fileOssProperties.getBucket());
            //设置oss实例的访问权限：公共读
            ossClient.setBucketAcl(fileOssProperties.getBucket(), CannedAccessControlList.PublicRead);
        }
        return ossClient;
    }

    private OSS getOssClient() {
        //判断oss实例是否存在：如果不存在则创建，如果存在则获取
        return new OSSClientBuilder().build(
                fileOssProperties.getEndpoint(),
                fileOssProperties.getAccesskey(),
                fileOssProperties.getSecretKey());
    }
}
