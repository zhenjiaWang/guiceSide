package org.guiceside.guice.module;

import com.google.inject.AbstractModule;
import com.taobao.hsf.lightapi.ServiceFactory;
import org.guiceside.support.hsf.HSFServiceFactory;
import org.guiceside.support.hsf.HSFServiceFactoryProvider;
import org.guiceside.support.hsf.ServiceFactoryProvider;
import org.guiceside.support.hsf.ServicesContainer;

/**
 * Created by zhenjiaWang on 16/6/22.
 */
public class HSFModule extends AbstractModule {
    private ServicesContainer servicesContainer;
    public HSFModule(ServicesContainer servicesContainer){
        this.servicesContainer=servicesContainer;
    }



    @Override
    protected void configure() {
        bind(ServicesContainer.class).toInstance(servicesContainer);
        bind(ServiceFactory.class).toProvider(
                ServiceFactoryProvider.class);
        bind(HSFServiceFactory.class).toProvider(
                HSFServiceFactoryProvider.class);
    }
}
