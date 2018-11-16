/**
 *   Copyright (c) 2014-2017 墨博云舟 All Rights Reserved. 
 */
package com.heitian.ssm.base.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * IcnDo : 数据的Id、code、name封装
 *
 * @author luxiangbing
 * @version 1.00
 * @since 2017/3/18 10:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IcnDo {
    /**
     * id
     */
    private String id;
    /**
     * 编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
}
