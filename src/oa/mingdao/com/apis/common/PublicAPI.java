package oa.mingdao.com.apis.common;


import com.google.inject.Inject;
import net.sf.json.JSONObject;
import oa.mingdao.com.common.BaseAPI;
import oa.mingdao.com.providers.biz.SysUserBiz;
import org.guiceside.commons.lang.StringUtils;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by gbcp on 16/8/8.
 */
@Path("/public")
public class PublicAPI extends BaseAPI {
    @Inject
    private SysUserBiz sysUserBiz;

    @Path("/userInfo")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response userInfo(@HeaderParam("userId") String userId) {
        JSONObject result = new JSONObject();
        StringBuilder errorBuilder = new StringBuilder();
        String bizResult = null;
        System.out.println("wzjwzj");
        System.out.println("userId="+userId);

        System.out.println("sysUserBiz="+sysUserBiz);

        if (StringUtils.isNotBlank(userId)) {

        }
        bizResult= sysUserBiz.userInfo(1l);
        buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();

    }



}
