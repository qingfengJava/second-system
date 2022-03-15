package com.qingfeng.controller;

import com.qingfeng.entity.QualityActive;
import com.qingfeng.service.QualityActiveService;
import com.qingfeng.utils.FileUtils;
import com.qingfeng.vo.ResStatus;
import com.qingfeng.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/3/14
 */
@CrossOrigin
@RestController
@Api(value = "提供精品活动相关的接口功能",tags = "精品活动管理")
@RequestMapping("/quality")
public class QualityActiveController {

    @Autowired
    private QualityActiveService qualityActiveService;

    /**
     * 文件路径
     */
    @Value("${photo.file.dir}")
    private String realPath;

    @ApiOperation("精品活动申请快照")
    @PostMapping("/add")
    public ResultVO addQualityActive(QualityActive qualityActive,String oldPhoto, MultipartFile img) throws IOException {
        try {
            //判断是否更新活动图片  空是true，表示没有更新活动图片
            boolean notEempty = (img != null);
            //不为空
            if (notEempty){
                //检查就照片是否存在，存在就将其删除掉，再保存新照片
                File file = new File(realPath,oldPhoto);
                if (file.exists()) {
                    //如果文件存在，就删除文件
                    if (!oldPhoto.equals("default.png")){
                        //如果不是默认头像就删除老照片
                        file.delete();
                    }
                }
                //处理新的图片上传   1、处理图片的上传 & 修改文件名
                String newFileName = FileUtils.uploadFile(img, realPath);
                //修改活动图片的信息
                qualityActive.setImg(newFileName);
            }else {
                //否则不修改图片
                qualityActive.setImg(oldPhoto);
            }
            return qualityActiveService.addQualityActive(qualityActive);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResultVO(ResStatus.NO,"网络错误！",null);
        }
    }

    @ApiOperation("查询社团学年精品活动的个数")
    @PostMapping("/queryApplyQualityActive/{organizeId}")
    public ResultVO queryApplyQualityActiveCount(@PathVariable("organizeId") Integer organizeId){
        //直接根据活动表Id查询精品活动申请快照表中对应Id精品活动的个数
        return qualityActiveService.queryApplyQualityActiveCount(organizeId);
    }
}
