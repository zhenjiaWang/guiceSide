package restful.oa.mingdao.com;

import com.google.inject.Inject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oa.mingdao.com.entity.SysProvince;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by zhenjiaWang on 16/8/4.
 */
@Path("/wzj")
@Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
public class CommonController {


    @Path("/index")
    @GET
    public String index(@HeaderParam("AuthCode") String authCode){
        JSONArray jsonArray=new JSONArray();

        return jsonArray.toString();
    }
}