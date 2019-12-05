package com.mcw.crowdfunding.listener;

import com.mcw.crowdfunding.util.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author mcw 2019\11\28 0028-14:49
 */
public class StartSystemListener implements ServletContextListener {

    Logger log=LoggerFactory.getLogger(StartSystemListener.class);

    //在服务器启动时,创建application对象时需要执行的方法.
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //1.将项目上下文路径(request.getContextPath())放置到application域中.
        ServletContext application = servletContextEvent.getServletContext();
        String contextPath = application.getContextPath();
        log.debug("当前应用的上下文路径:{}",contextPath);
        application.setAttribute(Const.PATH,contextPath);
        //在web.xml中进行配置<listener>

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.debug("当前应用application对象被销毁");
    }
}
