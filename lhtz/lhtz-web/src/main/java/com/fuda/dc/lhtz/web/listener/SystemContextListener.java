package com.fuda.dc.lhtz.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fuda.dc.lhtz.web.monitor.Monitor;

/**
 * 系统全局ContextListener
 * 
 * @author liukai
 */
public class SystemContextListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(SystemContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("contextInitialized ......");
        Monitor monitor = new Monitor();
        monitor.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    	
    }
}
