package com.test.example.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解
 *
 */
@Target({ ElementType.METHOD }) // 表示该注解只能在方法上标注
@Retention(RetentionPolicy.RUNTIME)//注解的生命周期
/**
 * 1.SOURCE:在源文件中有效（即源文件保留）
 * 2.CLASS:在class文件中有效（即class保留）
 * 3.RUNTIME:在运行时有效（即运行时保留）
 */
public @interface LogAnnotation {

	/**
	 * 日志模块
	 *
	 * @return
	 * @see com.test.example.log.constants.LogModule
	 */
	String module();

	/**
	 * 记录参数<br>
	 * 尽量记录普通参数类型的方法，和能序列化的对象
	 * 
	 * @return
	 */
	boolean recordParam() default true;
}
