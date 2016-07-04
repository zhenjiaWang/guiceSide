package org.guiceside.support.hsf;

import com.google.inject.Injector;
import com.taobao.hsf.lightapi.ServiceFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.guiceside.commons.HSFConfig;
import org.guiceside.commons.lang.BeanUtils;
import org.guiceside.commons.lang.ClassUtils;
import org.guiceside.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhenjiaWang on 16/6/23.
 */
public class ServicesContainer {
    public static ServiceFactory factory = ServiceFactory.getInstance();

    private SAXReader saxReader;

    private Document document;

    private Map<String, String> beanMap = new HashMap<String, String>();

    private Map<Class, String> serviceMap = new HashMap<Class, String>();




    private void initConsumer() {
        System.out.println("****initConsumer*****");
        List<Element> hsfGroupList = document
                .selectNodes("/hsf/group");
        if (hsfGroupList != null && !hsfGroupList.isEmpty()) {
            for (Element el : hsfGroupList) {
                String groupId = el.attributeValue("id");
                if (StringUtils.isNotBlank(groupId)) {
                    System.out.println("groupId**************" + groupId + "******************");
                    List<Element> serviceConsumerList = el.elements("consumer");
                    if (serviceConsumerList != null && !serviceConsumerList.isEmpty()) {
                        for (Element serviceConsumerEl : serviceConsumerList) {
                            String id= serviceConsumerEl.attributeValue("id");
                            String interfaceName= serviceConsumerEl.attributeValue("interface");
                            String version= serviceConsumerEl.attributeValue("version");
                            String clientTimeout= serviceConsumerEl.attributeValue("clientTimeout");
                            if(StringUtils.isNotBlank(id)&&StringUtils.isNotBlank(interfaceName)) {
                                if (StringUtils.isNotBlank(version) && StringUtils.isNotBlank(clientTimeout)) {
                                    Class aClass=ClassUtils.getClass(interfaceName);
                                    if(aClass!=null){
                                        serviceMap.put(aClass,id);
                                        Integer timeout=null;
                                        if(StringUtils.isNotBlank(clientTimeout)){
                                            timeout= BeanUtils.convertValue(clientTimeout,Integer.class);
                                        }
                                        if(timeout==null){
                                            timeout=3000;
                                        }
                                        factory.consumer(id)//参数是一个标识，初始化后，下次只需调用consumer("helloConsumer")即可直接拿出对应服务
                                                .service(interfaceName)//服务名 &　接口全类名
                                                .timeout(timeout)
                                                .version(version)//版本号
                                                .group(groupId)//组别
                                                .subscribe();//消费服务并获得服务的接口，至少要调用service()和version()才可以消费服务
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    private void initService() {
        System.out.println("****initService*****");
        List<Element> hsfServiceList = document
                .selectNodes("/hsf/service");
        if (hsfServiceList != null && !hsfServiceList.isEmpty()) {
            for (Element el : hsfServiceList) {
                String beanID = el.attributeValue("id");
                String beanCLASS = el.attributeValue("class");
                if(StringUtils.isNotBlank(beanCLASS)&&StringUtils.isNotBlank(beanID)){
                    beanMap.put(beanID, beanCLASS);
                    System.out.println("****" + beanID + "*****" + beanCLASS);
                }
            }
        }
    }
    private void initProvider(Injector injector) {
        System.out.println("****initProvider*****");
        List<Element> hsfGroupList = document
                .selectNodes("/hsf/group");
        if (hsfGroupList != null && !hsfGroupList.isEmpty()) {
            for (Element el : hsfGroupList) {
                String groupId=el.attributeValue("id");
                if(StringUtils.isNotBlank(groupId)){
                    System.out.println("groupId**************" + groupId + "******************");

                    List<Element> hsfServiceList = el.elements("service");
                    if (hsfServiceList != null && !hsfServiceList.isEmpty()) {
                        for (Element elService : hsfServiceList) {
                            String beanID = elService.attributeValue("id");
                            String beanCLASS = elService.attributeValue("class");
                            if(StringUtils.isNotBlank(beanCLASS)&&StringUtils.isNotBlank(beanID)){
                                beanMap.put(beanID, beanCLASS);
                                System.out.println("****" + beanID + "*****" + beanCLASS);
                            }
                        }
                    }

                    List<Element> serviceProviderList = el.elements("provider");
                    if (serviceProviderList != null && !serviceProviderList.isEmpty()) {
                        for (Element serviceProviderEl : serviceProviderList) {
                            String id= serviceProviderEl.attributeValue("id");
                            String interfaceName= serviceProviderEl.attributeValue("interface");
                            String version= serviceProviderEl.attributeValue("version");
                            String clientTimeout= serviceProviderEl.attributeValue("clientTimeout");
                            String serializeType= serviceProviderEl.attributeValue("serializeType");
                            String enableTXC= serviceProviderEl.attributeValue("enableTXC");
                            String ref= serviceProviderEl.attributeValue("ref");
                            if(StringUtils.isNotBlank(id)&&StringUtils.isNotBlank(interfaceName)){
                                if(StringUtils.isNotBlank(version)&&StringUtils.isNotBlank(ref)){
                                    String beanClass=beanMap.get(ref);
                                    if(StringUtils.isNotBlank(beanClass)){
                                        Class aClass=ClassUtils.getClass(beanClass);
                                        if(aClass!=null){
                                            Object serviceImp= injector.getInstance(aClass);
                                            Integer timeout=null;
                                            if(StringUtils.isNotBlank(clientTimeout)){
                                                timeout= BeanUtils.convertValue(clientTimeout,Integer.class);
                                            }
                                            if(timeout==null){
                                                timeout=3000;
                                            }
                                            factory.provider(id)//参数是一个标识，初始化后，下次只需调用provider("helloProvider")即可拿出对应服务
                                                    .service(interfaceName)//服务名 & 接口全类名
                                                    .clientTimeout(timeout)
                                                    .version(version)//版本号
                                                    .group(groupId)//组别
                                                            // .writeMode("unit",0) //设置单元化服务的writeMode,非unit服务第二个参数随意
                                                    .impl(serviceImp)//对应的服务实现
                                                    .publish();//发布服务，至少要调用service()和version()才可以发布服务
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void init(HSFConfig hsfConfig,Injector injector){
        saxReader = new SAXReader();
        Set<String> providers = hsfConfig.getProviders();
        if (providers != null && !providers.isEmpty()) {
            for (String providersFileName : providers) {
                InputStream is = this.getClass().getResourceAsStream("/" + providersFileName);
                try {
                    document = saxReader.read(is);
                    initProvider(injector);
                    document.clearContent();
                    is.close();
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }

        Set<String> consumers = hsfConfig.getConsumers();
        if (consumers != null && !consumers.isEmpty()) {
            for (String consumersFileName : consumers) {
                InputStream is = this.getClass().getResourceAsStream("/" + consumersFileName);
                try {
                    document = saxReader.read(is);
                    initConsumer();
                    document.clearContent();
                    is.close();
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }
    public ServicesContainer() {



//        factory.provider("helloProvider")//参数是一个标识，初始化后，下次只需调用provider("helloProvider")即可拿出对应服务
//                .service("oa.mingdao.com.action.MyService")//服务名 & 接口全类名
//                .version("1.0.0.daily")//版本号
//                .group("diamond")//组别
//                        // .writeMode("unit",0) //设置单元化服务的writeMode,非unit服务第二个参数随意
//                .impl(new MyServiceImp())//对应的服务实现
//                .publish();//发布服务，至少要调用service()和version()才可以发布服务
//
//        factory.consumer("helloConsumer")//参数是一个标识，初始化后，下次只需调用consumer("helloConsumer")即可直接拿出对应服务
//                .service("oa.mingdao.com.action.MyService")//服务名 &　接口全类名
//                .version("1.0.0.daily")//版本号
//                .group("diamond")//组别
//                .subscribe();//消费服务并获得服务的接口，至少要调用service()和version()才可以消费服务
        // 这里调用subscribe()是为了提前订阅地址，也可以在真正使用的时候在调用，如下方的main。
        //推荐还是在初始化的时候调用，可以让地址更快速地推送，虽然丑了一点。
    }

    public static ServiceFactory getServiceFactory() {
        return factory;
    }

    public String getServiceName(Class classKey){
        return serviceMap.get(classKey);
    }
}
