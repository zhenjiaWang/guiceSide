package org.guiceside.support.hsf;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.taobao.hsf.lightapi.ServiceFactory;

/**
 * Created by zhenjiaWang on 16/6/24.
 */
@Singleton
public class HSFServiceFactoryProvider implements Provider<HSFServiceFactory> {
    private final ServiceFactory serviceFactory;
    private final ServicesContainer servicesContainer;

    @Inject
    public HSFServiceFactoryProvider(ServiceFactory serviceFactory,ServicesContainer servicesContainer) {
        this.serviceFactory = serviceFactory;
        this.servicesContainer=servicesContainer;

    }

    public HSFServiceFactory get() {
       return new HSFServiceGuiceFactory(serviceFactory,servicesContainer);
    }
}
