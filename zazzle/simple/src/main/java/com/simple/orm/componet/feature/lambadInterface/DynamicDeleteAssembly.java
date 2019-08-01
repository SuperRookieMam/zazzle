package com.simple.orm.componet.feature.lambadInterface;


import com.simple.orm.componet.feature.DynamicDelete;

import javax.persistence.criteria.Predicate;

@FunctionalInterface
public interface DynamicDeleteAssembly<T> {
    public Predicate assembly(DynamicDelete<T> dynamicDelete);
}
