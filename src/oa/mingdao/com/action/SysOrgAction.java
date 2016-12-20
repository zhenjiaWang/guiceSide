package oa.mingdao.com.action;

import com.google.inject.Inject;
import com.taobao.txc.client.aop.TxcTransactionScaner;
import oa.mingdao.com.entity.SysProvince;
import oa.mingdao.com.service.SysProvinceService;
import org.guiceside.commons.lang.BeanUtils;
import org.guiceside.support.hsf.HSFServiceFactory;
import org.guiceside.web.action.BaseAction;
import org.guiceside.web.annotation.Action;
import org.guiceside.web.annotation.ReqGet;

import java.util.List;


/**
 * Created by zhenjiawANG on 15/6/14.
 */
@Action(name = "org", namespace = "/sys")
public class SysOrgAction extends BaseAction {

    @Inject
    private SysProvinceService sysProvinceService;

    @ReqGet
    private String name;

//    @Inject
    private HSFServiceFactory hsfServiceFactory;

    public String execute() throws Exception {
                List<SysProvince> sysProvinceList= sysProvinceService.getList();



        hsfServiceFactory.consumer(MyService.class).echo("hello ali edas");



        TxcTransactionScaner txcTransactionScaner=new TxcTransactionScaner("o2-shop-.1758862495957920.BJ");


//        BeanUtils.forceSetProperty(txcTransactionScaner,"accessKey","");
//        BeanUtils.forceSetProperty(txcTransactionScaner,"secretKey","");



        if(sysProvinceList!=null&&!sysProvinceList.isEmpty()){
            for(SysProvince province:sysProvinceList){
                System.out.println(province.getName());
            }
        }

//
        return "success";
    }

   // System.out.println(getHttpServletRequest());
//        System.out.println(name);
//        SysProvince sysProvince=sysProvinceService.getById(1l);
//        if(sysProvince!=null){
//            System.out.println(sysProvince.getName());
//        }
//        List<SysProvince> sysProvinceList= sysProvinceService.getList();
//        if(sysProvinceList!=null&&!sysProvinceList.isEmpty()){
//            for(SysProvince province:sysProvinceList){
//                System.out.println(province.getName());
//            }
//        }

}
