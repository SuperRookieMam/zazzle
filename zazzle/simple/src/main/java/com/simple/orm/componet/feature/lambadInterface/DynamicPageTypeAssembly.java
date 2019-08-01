package com.simple.orm.componet.feature.lambadInterface;

import com.simple.orm.componet.feature.DynamicPageTypeSelect;

import javax.persistence.criteria.Predicate;

@FunctionalInterface
public interface DynamicPageTypeAssembly<T> {
    public Predicate assembly(DynamicPageTypeSelect<T> dynamicPageTypeSelect);
}
