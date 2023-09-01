package org.guiceside.web.listener;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import org.apache.log4j.Logger;
import org.guiceside.commons.HSFConfig;
import org.guiceside.config.Configuration;
import org.guiceside.guice.module.*;
import org.guiceside.guice.strategy.AbstractInterceptorStrategy;
import org.guiceside.guice.strategy.PackageStrategy;
import org.guiceside.persistence.InitializerPersistence;
import org.guiceside.persistence.PersistenceFlavor;
import org.guiceside.persistence.PersistenceService;
import org.guiceside.support.hsf.ServicesContainer;

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
public class InitGuiceModule {

    private static final Logger log = Logger.getLogger(InitGuiceModule.class);

    protected static ServicesContainer servicesContainer;

    public static void startHSF(Configuration webConfiguration,Injector injector) {
        HSFConfig hsfConfig =webConfiguration.getHsfConfig();
        if(hsfConfig !=null&&servicesContainer!=null){
            if(hsfConfig.isEnable()){
                servicesContainer.init(hsfConfig,injector);
            }
        }
    }

    public static List<AbstractModule> buildModule(Configuration webConfiguration) {

        List<AbstractModule> guiceModuleList = new ArrayList<AbstractModule>();

        guiceModuleList.add(new GuiceSideModule(webConfiguration));


        List<AbstractInterceptorStrategy> interceptors = webConfiguration.getInterceptors();
        if (interceptors != null && !interceptors.isEmpty()) {
            for (AbstractInterceptorStrategy iObj : interceptors) {
                guiceModuleList.add(iObj);
                if (log.isDebugEnabled()) {
                    log.debug("Install " + iObj.getClass().getSimpleName() + " Module Successful");
                }
            }
        }


        PersistenceModule persistenceModule = null;
        PersistenceFlavor flavor = webConfiguration.getPersistenceFlavor();
        if (flavor != null) {
            persistenceModule = PersistenceService.enable(flavor);
            if (log.isDebugEnabled()) {
                log.debug("Install " + flavor + " Module Successful");
            }

            guiceModuleList.add(persistenceModule);
            log.debug("HibernatePackages " + webConfiguration.getHibernatePackages());
            builderPackages(guiceModuleList, webConfiguration.getHibernatePackages(), new HibernateModule());

            guiceModuleList.add(new HibernateLocalTxnModule());
            if (log.isDebugEnabled()) {
                log.debug("Install HibernateLocalTxnModule Module Successful");
            }

            guiceModuleList.add(new ThreadSafeInterceptorModule());
            if (log.isDebugEnabled()) {
                log.debug("Install ThreadSafeInterceptorModule Module Successful");
            }
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


        HSFConfig hsfConfig = webConfiguration.getHsfConfig();
        servicesContainer = null;
        if (hsfConfig != null) {
            if (hsfConfig.isEnable()) {
                servicesContainer = new ServicesContainer();
                log.debug("Initialization  HSF Service");
                guiceModuleList.add(new HSFModule(servicesContainer));
            }
            guiceModuleList.add(new ConnectManagerModule());
        }
        return guiceModuleList;
    }

    private static void builderPackages(List<AbstractModule> guiceModuleList, Set<String> packagesArr, PackageStrategy packageStrategy) {
        if (packagesArr != null && !packagesArr.isEmpty()) {
            for (String packages : packagesArr) {
                log.debug("packages " + packages);
                packageStrategy.addActionPackages(packages);
            }
            guiceModuleList.add((AbstractModule) packageStrategy);
        }
    }

}
