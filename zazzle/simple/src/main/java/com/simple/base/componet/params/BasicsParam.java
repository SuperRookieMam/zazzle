package com.simple.base.componet.params;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BasicsParam implements Serializable{
    private static final long serialVersionUID = 1730964885895817822L;
    // 字段名， 可以是 person.headImage
    private String key ;
    //field  in ,like
    private String type;
    //field 值
    private Object value;

}
