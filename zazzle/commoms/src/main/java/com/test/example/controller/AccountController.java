package com.test.example.controller;

import com.simple.base.componet.dto.ResultDto;
import com.simple.base.controller.BaseController;
import com.test.example.entity.Account;
import com.test.example.log.LogAnnotation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("account")
public class AccountController extends BaseController<Account,Long> {



    @GetMapping(value = "mylist")
    @ResponseBody
    @LogAnnotation(module = "添加权限")
    public ResultDto mylist(){
        //baseService.getBaseRepository().findAll()   baseService.getBaseRepository().findAll()
        return ResultDto.success(baseService.getBaseRepository().findAll());
    }
}
