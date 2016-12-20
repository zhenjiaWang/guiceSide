package org.guiceside.config;

import org.guiceside.commons.FilterObj;
import org.guiceside.commons.GlobalExceptionMapping;
import org.guiceside.commons.GlobalResult;
import org.guiceside.commons.HSFConfig;
import org.guiceside.guice.strategy.AbstractInterceptorStrategy;
import org.guiceside.persistence.PersistenceFlavor;

import javax.servlet.Filter;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * <p>
 * 保存guiceSide.xml信息 JavaBean
 * </p>
 *
 * @author zhenjia  <a href='mailto:zhenjiaWang@gmail.com'>email</a>
 * @version 1.0 $Date:200808
 * @since JDK1.5
 */
public class Configuration implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private PersistenceFlavor persistenceFlavor;


    private String freemarkerWrapper;

    private String freemarkerLoadPath;

    private String freemarkerUpdateDelay;

    private String freemarkerEncoding;

    private String freemarkerLocale;

    private String freemarkerNoCache;

    private String freemarkerContentType;

    private String freemarkerExceptionHandler;


    private String ignoreParamsKey;

    private Set<String> actionPackages;

    private Set<String> hibernatePackages;


    private HSFConfig hsfConfig;

    private List<GlobalResult> globalResults;

    private List<GlobalExceptionMapping> globalExceptionMappings;

    private List<AbstractInterceptorStrategy> interceptors;

    private List<FilterObj> filterObjList;

    private Map<String,FilterObj> filterObjMap;

    public PersistenceFlavor getPersistenceFlavor() {
        return persistenceFlavor;
    }

    public void setPersistenceFlavor(PersistenceFlavor persistenceFlavor) {
        this.persistenceFlavor = persistenceFlavor;
    }

    public List<GlobalResult> getGlobalResults() {
        return globalResults;
    }

    public void setGlobalResults(List<GlobalResult> globalResults) {
        this.globalResults = globalResults;
    }

    public List<GlobalExceptionMapping> getGlobalExceptionMappings() {
        return globalExceptionMappings;
    }

    public void setGlobalExceptionMappings(
            List<GlobalExceptionMapping> globalExceptionMappings) {
        this.globalExceptionMappings = globalExceptionMappings;
    }


    public List<AbstractInterceptorStrategy> getInterceptors() {
        return interceptors;
    }

    public void setInterceptors(List<AbstractInterceptorStrategy> interceptors) {
        this.interceptors = interceptors;
    }


    public String getFreemarkerLoadPath() {
        return freemarkerLoadPath;
    }

    public void setFreemarkerLoadPath(String freemarkerLoadPath) {
        this.freemarkerLoadPath = freemarkerLoadPath;
    }

    public String getFreemarkerUpdateDelay() {
        return freemarkerUpdateDelay;
    }

    public void setFreemarkerUpdateDelay(String freemarkerUpdateDelay) {
        this.freemarkerUpdateDelay = freemarkerUpdateDelay;
    }

    public String getFreemarkerEncoding() {
        return freemarkerEncoding;
    }

    public void setFreemarkerEncoding(String freemarkerEncoding) {
        this.freemarkerEncoding = freemarkerEncoding;
    }

    public String getFreemarkerLocale() {
        return freemarkerLocale;
    }

    public void setFreemarkerLocale(String freemarkerLocale) {
        this.freemarkerLocale = freemarkerLocale;
    }

    public String getFreemarkerNoCache() {
        return freemarkerNoCache;
    }

    public void setFreemarkerNoCache(String freemarkerNoCache) {
        this.freemarkerNoCache = freemarkerNoCache;
    }

    public String getFreemarkerContentType() {
        return freemarkerContentType;
    }

    public void setFreemarkerContentType(String freemarkerContentType) {
        this.freemarkerContentType = freemarkerContentType;
    }

    public String getFreemarkerWrapper() {
        return freemarkerWrapper;
    }

    public void setFreemarkerWrapper(String freemarkerWrapper) {
        this.freemarkerWrapper = freemarkerWrapper;
    }



    public String getFreemarkerExceptionHandler() {
        return freemarkerExceptionHandler;
    }

    public void setFreemarkerExceptionHandler(String freemarkerExceptionHandler) {
        this.freemarkerExceptionHandler = freemarkerExceptionHandler;
    }

    public Set<String> getActionPackages() {
        return actionPackages;
    }

    public void setActionPackages(Set<String> actionPackages) {
        this.actionPackages = actionPackages;
    }

    public Set<String> getHibernatePackages() {
        return hibernatePackages;
    }

    public void setHibernatePackages(Set<String> hibernatePackages) {
        this.hibernatePackages = hibernatePackages;
    }

    public String getIgnoreParamsKey() {
        return ignoreParamsKey;
    }

    public void setIgnoreParamsKey(String ignoreParamsKey) {
        this.ignoreParamsKey = ignoreParamsKey;
    }

    public List<FilterObj> getFilterObjList() {
        return filterObjList;
    }

    public Map<String, FilterObj> getFilterObjMap() {
        return filterObjMap;
    }

    public void setFilterObjMap(Map<String, FilterObj> filterObjMap) {
        this.filterObjMap = filterObjMap;
    }


    public void setFilterObjList(List<FilterObj> filterObjList) {
        this.filterObjList = filterObjList;
    }

    public HSFConfig getHsfConfig() {
        return hsfConfig;
    }

    public void setHsfConfig(HSFConfig hsfConfig) {
        this.hsfConfig = hsfConfig;
    }
}
