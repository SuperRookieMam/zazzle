package com.simple.codecreate.feature.replace;


import com.simple.codecreate.feature.annotation.Description;
import com.simple.codecreate.util.MyClassUtil;

import java.lang.reflect.Field;
import java.util.*;


public class InitParmas {

    public List<String> javaReplace(List<String> list,Class clazz) {
        List<String> list1 =new ArrayList<>();
        ReplacePlace.HowReplace(ele->{
            String newLine =ele;
           if (newLine.contains("~[packagedao]")) {
               String packgename = clazz.getPackage().getName();
               packgename = packgename.substring(0, packgename.lastIndexOf(".")) + ".dao";
               newLine =newLine.replaceAll("~\\[packagedao\\]", packgename);
           }
            if (ele.contains("~[entityname]")) {
                newLine =newLine.replaceAll("~\\[entityname\\]",clazz.getName());
            }
            if (ele.contains("~[entitySimpleName]")) {
                newLine =newLine.replaceAll("~\\[entitySimpleName\\]",clazz.getSimpleName());
            }
            if (ele.contains("~[entitySimpleName]")) {
                newLine =newLine.replaceAll("~\\[ID\\]", "Long");
            }
            if (ele.contains("~[packageService]")) {
                String packgeservice = clazz.getPackage().getName();
                packgeservice =packgeservice.substring(0,packgeservice.lastIndexOf("."))+".service";
                newLine =newLine.replaceAll("~\\[packageService\\]",packgeservice);

            }
            if (ele.contains("~[packageServiceImpl]")) {
                String packgeserviceImpl = clazz.getPackage().getName();
                packgeserviceImpl =packgeserviceImpl.substring(0,packgeserviceImpl.lastIndexOf("."))+".service.impl";
                newLine =newLine.replaceAll("~\\[packageServiceImpl\\]",packgeserviceImpl);
            }
            if (ele.contains("~[parantService]")) {
                String parantService = clazz.getPackage().getName();
                parantService =parantService.substring(0,parantService.lastIndexOf("."))+".service."+clazz.getSimpleName()+"Service";
                newLine =newLine.replaceAll("~\\[parantService\\]",parantService);
            }
            if (ele.contains("~[packagecontroller]")) {
                String packagecontroller = clazz.getPackage().getName();
                packagecontroller =packagecontroller.substring(0,packagecontroller.lastIndexOf("."))+".controller";
                newLine =newLine.replaceAll("~\\[packagecontroller\\]",packagecontroller);
            }
            if (ele.contains("~[entitySimpleNameFirstLower]")) {
                String entitySimpleNameFirstLower = clazz.getSimpleName();
                entitySimpleNameFirstLower =entitySimpleNameFirstLower.substring(0,1).toLowerCase()+entitySimpleNameFirstLower.substring(1);
                newLine =newLine.replaceAll("~\\[entitySimpleNameFirstLower\\]",entitySimpleNameFirstLower);
             }
            list1.add(newLine);
            },list);
       return list1;
    }


    public List<String> vueReplace(List<String> list,Class clazz,String vueType){
        List<String> cloneList =new ArrayList<>();
        for (String line:list){
            cloneList.add(line+"");
        }
        List<String> list1 =new ArrayList<>();
        Map<String,Integer> map =new HashMap<>();
        map.put("lineNum",-1);
        Map<String,String> mapStr =new HashMap<>();
        mapStr.put("forStr","");
        List<Field> fieldList = MyClassUtil.getAllFields(clazz);
        ReplacePlace.HowReplace(ele -> {
            String newLine =ele+"";
            map.put("lineNum",map.get("lineNum")+1);
            if (newLine.contains("<for>")){
                String forStr ="";
                for (int i=map.get("lineNum");i<list.size();i++){
                    if (cloneList.get(i).contains("</for>")){
                        cloneList.set(i,"<abandon>");
                        break;
                    }
                    if (map.get("lineNum")!=i){
                        forStr +=cloneList.get(i);
                    }
                    cloneList.set(i,"<abandon>");
                }
                mapStr.put("forStr",forStr);
                String forStrs ="";
                for (Field field:fieldList){
                    Description description =field.getAnnotation(Description.class);
                    String fortemp = mapStr.get("forStr");
                    if (fortemp.contains("<lable>")){
                        String lable =getDescriptionMsg(description,"lable",field);
                        fortemp =fortemp.replaceAll("<lable>",lable);
                    }
                    if (fortemp.contains("<property>")){
                        String property =getDescriptionMsg(description,"prop",field);
                        fortemp =fortemp.replaceAll("<property>",property);
                    }
                    forStrs += fortemp;
                }
                list1.add(forStrs);
                mapStr.put("forStr","");
                map.put("lineNum",-1);
            }else {
                if (!newLine.contains("<abandon>")){
                    if (newLine.contains("<lable>")){
                        String lable =clazz.getSimpleName();
                        newLine =newLine.replaceAll("<lable>",lable);
                    }
                    if (newLine.contains("<property>")){
                        String property =clazz.getSimpleName();
                        newLine =newLine.replaceAll("<property>",property);
                    }
                    if (newLine.contains("<name>")){
                        String tabLable =clazz.getSimpleName();
                        newLine =newLine.replaceAll("<name>",tabLable);
                    }
                    list1.add(newLine);
                }
            }
        },cloneList);
        return list1;
    }



    private String getDescriptionMsg(Description description,String type,Field field){
        if (description==null){
            return  "";
        }
        if (type.equals("lable")){
           String lable= description.label();
           if ("".equals(lable)){
               return field.getName();
           }
           return lable;
        }else if (type.equals("prop")){
            String prop= description.prop();
            if ("".equals(prop)){
                return field.getName();
            }
            return prop;
        }else if (type.equals("searchType")){
            String searchType= description.searchType();
            if ("".equals(searchType)){
                return field.getName();
            }
            return searchType;
        }else if  (type.equals("search")){
            if (description.search()){
                return "true";
            }
            return "";
        }else if  (type.equals("isColumn")){
            if (description.isColumn()){
                return "true";
            }
            return "";
        }else if  (type.equals("tabLable")){
            String tabLable= description.tabLable();
            if ("".equals(tabLable)){
                return field.getType().getSimpleName();
            }
            return tabLable;
        }
       return "";
    }


}
