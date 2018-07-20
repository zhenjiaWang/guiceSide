package org.guiceside.guice.module;

import com.google.inject.servlet.ServletModule;
import org.apache.log4j.Logger;
import org.guiceside.commons.FilterObj;
import org.guiceside.commons.lang.ClassUtils;
import org.guiceside.commons.lang.StringUtils;
import org.guiceside.config.Configuration;
import org.guiceside.web.annotation.Action;
import org.guiceside.web.dispatcher.mapper.ActionResourceException;
import org.guiceside.web.dispatcher.mapper.ActionResourceManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zhenjiaWang on 16/6/22.
 */
public class GuiceSideModule extends ServletModule {

    private static final Logger log =Logger.getLogger(GuiceSideModule.class);
    private Configuration webConfiguration;

    private GuiceSideModule(){

    }

    public GuiceSideModule(Configuration webConfiguration){
        this.webConfiguration=webConfiguration;
    }

    @Override
    protected void configureServlets() {
        if(webConfiguration!=null){
            List<FilterObj> filterObjList=webConfiguration.getFilterObjList();
            if(filterObjList!=null&&!filterObjList.isEmpty()){
                for(FilterObj filterObj:filterObjList){
                    filter(filterObj.getUrlPatternList()).through(filterObj.getFilterClass());
                }
            }
            Set<Class<?>> classes=addActionClasses(webConfiguration.getActionPackages());
            if(classes!=null&&!classes.isEmpty()){
                for (Class<?> cls : classes) {
                    if (cls.isAnnotationPresent(Action.class)) {
                        Action gsAction = cls.getAnnotation(Action.class);
                        try {
                            ActionResourceManager.put(gsAction.namespace(),
                                    gsAction.name(), cls);
                            if(log.isDebugEnabled()){
                                log.debug("addActionResource ["+gsAction.namespace()+"/"+gsAction.name()+"] mapping to "+cls.getName());
                            }
                        } catch (ActionResourceException e) {
                            log.error("There was an error in the load ActionMapping", e.getCause());
                        }
                    }
                }
            }
        }
    }

    private Set<Class<?>> addActionClasses(Set<String> actionPackages) {
        if (actionPackages != null && !actionPackages.isEmpty()) {
            Set<Class<?>> classes= new HashSet<Class<?>>();
            for (String packages : actionPackages) {
                if (StringUtils.isNotBlank(packages)) {
                    classes.addAll(ClassUtils.getClasses(packages));
                }
            }
            return classes;
        }
        return null;
    }
}
