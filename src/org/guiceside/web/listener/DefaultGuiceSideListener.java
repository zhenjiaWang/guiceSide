package org.guiceside.web.listener;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import org.apache.log4j.Logger;
import org.guiceside.GuiceSideConstants;
import org.guiceside.commons.HSFConfig;
import org.guiceside.config.Configuration;
import org.guiceside.config.ConfigurationManager;
import org.guiceside.guice.module.*;
import org.guiceside.guice.strategy.AbstractInterceptorStrategy;
import org.guiceside.guice.strategy.PackageStrategy;
import org.guiceside.persistence.InitializerPersistence;
import org.guiceside.persistence.PersistenceFlavor;
import org.guiceside.persistence.PersistenceService;
import org.guiceside.support.hsf.ServicesContainer;
import org.guiceside.web.dispatcher.mapper.ActionResourceManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
public class DefaultGuiceSideListener extends GuiceServletContextListener {

    private static final Logger log = Logger.getLogger(DefaultGuiceSideListener.class);


    private ServletContext servletContext;

    private Configuration webConfiguration;

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
        super.contextInitialized(servletContextEvent);

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

    protected String getConfigFileName() {
        return "guiceSide.xml";
    }

    /**
     * 初始化Google Injector
     *
     * @return injector
     */
    @Override
    public Injector getInjector() {
        Injector injector = null;
        try {
            boolean enablePersistence = false;
            List<AbstractModule> guiceModuleList=new ArrayList<AbstractModule>();

            guiceModuleList.add(new GuiceSideModule(webConfiguration));



            List<AbstractInterceptorStrategy> interceptors=webConfiguration.getInterceptors();
            if(interceptors!=null&&!interceptors.isEmpty()){
                for(AbstractInterceptorStrategy iObj:interceptors){
                    guiceModuleList.add(iObj);
                    if (log.isDebugEnabled()) {
                        log.debug("Install " + iObj.getClass().getSimpleName() + " Module Successful");
                    }
                }
            }



            PersistenceModule persistenceModule=null;
            PersistenceFlavor flavor = webConfiguration.getPersistenceFlavor();
            if (flavor != null) {
                persistenceModule=PersistenceService.enable(flavor);
                if (log.isDebugEnabled()) {
                    log.debug("Install " + flavor + " Module Successful");
                }
                enablePersistence = true;
                if(persistenceModule!=null){
                    guiceModuleList.add(persistenceModule);
                }
                log.debug("HibernatePackeages " + webConfiguration.getHibernatePackages());
                builderPackages(guiceModuleList,webConfiguration.getHibernatePackages(),new HibernateModule());

                guiceModuleList.add(new HibernateLocalTxnModule());
                if (log.isDebugEnabled()) {
                    log.debug("Install HibernateLocalTxnModule Module Successful");
                }

                guiceModuleList.add(new ThreadSafeInterceptorModule());
                if (log.isDebugEnabled()) {
                    log.debug("Install ThreadSafeInterceptorModule Module Successful");
                }
                if (enablePersistence) {
                    guiceModuleList.add(new AbstractModule() {
                        @Override
                        protected void configure() {
                            bind(InitializerPersistence.class).asEagerSingleton();
                            if (log.isDebugEnabled()) {
                                log.debug("Initialization  StartUp");
                            }
                        }
                    });

                }
            }


            HSFConfig hsfConfig =webConfiguration.getHsfConfig();
            ServicesContainer servicesContainer =null;
            if(hsfConfig !=null){
                if(hsfConfig.isEnable()){
                    servicesContainer=new ServicesContainer();
                    log.debug("Initialization  HSF Service");
                    guiceModuleList.add(new HSFModule(servicesContainer));
                    guiceModuleList.add(new ConnectManagerModule());
                }
            }
            injector=Guice.createInjector(guiceModuleList);
            if (log.isDebugEnabled()) {
                log.debug("Initialization  Guice Injector done");
            }
            if(hsfConfig !=null){
                servicesContainer.init(hsfConfig,injector);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return injector;

    }


    public ServletContext getServletContext() {
        return servletContext;
    }

    private void builderPackages(List<AbstractModule> guiceModuleList,Set<String> packagesArr,PackageStrategy packageStrategy){
        if (packagesArr != null&&!packagesArr.isEmpty()) {
            for (String packages : packagesArr) {
                log.debug("packages " + packages);
                packageStrategy.addActionPackages(packages);
            }
            guiceModuleList.add((AbstractModule) packageStrategy);
        }
    }

}
