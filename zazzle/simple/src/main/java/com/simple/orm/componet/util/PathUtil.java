package com.simple.orm.componet.util;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PathUtil {

    public static Path getPath(Root root,String key,JoinType joinType){
        Path path =null;
        Assert.notNull(key, "key must not null");
        if (key.contains(".")){
            String[] keys = StringUtils.split(key, ".");
            Join<?,?> join =joinIsExit(keys[keys.length-2],root.getJoins());
            if (join==null)
                path = joinAdd(root,joinType,keys,0).get(keys[keys.length-1]);
            else
                path =join.get(keys[keys.length-1]);
        }else {
            path = root.get(key);
        }
        Assert.notNull(path, "path must not null but path "+key+"is not exit");
        return  path;
    }

    /*检查最后一张表是否最在联表*/
    private static Join<?,?> joinIsExit(String lasstSecond,Set<Join<?,?>> joinSet) {
        Iterator<Join<?,?>> iterator =joinSet.iterator();
        while (iterator.hasNext()) {
            Join<?,?> join =iterator.next();
            if (lasstSecond.equals(join.getAttribute().getName())){
                return join;
            }
        }
        return null ;
    }
    /**
     * 注意key 是传入的name.eglishname.en 截取最后一个的值 name.eglishname
     * */
    private  static From<?,?> joinAdd(From<?,?> from, JoinType joinType,String[] keys,int i){
        if (i==keys.length-1){
            return from;
        }
        From<?,?> subFrom =null;
        Class clazz = from.get(keys[i]).getJavaType();
        if (List.class.isAssignableFrom(clazz)){
            subFrom = from.joinList(keys[i],joinType);
         }else if (Set.class.isAssignableFrom(clazz)){
            subFrom = from.joinSet(keys[i],joinType);
        }else if (Map.class.isAssignableFrom(clazz)){
            subFrom = from.joinMap(keys[i],joinType);
        }else {
            subFrom =from.join(keys[i],joinType);
        }
        return joinAdd(subFrom,joinType,keys,i+1);
    }
}
