package com.qingfeng.sdk.sms.email;

import com.qingfeng.currency.base.R;
import com.qingfeng.sdk.sms.email.domain.EmailEntity;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/12/12
 */
@Slf4j
public class EmailApiFallback implements EmailApi {

    /**
     * 请求失败直接返回-1
     * @param emailEntity
     * @return
     */
    @Override
    public Integer sendEmail(EmailEntity emailEntity) {
        return R.FAIL_CODE;
    }
}
