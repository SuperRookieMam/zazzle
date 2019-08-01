package com.simple.base.componet.dto;

import com.simple.base.componet.enumpackage.ResultEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultDto {

    private  int code;

    private  String msg;

    private  Object data;

    public static ResultDto success(Object data){
        ResultEnum resultEnum = ResultEnum.valueOf(0);
        ResultDto resultDto =new ResultDto();
        resultDto.setCode(resultEnum.getCode());
        resultDto.setMsg(resultEnum.getMsg());
        resultDto.setData(data);
        return resultDto;
    }

    public static  ResultDto error(Object data){
        ResultEnum resultEnum = ResultEnum.valueOf(1);
        ResultDto resultDto =new ResultDto();
        resultDto.setCode(resultEnum.getCode());
        resultDto.setMsg(resultEnum.getMsg());
        resultDto.setData(data);
        return resultDto;
    }

    public static  ResultDto error(Exception e){
        ResultEnum resultEnum = ResultEnum.valueOf(1);
        ResultDto resultDto =new ResultDto();
        resultDto.setCode(resultEnum.getCode());
        resultDto.setMsg(e.getMessage());
        resultDto.setData(null);
        return resultDto;
    }
}
