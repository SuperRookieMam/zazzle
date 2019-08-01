package com.simple.base.componet.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.simple.base.componet.params.BasicsParam;
import com.simple.base.componet.params.DynamicParam;
import com.simple.base.componet.params.FlatParam;
import com.simple.base.componet.params.MixtureParam;
import com.simple.orm.componet.feature.FlatBuilder;
import com.simple.orm.componet.util.PathUtil;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ParamUtil implements Serializable {
    private static final long serialVersionUID = 2507212910575627408L;

    /**
     * 这个下面的参数是and连接，如果分很多种情况就没得完了
     * */
    public static Predicate analysisDynamicParam(DynamicParam dynamicParam, CriteriaBuilder builder, Root<?> root){
        List<Predicate> list =new ArrayList<>();
        List<BasicsParam> basicsParams = dynamicParam.getBasicsParams();
        Predicate basicsPredicate =   analysisBasicsParams(basicsParams,builder,root,"and");
        if (!ObjectUtils.isEmpty(basicsPredicate))
            list.add(basicsPredicate);

        List<FlatParam> flatParams = dynamicParam.getFlatParams();
        Predicate flatPredicate =   analysisFlatParams(flatParams,builder,root,"and");
        if (!ObjectUtils.isEmpty(flatPredicate))
            list.add(flatPredicate);

        List<MixtureParam> mixtureParams = dynamicParam.getMixtureParams();
        Predicate mixturePredicate = analysisMixtureParams(mixtureParams,builder,root,"and");
        if (!ObjectUtils.isEmpty(mixturePredicate))
            list.add(flatPredicate);
        return buildList("and",list,builder);

    }

    /**
     * 解析
     * */
    private static Predicate  analysisMixtureParams(List<MixtureParam> list, CriteriaBuilder builder, Root<?> root, String type){
        if (!ObjectUtils.isEmpty(list)){
            List<Predicate> predicates =new ArrayList<>();
            for (MixtureParam ele : list){
                Predicate predicate = analysisMixtureParam(ele,builder,root);
                if (!ObjectUtils.isEmpty(predicate)){
                    predicates.add(predicate);
                }
            }
            return buildList(type,predicates,builder);
        }
            return null;
    }
    /**
     * 注意数组之间的元素是and连接的
     * MixtureParam 的type 是三个list 字段之间的连接条件；
     * 如果条件很复杂可以MixtureParam 递归构成
     * */
    protected static Predicate  analysisMixtureParam(@NotNull MixtureParam mixtureParam, CriteriaBuilder builder, Root<?> root){
        List<Predicate> predicates =new ArrayList<>();
        List<BasicsParam> basicsParams = mixtureParam.getBasicsParams();
        Predicate basicPredicate =  analysisBasicsParams(basicsParams,builder,root,"and");
        if (!ObjectUtils.isEmpty(basicPredicate)){
            predicates.add(basicPredicate);
        }


        List<FlatParam> flatParams = mixtureParam.getFlatParams();
        Predicate flatPredicate = analysisFlatParams(flatParams,builder,root,"and");
        if (!ObjectUtils.isEmpty(flatPredicate)){
            predicates.add(flatPredicate);
        }

        List<MixtureParam> mixtureParams =mixtureParam.getMixtureParams();
        if (!ObjectUtils.isEmpty(mixtureParams)){
            List<Predicate> list = new ArrayList<>();
            for(MixtureParam ele:mixtureParams){
                Predicate predicate = analysisMixtureParam(ele,builder,root);
                if (!ObjectUtils.isEmpty(predicate)){
                    list.add(predicate);
                }
            }

            Predicate predicate =  buildList("and",list,builder);
             if (!ObjectUtils.isEmpty(predicate)){
                 predicates.add(predicate);
             }
        }
        if (predicates.isEmpty()){
            return null;
        }else {
            return buildList(mixtureParam.getType(),predicates,builder);
        }
    }


    /**
     * 吧一个扁平的多条件参数解析成为一个Predicate
     * */
     protected static Predicate  analysisFlatParams(List<FlatParam> flatParams, CriteriaBuilder builder, Root<?> root, String type){
        if (!ObjectUtils.isEmpty(flatParams)){
            List<Predicate> predicates =new ArrayList<>();
            for (FlatParam flatParam : flatParams){
                List<BasicsParam> basicsParams = flatParam.getBasicsParams();
                Predicate predicate =  analysisBasicsParams(basicsParams,builder,root,flatParam.getType());
                if (!ObjectUtils.isEmpty(predicate)){
                    predicates.add(predicate);
                }
            }
            return buildList(type,predicates,builder);
        }
        return null;
    }
     private static  Predicate buildList (String type,List<Predicate> list,CriteriaBuilder builder) {
        if (ObjectUtils.isEmpty(list))
            return null;

        if (type.equalsIgnoreCase("and")){
            if (list.size()==0)
                return null;
            return list.size()>1?builder.and(list.toArray(new Predicate[0])):list.get(0);
        }else if (type.equalsIgnoreCase("or")){
            if (list.size()==0)
                return null;
            return list.size()>1?builder.or(list.toArray(new Predicate[0])):list.get(0);
        }else {
            Assert.isTrue(false,"请传入正确的参数");
        }
        return null;
     }


    /**
     *解析最简单的BasicsParam列表转换成为一个predict
     * */
    protected static Predicate analysisBasicsParams(List<BasicsParam> basicsParams, CriteriaBuilder builder, Root<?> root, String type) {
            if (!ObjectUtils.isEmpty(basicsParams)){
                FlatBuilder flatBuilder =  new FlatBuilder<>(builder,root);
                for (BasicsParam basic:basicsParams) {
                    getParamType(basic,flatBuilder);
                }
                if (type.equalsIgnoreCase("and")){
                    return flatBuilder.and();
                }else if (type.equalsIgnoreCase("or")){
                    return flatBuilder.or();
                }else {
                    Assert.isTrue(false,"请传入正确的连接条件");
                }
            }
            return  null;
    }
    /**
     * 吧一个最基础的BasicsParam参数转换成为一个Predicate并且放在FlatBuilder的内部list里面，只需要and或者or 就可以获取相对的Predicate集合
     * */
    protected static void getParamType(@NotNull BasicsParam basicsParam,@NotNull FlatBuilder flatBuilder){
            switch (basicsParam.getType()){
                case "like":
                    flatBuilder.addLike(basicsParam.getKey(),basicsParam.getValue(), JoinType.LEFT);
                    break;
                case "notLike":
                    flatBuilder.addNotLike(basicsParam.getKey(),basicsParam.getValue(), JoinType.LEFT);
                    break;
                case "lt":
                    flatBuilder.addLt(basicsParam.getKey(),basicsParam.getValue(), JoinType.LEFT);
                    break;
                case "le":
                    flatBuilder.addLe(basicsParam.getKey(),basicsParam.getValue(), JoinType.LEFT);
                    break;
                case "gt":
                    flatBuilder.addGt(basicsParam.getKey(),basicsParam.getValue(), JoinType.LEFT);
                    break;
                case "ge":
                    flatBuilder.addGe(basicsParam.getKey(),basicsParam.getValue(), JoinType.LEFT);
                    break;
                case "eq":
                    flatBuilder.addEq(basicsParam.getKey(),basicsParam.getValue(), JoinType.LEFT);
                    break;
                case "notEq":
                    flatBuilder.addNotEq(basicsParam.getKey(),basicsParam.getValue(), JoinType.LEFT);
                    break;
                case "in":
                    Object value =basicsParam.getValue();
                    Object[] values=null;
                    if (Collection.class.isInstance(value)){
                        values = ((Collection)value).stream().toArray();
                    }else if (value.getClass().isArray()){
                        values =(Object[])value;
                    }
                    flatBuilder.addIn(basicsParam.getKey(),JoinType.LEFT,values);
                    break;
                case "notIn":
                    Object  object=basicsParam.getValue();
                    Object[] objects=null;
                    if (Collection.class.isInstance(object)){
                       objects = ((Collection)object).stream().toArray();
                    }else if (object.getClass().isArray()){
                        objects =(Object[])object;
                    }
                    flatBuilder.addNotIn(basicsParam.getKey(),JoinType.LEFT,objects);
                    break;
                case "isNull":
                    flatBuilder.addIsNull(basicsParam.getKey(),JoinType.LEFT);
                    break;
                case "isNotNull":
                    flatBuilder.addIsNotNull(basicsParam.getKey(),JoinType.LEFT);
                    break;
                default:
                    Assert.isTrue(false,"其他链接类型暂不支持");
                  break;
            }
    }

    public   static  void groupBy(CriteriaQuery query, Root root,String...fieldNames){
        if (fieldNames!=null){
            List<Path> paths =new ArrayList<>();
            for (String field:fieldNames){
                paths.add(PathUtil.getPath(root,field,JoinType.LEFT));
            }
            query.groupBy(paths);
        }
    }
    /**
     *获取排序
     * sort 的格式为 如果有多个排序字段为" fieldName:desc"," fieldName:asc"
     * 注意这个值做了单一属性的排序，多字段
     * */
    public static  void orderby(CriteriaBuilder builder,CriteriaQuery query,Root root,String... sorts){
        if (sorts!=null){
            List<Sort.Order> list =new ArrayList<>();
            for(String sort:sorts){
                String[] sor =sort.split(":");
                if ("desc".equalsIgnoreCase(sor[1])){
                    list.add(new Sort.Order(Sort.Direction.DESC,sor[0]));
                }else {
                    list.add(new Sort.Order(Sort.Direction.ASC,sor[0]));
                }
            }
            query.orderBy(QueryUtils.toOrders(Sort.by(list), root, builder));
        }
    }

    public static  DynamicParam strToDynamicParam (String jsonStr){
        System.out.println(jsonStr);
        JSONObject jsonObject =JSON.parseObject(jsonStr);
        DynamicParam dynamicParam =jsonObject.toJavaObject(DynamicParam.class);
        return dynamicParam;
    }

}
