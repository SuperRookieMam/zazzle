package com.simple.orm.dao.Impl;

import com.simple.orm.componet.feature.*;
import com.simple.orm.dao.MyJpaBaseRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

@Getter
@Setter
public class MyJpaBaseRepositoryImpl<T,ID extends Serializable> extends SimpleJpaRepository<T, ID>implements MyJpaBaseRepository<T,ID> {

    private final JpaEntityInformation<T, ?> entityInformation;
    private final EntityManager entityManager;

    public MyJpaBaseRepositoryImpl(Class<T> domainClass, EntityManager entityManager){
        super(domainClass,entityManager);
        this.entityManager =entityManager;
        this.entityInformation = JpaEntityInformationSupport.getEntityInformation(domainClass, entityManager);
    }
    @Override
    public DynamicDelete<T> getDynamicDelete(){
        return new DynamicDelete<>(entityInformation.getJavaType(),entityManager);
    }
    @Override
    public DynamicTypeSelect<T> getDynamicTypeSelect(){
        return new DynamicTypeSelect<>(entityInformation.getJavaType(),entityManager);
    }
    @Override
    public DynamicTupleSelect<T> getDynamicTupleSelect(){
        return new DynamicTupleSelect<>(entityInformation.getJavaType(),entityManager);
    }
    @Override
    public DynamicUpdate<T> getDynamicUpdate(){
        return new DynamicUpdate<>(entityInformation.getJavaType(),entityManager);
    }
    @Override
    public DynamicPageTypeSelect<T> readPageType(int pageNum, int pageSize){
         return new DynamicPageTypeSelect<>(entityInformation.getJavaType(),entityManager,pageNum,pageSize);
    }
    @Override
    public DynamicPageTupleSelect<T> readPageMap(int pageNum,int pageSize){
        return new DynamicPageTupleSelect<>(entityInformation.getJavaType(),entityManager,pageNum,pageSize);
    }
}
