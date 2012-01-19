package com.sharifpro.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyApplicationContext implements ApplicationContextAware {

    private static ApplicationContext appContext;

    @Override
    public void setApplicationContext(ApplicationContext globalAppContext)
        throws BeansException {

    	MyApplicationContext.appContext = globalAppContext;

    }

    public static ApplicationContext getApplicationContext() {
    	if(appContext == null) {
    		appContext = new ClassPathXmlApplicationContext("/WEB-INF/applicationContext.xml");
    	}
        return appContext;
    }

}