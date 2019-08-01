package com.simple.orm.componet.feature;

import com.simple.orm.componet.feature.lambadInterface.DynamicDeleteAssembly;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;
import java.io.Serializable;

public class DynamicDelete<T>implements Serializable {
    private static final long serialVersionUID = 1129975789810047194L;
    public final DynamicPredicateBuilder<T> assembly;
    public final FlatBuilder<T> flat;
    public final Root<T> root;
    public final CriteriaDelete<T> delete;
    public final CriteriaBuilder builder;
    private final EntityManager entityManager;
    public DynamicDelete(Class<T> tClass, EntityManager entityManager){
        this.entityManager =entityManager;
        this.builder =entityManager.getCriteriaBuilder();
        this.delete =builder.createCriteriaDelete(tClass);
        this.root =delete.from(tClass);
        this.assembly =new DynamicPredicateBuilder<>(builder,root);
        this.flat =new FlatBuilder<>(builder,root);
    }
    public DynamicDelete<T> dynamicBuild(DynamicDeleteAssembly<T> dynamicDeleteAssembly) {
        dynamicDeleteAssembly.assembly(this);
        return this;
    }

    public int getResult(){
        return entityManager.createQuery(delete).executeUpdate();
    }
}
