package org.guiceside.web.dispatcher;

import com.google.inject.Injector;
import com.google.inject.Singleton;
import org.apache.log4j.Logger;
import org.guiceside.GuiceSideConstants;
import org.guiceside.config.Configuration;
import org.guiceside.web.action.ActionExcetion;
import org.guiceside.web.context.ActionContext;
import org.guiceside.web.context.DefaultActionContext;
import org.guiceside.web.dispatcher.mapper.ActionMapper;
import org.guiceside.web.dispatcher.mapper.ActionMapperFactory;
import org.guiceside.web.dispatcher.mapper.ActionMapping;
import org.guiceside.web.listener.DefaultGuiceSideListener;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.SocketException;

/**
 * <p>
 * GuiceSide Web Mvc 核心Filter
 * 统一接收用户请求并根据规则指派Action类处理用户请求
 * </p>
 *
 * @author zhenjia <a href='mailto:zhenjiaWang@gmail.com'>email</a>
 * @version 1.0 $Date:200808
 * @since JDK1.5
 */
@Singleton
public class FilterDispatcher implements Filter {

    private static final Logger log = Logger.getLogger(FilterDispatcher.class);

    protected FilterConfig filterConfig;

    static final ThreadLocal<ActionContext> localActionContext = new ThreadLocal<ActionContext>();

    final String encoding="UTF-8";

    final String P3P="CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'";

    public FilterConfig getFilterConfig() {
        return filterConfig;
    }

    public void destroy() {

    }

    /**
     * guiceSide 核心Filter<br/>
     * 统一接收并处理用户请求
     *
     * @see DispatcherUtils
     * @see com.google.inject.Inject
     * @see org.guiceside.web.dispatcher.mapper.ActionMapper
     */
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletRequest.setCharacterEncoding(encoding);
        httpServletResponse.setCharacterEncoding(encoding);
        httpServletResponse.setHeader("P3P",P3P);
        ServletContext servletContext = filterConfig.getServletContext();
        Injector injector = (Injector) servletContext
                .getAttribute(Injector.class.getName());
        if (injector == null) {
            log.error("Guice Injector not found", new UnavailableException(
                    "Guice Injector not found (did you forget to register a "
                            + DefaultGuiceSideListener.class.getSimpleName()
                            + "?)"));
            throw new UnavailableException(
                    "Guice Injector not found (did you forget to register a "
                            + DefaultGuiceSideListener.class.getSimpleName()
                            + "?)");
        }
        Configuration configuration = (Configuration) servletContext
                .getAttribute(GuiceSideConstants.GUICE_SIDE_CONFIG);
        if (configuration == null) {
            log.error("Configuration not found", new UnavailableException(
                    "Configuration not found (did you forget to register a "
                            + DefaultGuiceSideListener.class.getSimpleName()
                            + "?)"));
            throw new UnavailableException(
                    "Configuration not found (did you forget to register a "
                            + DefaultGuiceSideListener.class.getSimpleName()
                            + "?)");
        }

        ActionMapper actionMapper = ActionMapperFactory.getActionMapper();
        ActionMapping actionMapping = actionMapper.getMapping(
                httpServletRequest, configuration,FilterDispatcher.class.getName());
        if (actionMapping != null) {
            ActionContext previous = localActionContext.get();
            DispatcherUtils du = DispatcherUtils.getInstance();
            try {
                actionMapper.createAction(actionMapping, injector);
                DefaultActionContext actionContext = new DefaultActionContext(
                        du.createdContext(httpServletRequest, httpServletResponse,
                                servletContext, actionMapping));
                localActionContext.set(actionContext);
                du.execute(localActionContext.get(), injector);
                return;
            } catch (Exception e) {
                boolean exceptionLog = true;
                if (e instanceof SocketException) {
                    exceptionLog = false;
                }
                if (e instanceof IllegalStateException) {
                    exceptionLog = false;
                }
                if (exceptionLog) {
                    log.error("execute failed", new ActionExcetion("[execute failed] In Action {"
                            + actionMapping.getActionClass().getName()
                            + "} Method#" + actionMapping.getMethod()
                            + "# On an Error", e));
                    throw new ActionExcetion("[execute failed] In Action {"
                            + actionMapping.getActionClass().getName()
                            + "} Method#" + actionMapping.getMethod()
                            + "# On an Error", e);
                }
            } finally {
                localActionContext.set(previous);
            }
        }
        chain.doFilter(request, response);
    }


    /**
     * 初始化DispatcherUtils
     *
     * @param filterConfig
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        DispatcherUtils.initialize();
    }
}
