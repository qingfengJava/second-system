package com.qingfeng.cms.biz.organize.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.organize.dao.OrganizeImgDao;
import com.qingfeng.cms.biz.organize.service.OrganizeImgService;
import com.qingfeng.cms.domain.organize.entity.OrganizeImgEntity;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.currency.exception.BizException;
import com.qingfeng.currency.exception.code.ExceptionCode;
import com.qingfeng.sdk.file.FileOssApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 社团组织图片信息
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:26
 */
@Service
public class OrganizeImgServiceImpl extends ServiceImpl<OrganizeImgDao, OrganizeImgEntity> implements OrganizeImgService {

    @Autowired
    private DozerUtils dozerUtils;

    @Autowired
    private FileOssApi fileOssApi;

    /**
     * @param organizeId
     * @param userId
     * @return
     */
    @Override
    public List<OrganizeImgEntity> getImgList(Long organizeId, Long userId) {
        List<OrganizeImgEntity> organizeImgList = baseMapper.selectList(Wraps.lbQ(new OrganizeImgEntity())
                .eq(OrganizeImgEntity::getOrganizeId, organizeId));
        if (CollUtil.isNotEmpty(organizeImgList)) {
            return organizeImgList;
        } else {
            return baseMapper.selectList(Wraps.lbQ(new OrganizeImgEntity())
                    .eq(OrganizeImgEntity::getCreateUser, userId));
        }
    }

    @Override
    public void saveOrganizeImg(Long organizeId, MultipartFile file) {
        R<String> r = fileOssApi.uploadImg(file);
        if (r.getIsSuccess()) {
            String imgUrl = r.getData();

            //将url信息进行保存
            baseMapper.insert(OrganizeImgEntity.builder()
                    .organizeId(organizeId)
                    .imgUrl(imgUrl)
                    .build());
        } else {
            throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), "文件上传失败！");
        }
    }

}