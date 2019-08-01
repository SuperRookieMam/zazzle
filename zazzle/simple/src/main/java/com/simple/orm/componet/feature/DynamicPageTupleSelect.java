package com.simple.orm.componet.feature;

import com.simple.orm.componet.feature.lambadInterface.DynamicPageTupleAssembly;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicPageTupleSelect<T> implements Serializable{
    private static final long serialVersionUID = -8058696758960436016L;

    public final DynamicPredicateBuilder<T> assembly;
    public final FlatBuilder<T> flat;
    public final Root<T> root;
    public final Root<T> countRoot;
    public final CriteriaQuery<Tuple> query;
    public final CriteriaQuery<Tuple> countQuery;
    public final CriteriaBuilder builder;
    public final EntityManager entityManager;
    public final int pageNum;
    public final int pageSize;
    public DynamicPageTupleSelect(Class<T> tClass, EntityManager entityManager,int pageNum,int pageSize){
        this.entityManager =entityManager;
        this.builder =entityManager.getCriteriaBuilder();
        this.query =builder.createTupleQuery();
        this.countQuery =builder.createTupleQuery();
        this.root =query.from(tClass);
        this.countRoot =this.countQuery.from(tClass);
        this.assembly =new DynamicPredicateBuilder<>(builder,root);
        this.flat =new FlatBuilder<>(builder,root);
        this.pageNum =pageNum;
        this.pageSize =pageSize;
    }

    public DynamicPageTupleSelect<T> dynamicBuild(DynamicPageTupleAssembly<T> dynamicPageTupleAssembly) {
        dynamicPageTupleAssembly.assembly(this);
        return this;
    }
    public PageInfo<Map<String,Object>> getResult(){
        List<Tuple> list = entityManager.createQuery(this.countQuery).getResultList();
        long total =0;
        for (Tuple ele:list){
             for (TupleElement el:ele.getElements()){
                  if (el.getAlias().equals("total")){
                      total += ele.get(el.getAlias(),Long.class);
                  }
             }
         }
        PageRequest pageable = PageRequest.of(pageNum,pageSize);
        List<Tuple> tuples = entityManager.createQuery(this.query).getResultList();
        List <Map<String,Object>> list1 =new ArrayList<>();
        for (Tuple ele:tuples){
            Map<String,Object> map =new HashMap<>();
            for (TupleElement el:ele.getElements()){
                map.put(el.getAlias(),ele.get(el));
            }
            list1.add(map);
        }
        PageImpl<Map<String,Object>> page =   new PageImpl(list1,pageable,total);
        PageInfo<Map<String,Object>> pageInfo =new PageInfo<>();
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
