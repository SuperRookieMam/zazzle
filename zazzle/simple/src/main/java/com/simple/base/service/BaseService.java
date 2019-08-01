package com.simple.base.service;


import com.simple.base.componet.params.DynamicParam;
import com.simple.base.dao.BaseRepository;
import com.simple.base.entity.BaseEntity;
import com.simple.orm.componet.feature.PageInfo;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T extends BaseEntity,ID extends Serializable> {

    T findById(ID id);

    List<T> findByIds(Iterable<ID> iterable);

    List<T> findListByParams(DynamicParam dynamicParam);

    PageInfo<T> findPageByParams(DynamicParam dynamicParam);

    T updateByEntity(T entity);

    List<T> updateByEntitys(Iterable<T> iterable);

    int updateByParams(DynamicParam dynamicParam);

    void deletById(ID id);

    void deletByEntitys(Iterable<T> entitys);

    int deletByParam(DynamicParam dynamicParam);

    T insertByEntity(T entity);

    List<T> insertByEntitys(Iterable<T> entitys);

    BaseRepository<T,ID> getBaseRepository();
}
