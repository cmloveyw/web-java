/*
 *  Copyright (c) 2014-2017. 墨博云舟 All Rights Reserved. 
 */

package com.heitian.ssm.base.entity.entity;

/**
 * DateEntity : 时间实体
 *
 * @author UlverYang
 * @version 1.00
 * @since 2017-03-13 11:02
 */
public interface DateEntity extends Entity {

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    Long getCreateTime();

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    void setCreateTime(Long createTime);

    /**
     * 获取修改时间
     *
     * @return modify_time - 修改时间
     */
    Long getModifyTime();

    /**
     * 设置修改时间
     *
     * @param modifyTime 修改时间
     */
    void setModifyTime(Long modifyTime);
}
