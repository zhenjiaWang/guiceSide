package org.guiceside.web.dispatcher;

import com.google.inject.Injector;
import org.guiceside.web.action.Action;
import org.guiceside.web.action.ActionProxy;
import org.guiceside.web.context.ActionContext;
import org.guiceside.web.dispatcher.mapper.ActionMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 辅助FilterDispatcher的工具类<br/>
 * 提供创建当前上下文以及执行Action两个方法<br/>
 * </p>
 *
 * @author zhenjia <a href='mailto:zhenjiaWang@gmail.com'>email</a>
 * @version 1.0 $Date:200808
 * @since JDK1.5
 */
public class DispatcherUtils {

    private static DispatcherUtils instance;



    /**
     * 初始化DispatcherUtils 单例模式
     */
    public static void initialize() {
        synchronized (DispatcherUtils.class) {
            if (instance == null) {
                instance = new DispatcherUtils();
            }
        }

    }
    /**
     * 获取DispatcherUtils实例
     *
     * @return DispatcherUtils
     */
    public static DispatcherUtils getInstance() {
        return instance;
    }

    /**
     * 设置DispatcherUtils实例
     *
     * @param instance
     */
    public static void setInstance(DispatcherUtils instance) {
        DispatcherUtils.instance = instance;
    }


    /**
     * 创建当前Context
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param servletContext
     * @param actionMapping
     * @return 返回Map结构Action上下文
     */
    public Map<String, Object> createdContext(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            ServletContext servletContext, ActionMapping actionMapping) {
        Map<String, Object> actionContext = new HashMap<String, Object>();
        actionContext.put(ActionContext.HTTPSERVLETREQUEST, httpServletRequest);
        actionContext.put(ActionContext.HTTPSERVLETRESPONSE,
                httpServletResponse);
        actionContext.put(ActionContext.SERVLETCONTEXT, servletContext);
        actionContext.put(ActionContext.ACTIONMAPPING, actionMapping);
        actionContext.put(ActionContext.REQUESTDATA, actionMapping.getParams());
        return actionContext;
    }

    /**
     * 执行Action
     *
     * @param actionContext
     * @param injector
     * @throws Exception
     * @see org.guiceside.web.context.DefaultActionContext
     */

    public void execute(ActionContext actionContext,
                        Injector injector) throws Exception {
        Action action = injector.getInstance(ActionProxy.class);
        action.execute(actionContext);

    }

}
