package restful.oa.mingdao.com;

import com.google.inject.Inject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oa.mingdao.com.entity.SysProvince;
import oa.mingdao.com.service.SysProvinceService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhenjiaWang on 16/8/4.
 */
@Path("/wzj")
@Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
public class CommonController {

    @Inject
    private SysProvinceService sysProvinceService;

    @Path("/index")
    @GET
    public String index(){
        JSONArray jsonArray=new JSONArray();
        List<SysProvince> sysProvinceList= sysProvinceService.getList();
        if(sysProvinceList!=null&&!sysProvinceList.isEmpty()){
            SysProvince sysProvince=  sysProvinceList.get(0);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("id", sysProvince.getId()+"");
            jsonObject.put("name", sysProvince.getName());
            jsonArray.add(jsonObject);
        }
        return jsonArray.toString();
    }
}