package com.qingfeng.sdk.messagecontrol.clazz;

import com.qingfeng.cms.domain.clazz.vo.UserVo;
import com.qingfeng.currency.base.R;

import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/14
 */
public class ClazzInfoApiFallback implements ClazzInfoApi {

    /**
     * 查询班级下的学生信息
     * @return
     */
    @Override
    public R<List<UserVo>> stuList() {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }
}
