package com.qingfeng.cms.domain.notice.vo;

import com.qingfeng.cms.domain.notice.entity.NoticeEntity;
import com.qingfeng.cms.domain.notice.ro.NoticeRo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel(description = "用户查看的公告实体")
public class NoticeUserPageVo implements Serializable {

    @ApiModelProperty(value = "总记录数")
    private Integer total;

    @ApiModelProperty(value = "公告内容")
    private List<NoticeRo> noticeList;

    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @ApiModelProperty(value = "每页条目数")
    private Integer pageSize;

}
