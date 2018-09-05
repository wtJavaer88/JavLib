package com.wnc.javlib.task;

import com.wnc.javlib.jpa.JMovieService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringContextUtils implements ApplicationContextAware{
    private static ApplicationContext context;

    @Override
    public void setApplicationContext( ApplicationContext context )
            throws BeansException
    {
        SpringContextUtils.context = context;
    }

    public static ApplicationContext getContext()
    {
        return context;
    }

    public static JMovieService getJMovieService() {
        return (JMovieService)context.getBean("JMovieService");
    }
}
