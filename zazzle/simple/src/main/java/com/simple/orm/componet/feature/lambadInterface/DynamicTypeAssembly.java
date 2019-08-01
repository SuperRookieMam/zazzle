package com.simple.orm.componet.feature.lambadInterface;

import com.simple.orm.componet.feature.DynamicTypeSelect;

import javax.persistence.criteria.Predicate;
//函数式接口，里面只能有一个抽象方法。这种类型的接口也称为SAM接口，即Single Abstract Method interfaces
@FunctionalInterface
public interface DynamicTypeAssembly<T>  {
    public Predicate assembly(DynamicTypeSelect<T> dynamicTypeSelect);
}
