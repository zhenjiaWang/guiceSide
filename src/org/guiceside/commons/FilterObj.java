package org.guiceside.commons;

import org.guiceside.commons.lang.StringUtils;

import javax.servlet.Filter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhenjiaWang on 16/6/21.
 */
public class FilterObj implements Serializable {
    private String filter;

    private String urlPattern;

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    public List<String> getUrlPatternList(){
        List<String> stringList=null;
        if(StringUtils.isNotBlank(urlPattern)){
            String[] urlPatterns=urlPattern.split(",");
            if(urlPatterns!=null&&urlPatterns.length>0){
                stringList=new ArrayList<String>();
                for(String pattern:urlPatterns){
                    if(StringUtils.isNotBlank(pattern)){
                        stringList.add(pattern);
                    }
                }
            }
        }
        return stringList;
    }

    public Class<Filter> getFilterClass(){
        Class filterClass=null;
        if(StringUtils.isNotBlank(filter)){
            try{
                filterClass=Class.forName(filter);
            }catch (Exception e){

            }
        }
        return filterClass;
    }
}
