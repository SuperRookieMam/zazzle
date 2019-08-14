package com.test.example.service.impl;

import com.simple.base.service.impl.BaseServiceImpl;
import com.test.example.log.Log;
import com.test.example.service.LogService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Qualifier("LogServiceImpl")
@Service
public class LogServiceImpl extends BaseServiceImpl<Log, Long> implements LogService {


    /**
     * 将日志保存到数据库<br>
     * 注解@Async是开启异步执行
     *
     * @param log
     */
    @Async
    @Override
    public void save(Log log) {
        if (log.getCreateTime() == null) {
            log.setCreateTime(LocalDateTime.now());
        }
        if (log.getFlag() == null) {
            log.setFlag(Boolean.TRUE);
        }

        baseRepository.save(log);
    }

}
