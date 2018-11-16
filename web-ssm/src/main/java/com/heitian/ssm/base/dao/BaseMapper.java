package com.heitian.ssm.base.dao;


import com.heitian.ssm.base.entity.entity.Entity;
import com.heitian.ssm.base.model.BaseQueryModel;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;

import java.util.List;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.provider.SpecialProvider;

/**
 * Des:基础Mapper接口，所有与数据库对应的mapper接口都要基础 此接口
 * 提供 了公共的 对 单表的一些基本操作；只针对于单表操作；
 * Created by UlverYang on 2016-04-15 18:46.
 */
public interface BaseMapper<T extends Entity, R> extends Mapper<T>, MySqlMapper<T> {

    @Override
    @Options(
            useGeneratedKeys = false,
            keyProperty = "id"
    )
    @InsertProvider(
            type = SpecialProvider.class,
            method = "dynamicSQL"
    )
    int insertList(List<T> var1);

    /**
     * sql查询manager
     *
     * @param queryModel 自定义的查询模型
     *
     * @return
     */
    List<R> selectListOrPageBySql(BaseQueryModel queryModel);

//    /**
//     * 查询最大db_id
//     *
//     * @return 最大db_id
//     *
//     * @since 2017年10月10日 by shen.zhibing
//     */
//    @SelectProvider(type = CustomSpecialProvider.class, method = "selectMaxDbId")
//    Long selectMaxDbId();

}
