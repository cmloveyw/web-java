/*
 *  Copyright (c) 2014-2017. 墨博云舟 All Rights Reserved. 
 */

package com.heitian.ssm.base.entity.entity;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * IdEntity : 主键实体
 *
 * @author UlverYang
 * @version 1.00
 * @since 2017-03-08 17:47
 */
public abstract class IdEntity implements Entity {
    /**
     * openid标识
     */
    @Id
    @Column(columnDefinition = "varchar(20) default null", name = "id", nullable = false, length = 20)
    private String id;

    /**
     * 获取openid标识
     *
     * @return id - openid标识
     */
    public String getId() {

        return id;
    }

    /**
     * 设置openid标识
     *
     * @param id openid标识
     */
    public void setId(String id) {

        this.id = id;
    }
}
