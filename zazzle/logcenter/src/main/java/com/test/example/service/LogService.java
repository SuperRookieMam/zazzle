package com.test.example.service;

import com.simple.base.service.BaseService;
import com.test.example.log.Log;

public interface LogService  extends BaseService<Log, Long> {

    /**
     * 保存日志
     *
     * @param log
     */
    void save(Log log);

}
