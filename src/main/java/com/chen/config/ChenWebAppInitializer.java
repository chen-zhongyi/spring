package com.chen.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Created by 陈忠意 on 2017/7/17.
 */
public class ChenWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

    @Override
    protected String[] getServletMappings(){
        return new String[] { "/" };
    }

    @Override
    protected Class<?>[] getRootConfigClasses(){
        return new Class<?>[] { RootConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses(){
        return new Class<?>[] { WebConfig.class };
    }
}
