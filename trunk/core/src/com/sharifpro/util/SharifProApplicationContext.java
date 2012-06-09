package com.sharifpro.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SharifProApplicationContext implements ApplicationContextAware {

    private static ApplicationContext appContext;

    @Override
    public void setApplicationContext(ApplicationContext globalAppContext)
        throws BeansException {

    	SharifProApplicationContext.appContext = globalAppContext;
    	PropertyProvider.MESSAGES = (AbstractMessageSource) SharifProApplicationContext.getApplicationContext().getBean("messageSource");
    }

    public static ApplicationContext getApplicationContext() {
    	if(appContext == null) {
    		appContext = new ClassPathXmlApplicationContext("/WEB-INF/applicationContext.xml");
    	}
        return appContext;
    }

}