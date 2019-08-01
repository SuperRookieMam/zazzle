package com.simple.orm.componet.feature;
import com.simple.orm.componet.util.PathUtil;
import org.springframework.util.Assert;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class FlatBuilder<T> implements Serializable {
    private static final long serialVersionUID = 8265136946332840908L;
    private final List<Predicate> list ;
    private final Root<T> root;
    private final CriteriaBuilder builder ;

   public FlatBuilder(CriteriaBuilder builder,Root<T> root){
        this.builder =builder;
        this.root =root;
        this.list =new ArrayList<>();
    }

    public FlatBuilder<T> addLike(String key, Object value, JoinType joinType){
        list.add(builder.like(PathUtil.getPath(root,key,joinType),"%/"+value+"%",'/'));
        return this;
    }
    public FlatBuilder<T> addNotLike(String key,Object value, JoinType joinType){
        list.add(builder.notLike(PathUtil.getPath(root,key,joinType),"%/"+value+"%",'/'));
        return this;
    }
    public FlatBuilder<T> addLt(String key,Object value, JoinType joinType){
       Path path =PathUtil.getPath(root,key,joinType);
        list.add(Number.class.isAssignableFrom(path.getJavaType())
                ?builder.lt(path,(Number)getComparable(path,value))
                :builder.lessThan(path,getComparable(path,value)));
        return this;
    }
    public FlatBuilder<T> addLe(String key,Object value, JoinType joinType){
        Path path =PathUtil.getPath(root,key,joinType);
      list.add(Number.class.isAssignableFrom(path.getJavaType())
                ?builder.le(path,(Number)getComparable(path,value))
                :builder.lessThanOrEqualTo(path,getComparable(path,value)));
        return this;
    }
    public FlatBuilder<T> addGt(String key,Object value, JoinType joinType){
        Path path =PathUtil.getPath(root,key,joinType);
        list.add(Number.class.isAssignableFrom(path.getJavaType())
                ?builder.gt(path,(Number)getComparable(path,value))
                :builder.greaterThan(path,getComparable(path,value)));
        return this;
    }
    public FlatBuilder<T> addGe(String key,Object value, JoinType joinType){
        Path path =PathUtil.getPath(root,key,joinType);
        list.add( path.getJavaType().getSimpleName().contains("Date")
                ?builder.ge(path,(Number)getComparable(path,value))
                :builder.greaterThanOrEqualTo(path,getComparable(path,value)));
        return this;
    }
    public FlatBuilder<T> addEq(String key,Object value,JoinType joinType){
        Path path =PathUtil.getPath(root,key,joinType);
        list.add(builder.equal(path,getComparable(path,value)));
        return this;
    }
    public FlatBuilder<T> addNotEq(String key,Object value,JoinType joinType){
        Path path =PathUtil.getPath(root,key,joinType);
        list.add(builder.notEqual(path,getComparable(path,value)));
        return this;
    }
    public FlatBuilder<T> addIn(String key,JoinType joinType,Object...value){
        Path path =PathUtil.getPath(root,key,joinType);
        CriteriaBuilder.In in = builder.in(path);
        for (int i = 0; i <value.length ; i++) {
            in.value(value[i]);
        }
        list.add(in);
        return this;
    }
    public FlatBuilder<T> addNotIn(String key,JoinType joinType,Object... value){
        Path path =PathUtil.getPath(root,key,joinType);
        CriteriaBuilder.In inn = builder.in(path);
        for (int i = 0; i <value.length ; i++) {
            inn.value(value[i]);
        }
        list.add(builder.not(inn));
        return this;
    }
    public FlatBuilder<T> addIsNull(String key,JoinType joinType){
        list.add(builder.isNull(PathUtil.getPath(root,key,joinType)));
        return this;
    }
    public FlatBuilder<T> addIsNotNull(String key,JoinType joinType){
        list.add(builder.isNotNull(PathUtil.getPath(root,key,joinType)));
        return this;
    }
    public Predicate or(){
        Predicate predicate =list.size()>1?builder.or(list.toArray(new Predicate[0])):list.get(0);
        this.list.clear();
        return predicate;
    }
    public Predicate and(){
        Predicate predicate =list.size()>1?builder.and(list.toArray(new Predicate[0])):list.get(0);
        this.list.clear();
        return predicate;
    }
    /**
     * 主要是时间和基本类型的转换成为JPA可以比较的值
     * */
    public  static Comparable  getComparable(Path path,Object value) {
        Class timeclass =path.getJavaType();//如果是基本的数据类型JVM会自动封装所以也会是个Comparable
            try {
                   if (String.class.isAssignableFrom(timeclass)){
                       return value.toString();
                   }else if (Date.class.isAssignableFrom(timeclass)){
                     return new SimpleDateFormat(getPantter(value.toString())).parse(value.toString());
                   }else if (LocalDate.class.isAssignableFrom(timeclass)){
                       DateTimeFormatter formatter = DateTimeFormatter.ofPattern(getPantter(value.toString()));
                       return LocalDate.parse(value.toString(),formatter);
                   }else if (LocalDateTime.class.isAssignableFrom(timeclass)){
                       DateTimeFormatter formatter = DateTimeFormatter.ofPattern(getPantter(value.toString()));
                       return LocalDateTime.parse(value.toString(),formatter);
                   }else if (ZonedDateTime.class.isAssignableFrom(timeclass)){
                       DateTimeFormatter formatter = DateTimeFormatter.ofPattern(getPantter(value.toString()));
                       LocalDateTime localDateTime =  LocalDateTime.parse(value.toString(),formatter);
                       ZoneId zone = ZoneId.of(TimeZone.getDefault().getID());
                       return localDateTime.atZone(zone);
                   }else if (LocalTime.class.isAssignableFrom(timeclass)){
                       DateTimeFormatter formatter = DateTimeFormatter.ofPattern(getPantter(value.toString()));
                       return LocalDateTime.parse(value.toString(),formatter);
                   }else {// 数字类型，上面我gerclass 所以数字类型自动封装也会是个Comparable
                        return (Comparable)value;
                   }
            } catch (ParseException e) {
                    e.printStackTrace();
                    throw new RuntimeException("时间解析错误");
          }
    }
    private static String getPantter(String value){
        if (value.matches("\\d{4}-\\d{2}-\\{d}{2}\\s+\\d{2}:\\d{2}:\\d{2}")){
            return "yyyy-MM-dd HH:mm:ss";
        }else if (value.matches("\\d{4}-\\d{2}-\\{d}{2}")){
            return "yyyy-MM-dd";
        }else if (value.matches("\\d{2}:\\d{2}:\\d{2}")){
            return "HH:mm:ss";
        }else if (value.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\+\\d{2}:\\d{2}\\[\\w+/\\w+\\]")) {
            return "yyyy-MM-dd HH:mm:ss";
        }else if (value.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\+\\d{2}:\\d{2}")){
            return "yyyy-MM-dd HH:mm:ss";
        }else {
            Assert.isNull(null,"时间格式暂不支持");
            return null;
        }
    }
}
