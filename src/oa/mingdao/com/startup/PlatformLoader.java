package oa.mingdao.com.startup;

import oa.mingdao.com.utils.*;
import com.google.inject.Injector;
import org.apache.log4j.Logger;
import org.guiceside.commons.TimeUtils;
import org.guiceside.commons.lang.StringUtils;
import org.guiceside.support.properties.PropertiesConfig;
import org.guiceside.web.listener.DefaultGuiceSideListener;
import org.quartz.Scheduler;

import javax.servlet.ServletContext;
import javax.servlet.UnavailableException;
import java.net.Inet4Address;
import java.net.InetAddress;


/**
 * @author zhenjia <a href='mailto:zhenjiaWang@gmail.com'>email</a>
 * @version 1.0 2008-12-1
 * @since JDK1.5
 */
public class PlatformLoader {
    private static final Logger log = Logger.getLogger(PlatformLoader.class);
    private Scheduler scheduler;

    public void init(ServletContext servletContext) throws Exception {
        System.out.println("qwqwqqw");
        long tStart = System.currentTimeMillis();
        long tEnd = System.currentTimeMillis();
        if (log.isDebugEnabled()) {
            log.debug("PlatformLoader done! time:" + TimeUtils.getTimeDiff(tStart, tEnd));
        }
        PropertiesConfig webConfig = new PropertiesConfig("webconfig.properties");
        servletContext.setAttribute("webConfig", webConfig);

        Injector injector = (Injector) servletContext
                .getAttribute(Injector.class.getName());
        if (injector == null) {
            log.error("Guice Injector not found", new UnavailableException(
                    "Guice Injector not found (did you forget to register a "
                            + DefaultGuiceSideListener.class.getSimpleName()
                            + "?)"));
            throw new Exception(
                    "Guice Injector not found (did you forget to register a "
                            + DefaultGuiceSideListener.class.getSimpleName()
                            + "?)");
        }
        injector = (Injector) servletContext.getAttribute(Injector.class
                .getName());
        if (injector == null) {
            log.error("Guice Injector not found", new UnavailableException(
                    "Guice Injector not found (did you forget to register a "
                            + DefaultGuiceSideListener.class.getSimpleName()
                            + "?)"));
        }

        String releaseEnvironment = webConfig.getString("releaseEnvironment");
//        String dis_ip = webConfig.getString("DIS_IP");
//        InetAddress address = Inet4Address.getLocalHost();
//        String ip = address.getHostAddress();
//        System.out.println(ip);
//        boolean prodEnv = dis_ip.contains(ip);
//
//        System.out.println("prodEnv=" + prodEnv);
//        if (StringUtils.isNotBlank(releaseEnvironment)) {
//            if (releaseEnvironment.equals("DIS")) {
//                // RedisPoolProvider.initSetting(webConfig);
//            }
//        }


        EnvironmentValue.getInstance().setWebConfig(webConfig);
    }
    public void destroyed(ServletContext servletContext) throws Exception {
        if(scheduler!=null){
            System.out.println("shutdown");
            scheduler.shutdown();
        }
    }

}