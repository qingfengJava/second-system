package com.qingfeng.currency.authority.dto.core;

import com.qingfeng.currency.authority.entity.core.Org;
import com.qingfeng.currency.model.ITreeNode;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 组织树
 *
 * @author 清风学Java
 */
@ToString(callSuper = true)
@Data
@ApiModel(value = "OrgTreeDTO", description = "组织树")
public class OrgTreeDTO extends Org implements ITreeNode<OrgTreeDTO, Long> {
    private List<OrgTreeDTO> children;
    private String label;

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public Long getCreateUser() {
        return super.getCreateUser();
    }

    @Override
    public Long getUpdateUser() {
        return super.getUpdateUser();
    }

//    @Override
//    public void add(OrgTreeDTO node) {
//        children.add(node);
//    }

    @Override
    public List<OrgTreeDTO> getChildren() {
        return this.children;
    }

    @Override
    public void setChildren(List<OrgTreeDTO> children) {
        this.children = children;
    }

    public String getLabel() {
        return this.getName();
    }

    //    @Override
//    public boolean equals(Object o) {
//        return super.equals(o);
//    }
//
//    @Override
//    public int hashCode() {
//        return super.hashCode();
//    }
}
