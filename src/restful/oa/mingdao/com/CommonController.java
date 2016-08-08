package restful.oa.mingdao.com;

import com.google.inject.Inject;
import net.sf.json.JSONObject;
import oa.mingdao.com.entity.SysProvince;
import oa.mingdao.com.service.SysProvinceService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhenjiaWang on 16/8/4.
 */
@Path("/wzj")
public class CommonController {

    @Inject
    private SysProvinceService sysProvinceService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String index(){
        return "hello";
    }

//    @Path("/home")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public HashMap home(){
//        HashMap<String, String> map = new HashMap<String, String>();
//        List<SysProvince> sysProvinceList= sysProvinceService.getList();
//        if(sysProvinceList!=null&&!sysProvinceList.isEmpty()){
//            SysProvince sysProvince=  sysProvinceList.get(0);
//            map.put("id", sysProvince.getId()+"");
//            map.put("name", sysProvince.getName());
//        }
//        return map;
//    }

}