///*
// *  Copyright (c) 2014-2017. 墨博云舟 All Rights Reserved. 
// */
//
//package com.heitian.ssm.base.business.impl;
//
//import com.github.pagehelper.Page;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.StringUtil;
//import com.heitian.ssm.base.business.BaseBusiness;
//import com.heitian.ssm.base.dao.BaseMapper;
//import com.heitian.ssm.base.entity.entity.DateEntity;
//import com.heitian.ssm.base.entity.entity.Entity;
//import com.heitian.ssm.base.entity.entity.IdEntity;
//import com.heitian.ssm.exceptions.PlatformException;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//import java.util.List;
//
//import javax.persistence.Column;
//
//import tk.mybatis.mapper.entity.Example;
//
///**
// * BaseBusinessImpl : 基础business 实现
// *
// * @param <T> entity类
// * @param <R> DO类或者entity类
// *
// * @author UlverYang
// * @version 1.00
// * @since 2017-03-08 18:25
// */
//public class BaseBusinessImpl<T extends Entity, R> implements BaseBusiness<T, R> {
//
//    /**
//     * 基础mapper
//     */
//    protected BaseMapper<T, R> baseMapper;
//
////    /**
////     * spring工具类
////     */
////    @Autowired
////    SpringUtils springUtils;
//
//    public void setBaseMapper(BaseMapper<T, R> baseMapper) {
//
//        this.baseMapper = baseMapper;
//    }
//
//    public BaseMapper<T, R> getBaseMapper() {
//
//        return baseMapper;
//    }
//
//    @Override
//    public T selectByKey(String key) {
//
//        return baseMapper.selectByPrimaryKey(key);
//    }
//
//    @Override
//    public T selectByIntKey(int key) {
//
//        return baseMapper.selectByPrimaryKey(key);
//    }
//
//    @Override
//    public void insertBatch(List<T> entityList) {
//
//        if (entityList.size() > 0) {
//            T t = entityList.get(0);
//            if (t instanceof IdEntity) {
//                for (T entity : entityList) {
//                    IdEntity idEntity = (IdEntity) entity;
//                    if (CrmStringUtils.isEmptyOrNull(idEntity.getId())) {
//                        idEntity.setId(OpenIdUtil.generateObjectId());
//                    }
//                }
//            }
//
//            if (t instanceof DateEntity) {
//                long currentTime = DatetimeUtils.currentTimestamp();
//                for (T entity : entityList) {
//                    DateEntity dateEntity = (DateEntity) entity;
//                    if (null == dateEntity.getCreateTime()) {
//                        dateEntity.setCreateTime(currentTime);
//                    }
//                    if (null == dateEntity.getModifyTime()) {
//                        dateEntity.setModifyTime(currentTime);
//                    }
//                }
//            }
//        }
//        baseMapper.insertList(entityList);
//    }
//
//    @Override
//    public void save(T entity, boolean isInsert) {
//
//        if (!(entity instanceof Entity)) {
//            throw new PlatformException("实体非Entity子类");
//        }
//        if (isInsert) {
//            if (entity instanceof IdEntity) {
//                IdEntity idEntity = (IdEntity) entity;
//                if (CrmStringUtils.isEmptyOrNull(idEntity.getId())) {
//                    idEntity.setId(OpenIdUtil.generateObjectId());
//                }
//            }
//            if (entity instanceof DateEntity) {
//                DateEntity dateEntity = (DateEntity) entity;
//                long currentTime = DatetimeUtils.currentTimestamp();
//                if (null == dateEntity.getCreateTime()) {
//                    dateEntity.setCreateTime(currentTime);
//                }
//                if (null == dateEntity.getModifyTime()) {
//                    dateEntity.setModifyTime(currentTime);
//                }
//            }
//            baseMapper.insertSelective(entity);
//        } else {
//            updateSetDate(entity);
//            baseMapper.updateByPrimaryKeySelective(entity);
//        }
//    }
//
//    private Class getTClass() {
//
//        Type type = this.getClass().getGenericSuperclass();
//        Type[] args = ((ParameterizedType) type).getActualTypeArguments();
//        Class entityClass = (Class) args[0];
//        return entityClass;
//    }
//
//    /**
//     * 获取主键字段
//     *
//     * @param c 类
//     *
//     * @return
//     */
//    private Field getIdField(Class c) {
//
//        Field[] fs = c.getDeclaredFields();
//        Field idField = null;
//        for (Field f : fs) {
//            if (null != f.getAnnotation(javax.persistence.Id.class)) {
//                idField = f;
//                break;
//            }
//        }
//        if (null == idField) {
//            if (c.getSuperclass().equals(Object.class)) {
//                return null;
//            }
//            return getIdField(c.getSuperclass());
//        } else {
//            return idField;
//        }
//    }
//
//    @Override
//    public void delete(Object key) {
//
//        Class entityClass = getTClass();
//        try {
//            Field statusField;
//            try {
//                statusField = entityClass.getDeclaredField("status");
//            } catch (NoSuchFieldException ex) {
//                statusField = entityClass.getSuperclass().getDeclaredField("status");
//            }
//            if (null != statusField) {
//
//                Field idField = getIdField(entityClass);
//
//                String name = idField.getName();
//                String first = (name.charAt(0) + "").toUpperCase();
//                Method idMethod = entityClass.getMethod("set" + first + name.substring(1), idField.getType());
//                T obj = (T) entityClass.newInstance();
//                idMethod.invoke(obj, key);
//
//                Class statusType = statusField.getType();
//                Method statusMethod = entityClass.getMethod("setStatus", statusType);
//
//                if (statusType.equals(Integer.class)) {
//                    statusMethod.invoke(obj, -1);
//                } else {
//                    statusMethod.invoke(obj, "-1");
//                }
//
////                BussinessDataLogUtil
////                        .addChild(BussinessDBOperaType.UPDATE, null, obj, "状态删除(" + entityClass.getSimpleName() + ")信息",
////                                getBaseMapperRealClass());
//                this.save(obj, false);
//            } else {
////                BussinessDataLogUtil.addChild(BussinessDBOperaType.DELETE_KEY, null, key,
////                        "物理删除(" + entityClass.getSimpleName() + ")信息", getBaseMapperRealClass());
//                baseMapper.deleteByPrimaryKey(key);
//            }
//        } catch (NoSuchFieldException ex) {
////            BussinessDataLogUtil
////                    .addChild(BussinessDBOperaType.DELETE_KEY, null, key, "物理删除(" + entityClass.getSimpleName() + ")信息",
////                            getBaseMapperRealClass());
//            baseMapper.deleteByPrimaryKey(key);
//        } catch (Exception e) {
//            //throw new BusinessException(e);
//        }
//    }
//
//    private Class getBaseMapperRealClass() {
//
//        Class[] is = baseMapper.getClass().getInterfaces();
//        for (Class c : is) {
//            if (BaseMapper.class.isAssignableFrom(c)) {
//                return c;
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 更新设置修改时间
//     *
//     * @param entity 对象
//     */
//    private void updateSetDate(T entity) {
//
//        if (entity instanceof DateEntity) {
//            DateEntity dateEntity = (DateEntity) entity;
//            long currentTime = DatetimeUtils.currentTimestamp();
//
//            // if(null == dateEntity.getModifyTime()) {
//            dateEntity.setModifyTime(currentTime);
//            // }
//        }
//    }
//
//    @Override
//    public void updateAll(T entity) {
//
//        updateSetDate(entity);
//
//        baseMapper.updateByPrimaryKey(entity);
//    }
//
//    @Override
//    public void updateNotNull(T entity) {
//
//        this.save(entity, false);
//    }
//
//    @Override
//    public List<T> selectList(T entity) {
//
//        return this.baseMapper.select(entity);
//    }
//
//    @Override
//    public void deleteBatch(Object[] keys) {
//
//        for (Object key : keys) {
//            this.delete(key);
//        }
//    }
//
//    @Override
//    public void deleteBatch(String keys) {
//
//        String[] keysArray = keys.split(",");
//        for (String key : keysArray) {
//            this.delete(key);
//        }
//    }
//
//    @Override
//    public Page<T> selectListWithPageByExample(Example example, int currentPage, int pageItemSize, String orderField,
//            String orderType) {
//
//        Class entityClass = getTClass();
//
//        setStatusCriteria(example);
//
//        if (StringUtil.isEmpty(orderField)) {
//            PageHelper.startPage(currentPage, pageItemSize);
//        } else {
//            try {
//                String orderCause;
//                Column a = entityClass.getDeclaredField(orderField).getAnnotation(
//                        Column.class);
//
//                if (null != a) {
//                    orderCause = a.name() + " " + orderType;
//                } else {
//                    orderCause = orderField + " " + orderType;
//                }
//                PageHelper.startPage(currentPage, pageItemSize, orderCause);
//
//            } catch (Exception e) {
//                throw new BusinessException(e);
//            }
//        }
//        return (Page<T>) this.baseMapper.selectByExample(example);
//    }
//
//    @Override
//    public List<T> selectListByExample(Example example) {
//
//        setStatusCriteria(example);
//        return this.baseMapper.selectByExample(example);
//    }
//
//    /**
//     * 设置status查询条件；在有status字段时候，只查询status为正常的；
//     *
//     * @param example 查询条件
//     */
//    private void setStatusCriteria(Example example) {
//
//        Class entityClass = getTClass();
//        try {
//            Example ex = example;
//            try {
//                if (null != entityClass.getDeclaredField("status")) {
//                    ex.getOredCriteria().get(0).andEqualTo("status", 1);
//                }
//            } catch (NoSuchFieldException e) {
//                if (null != entityClass.getSuperclass().getDeclaredField("status")) {
//                    ex.getOredCriteria().get(0).andEqualTo("status", 1);
//                }
//            }
//        } catch (Exception e) {
//            // TODO: 2017-03-08
//        }
//    }
//
//    @Override
//    public int countByExample(Example example) {
//
//        setStatusCriteria(example);
//        return this.baseMapper.selectCountByExample(example);
//    }
//
//    @Override
//    public void settingXmlPaging(BaseQueryModel model) {
//
//        if (model.getIsPaging()) {
//            PageHelper.startPage(model.getCurrentPage(), model.getPageItemSize());
//        }
//    }
//
//    @Override
//    public List<R> selectListOrPageBySql(BaseQueryModel queryModel) {
//
//        this.settingXmlPaging(queryModel);
//        return this.baseMapper.selectListOrPageBySql(queryModel);
//    }
//
//    @Override
//    public R getDetails(BaseQueryModel queryModel) {
//
//        List<R> list = this.baseMapper.selectListOrPageBySql(queryModel);
//        if (list.size() != 1) {
//            return null;
//        } else {
//            return list.get(0);
//        }
//    }
//
//    @Override
//    public T selectOneByExample(Example example) {
//
//        List<T> list = this.selectListByExample(example);
//        if (list.size() == 1) {
//            return list.get(0);
//        } else if (list.size() == 0) {
//            return null;
//        } else {
//            throw new PlatformException(CommonErrorCode.QUERIED_MULTI_RECORDS, "");
//        }
//    }
//
//    //    @Override
//    //    public void deleteList(List<T> list) {
//    //        for (T _class : list) {
//    //            baseMapper.delete(_class);
//    //        }
//    //    }
//
//    @Override
//    public List<T> selectAll() {
//
//        return baseMapper.selectAll();
//    }
//
//    @Override
//    public List<T> selectNormalAll(Class<?> entityClass) {
//        Example example = new Example(entityClass);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("status", CommonConstants.Status.NORMAL_INT);
//        return baseMapper.selectByExample(example);
//    }
//
//    @Override
//    public void deletePhysicsData(Object key) {
//
//        baseMapper.deleteByPrimaryKey(key);
//    }
//
//    @Override
//    public int countByEntity(T entity) {
//
//        return this.baseMapper.selectCount(entity);
//    }
//
//    @Override
//    public Long selectMaxDbId() {
//        return this.baseMapper.selectMaxDbId();
//    }
//}
