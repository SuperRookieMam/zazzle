package com.simple.orm.componet.config;


import com.simple.orm.componet.factory.BaseDaoFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration // @Configuration 使用后   spring启动时就会加载到内存  相当于配置xml
@EnableTransactionManagement //启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
@AutoConfigureAfter(DatasourceConfigration.class) // 在指定的配置类初始化后再加载
@EnableJpaRepositories( //@EnableJpaRepositories用来扫描和发现指定包及其子包中的Repository定义     @EntityScan用来扫描和发现指定包及其子包中的Entity定义
        basePackages = {"com.**.dao"},//dao为Jpa的层
        entityManagerFactoryRef="jpaEntityManagerFactory", //实体管理工厂引用名称，对应到@Bean注解对应的方法entityManagerFactory
        transactionManagerRef="platformTransactionManager",//事务管理工厂引用名称，对应到@Bean注解对应的方法transactionManagerPrimary
        repositoryFactoryBeanClass = BaseDaoFactoryBean.class  // 指定Repository的工厂类   目测就多了个实现dao数目的校验和 dao实例实现 自定义接口方法
)
public class JpaConfigration {

    /*
    @Autowired//默认按type注入
    @Qualifier("cusInfoService")//一般作为@Autowired()的修饰用
    @Resource(name="cusInfoService")//默认按name注入，可以通过name和type属性进行选择性注入
    一般@Autowired和@Qualifier一起用，@Resource单独用。
    */


    @Autowired
    @Qualifier("datasource")
    private DataSource dataSource;
    //实体包的位置   跟model层息息相关 ， 加model文件夹这里就要加
    private  String[] packagepath={"com.**.entity","com.**.model","com.**.log"};



    @Bean(name = "jpaEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory () {
        HibernateJpaVendorAdapter jpaVendorAdapter=new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(true);
        //字符创建表结构
        jpaVendorAdapter.setGenerateDdl(true);
        //设置数据库类型
        jpaVendorAdapter.setDatabase(Database.MYSQL);
        //factorybean
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setJpaProperties(getVendorProperties());


        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        //永久层单元，如果时实体类就时设置的字符串加.
        factoryBean.setPersistenceUnitName("");
        //其实就是上面@实体类的包 的数组
        factoryBean.setPackagesToScan(packagepath);
        //下面时还可设置的，但是作用时什么不知道
        return factoryBean;
    }

    private Properties getVendorProperties() {
       /* Map<String, Object> props = new HashMap<>();
        props.put("hibernate.use-new-id-generator-mappings", "true");
        props.put("hibernate.ddl-auto", "update");*/
        //jpaProperties.getHibernateProperties(DataSource)
        Properties properties = new Properties();
        properties.setProperty("hibernate.ddl-auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect");
//        //驼峰转下滑杠明明规则
        properties.setProperty("hibernate.naming.physical-strategy", "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl");
       /* properties.setProperty("base-package", "com.**.jpaDao");
        properties.setProperty("factory-class", BaseDaoFactoryBean.class.getName());
        properties.setProperty("entity-manager-factory-ref","localContainerEntityManagerFactoryBean");
        properties.setProperty("transactionManagerRef","jpaTransactionManager");*/
        return properties;
    }

    //  通常事务方法加注解 @Transactional(value = "jpaTransactionManager") 需要详细配置事务需求就自定义事务管理器

    /**
     * 事务管理器   其他方法使用事务加注解   @Transactional(value="platformTransactionManager")
     * @return
     */
    @Bean("platformTransactionManager")
    public PlatformTransactionManager transactionManagerPrimary(/*EntityManagerFactoryBuilder builder*/) {
        /* return new JpaTransactionManager(entityManagerFactory(builder).getObject());*/
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return txManager;
    }


}
