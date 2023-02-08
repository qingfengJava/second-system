package com.qingfeng.cms.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.qingfeng.cms.service.FileVodService;
import com.qingfeng.cms.utils.AliyunVodSDKUtils;
import com.qingfeng.cms.utils.VodConstantPropertiesUtil;
import com.qingfeng.currency.exception.BizException;
import com.qingfeng.currency.exception.code.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/2/8
 */
@Service
@Slf4j
public class FileVodServiceImpl implements FileVodService {

    /**
     * 上传视频
     * @param file
     * @return
     */
    @Override
    public String uploadVod(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));

            UploadStreamRequest request = new UploadStreamRequest(
                    VodConstantPropertiesUtil.ACCESS_KEY_ID,
                    VodConstantPropertiesUtil.ACCESS_KEY_SECRET,
                    title, originalFilename, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
            // 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            String videoId = response.getVideoId();
            if (!response.isSuccess()) {
                String errorMessage = "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();
                log.warn(errorMessage);
                if (StringUtils.isEmpty(videoId)) {
                    throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), errorMessage);
                }
            }
            return videoId;
        } catch (IOException e) {
            throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), "qingfeng vod 服务上传失败");
        }

    }

    @Override
    public void removeVideo(String videoId) {
        try{
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(
                    VodConstantPropertiesUtil.ACCESS_KEY_ID,
                    VodConstantPropertiesUtil.ACCESS_KEY_SECRET);

            DeleteVideoRequest request = new DeleteVideoRequest();

            request.setVideoIds(videoId);

            DeleteVideoResponse response = client.getAcsResponse(request);

            System.out.print("RequestId = " + response.getRequestId() + "\n");

        }catch (ClientException e){
            throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), "视频删除失败！");
        }
    }
}
