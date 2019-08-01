package com.simple.base.componet.params;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class FlatParam implements Serializable  {
    private static final long serialVersionUID = 3979567146969100984L;
    // and  或者 or
    private String type;

    private List<BasicsParam> basicsParams;
}
