package com.simple.base.componet.params;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class MixtureParam implements Serializable{
    private static final long serialVersionUID = 2994955292681520883L;
    //指定 and or
    String type;

    List<FlatParam> flatParams;

    List<BasicsParam> basicsParams;

    List<MixtureParam> mixtureParams;
}
