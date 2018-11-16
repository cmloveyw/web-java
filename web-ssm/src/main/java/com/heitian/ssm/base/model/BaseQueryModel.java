/*
 *  Copyright (c) 2014-2017. 墨博云舟 All Rights Reserved. 
 */

package com.heitian.ssm.base.model;

import java.io.Serializable;

/**
 * Des: 基础 查询模型对象；
 * 此类包含了 通用的 分页 信息以及排序 信息
 * 所有的业务查询模型，要 继承 此类
 * Created by UlverYang on 2016-04-15 14:07.
 */
public class BaseQueryModel implements Serializable {

    private Boolean isPaging = false; // 是否分页；默认不分页

    private String sortField; // 排序字段

    private String orderColumn; // 排序字段的数据库字段

    private String sortType = "ASC"; // 排序类型

    private String orderByString; // sortField转换成的数据库字段

    private Integer currentPage = 1; // 当前页

    private Integer pageItemSize = 10; // 每页条数

    /**
     * 默认支持的查询条件：id，和status状态
     */
    private String shopId; // 商户id

    private Object id; // 主键

    /**
     * 登录用户id
     */
    private String loginUserId;

//    private Integer status = CommonConstants.Status.NORMAL_INT; //状态 1正常，-1删除

    private Integer isExport = 0;//是否导出：1是，0否

    private String exportIds; // 需要导出的id字符串，用逗号分割；接受页面传递过来的数据

    public Object[] exportIdArray; // sql查询：exportIds解析之后得到exportIdArray；在mapper xml文件是使用

    private int exportIdsType = 1; // 1 integer, 2 string


    public static String DESC = "DESC";

    public static String ASC = "ASC";

    public Object[] getExportIdArray() {

//        if (CrmStringUtils.isNotEmptyOrNull(exportIds)) {
//            String[] idStringArray = exportIds.split(",");
//            Object[] idObjectArray = new Object[idStringArray.length];
//            for (int i = 0; i < idStringArray.length; i++) {
//                if (exportIdsType == 1) {
//                    idObjectArray[i] = Integer.valueOf(idStringArray[i]);
//                } else {
//                    idObjectArray[i] = String.valueOf(idStringArray[i]);
//                }
//
//            }
//            return idObjectArray;
//        }
        return null;
    }

    /**
     * 是否公共导出=======================
     */
    private Integer isCommonExport = 0;//是否导出：1是，0否
    /**=======================*/

    /**
     * 设置 导出时，的id的类型为String；默认是int
     */
    public void settingExportIdsTypeToString() {

        this.exportIdsType = 2;
    }

    public Integer getIsExport() {

        return isExport;
    }

    public void setIsExport(Integer isExport) {

        this.isExport = isExport;
    }

    public Object getId() {

        return id;
    }

    public void setId(Object id) {

        this.id = id;
    }

    public Boolean getIsPaging() {

        return isPaging;
    }

    public void setIsPaging(Boolean paging) {

        isPaging = paging;
    }

    public void paging() {
        /**如果当前是导出操作，那么就不分页*/
        if (isCommonExport == 1) {
            this.setIsPaging(false);
        } else {
            this.setIsPaging(true);
        }
    }

    public void noPaging() {

        this.setIsPaging(false);
    }

    public String getSortType() {

        return sortType;
    }

    public void setSortType(String sortType) {

        if (null != sortType && !"".equals(sortType.trim())) {

            sortType = sortType.trim().toUpperCase();

            if (DESC.equals(sortType)) {
                this.sortType = "DESC";
            } else if (ASC.equals(sortType)) {
                this.sortType = "ASC";
            }
        }
    }

    public Integer getCurrentPage() {

        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {

        this.currentPage = currentPage;
    }

    public Integer getPageItemSize() {

        return pageItemSize;
    }

    public void setPageItemSize(Integer pageItemSize) {

        this.pageItemSize = pageItemSize;
    }

    public String getSortField() {

        return sortField;
    }

    public void setSortField(String sortField) {

        if (null != sortField && !"".equals(sortField.trim())) {
            sortField = sortField.trim();
            if (!sortField.contains("\t") && !sortField.contains("\r") && !sortField.contains("\t") && !sortField
                    .contains(" ")) {
                this.sortField = sortField;
            } else {
                this.sortField = null;
            }
        } else {
            this.sortField = null;
        }
    }

    public void setOrderColumn(String orderColumn) {

        this.orderColumn = orderColumn;
    }

    private String getOrderColumn() {

        if (null != this.sortField) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < this.sortField.length(); i++) {
                char c = this.sortField.charAt(i);
                if (c >= 'A' && c <= 'Z') {
                    c = (char) (c + 32);
                    sb.append("_" + c);
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        } else {
            return "";
        }
    }

    public String getOrderByString() {

        if (null != this.sortField) {
            StringBuffer sb = new StringBuffer("order by ");
            for (int i = 0; i < this.sortField.length(); i++) {
                char c = this.sortField.charAt(i);
                if (c >= 'A' && c <= 'Z') {
                    c = (char) (c + 32);
                    sb.append("_" + c);
                } else {
                    sb.append(c);
                }
            }
            sb.append(" ");
            sb.append(this.sortType);
            return sb.toString();
        }
        return "";
    }

//    public Integer getStatus() {
//
//        return status;
//    }
//
//    public void setStatus(Integer status) {
//
//        this.status = status;
//    }

    public String getExportIds() {

        return exportIds;
    }

    public void setExportIds(String exportIds) {

        this.exportIds = exportIds;
    }

    public String getShopId() {

        return shopId;
    }

    public void setShopId(String shopId) {

        this.shopId = shopId;
    }

    public Integer getIsCommonExport() {

        return isCommonExport;
    }

    public void setIsCommonExport(Integer isCommonExport) {

        this.isCommonExport = isCommonExport;
    }

    public int getExportIdsType() {
        return exportIdsType;
    }

    public void setExportIdsType(int exportIdsType) {
        this.exportIdsType = exportIdsType;
    }

    public String getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(String loginUserId) {
        this.loginUserId = loginUserId;
    }
}
