/**
 * Copyright (c) 2014-2017 墨博云舟 All Rights Reserved. 
 */

package com.heitian.ssm.base.model;

/**
 * Des:
 * Created by UlverYang[yang.jian@mobcb.com] on 2017-02-23 17:20.
 */
public class ShopInfoPojo {
    private Integer id;

    private String shopName;

    private String orgId;

    private String consumeShopId;

    public String getConsumeShopId() {
        return consumeShopId;
    }

    public void setConsumeShopId(String consumeShopId) {
        this.consumeShopId = consumeShopId;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {

        this.id = id;
    }

    public String getShopName() {

        return shopName;
    }

    public void setShopName(String shopName) {

        this.shopName = shopName;
    }

    public String getOrgId() {

        return orgId;
    }

    public void setOrgId(String orgId) {

        this.orgId = orgId;
    }
}