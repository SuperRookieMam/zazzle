package com.simple.orm.componet.feature.lambadInterface;

import com.simple.orm.componet.feature.DynamicPageTupleSelect;

import javax.persistence.criteria.Predicate;

@FunctionalInterface
public interface DynamicPageTupleAssembly<T> {
    public Predicate assembly(DynamicPageTupleSelect<T> dynamicPageTupleSelect);
}
