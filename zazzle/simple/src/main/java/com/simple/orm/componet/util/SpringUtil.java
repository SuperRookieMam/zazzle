package com.simple.orm.componet.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
    }
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static  Object getBean(String beanName){
        if(beanName==null)return null;
        return applicationContext.getBean(beanName);
    }

    public static Object getBeanByType(Class clazz){
        return applicationContext.getBean(clazz);
    }
    public static Map<String,Object> getBeansByType(Class clazz){
        return applicationContext.getBeansOfType(clazz);
    }
    public static String[] getBeanNamesByType(Class clazz){
        return applicationContext.getBeanNamesForType(clazz);
    }
}
