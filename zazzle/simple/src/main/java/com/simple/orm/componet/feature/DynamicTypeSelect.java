package com.simple.orm.componet.feature;

import com.simple.orm.componet.feature.lambadInterface.DynamicTypeAssembly;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public class DynamicTypeSelect<T> implements Serializable{
    private static final long serialVersionUID = -6274355106389230973L;

    public final DynamicPredicateBuilder<T> assembly;
    public final FlatBuilder<T> flat;
    public final Root<T> root;
    public final CriteriaQuery<T> query;
    public final CriteriaBuilder builder;
    private final EntityManager entityManager;
    public DynamicTypeSelect(Class<T> tClass, EntityManager entityManager){
        this.entityManager =entityManager;
        this.builder =entityManager.getCriteriaBuilder();
        this.query =builder.createQuery(tClass);
        this.root =query.from(tClass);
        this.assembly =new DynamicPredicateBuilder<>(builder,root);
        this.flat =new FlatBuilder<>(builder,root);
    }
    public DynamicTypeSelect<T> dynamicBuild(DynamicTypeAssembly<T> dynamicTypeAssembly) {
        dynamicTypeAssembly.assembly(this);
        return this;
    }
    public List<T> getResult(){
        return this.entityManager.createQuery(query).getResultList();
    }
}
