package com.test.example.autoconfigure;


import com.test.example.log.Log;
import com.test.example.log.constants.LogQueue;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

/**
 * 通过mq发送日志<br>
 * 2018.07.29添加,在LogAutoConfiguration中将该类声明成Bean，用时直接@Autowired即可
 *
 */
public class LogMqClient {

    private static final Logger logger = LoggerFactory.getLogger(LogMqClient.class);

    private AmqpTemplate amqpTemplate;

    public LogMqClient(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void sendLogMsg(String module, String username, String params, String remark, boolean flag) {
        CompletableFuture.runAsync(() -> {
            try {
                Log log = new Log();
                log.setCreateTime(LocalDateTime.now());
                if (StringUtils.isNotBlank(username)) {
                    log.setUsername(username);
                } else {
                    log.setUsername("");
                }

                log.setFlag(flag);
                log.setModule(module);
                log.setParams(params);
                log.setRemark(remark);

                amqpTemplate.convertAndSend(LogQueue.LOG_QUEUE, log);
                logger.info("发送日志到队列：{}", log);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        });
    }
}
