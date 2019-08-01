package com.simple.orm.componet.feature;

import com.simple.orm.componet.feature.lambadInterface.DynamicUpdateAssembly;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.io.Serializable;

public class DynamicUpdate<T>implements Serializable {
    private static final long serialVersionUID = -1438447111419275722L;
    public final DynamicPredicateBuilder<T> assembly;
    public final FlatBuilder<T> flat;
    public final Root<T> root;
    public final CriteriaUpdate<T> update;
    public final CriteriaBuilder builder;
    private final EntityManager entityManager;
    public DynamicUpdate(Class<T> tClass, EntityManager entityManager){
        this.entityManager =entityManager;
        this.builder =entityManager.getCriteriaBuilder();
        this.update =builder.createCriteriaUpdate(tClass);
        this.root =update.from(tClass);
        this.assembly =new DynamicPredicateBuilder<>(builder,root);
        this.flat =new FlatBuilder<>(builder,root);
    }
    public DynamicUpdate<T> dynamicBuild(DynamicUpdateAssembly<T> dynamicUpdateAssembly) {
        dynamicUpdateAssembly.assembly(this);
        return this;
    }

    public int getResult(){
        return entityManager.createQuery(update).executeUpdate();
    }
}
