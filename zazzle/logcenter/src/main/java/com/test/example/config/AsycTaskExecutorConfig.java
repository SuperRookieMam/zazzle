package com.test.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 线程池配置、启用异步
 *
 *
 */
@EnableAsync(proxyTargetClass = true)//利用@EnableAsync注解开启异步任务支持
/**
 * 异步任务配置,spring启动时就会加载到内存
 * @Component和@Configuration作为配置类的差别：
 * 内部调用时@Component每次都会new新对像    @Configuration 会指向该对象的引用
 *
 * @Configuration标注在类上，相当于把该类作为spring的xml配置文件中的<beans>，作用为：配置spring容器(应用上下文)
 * @Configuration不可以是final类型；
 * @Configuration不可以是匿名类；
 * 嵌套的configuration必须是静态类。
 * */
@Configuration
public class AsycTaskExecutorConfig {

	@Bean("task")
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(50);
		taskExecutor.setMaxPoolSize(100);

		return taskExecutor;
	}
}
