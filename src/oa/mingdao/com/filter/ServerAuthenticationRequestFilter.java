package oa.mingdao.com.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import oa.mingdao.com.common.BaseFilter;
import oa.mingdao.com.utils.EnvironmentUtils;
import oa.mingdao.com.utils.JwtKey;
import org.guiceside.commons.lang.DateFormatUtil;
import org.guiceside.commons.lang.StringUtils;
import org.guiceside.support.properties.PropertiesConfig;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import static cn.xinzhu.utils.WxAPPUtils.issuerJWT;

/**
 * Created by gbcp on 2016/12/28.
 */
@PreMatching
@Priority(value = 2)
@Provider
public class ServerAuthenticationRequestFilter extends BaseFilter implements
        ContainerRequestFilter {
    private PropertiesConfig webConfig = null;
    private String releaseEnvironment = null;
    private String webIP = null;
    private List<String> sessionIgnore = new ArrayList<>();

    {
        sessionIgnore.add("common");
        sessionIgnore.add("public");
    }

    public ServerAuthenticationRequestFilter() {
        System.out.println("ServerAuthenticationRequestFilter initialization");
        webConfig = new PropertiesConfig("webconfig.properties");
        EnvironmentUtils.checkReleaseEnvironment(webConfig);
        releaseEnvironment = webConfig.getString("releaseEnvironment");
        if (StringUtils.isNotBlank(releaseEnvironment)) {
            //webIP = webConfig.getString(releaseEnvironment + "_WEB_IP");
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String path = requestContext.getUriInfo().getPath();
        if (StringUtils.isNoneBlank(path)) {
            String action=path.substring(0,path.indexOf("/"));
            if (!sessionIgnore.contains(action)) {
                String sessionJWT = null;
                if (!requestContext.getMethod().toUpperCase().equals("OPTIONS")) {
                    sessionJWT = requestContext.getHeaderString("Authorization");
                    if (sessionJWT == null) {
                        requestContext.abortWith(responseBuild(400));
                        return;
                    }
                }
                if (StringUtils.isNotBlank(sessionJWT)) {
                    String issuer = null;
                    String userId = null;
                    Key key = JwtKey.getInstance().getKey();
                    try {
                        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(sessionJWT).getBody();
                        if (claims != null) {
                            Date expirationDate = claims.getExpiration();
                            Date curDate = DateFormatUtil.getCurrentDate(true);
                            if (curDate.getTime() <= expirationDate.getTime()) {
                                issuer = claims.getIssuer();
                                if (StringUtils.isNotBlank(issuer)) {
                                    if (issuer.equals("wzj")) {
                                        userId = claims.getSubject();
                                        requestContext.getHeaders().add("userId", userId);
                                    } else {
                                        requestContext.abortWith(responseBuild(408));
                                    }
                                }
                            } else {//jwt 超时
                                requestContext.abortWith(responseBuild(410));
                            }

                        }
                    } catch (Exception e) {
                        requestContext.abortWith(responseBuild(400));
                    }
                } else {
                    if (!requestContext.getMethod().toUpperCase().equals("OPTIONS")) {
                        requestContext.abortWith(responseBuild(400));
                    }
                }
            }
        }
    }
}
