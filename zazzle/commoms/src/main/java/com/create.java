package com;

import com.simple.codecreate.util.CreateFileUtil;

public class create {
    public static void main(String[] args) throws Exception {
       // CreateFileUtil.createAllFile("com.test.example.entity","E:\\template","E:\\javafile");

        CreateFileUtil.createAllFile("com.test.example.log",
                "E:\\template",
                "E:\\javafile");
    }
}
