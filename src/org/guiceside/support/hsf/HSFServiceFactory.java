package org.guiceside.support.hsf;

/**
 * Created by zhenjiaWang on 16/6/23.
 */
public interface HSFServiceFactory {
    public <T> T consumer(Class<T> classType);
}
