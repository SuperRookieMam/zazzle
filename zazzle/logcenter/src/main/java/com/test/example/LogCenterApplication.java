package com.test.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 日志中心
 *
 *@EnableDiscoveryClient和@EnableEurekaClient共同点就是：都是能够让注册中心能够发现，扫描到改服务。
 * 不同点：@EnableEurekaClient只适用于Eureka作为注册中心，@EnableDiscoveryClient 可以是其他注册中心。
 * @EnableDiscoveryClient基于spring-cloud-commons,
 * @EnableEurekaClient基于spring-cloud-netflix
 */


@EnableDiscoveryClient
@SpringBootApplication
public class LogCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogCenterApplication.class, args);
	}

}