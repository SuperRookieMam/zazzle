package com.simple.orm.componet.factory;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * 替换Jpa 的 repository的工厂类使用自定Factory
 * 参见jpa 源码
 * */
public class BaseDaoFactoryBean<R extends JpaRepository<T, ID>,T,ID extends Serializable> extends JpaRepositoryFactoryBean<R, T, ID> {

    public BaseDaoFactoryBean(Class<? extends R> repositoryInterface) {
        super(repositoryInterface);

    }
    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new BaseDaoFactory(entityManager);
    }
}
