package com.simple.orm.componet.feature.lambadInterface;

import com.simple.orm.componet.feature.DynamicTupleSelect;

import javax.persistence.criteria.Predicate;

@FunctionalInterface
public interface DynamicTupleAssembly<T> {
    public Predicate assembly(DynamicTupleSelect<T> dynamicTupleSelect);
}
