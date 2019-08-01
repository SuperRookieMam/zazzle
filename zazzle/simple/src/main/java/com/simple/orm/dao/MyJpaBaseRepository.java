package com.simple.orm.dao;

import com.simple.orm.componet.feature.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;


/**
 *继承勒JpaRepository的接口，findN那些所有方法，
 *JpaSpecificationExecutor 的执行器所有方法
 * */
@NoRepositoryBean  //使用了该注解的接口不会被单独创建实例，只会作为其他接口的父接口而被使用。
public interface MyJpaBaseRepository<T,ID extends Serializable>  extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    DynamicDelete<T> getDynamicDelete();

    DynamicTypeSelect<T> getDynamicTypeSelect();

    DynamicTupleSelect<T> getDynamicTupleSelect();

    DynamicUpdate<T> getDynamicUpdate();

    DynamicPageTypeSelect<T> readPageType(int pageNum, int pageSize);

    DynamicPageTupleSelect<T> readPageMap(int pageNum, int pageSize);
}
