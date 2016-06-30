package org.guiceside.support.hsf;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.taobao.hsf.lightapi.ServiceFactory;

/**
 * Created by zhenjiaWang on 16/6/24.
 */
@Singleton
public class ServiceFactoryProvider implements Provider<ServiceFactory> {
    private final ServicesContainer servicesContainer;

    @Inject
    public ServiceFactoryProvider(ServicesContainer servicesContainer) {
        this.servicesContainer = servicesContainer;

    }

    public ServiceFactory get() {
        return this.servicesContainer.getServiceFactory();
    }
}
