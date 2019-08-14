package com.simple.base.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.simple.base.componet.params.DynamicParam;
import com.simple.base.componet.util.ParamUtil;
import com.simple.base.dao.BaseRepository;
import com.simple.base.entity.BaseEntity;
import com.simple.base.service.BaseService;
import com.simple.orm.componet.feature.*;
import com.simple.orm.componet.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class BaseServiceImpl<T extends BaseEntity,ID extends Serializable> implements BaseService<T, ID> {
    @Autowired
    public BaseRepository<T,ID> baseRepository;
    @Override
    public T  findById(ID id) {
        Optional<T> optional= baseRepository.findById(id);
        return optional.isPresent()?optional.get():null;
    }
    @Override
    public List<T> findByIds(Iterable<ID> iterable){
        return  baseRepository.findAllById(iterable);
    }
    @Override
    public List<T> findListByParams(DynamicParam dynamicParam){
        DynamicTypeSelect<T> dynamicTypeSelect = baseRepository.getDynamicTypeSelect();
        dynamicTypeSelect.dynamicBuild(ele ->{
            Predicate predicate = ParamUtil.analysisDynamicParam(dynamicParam,ele.builder,ele.root);
            if (!ObjectUtils.isEmpty(predicate))
                ele.query.where(predicate);
                ParamUtil.orderby(ele.builder,ele.query,ele.root, dynamicParam.getSort()==null?null: dynamicParam.getSort().toArray(new String[0]));
                ParamUtil.groupBy(ele.query,ele.root,dynamicParam.getGroupby()==null?null:dynamicParam.getGroupby().toArray(new String[0]));
            return predicate;
        });
        return  dynamicTypeSelect.getResult();
    }
    @Override
    public PageInfo<T> findPageByParams(DynamicParam dynamicParam){
        Assert.isTrue(!ObjectUtils.isEmpty(dynamicParam.getPageNum())&&!ObjectUtils.isEmpty(dynamicParam.getPageSize()),"分页信息不能为空");
        DynamicPageTypeSelect<T> DynamicPageTypeSelect = baseRepository.readPageType(dynamicParam.getPageNum(),dynamicParam.getPageSize());
        DynamicPageTypeSelect.dynamicBuild(ele ->{
            Predicate predicate = ParamUtil.analysisDynamicParam(dynamicParam,ele.builder,ele.root);
            Predicate predicateCount = ParamUtil.analysisDynamicParam(dynamicParam,ele.builder,ele.countRoot);
            if (!ObjectUtils.isEmpty(predicate))
                ele.query.where(predicate);
            if (!ObjectUtils.isEmpty(predicateCount))
                ele.countQuery.where(predicateCount);
            JSONArray jsonArray = dynamicParam.getSort();
            ParamUtil.orderby(ele.builder,ele.query,ele.root, jsonArray==null?null: jsonArray.toArray(new String[0]));
            ParamUtil.groupBy(ele.query,ele.root,dynamicParam.getGroupby()==null?null:dynamicParam.getGroupby().toArray(new String[0]));
            return predicate;
        });
        return  DynamicPageTypeSelect.getResult();
    }
    @Override
    @Transactional(value = "platformTransactionManager",rollbackFor = Exception.class)
    public T updateByEntity(T entity){
       return baseRepository.save(entity);
    }
    @Override
    @Transactional(value = "platformTransactionManager",rollbackFor = Exception.class)
    public List<T> updateByEntitys(Iterable<T> iterable){
       return baseRepository.saveAll(iterable);
    }
    @Override
    @Transactional(value = "platformTransactionManager",rollbackFor = Exception.class)
    public int updateByParams(DynamicParam dynamicParam){
        DynamicUpdate<T> dynamicUpdate = baseRepository.getDynamicUpdate();
        JSONObject jsonObject = dynamicParam.getUpdateFiled();
        Assert.isTrue(!ObjectUtils.isEmpty(jsonObject),"更新字段不能为空");
        dynamicUpdate.dynamicBuild(ele ->{
            Set<String> set=jsonObject.keySet();
            Iterator<String> iterator=set.iterator();
            while (iterator.hasNext()){
                String key =iterator.next();
                Path path = PathUtil.getPath(ele.root,key, JoinType.INNER);
                Object object = FlatBuilder.getComparable(path,jsonObject.get(key));
                ele.update.set(path,object);
            }
            Predicate predicate = ParamUtil.analysisDynamicParam(dynamicParam,ele.builder,ele.root);
            ele.update.where(predicate);
            return null;
        });
        return dynamicUpdate.getResult();
    }

    @Override
    @Transactional(value = "platformTransactionManager", rollbackFor = Exception.class)
    public void deletById(ID id){
        baseRepository.deleteById(id);
    }

    @Override
    @Transactional(value = "platformTransactionManager",rollbackFor = Exception.class)
    public void deletByEntitys(Iterable<T> entitys){
        baseRepository.deleteAll(entitys);
    }
    @Override
    @Transactional(value = "platformTransactionManager",rollbackFor = Exception.class)
    public int deletByParam(DynamicParam dynamicParam){
        DynamicDelete<T> dynamicDelete = baseRepository.getDynamicDelete();
        dynamicDelete.dynamicBuild(ele -> {
        Predicate predicate = ParamUtil.analysisDynamicParam(dynamicParam,ele.builder,ele.root);
            ele.delete.where(predicate);
            return predicate;
        });
        return dynamicDelete.getResult();
    }
    @Override
    @Transactional(value = "platformTransactionManager",rollbackFor = Exception.class)
    public T insertByEntity(T entity){
        return baseRepository.save(entity);
    }

    @Override
    @Transactional(value = "platformTransactionManager", rollbackFor = Exception.class)
    public List<T> insertByEntitys(Iterable<T> entitys){
        return baseRepository.saveAll(entitys);
    }
    @Override
    public BaseRepository<T,ID> getBaseRepository(){
        return baseRepository;
    }

}
