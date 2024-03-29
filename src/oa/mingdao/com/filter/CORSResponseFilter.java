package oa.mingdao.com.filter;


import oa.mingdao.com.utils.EnvironmentUtils;
import org.guiceside.commons.lang.StringUtils;
import org.guiceside.support.properties.PropertiesConfig;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created by gbcp on 2016/12/28.
 */

@Provider
@Priority(value = 2)
public class CORSResponseFilter implements ContainerResponseFilter {
    final String P3P = "CP=";
    private String releaseEnvironment = null;
    private String webIP = null;
    private PropertiesConfig webConfig = null;

    public CORSResponseFilter() {
//        System.out.println("CORSResponseFilter initialization");
//        webConfig = new PropertiesConfig("webconfig.properties");
//        EnvironmentUtils.checkReleaseEnvironment(webConfig);
//        releaseEnvironment = webConfig.getString("releaseEnvironment");
//        if (StringUtils.isNotBlank(releaseEnvironment)) {
//            webIP = webConfig.getString(releaseEnvironment + "_WEB_IP");
//        }
//        System.out.println("===========Access-Control-Allow-Origin======="+webIP);
    }


    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        String originHeader = containerRequestContext.getHeaderString("Origin");

        //   System.out.println("===================" + originHeader);
        if (StringUtils.isNotBlank(webIP) && StringUtils.isNotBlank(originHeader)
                && webIP.contains(originHeader)) {
            containerResponseContext.getHeaders().add("Access-Control-Allow-Origin", originHeader);
        }
        containerResponseContext.getHeaders().add("Access-Control-Allow-Headers",
                "origin, content-type, accept, authorization");
        containerResponseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        containerResponseContext.getHeaders().add("Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        containerResponseContext.getHeaders().add("P3P", P3P);
    }
}
