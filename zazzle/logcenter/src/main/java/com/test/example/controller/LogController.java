package com.test.example.controller;

import com.simple.base.componet.dto.ResultDto;
import com.test.example.log.Log;
import com.test.example.service.LogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("log")
public class LogController {

    @Autowired
    @Qualifier("LogServiceImpl")
    public LogService baseService;

    @GetMapping("{id}")
    @ResponseBody
    @ApiOperation(value="根据Id查询实体", notes="findById")
    public ResultDto findById(@PathVariable("id") Long id){
        return ResultDto.success(baseService.findById(id));
    }


    @PostMapping("/logs-anon/internal")
    @ApiOperation(value="写日志", notes="save")
    public void save(@RequestBody Log log) {
        baseService.save(log);
    }

}
