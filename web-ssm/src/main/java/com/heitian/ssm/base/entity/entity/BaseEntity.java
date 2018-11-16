/*
 *  Copyright (c) 2014-2017. 墨博云舟 All Rights Reserved. 
 */

package com.heitian.ssm.base.entity.entity;


import com.heitian.ssm.base.util.FieldComment;

import javax.persistence.Column;

/**
 * BaseEntity : 基础实体
 *
 * @author UlverYang
 * @version 1.00
 * @since 2017-03-08 17:47
 */
public class BaseEntity extends IdEntity implements DateEntity {

    /**
     * 创建时间
     */
    @FieldComment(text = "创建时间")
    @Column(columnDefinition = "bigint(19) default 0", name = "create_time", nullable = false, length = 19)
    private Long createTime;

    /**
     * 修改时间
     */
    @FieldComment(text = "修改时间")
    @Column(columnDefinition = "bigint(19) default 0", name = "modify_time", nullable = false, length = 19)
    private Long modifyTime;

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    @Override
    public Long getCreateTime() {

        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    @Override
    public void setCreateTime(Long createTime) {

        this.createTime = createTime;
    }

    /**
     * 获取修改时间
     *
     * @return modify_time - 修改时间
     */
    @Override
    public Long getModifyTime() {

        return modifyTime;
    }

    /**
     * 设置修改时间
     *
     * @param modifyTime 修改时间
     */
    @Override
    public void setModifyTime(Long modifyTime) {

        this.modifyTime = modifyTime;
    }
}