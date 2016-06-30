package org.guiceside.commons;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by zhenjiaWang on 16/6/23.
 */
public class HSFConfig implements Serializable {

    private boolean enable;

    private Set<String> providers;

    private Set<String> consumers;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Set<String> getConsumers() {
        return consumers;
    }

    public void setConsumers(Set<String> consumers) {
        this.consumers = consumers;
    }

    public Set<String> getProviders() {
        return providers;
    }

    public void setProviders(Set<String> providers) {
        this.providers = providers;
    }
}
