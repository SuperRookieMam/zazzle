package com.simple.orm.componet.feature;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;

public class DynamicPredicateBuilder<T> implements Serializable {
    private static final long serialVersionUID = 413816655718302694L;
    private final CriteriaBuilder builder ;
    public DynamicPredicateBuilder(CriteriaBuilder builder,Root<T> root){
        this.builder =builder;
    }
    public Predicate and(Predicate...predicates){
        return builder.and(predicates);
    }

    public Predicate or(Predicate...predicates){
        return builder.or(predicates);
    }

}
