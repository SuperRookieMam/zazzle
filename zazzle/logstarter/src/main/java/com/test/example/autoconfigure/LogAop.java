package com.test.example.autoconfigure;

import com.alibaba.fastjson.JSONObject;
import com.test.example.log.Log;
import com.test.example.log.LogAnnotation;
import com.test.example.log.constants.LogQueue;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;



/**
 * aop实现日志
 *
 */
@Aspect
public class LogAop {

    private static final Logger logger = LoggerFactory.getLogger(LogAop.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 环绕带注解 @LogAnnotation  的方法做aop
     */
    @Around(value = "@annotation(com.test.example.log.LogAnnotation)")
    public Object logSave(ProceedingJoinPoint joinPoint) throws Throwable {
        Log log = new Log();
        log.setCreateTime(LocalDateTime.now());
        log.setUsername("123");

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        LogAnnotation logAnnotation = methodSignature.getMethod().getDeclaredAnnotation(LogAnnotation.class);
        log.setModule(logAnnotation.module());

        if (logAnnotation.recordParam()) { // 是否要记录方法的参数数据
            String[] paramNames = methodSignature.getParameterNames();// 参数名
            if (paramNames != null && paramNames.length > 0) {
                Object[] args = joinPoint.getArgs();// 参数值

                Map<String, Object> params = new HashMap<>();
                for (int i = 0; i < paramNames.length; i++) {
                    Object value = args[i];
                    if (value instanceof Serializable) {
                        params.put(paramNames[i], value);
                        if(paramNames[i].equals("uname")){
                            log.setUsername(value+"");
                        }
                    }
                }

                try {
                    log.setParams(JSONObject.toJSONString(params)); // 以json的形式记录参数
                } catch (Exception e) {
                    logger.error("记录参数失败：{}", e.getMessage());
                }
            }
        }

        try {
            Object object = joinPoint.proceed();// 执行原方法
            log.setFlag(Boolean.TRUE);

            return object;
        } catch (Exception e) { // 方法执行失败
            log.setFlag(Boolean.FALSE);
            log.setRemark(e.getMessage()); // 备注记录失败原因
            throw e;
        } finally {
            // 异步将Log对象发送到队列
            CompletableFuture.runAsync(() -> {
                try {
                    amqpTemplate.convertAndSend(LogQueue.LOG_QUEUE, log);
                    logger.info("发送日志到队列：{}", log);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            });

        }

    }
}
