package oa.mingdao.com.filter;

import oa.mingdao.com.common.BaseFilter;
import oa.mingdao.com.utils.EnvironmentUtils;
import org.guiceside.commons.lang.StringUtils;
import org.guiceside.support.properties.PropertiesConfig;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

/**
 * Created by gbcp on 2016/12/28.
 */
//@PreMatching
//@Priority(value = 4)
//@Provider
public class PostTokenRequestFilter extends BaseFilter implements
        ContainerRequestFilter {
    private PropertiesConfig webConfig = null;
    private String releaseEnvironment = null;
    private String webIP = null;

    public PostTokenRequestFilter() {
        System.out.println("PostTokenRequestFilter initialization");
        webConfig = new PropertiesConfig("webconfig.properties");
        EnvironmentUtils.checkReleaseEnvironment(webConfig);
        releaseEnvironment = webConfig.getString("releaseEnvironment");
        if (StringUtils.isNotBlank(releaseEnvironment)) {
            webIP = webConfig.getString(releaseEnvironment + "_WEB_IP");
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        System.out.println("PostTokenRequestFilter filter");
        if (!requestContext.getMethod().toUpperCase().equals("OPTIONS")) {
            String path = requestContext.getUriInfo().getPath();
            if (StringUtils.isNotBlank(path)) {
                if (!path.startsWith("common") ) {
//                    if (requestContext.getMethod().toUpperCase().equals("POST")) {
//                        String authorizationToken = requestContext.getHeaderString("Authorization");
//                        if (StringUtils.isNotBlank(authorizationToken)) {
//                            String[] auToken = authorizationToken.split("_");
//                            if (auToken != null && auToken.length == 3) {
//                                String tokenUUID = auToken[0];
//                                String token = auToken[1];
//                                String tokenUserId = auToken[2];
//                                if (StringUtils.isNotBlank(sessionID) && StringUtils.isNotBlank(tokenUUID) && StringUtils.isNotBlank(token)&&StringUtils.isNotBlank(tokenUserId)) {
//                                    JedisPool jedisPool = RedisPoolProvider.getPool("REDIS_SESSION");
//                                    if (jedisPool != null) {
//                                        String tokenSessionUserId = RedisSessionUtils.getAttr(jedisPool, sessionID, "userId");
//                                        if (StringUtils.isNotBlank(tokenSessionUserId)) {
//                                            if(tokenSessionUserId.equals(tokenUserId)){
//                                                String tokenValue = RedisSessionUtils.getAttr(jedisPool, sessionID, tokenUUID);
//                                                if (StringUtils.isNotBlank(tokenValue)) {
//                                                    if (!tokenValue.equals(token)) {
//                                                        requestContext.abortWith(responseBuild(401));
//                                                    } else {
//                                                        RedisSessionUtils.removeAttr(jedisPool, sessionID, tokenUUID);
//                                                    }
//                                                }
//                                            }else{
//                                                requestContext.abortWith(responseBuild(416));
//                                            }
//                                        }else{
//                                            requestContext.abortWith(responseBuild(416));
//                                        }
//                                    }
//                                } else {
//                                    requestContext.abortWith(responseBuild(401));
//                                }
//                            } else {
//                                requestContext.abortWith(responseBuild(401));
//                            }
//                        } else {
//                            requestContext.abortWith(responseBuild(401));
//                        }
//                        requestContext.removeProperty("userSessionId");
//                    }
                }
            }
        }
    }
}
