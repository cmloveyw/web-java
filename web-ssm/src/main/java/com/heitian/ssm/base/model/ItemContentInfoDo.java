/**
 *   Copyright (c) 2014-2017 墨博云舟 All Rights Reserved. 
 */
package com.heitian.ssm.base.model;

import java.io.Serializable;

/**
 * Des:content信息
 * Created by UlverYang[yang.jian@mobcb.com] on 2017-02-20 16:54.
 */
public class ItemContentInfoDo implements Serializable {

    private String itemCode;
    private String itemName;

    public ItemContentInfoDo() {
    }

    public ItemContentInfoDo(String itemCode, String itemName) {
        this.itemCode = itemCode;
        this.itemName = itemName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
