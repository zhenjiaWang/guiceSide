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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
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

        Class<?> cls = Class.forName(TxcTransactionScaner.class.getName());

        //设定构造函数的参数类型
        Class<?>[] parTypes=new Class<?>[1];
        parTypes[0]=String.class;
        //获取构造器
        Constructor<?> con=cls.getConstructor(parTypes);//----------------重点注意，参数变化了

        Object[] pars=new Object[1];
        pars[0]="o2-shop-.1758862495957920.BJ";
        //构造对象
        Object txcObj=con.newInstance(pars);           //----------------重点注意，参数变化了
        Class<?> obj = txcObj.getClass();
        Field[] fields = obj.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            if ("accessKey".equals(fields[i].getName())) {
                fields[i].set(txcObj, "Y6G2pVpsa2wbloBQ");
            }else if ("secretKey".equals(fields[i].getName())) {
                fields[i].set(txcObj, "dlH4KDRz6poW4FCe6JaL8bOFifYYRn");
            }else{
                continue;
            }
        }





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
