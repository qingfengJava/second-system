package com.qingfeng.cms.domain.news.vo;

import com.qingfeng.cms.domain.news.entity.NewsNotifyEntity;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 封装分页查询出的消息通知的信息
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/12/17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "NewsNotifyListVo", description = "消息通知分页数据实体")
public class NewsNotifyListVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer total;

    private List<NewsNotifyEntity> newsNotifyEntityList;
}
