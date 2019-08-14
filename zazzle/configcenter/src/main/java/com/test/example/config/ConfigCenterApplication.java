package com.test.example.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;


/**
 * 配置中心
 *
 *
 */
@EnableConfigServer      //开启配置服务器的支持，嵌入到Spring Boot应用程序中
@EnableDiscoveryClient   // 开启 Eureka 客户端的支持
@SpringBootApplication
public class ConfigCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigCenterApplication.class, args);
	}

}
