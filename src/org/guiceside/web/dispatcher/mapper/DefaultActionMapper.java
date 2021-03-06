package org.guiceside.web.dispatcher.mapper;

import com.google.inject.Injector;
import org.apache.log4j.Logger;
import org.guiceside.commons.FilterObj;
import org.guiceside.commons.collection.DataUtility;
import org.guiceside.commons.collection.RequestData;
import org.guiceside.commons.lang.StringUtils;
import org.guiceside.config.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 默认实现ActionMapper<br/>
 * 解析HTTP请求数据,返回ActionMapping JavaBean<br/>
 * </p>
 *
 * @author zhenjia <a href='mailto:zhenjiaWang@gmail.com'>email</a>
 * @version 1.0 $Date:200808
 * @since JDK1.5
 */
public class DefaultActionMapper implements ActionMapper {

    private static final Class<?>[] COMMAND_METHOD_PARAM = new Class<?>[]{};
    private static final Logger log = Logger.getLogger(DefaultActionMapper.class);

    final static Pattern pattern = Pattern.compile("\\S*[?]\\S*");

    /**
     * 实现ActionMapper.getMapping方法
     *
     * @param httpServletRequest
     * @param configuration
     */


    public ActionMapping getMapping(HttpServletRequest httpServletRequest,
                                    Configuration configuration,String filterClassName)
            throws RuntimeException {

        ActionMapping actionMapping = new ActionMapping();
        String uri = getUri(httpServletRequest);



        actionMapping.setUri(uri);
        actionMapping.setRefererUrl(httpServletRequest.getHeader("Referer"));
        parse(uri, actionMapping);
        if (StringUtils.isBlank(actionMapping.getName())) {
            return null;
        }
        String extension=parseSuffix(uri);
        if(StringUtils.isNotBlank(extension)){
            actionMapping.setExtension(extension.toLowerCase());

            Map<String,FilterObj> fileObjectMap= configuration.getFilterObjMap();
            if(fileObjectMap!=null&&!fileObjectMap.isEmpty()){
                FilterObj filterObj=fileObjectMap.get(filterClassName);
                List<String> excludeList=filterObj.getExcludeList();
                if(excludeList!=null&&!excludeList.isEmpty()){
                    if(excludeList.contains(extension)){
                        return null;
                    }
                }
            }
        }

        RequestData<String, Object> oDate = actionMapping.getParams();
        RequestData<String, Object> requestData = DataUtility.getRequestData(httpServletRequest);
        if (oDate != null) {
            oDate.putAll(requestData);
            actionMapping.setParams(oDate);
        } else {
            actionMapping.setParams(requestData);
        }
        getActionClass(actionMapping);
        return actionMapping;
    }

    public void createAction(ActionMapping actionMapping, Injector injector) {
        if (actionMapping.getActionClass() != null) {
            // Action Class load..
            getActionObject(actionMapping, injector);
            if (actionMapping.getActionObject() != null) {
                try {
                    getActionMethod(actionMapping);
                } catch (ActionResourceException e) {
                    log.error("NoSuchMethodException", new ActionResourceException("{"
                            + actionMapping.getActionClass()
                            + " }  Method#" + actionMapping.getMethodName()
                            + "#  not found", e));
//                    throw new ActionResourceException("{"
//                            + actionMapping.getActionClass()
//                            + " }  Method#" + actionMapping.getMethodName()
//                            + "#  not found", e);
                }
            } else {
                log.error("instance failed", new ActionResourceException(
                        "[Injector instance failed] instance Object:"
                                + actionMapping.getActionClass() + " "));
//                throw new ActionResourceException(
//                        "[Injector instance failed] instance Object:"
//                                + actionMapping.getActionClass() + " ");
            }
        } else {
            log.error("not found resource", new ActionResourceException(
                    "request url :"
                            + actionMapping.getUri() + " not found resource"));
//            throw new ActionResourceException("request url :"
//                    + actionMapping.getUri() + " not found resource");
        }
    }

    /**
     * 获取当前请求的Method
     *
     * @param actionMapping
     * @throws NoSuchMethodException
     */
    protected void getActionMethod(ActionMapping actionMapping)
            throws ActionResourceException {
        Method method = null;

        for (Class<?> superClass = actionMapping.getActionClass(); ((superClass != Object.class && superClass != null) && (method == null)); superClass = superClass
                .getSuperclass()) {
            try {
                method = superClass.getDeclaredMethod(actionMapping
                        .getMethodName(), COMMAND_METHOD_PARAM);
            } catch (Exception e) {

            }
        }
        if (method == null) {
            throw new ActionResourceException();
        }
        actionMapping.setMethod(method);
    }

    /**
     * 通过Injector 实例 Action 完成赖性Inject
     *
     * @param actionMapping
     * @param injector
     */
    protected void getActionObject(ActionMapping actionMapping,
                                   Injector injector) {
        Object actionObject = injector.getInstance(actionMapping
                .getActionClass());
        actionMapping.setActionObject(actionObject);
    }

    /**
     * 通过请求uri 获取ActionClass
     *
     * @param actionMapping
     */
    protected void getActionClass(ActionMapping actionMapping) {
        Class<?> actionClass = ActionResourceManager.getAction(actionMapping
                .getNamespace()
                + "/" + actionMapping.getName());
        actionMapping.setActionClass(actionClass);
    }



    /**
     * 通过请求uri解析Action Name Namespace
     *
     * @param uri
     * @param actionMapping
     */
    protected void parse(String uri, ActionMapping actionMapping) {
        int start = uri.indexOf("/");
        String namespace = null, name = null;
        String methodName = "execute";
        if (StringUtils.isBlank(actionMapping.getExtension())) {
            String tempUri = uri.substring(start+1);
            String[] urlBuild=tempUri.split("/");
            if(urlBuild!=null&&urlBuild.length>=2){
                namespace="/"+urlBuild[0];
                name=urlBuild[1];
                if(urlBuild.length>2){
                    methodName=urlBuild[2];
                }
            }
        } else {
            int lastSlash = uri.lastIndexOf("/");
            if (lastSlash == -1) {
                namespace = "";
                name = uri;
            } else if (lastSlash == 0) {
                namespace = "/";
                name = uri.substring(lastSlash + 1);
            } else {
                namespace = uri.substring(0, lastSlash);
                name = uri.substring(lastSlash + 1);
            }

            int exclamation = -1;
            exclamation = name.lastIndexOf("!");
            if (exclamation != -1) {
                methodName = name.substring(exclamation + 1);
                name = name.substring(0, exclamation);
            } else {
                methodName = "execute";
            }
        }
        actionMapping.setNamespace(namespace);
        actionMapping.setName(name);
        actionMapping.setMethodName(methodName);
    }

    /**
     * 获得请求uri
     *
     * @param request
     * @return 返回当前请求uri
     */
    private String getUri(HttpServletRequest request) {
        String uri = request.getRequestURI();
        uri = uri.substring(request.getContextPath().length());
        return uri;
    }

    private boolean methodCheck(String nameSpace, String name, String methodName) {
        Class<?> actionClass = ActionResourceManager.getAction(nameSpace
                + "/" + name);
        if (actionClass != null) {
            Method method = null;
            for (Class<?> superClass = actionClass; ((superClass != Object.class && superClass != null) && (method == null)); superClass = superClass
                    .getSuperclass()) {
                try {
                    method = superClass.getDeclaredMethod(methodName, COMMAND_METHOD_PARAM);
                } catch (Exception e) {

                }
            }
            if (method == null) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 获取链接的后缀名
     * @return
     */
    private static String parseSuffix(String url) {

        String str=null;
        Matcher matcher = pattern.matcher(url);

        String[] spUrl = url.toString().split("/");
        if(spUrl!=null&&spUrl.length>0){
            int len = spUrl.length;
            String endUrl = spUrl[len - 1];
            if(matcher.find()) {
                String[] spEndUrl = endUrl.split("\\?");
                str=spEndUrl[0];
            }else {
                str=endUrl;
            }
            if(str.indexOf(".")!=-1){
                str=str.split("\\.")[1];
            }else{
                str=null;
            }
        }
        return str;
    }
}
