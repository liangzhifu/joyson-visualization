package com.joyson.visualization.common.initializer;

import com.joyson.visualization.common.config.RootConfig;
import com.joyson.visualization.common.config.ServletConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Created by L on 2018/7/14.
 */
public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] {RootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {ServletConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"*.do"};
    }

}
