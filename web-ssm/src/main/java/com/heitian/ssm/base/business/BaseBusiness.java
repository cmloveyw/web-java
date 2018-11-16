/*
 *  Copyright (c) 2014-2017. 墨博云舟 All Rights Reserved. 
 */

package com.heitian.ssm.base.business;

import com.github.pagehelper.Page;
import com.heitian.ssm.base.model.BaseQueryModel;

import java.util.List;

import tk.mybatis.mapper.entity.Example;

/**
 * BaseBusiness : 基础business 接口
 * <p>
 * 所有 表示是单表操作；都使用 通用mapper 动态sql来执行，不需要再mapper xml文件中配合；可以直接使用
 *
 * @param <T> entity类
 * @param <R> DO类或者entity类
 *
 * @author UlverYang
 * @version 1.00
 * @since 2017-03-08 18:26
 */
public interface BaseBusiness<T, R> {

    /**
     * 查询单条数据，根据example
     *
     * @param example 条件
     *
     * @return 实体信息
     */
    T selectOneByExample(Example example);

    /**
     * 根据string主键查询
     *
     * @param key 主键
     *
     * @return 实体对象
     */
    T selectByKey(String key);

    /**
     * 根据int主键查询
     *
     * @param key 主键
     *
     * @return 实体对象
     */
    T selectByIntKey(int key);

    /**
     * 新增或者更新实体对象
     *
     * @param entity   实体对象
     * @param isInsert 为true时是 新增；false时是更新
     */
    void save(T entity, boolean isInsert);

    /**
     * 批量插入 实体对象
     *
     * @param entityList 实体对象list
     */
    void insertBatch(List<T> entityList);

    /**
     * 根据主键删除实体对象；
     * <p>
     * 单表操作；（如果有Status字段，则更新状态为Satus=-1；否则直接删除数据）
     *
     * @param key 主键
     */
    void delete(Object key);

    /**
     * 根据主键删除物理数据，直接删除数据
     *
     * @param key 主键
     */
    void deletePhysicsData(Object key);

    /**
     * 更新实体对象的所有字段
     *
     * @param entity 实体对象
     */
    void updateAll(T entity);

    /**
     * 更新实体对象的所有非空字段
     *
     * @param entity 实体对象
     */
    void updateNotNull(T entity);

    /**
     * 以实体对象中非空字段未查询条件 来 查询数据：单表操作
     *
     * @param entity 查询条件：实体对象
     *
     * @return 实体对象list
     */
    List<T> selectList(T entity);

    /**
     * 根据主键数组批量删除数据：单表操作（如果有Status字段，则更新状态为Satus=-1；否则直接删除数据）
     *
     * @param keys 主键 数组
     */
    void deleteBatch(Object[] keys);

    /**
     * 根据多个主键id（英文逗号分隔的id）删除数据；单表操作（如果有Status字段，则更新状态为Satus=-1；否则直接删除数据）
     *
     * @param keys 英文逗号分隔的id
     */
    void deleteBatch(String keys);

    //    /**
    //     * 单表操作：分页查询数据
    //     *
    //     * @param entity       查询的数据
    //     * @param currentPage
    //     * @param pageItemSize
    //     * @param orderField
    //     * @param orderType
    //     * @return
    //     */
    //    Page<T> selectListWithPage(T entity, int currentPage, int pageItemSize, String orderField, String orderType);

    /**
     * 单表操作：分页查询数据
     *
     * @param example      Example对象
     * @param currentPage  当前页
     * @param pageItemSize 每页大小
     * @param orderField   排序字段
     * @param orderType    排序类型
     *
     * @return 分页数据
     */
    Page<T> selectListWithPageByExample(Example example, int currentPage, int pageItemSize, String orderField,
            String orderType
    );

    /**
     * 根据Example查询实体list：单表操作：更具example 查询数据
     *
     * @param example 查询条件
     *
     * @return 实体list
     */
    List<T> selectListByExample(Example example);

    /**
     * 根据Example条件进行count统计；单表操作：count
     *
     * @param example 查询条件
     *
     * @return count数量
     */
    int countByExample(Example example);

    /**
     * 根据实体对象条件进行count统计；单表操作：count单表操作：count
     *
     * @param entity 查询条件
     *
     * @return count数量
     */
    int countByEntity(T entity);

    /**
     * sql操作：设置通过xml查询的分页的配置；（通过xml配置sql的方式）
     *
     * @param model 查询条件
     */
    void settingXmlPaging(BaseQueryModel model);

    /**
     * 根据queryModel参数查询数据;
     * 支持分页 通过 queryModel中的isPaging的值来控制:true表示分页；false表示不分页
     *
     * @param queryModel 查询模型；其中包含 搜索条件和通用分页及排序条件
     *
     * @return do或实体list
     */
    List<R> selectListOrPageBySql(BaseQueryModel queryModel);

    /**
     * 获取do或实体对象；返回一条数据
     *
     * @param queryModel 查询条件
     *
     * @return do或实体对象
     */
    R getDetails(BaseQueryModel queryModel);

    //    /**
    //     * 删除对象信息；直接删除数据
    //     * @param list
    //     */
    //    void deleteList(List<T> list);

    /**
     * 查询所有实体对象数据
     *
     * @return 所有实体对象list
     */
    List<T> selectAll();

    /**
     * 查询所有实体有效数据
     * status == 1
     *
     * @param entityClass
     *
     * @return
     */
    List<T> selectNormalAll(Class<?> entityClass);

    /**
     * 查询最大db_id
     *
     * @return 最大db_id
     * @since 2017年10月10日 by shen.zhibing
     */
    Long selectMaxDbId();
}
