package com.simple.orm.componet.feature;

import com.simple.orm.componet.feature.lambadInterface.DynamicTupleAssembly;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DynamicTupleSelect<T> implements Serializable {
    private static final long serialVersionUID = -5235577736229047013L;

    public final DynamicPredicateBuilder<T> assembly;
    public final FlatBuilder<T> flat;
    public final Root<T> root;
    public final CriteriaQuery<Tuple> query;
    public final CriteriaBuilder builder;
    public final EntityManager entityManager;
    public DynamicTupleSelect(Class<T> tClass, EntityManager entityManager){
        this.entityManager =entityManager;
        this.builder =entityManager.getCriteriaBuilder();
        this.query =builder.createTupleQuery();
        this.root =query.from(tClass);
        this.assembly =new DynamicPredicateBuilder<>(builder,root);
        this.flat =new FlatBuilder<>(builder,root);
    }


    public DynamicTupleSelect<T> dynamicBuild(DynamicTupleAssembly<T> dynamicTupleAssembly) {
        dynamicTupleAssembly.assembly(this);
        return this;
    }


    public List<Map<String,Object>>  getResult(){
        List<Tuple> list =entityManager.createQuery(query).getResultList();
        return list.stream().map(ele -> {
                Map<String,Object> map =new HashMap<>();
                ele.getElements().forEach(ele1 ->{
                    map.put(ele1.getAlias(),ele.get(ele1));
                 });
                return map;
            }).collect(Collectors.toList());

    }
}
