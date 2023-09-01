package org.guiceside.web.listener;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.google.inject.servlet.GuiceServletContextListener;
import com.squarespace.jersey2.guice.BootstrapUtils;
import org.apache.log4j.Logger;
import org.glassfish.hk2.api.ServiceLocator;
import org.guiceside.GuiceSideConstants;
import org.guiceside.config.Configuration;
import org.guiceside.config.ConfigurationManager;
import org.guiceside.web.dispatcher.mapper.ActionResourceManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.util.List;

/**
 * <p>
 * 将此Listener(或子类)配置在web.xml中 加载guiceSide.xml,进行Guice的初始化
 * 才能在Application中通过Guice Inject 你的任何对像
 * </p>
 *
 * @author zhenjia <a href='mailto:zhenjiaWang@gmail.com'>email</a>
 * @version 1.0 $Date:200808
 * @since JDK1.5
 */
public class JerseyGuiceSideListener extends GuiceServletContextListener {

    private static final Logger log = Logger.getLogger(JerseyGuiceSideListener.class);


    private ServletContext servletContext;

    private Configuration webConfiguration;

    protected final ServiceLocator locator;

    protected  Injector injector;

    public JerseyGuiceSideListener() {
        this.locator = BootstrapUtils.newServiceLocator();
    }
    /**
     * 加载guiceSide.xml
     *
     * @param servletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        this.servletContext = servletContextEvent.getServletContext();
        ConfigurationManager.initialize(getConfigFileName());
        try {
            webConfiguration = ConfigurationManager.getInstance().loadConfig();
            servletContext.setAttribute(GuiceSideConstants.GUICE_SIDE_CONFIG, webConfiguration);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("Unable to load guiceSide.xml, file path:[" + getConfigFileName() + "] is wrong");
            }
            e.printStackTrace();
        }
        if (log.isDebugEnabled()) {
            log.debug("load guiceSide.xml done");
        }
        Stage stage = this.stage();
        List<AbstractModule> modules = InitGuiceModule.buildModule(webConfiguration);
        this.injector = BootstrapUtils.newInjector(this.locator, stage, modules);
        BootstrapUtils.install(this.locator);

        super.contextInitialized(servletContextEvent);
        //InitGuiceModule.startHSF(webConfiguration, injector);
    }

    /**
     * 释放资源
     *
     * @param servletContextEvent
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ActionResourceManager.clear();
        servletContext.removeAttribute(Injector.class.getName());
        servletContext.removeAttribute(GuiceSideConstants.GUICE_SIDE_CONFIG);
        if (log.isDebugEnabled()) {
            log.debug("CleanUp ActionResourceManager Successful");
            log.debug("CleanUp Guice Injector Successful");
            log.debug("CleanUp Configuration Successful");
        }
        super.contextDestroyed(servletContextEvent);
    }


    /**
     * 初始化Google Injector
     *
     * @return injector
     */
    @Override
    public Injector getInjector() {
        return injector;
    }

    protected Stage stage() {
        return null;
    }

    protected String getConfigFileName() {
        return "guiceSide.xml";
    }

    public ServiceLocator getServiceLocator() {
        return this.locator;
    }


    public ServletContext getServletContext() {
        return servletContext;
    }
}
