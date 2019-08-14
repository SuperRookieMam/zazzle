package com.test.example.consumer;

import com.test.example.log.Log;
import com.test.example.log.constants.LogQueue;
import com.test.example.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


/**
 * 从mq队列消费日志数据
 *
 *
 */
@Component  // spring 的 bean  ，注入到xml
@RabbitListener(queues = LogQueue.LOG_QUEUE) // 监听队列，往哪些队列发消息，可以多个 @RabbitListener(queues ={"zhihao.miao.order","zhihao.info"})
public class LogConsumer {

	private static final Logger logger = LoggerFactory.getLogger(LogConsumer.class);

	@Autowired
	@Qualifier("LogServiceImpl")
	private LogService logService;

	/**
	 * 处理消息
	 * 
	 * @param log
	 */
	@RabbitHandler
	public void logHandler(Log log) {
		try {
			logService.save(log);
		} catch (Exception e) {
			logger.error("保存日志失败，日志：{}，异常：{}", log, e);
		}

	}
}
