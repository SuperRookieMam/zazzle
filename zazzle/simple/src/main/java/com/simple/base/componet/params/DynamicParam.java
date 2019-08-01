package com.simple.base.componet.params;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class DynamicParam implements Serializable {
    private static final long serialVersionUID = -994650743414339348L;
    private String params;

    private  Integer pageNum;
    //value = "分页大  小", dataType = "Integer", required = true
    private Integer pageSize;
    //因为排序时有序的所以用arry来接收
    private JSONArray sort; //排序对象[{'sortType': 'desc','fieldName':'fieldName'}]
    //按照顺序分组
    private JSONArray groupby;
    // 一个复杂的参数参数列表，参数之间用and连接
    private List<MixtureParam> mixtureParams;
    //如果条件简单就可以用这个扁平式的参数，参数之间用and连接
    private List<FlatParam> flatParams;
    // 如果条件更简单全部都是基础参数的and 条件可以传这个
    private List<BasicsParam> basicsParams;
    // 如果是要跟新满足条件的某些字段可以传这个
    private JSONObject updateFiled;

}
