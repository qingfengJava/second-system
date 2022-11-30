package com.qingfeng.sdk.messagecontrol.dict;

import com.qingfeng.cms.domain.dict.entity.DictEntity;
import com.qingfeng.currency.base.R;

import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/11/30
 */
public class DictFallback implements DictApi{

    /**
     * 查询所有的学院信息
     * @return
     */
    @Override
    public R<List<DictEntity>> findDepartment() {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }
}
