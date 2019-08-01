package com.simple.orm.componet.feature;

import com.simple.orm.componet.feature.lambadInterface.DynamicPageTypeAssembly;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public class DynamicPageTypeSelect<T> implements Serializable {
    private static final long serialVersionUID = 4902747308932587487L;
    public final DynamicPredicateBuilder<T> assembly;
    public final FlatBuilder<T> flat;
    public final Root<T> root;
    public final Root<T> countRoot;
    public final CriteriaQuery<T> query;
    public final CriteriaQuery<Long> countQuery;
    public final CriteriaBuilder builder;
    public final EntityManager entityManager;
    public final int pageNum;
    public final int pageSize;
    public DynamicPageTypeSelect(Class<T> tClass, EntityManager entityManager,int pageNum,int pageSize){
        this.entityManager =entityManager;
        this.builder =entityManager.getCriteriaBuilder();
        this.query =builder.createQuery(tClass);
        this.countQuery =builder.createQuery(Long.class);
        this.root =query.from(tClass);
        this.countRoot =this.countQuery.from(tClass);
        this.countQuery.select(builder.count(this.countRoot));
        this.assembly =new DynamicPredicateBuilder<>(builder,root);
        this.flat =new FlatBuilder<>(builder,root);
        this.pageNum =pageNum;
        this.pageSize =pageSize;
    }

    public DynamicPageTypeSelect<T> dynamicBuild(DynamicPageTypeAssembly<T> dynamicPageTypeAssembly) {
        dynamicPageTypeAssembly.assembly(this);
        return this;
    }
    public PageInfo<T> getResult(){
       List<Long> list = entityManager.createQuery(this.countQuery).getResultList();
       long total =0;
        for (Long ele:list){
            total +=ele;
        }
        PageRequest pageable = PageRequest.of(pageNum,pageSize);

        PageImpl<T> page =   new PageImpl(entityManager.createQuery(this.query).getResultList(),
                                pageable,
                                total);
         PageInfo<T> pageInfo =new PageInfo<>();
         pageInfo.setPageNum(page.getNumber());
         pageInfo.setPageSize(page.getSize());
         pageInfo.setStartRow((pageNum-1)*pageSize);
         pageInfo.setEndRow((pageNum-1)*pageSize+pageSize);
         pageInfo.setPages(page.getTotalPages());
         pageInfo.setList(page.getContent());
         pageInfo.setTotal(page.getTotalElements());
         pageInfo.setOrderBy(page.getSort().toString());
        return pageInfo;
    }

}
