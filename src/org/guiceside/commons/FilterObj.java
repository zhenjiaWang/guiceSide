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

    private String exclude;

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

    public String getExclude() {
        return exclude;
    }

    public void setExclude(String exclude) {
        this.exclude = exclude;
    }


    public List<String> getExcludeList(){
        List<String> stringList=null;
        if(StringUtils.isNotBlank(exclude)){
            stringList=strs2List(exclude);
        }
        return stringList;
    }

    public List<String> getUrlPatternList(){
        List<String> stringList=null;
        if(StringUtils.isNotBlank(urlPattern)){
            stringList=strs2List(urlPattern);
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


    public List<String> strs2List(String str){
        List<String> stringList=null;
        if(StringUtils.isNotBlank(str)){
            String[] strs=str.split(",");
            if(strs!=null&&strs.length>0){
                stringList=new ArrayList<String>();
                for(String s:strs){
                    if(StringUtils.isNotBlank(s)){
                        stringList.add(s);
                    }
                }
            }
        }
        return stringList;
    }
}
