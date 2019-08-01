package com.simple.codecreate.feature.replace;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReplacePlace {

    public static void HowReplace(ReplaceParam replaceParam, List<String> list){
        Map<String,String> map =new HashMap<>();
        list.forEach(ele -> {
           replaceParam.replaceParams(ele);
        });
    }
}
