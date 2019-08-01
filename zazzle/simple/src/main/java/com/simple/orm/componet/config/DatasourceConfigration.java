package com.simple.orm.componet.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * 数据源配置，基本配置,连接数量需要则自行配置，spring启动时就会加载到内存
 * @Component和@Configuration作为配置类的差别：
 * 内部调用时@Component每次都会new新对像    @Configuration 会指向该对象的引用
 *
 * @Configuration标注在类上，相当于把该类作为spring的xml配置文件中的<beans>，作用为：配置spring容器(应用上下文)
 * @Configuration不可以是final类型；
 * @Configuration不可以是匿名类；
 * 嵌套的configuration必须是静态类。
 * */
@Configuration
@Component
public class DatasourceConfigration {
    @Value("${spring.datasource.url}")
    private  String url;
    @Value("${spring.datasource.username}")
    private  String username;
    @Value("${spring.datasource.password}")
    private  String password;
    @Value("${spring.datasource.driverClassName}")
    private  String driverClassName;

    @Primary   //主要的，作用：注入的时候优先选择它
    @Bean("datasource")  // 配合 @Configuration @Component  使用，标注在方法上  其他地方注入时对应Qualifier 里面的名称   @Autowired   @Qualifier("datasource")
    @ConfigurationProperties("spring.datasource")//使用 @ConfigurationProperties 注解，可以将 "application.yml" 配置文件中的键-值自动映射注入 Java Bean 中
    public DataSource getMysqlDatasource(){
        DruidDataSource druidDataSource =new DruidDataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        druidDataSource.setDriverClassName(driverClassName);
        return druidDataSource;
    }
}
