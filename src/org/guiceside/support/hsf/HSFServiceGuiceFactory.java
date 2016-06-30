package org.guiceside.support.hsf;

import com.taobao.hsf.lightapi.ServiceFactory;
import org.guiceside.commons.lang.StringUtils;

/**
 * Created by zhenjiaWang on 16/6/23.
 */
public class HSFServiceGuiceFactory implements HSFServiceFactory {

    private final ServiceFactory serviceFactory;
    private final ServicesContainer servicesContainer;

    public HSFServiceGuiceFactory(ServiceFactory serviceFactory,ServicesContainer servicesContainer) {
        this.serviceFactory = serviceFactory;
        this.servicesContainer = servicesContainer;
    }


    @Override
    public <T> T consumer( Class<T> classKey) {
        return getConsumeService(classKey);
    }

    public <T> T getConsumeService(Class<T> classKey) { //这个方法调用之前要保证被初始化过。
        Object obj = null;
        try {
            String consumeName=servicesContainer.getServiceName(classKey);
            if(StringUtils.isNotBlank(consumeName)){
                serviceFactory.consumer(consumeName).sync();//同步等待地址推送，最多6秒。
                obj = serviceFactory.consumer(consumeName).subscribe();
            }

        } catch (Exception e) {

        }
        return (T) obj;
    }
}
