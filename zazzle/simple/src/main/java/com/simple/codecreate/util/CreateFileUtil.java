package com.simple.codecreate.util;


import com.simple.codecreate.feature.annotation.IsCreate;
import com.simple.codecreate.feature.replace.InitParmas;

import java.io.*;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateFileUtil {

    public static void createAllFile(String packageName,String templatePath,String sourcePath) throws Exception {
        // 获取指定包名下面所有加了注解需要生成文件的实体类
       File file = getFileByPackage(packageName);
       List<Class> list =new ArrayList<>();
       getAllClass(file,list);
        // 获取指定文件下面的模板文件
       Map<String ,List<String>> map =new HashMap<>();
       getTemplateFils(templatePath,map);
       createJavaFile(map,
                      list,
                      sourcePath,
                      "DaoTemplate",
                      "ServiceTemplate",
                      "ServiceImplTemplate",
                        "ControllerTemplate");
        createVueFile(map,
                list,
                sourcePath,
                "FormTemplate",
                "TableTemplate");


    }


    private static void createJavaFile(Map<String ,List<String>> map,List<Class> list,String sourcePath,String...templateNames) throws Exception {
        for (Class clazz:list){
            List<String> list1 =null;
            for (String name:templateNames){
                list1 =new InitParmas().javaReplace(map.get(name),clazz);
                String fileName = clazz.getSimpleName();
                if (name.startsWith("Dao")){
                    fileName+="Repository";
                }else if (name.startsWith("ServiceImpl")){
                    fileName+="ServiceImpl";
                }else if (name.startsWith("Service")){
                    fileName+="Service";
                }else if (name.startsWith("Controller")){
                    fileName+="Controller";
                }
                createFile(list1,
                           sourcePath,
                           name.substring(0,name.indexOf("Template")),
                            fileName,
                           "java");
            }
        }
    }

    private static void  createVueFile(Map<String ,List<String>> map,List<Class> list,String sourcePath,String...templateNames) throws Exception {
        for (Class clazz:list){
            List<String> list1 =null;
            for (String name:templateNames){
                String vueType=name.contains("Table")?"table":"form";
                String fileName=name.contains("Table")?(clazz.getSimpleName()+"s"):clazz.getSimpleName();
                list1 =new InitParmas().vueReplace(map.get(name),clazz,vueType);

                createFile(list1,
                        sourcePath,
                        name.substring(0,name.indexOf("Template")),
                        fileName,
                        "vue");
            }
        }
    }
    private static File getFileByPackage(String packageName){
        String classes =  CreateFileUtil.class.getClassLoader().getResource("").getPath();
        classes +=packageName.replaceAll("\\.", "/");
        File file =new File(classes);
        if (!file.exists()){
            throw  new RuntimeException("包名不存在");
        }
        return file;
    }

    private   static void getAllClass(File file,List<Class> list) throws ClassNotFoundException {
         if (file.isFile()&&file.getName().endsWith(".class")){
            String classpackge = file.getPath();
            classpackge =classpackge.substring(classpackge.indexOf("classes\\")+8);
            String re = File.separator.equals("\\")?"\\\\":"/";
             classpackge = classpackge.replaceAll(re,"\\.");
             classpackge = classpackge.substring(0,classpackge.lastIndexOf(".class"));
             Class clazz =Class.forName(classpackge);
             Annotation annotation =clazz.getAnnotation(IsCreate.class);
             if (annotation!=null&&((IsCreate)annotation).on()){
                 list.add(clazz);
             }
         }else {
            File[] files = file.listFiles();
             for (File file1:files){
                 getAllClass(file1,list);
             }
         }
    }



    private static void getTemplateFils(String path, Map<String,List<String>> map) throws Exception {
        File file =new File(path);
        if (!file.exists()&&!file.isDirectory()){
            throw  new  RuntimeException("目录不存在");
        }
        File[] files =file.listFiles();
        for (File file1:files){
            if (file1.isFile()&&file1.getName().endsWith(".template")){
                String fileName =file1.getName().substring(0,file1.getName().lastIndexOf("."));
                map.put(fileName,getFileStr(file1));
            }
        }
    }

    private static List<String> getFileStr(File file) throws Exception {
        List<String> list =new ArrayList<>();
        FileInputStream fileInputStream =new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream,"utf-8");
        BufferedReader bufferedReader =new BufferedReader(inputStreamReader);
        String line ="";
        while ((line=bufferedReader.readLine())!=null){
            list.add(line+System.getProperty("line.separator"));
        }
        fileInputStream.close();
        inputStreamReader.close();
        bufferedReader.close();
        return list;
    }
    /**
     * 文件未知必须存在 path 创建在哪里
     * */
    private static File createFile(List<String> list ,String path,String fileType,String fileName,String endWith) throws Exception {
       File file = new File(path);
       if (!file.exists()||!file.isDirectory()){
           throw new  RuntimeException("路径不时一个路径或者目录不存在");
       }
       String javafile=path+File.separator+ "javaFile";
       File javaFile = new File(javafile);
       if (!javaFile.exists()){
           javaFile.mkdir();
       }
       String filetype =javafile+File.separator+fileType;
       File filetypeDir = new File(filetype);
        if (!filetypeDir.exists()){
            filetypeDir.mkdir();
        }
        String newfile =filetype+File.separator+fileName+"."+endWith;
        File newFile = new File(newfile);
        FileOutputStream fileOutputStream =new FileOutputStream(newFile);
        for (String line:list){
          fileOutputStream.write(line.getBytes());
        }
        fileOutputStream.close();
        return newFile;
    }


}
