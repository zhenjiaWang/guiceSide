package oa.mingdao.com;

import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.guiceside.commons.HttpForCardUtils;
import org.guiceside.commons.OKHttpUtil;
import org.guiceside.commons.TimeUtils;
import org.guiceside.commons.lang.DateFormatUtil;
import org.guiceside.commons.lang.NumberUtils;
import org.guiceside.commons.lang.StringUtils;
import org.guiceside.support.redis.RedisPipelineTxStoreUtils;
import org.guiceside.support.redis.RedisStoreUtils;
import org.guiceside.support.redis.RedisTxStoreUtils;
import redis.clients.jedis.*;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by zhenjiawang on 2016/12/24.
 */
public class TestBase {

    public static void redis() {
        System.out.println("TDI".toLowerCase(Locale.US));
        JedisPoolConfig config = new JedisPoolConfig();
        //最大空闲连接数, 应用自己评估，不要超过KVStore每个实例最大的连接数
        config.setMaxIdle(20);
        //最大连接数, 应用自己评估，不要超过KVStore每个实例最大的连接数
        config.setMaxTotal(200);

        config.setTestOnBorrow(false);

        config.setTestWhileIdle(false);

        config.setTestOnReturn(false);

        String host = "123.56.70.244";
        String pwd = "6kMPJgsV";
        int port = 59983;
        final JedisPool pool = new JedisPool(config, host, port, 20000, pwd);
//        try {
//            Jedis jedis = null;
//            try {
//                long start = System.currentTimeMillis();
//                jedis = pool.getResource();
//
////                Transaction transaction = jedis.multi();
////                Response<Double> moneyResponse=null;
////                for(int i=0;i<1000;i++){
////
////                    transaction.hincrByFloat("wzj{test:wzj}","money",100);
////                    transaction.hincrByFloat("wzj{test:wzj}","sub",20);
////                    moneyResponse=transaction.hincrByFloat("wzj{test:wzj}","age",0.1);
////
////                    transaction.hset("wzj{test:wzj}","name","wzj");
////                    transaction.hset("wzj{test:wzj}","car","bmw");
////                    transaction.lpush("wzjList{test:wzj}",i+"");
////                    transaction.set("yyy{test:wzj}","wzj"+i);
////                    System.out.println(i);
////
////                }
////                transaction.exec();
////                System.out.println(jedis.hget("wzj{test:wzj}","age"));
////                System.out.println(moneyResponse.get());
////                jedis.watch("wzj{test:wzj}","wzjList{test:wzj}");
//                Pipeline pipeline = jedis.pipelined();
//                //  pipeline.multi();
//                Response<Double> moneyResponse = null;
//                for (int i = 0; i < 100; i++) {
//
//                    moneyResponse = pipeline.hincrByFloat("wzj{test:wzj}", "money", 100);
//                    pipeline.hincrByFloat("wzj{test:wzj}", "sub", 20);
//                    pipeline.hincrByFloat("wzj{test:wzj}", "age", 0.1);
//
//                    pipeline.hset("wzj{test:wzj}", "name", "wzj");
//                    pipeline.hset("wzj{test:wzj}", "car", "bmw");
//                    pipeline.lpush("wzjList{test:wzj}", i + "");
//                    System.out.println(i);
//
//                }
//                // pipeline.exec();
//                // pipeline.sync();
//                List<Object> objectList = pipeline.syncAndReturnAll();
//                if (objectList != null && !objectList.isEmpty()) {
//                    System.out.println(objectList.size());
//                }
//                System.out.println(moneyResponse.get());
//                System.out.println(jedis.hget("wzj{test:wzj}", "money"));
//                pipeline.hincrByFloat("wzj{test:wzj}", "money", 100);
//                pipeline.sync();
//                System.out.println(jedis.hget("wzj{test:wzj}", "money"));
//                long end = System.currentTimeMillis();
//
//                System.out.println(TimeUtils.getTimeDiff(start, end));
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                if (jedis != null) {
//                    jedis.close();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        int thread_num = 10;
        int client_num = 2000;
        ExecutorService exec = Executors.newCachedThreadPool();
        final Semaphore semp = new Semaphore(thread_num);

        for (int index = 0; index < client_num; index++) {

            final int NO = index;

            Runnable run = new Runnable() {
                public void run() {
                    try {
                        semp.acquire();
                        System.out.println("Thread:" + NO + "：" + System.currentTimeMillis() + "更改数据");
                        Jedis jedis = null;
                        try {
                            jedis = pool.getResource();
                            Pipeline pipeline = jedis.pipelined();
                            RedisPipelineTxStoreUtils.hincrByFloat(pipeline, "{sysAsset}hincrBy", "A", 1);
                            RedisPipelineTxStoreUtils.hincrByFloat(pipeline, "{sysAsset}hincrBy", "B", 2);
                            RedisPipelineTxStoreUtils.hincrByFloat(pipeline, "{sysAsset}hincrBy", "C", 3);
                            pipeline.sync();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (jedis != null) {
                                jedis.close();
                            }
                        }
                        semp.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            exec.execute(run);
        }


//        scheduExec.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    int index=0;
//                    while (true&&index<=500) {
//                        System.out.println(index+" 次push");
//                        long startTime = System.currentTimeMillis();
//                        FastGoEasy.publish("testTest","1");
//                        long endTime = System.currentTimeMillis();
//                        System.out.println(" update time=" + TimeUtils.getTimeDiff(startTime, endTime));
//
//
//                        index++;
//                        Thread.sleep(50);
//
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

//
//        Double i=0.00000000000000015;
//        i=NumberUtils.multiply(i,1,4);
//        System.out.println(i);
//        JedisPoolConfig config = new JedisPoolConfig();
//        //最大空闲连接数, 应用自己评估，不要超过KVStore每个实例最大的连接数
//        config.setMaxIdle(200);
//        //最大连接数, 应用自己评估，不要超过KVStore每个实例最大的连接数
//        config.setMaxTotal(300);
//
//        config.setTestOnBorrow(false);
//
//        config.setTestWhileIdle(false);
//
//        config.setTestOnReturn(false);
//
//        String host = "123.56.70.244";
//        String pwd = "o2Work2016";
//        int port = 59980;
//        JedisPool pool = new JedisPool(config, host, port, 10000, pwd);
//        Jedis jedis = pool.getResource();
//        if (jedis != null) {
//            jedis.watch("wzj");
//            Transaction transaction= jedis.multi();
//            RedisTxStoreUtils.hset(transaction,"wzj","money","12.151");
//            RedisTxStoreUtils.hset(transaction,"wzj","car","bmw");
//            RedisTxStoreUtils.hset(transaction,"wzj","car1","dali");
//            Response<Double> doubleResponse= RedisTxStoreUtils.hincrByFloat(transaction,"wzj","money",100);
//
//
//            List<java.lang.Object> resultTxList=transaction.exec();
//            if(resultTxList!=null&&!resultTxList.isEmpty()){
//                for(java.lang.Object o:resultTxList){
//                    System.out.println(o.toString());
//                }
//            }
//            System.out.println(doubleResponse.get());
//            long al=6239391682041053184l;
//            long bl=6239391682041053184l;
//            System.out.println(al==bl);
//            jedis.close();
//        }
//        pool.destroy();
    }

    public static void aa() throws Exception {


//        double random=Math.round(Math.random()*10000);
//        long l = new Double(99.662712d).longValue();
//        System.out.println(l);
//        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Long time=new Long(1483545600000l);
//        String d = format.format(time);
//        Date date=format.parse(d);
//        Long timestamp=DateFormatUtil.getCurrentDate(true).getTime() ;
//        System.out.println("timestamp:"+timestamp);
//        String dayTimestamp=timestamp.toString().substring(0,8);
//        String hmsTimestamp=timestamp.toString().substring(8);
//        System.out.println("dayTimestamp:"+dayTimestamp);
//        System.out.println("hmsTimestamp:"+hmsTimestamp);
//        System.out.println(DateFormatUtil.getCurrentDate(false).getTime());
//        System.out.println("Format To String(Date):"+d);
//        System.out.println("Format To Date:"+date);
//        System.out.println(System.currentTimeMillis());
//        System.out.println(NumberUtils.getInteger(99.09962733d));
//        System.out.println(NumberUtils.getFloatDecimal(99.09962733d));
//        System.out.println(NumberUtils.floatDecimal2Integer(99.09962733d,NumberUtils.multiplePrice));
//        System.out.println(NumberUtils.integertFloatDecimal(NumberUtils.floatDecimal2Integer(99.09962733d,NumberUtils.multiplePrice),NumberUtils.multiplePrice));
//
//        SysCity sysCity=new SysCity();
//        EntrustO2BuyPK entrustO2BuyPK=new EntrustO2BuyPK(6222706946573418496l,6222286948941144064l);
//        sysCity.setName("wzj");
//        sysCity.setPk(entrustO2BuyPK);
//       System.out.println(JsonUtils.formIdEntity(sysCity));
//       JSONObject jsonObject=new JSONObject();
//        getBTC_NEW_PRICE(jsonObject);
//        System.out.println(jsonObject.toString());

        Date d = new Date(1484308293000l);
        System.out.println(DateFormatUtil.format(d, "HH:mm:ss"));
        Long timestamp = DateFormatUtil.getCurrentDate(false).getTime() / 1000;

        System.out.println("timestamp:" + timestamp);
        String dayTimestamp = timestamp.toString().substring(0, 8);
        String hmsTimestamp = timestamp.toString().substring(8);
        System.out.println(dayTimestamp);
        System.out.println(hmsTimestamp);

        DecimalFormat d1 = new DecimalFormat("#,##0.000000");
        // 设置舍入模式
        //d1.setRoundingMode(RoundingMode.FLOOR);

        System.out.println(Math.abs((19.9 - 19.9)) > 1E-4);
        System.out.println(d1.format(28123.88822d));
        Long aa = 12312372173173123l;
        Long bb = 12312372173173123l;
        System.out.println(aa.equals(bb));
        String mobilePhone = "13488725292";
        //13488725292
        String secretMobilePhone = mobilePhone.substring(0, 3) + "****" + mobilePhone.substring(7);
        System.out.println(secretMobilePhone);
        String host = "http://idcard.market.alicloudapi.com";
        String path = "/lianzhuo/idcard";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE 65b12e36a8874c1ba6573b6fa7e190cf");
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("cardno", "5329011986121500921");
        querys.put("name", "王振佳");
        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpForCardUtils.doGet(host, path, method, headers, querys);
            System.out.println(response.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void getBTC_NEW_PRICE(JSONObject object) {
        Double okCoinBTC = 0.00d;
        Double btcChinaBTC = 0.00d;
        Double hbBTC = 0.00d;
        Double ybBTC = 0.00d;
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("symbol", "btc_cny");
        try {
            String resultOkCoin = OKHttpUtil.get("https://www.okcoin.cn/api/v1/ticker.do", paramsMap);

            if (StringUtils.isNotBlank(resultOkCoin)) {
                JSONObject okCoinObj = JSONObject.fromObject(resultOkCoin);
                if (okCoinObj != null) {
                    if (okCoinObj.containsKey("ticker")) {
                        JSONObject okCoinTickerObj = okCoinObj.getJSONObject("ticker");
                        if (okCoinTickerObj != null) {
                            if (okCoinTickerObj.containsKey("last")) {
                                okCoinBTC = okCoinTickerObj.getDouble("last");
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {

        }

        paramsMap.clear();
        paramsMap.put("market", "btccny");
        try {
            String resultBtcChina = OKHttpUtil.get("https://data.btcchina.com/data/ticker", paramsMap);
            if (StringUtils.isNotBlank(resultBtcChina)) {
                JSONObject btcChinaObj = JSONObject.fromObject(resultBtcChina);
                if (btcChinaObj != null) {
                    if (btcChinaObj.containsKey("ticker")) {
                        JSONObject btcTickerObj = btcChinaObj.getJSONObject("ticker");
                        if (btcTickerObj != null) {
                            if (btcTickerObj.containsKey("last")) {
                                btcChinaBTC = btcTickerObj.getDouble("last");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {

        }

        paramsMap.clear();
        try {
            String resultHb = OKHttpUtil.get("http://api.huobi.com/staticmarket/detail_btc_json.js", paramsMap);
            if (StringUtils.isNotBlank(resultHb)) {
                JSONObject hbObj = JSONObject.fromObject(resultHb);
                if (hbObj != null) {
                    if (hbObj.containsKey("p_last")) {
                        hbBTC = hbObj.getDouble("p_last");
                    }
                }
            }
        } catch (Exception e) {

        }
        paramsMap.clear();
        try {
            String resultYb = OKHttpUtil.get("https://www.yuanbao.com/api_market/getinfo_cny/coin/btc", paramsMap);
            if (StringUtils.isNotBlank(resultYb)) {
                JSONObject ybObj = JSONObject.fromObject(resultYb);
                if (ybObj != null) {
                    if (ybObj.containsKey("price")) {
                        ybBTC = ybObj.getDouble("price");
                    }
                }
            }
        } catch (Exception e) {

        }
        if (btcChinaBTC == null) {
            btcChinaBTC = 0.00d;
        }
        if (okCoinBTC == null) {
            okCoinBTC = 0.00d;
        }
        if (hbBTC == null) {
            hbBTC = 0.00d;
        }
        if (ybBTC == null) {
            ybBTC = 0.00d;
        }

        double a[] = new double[4];
        a[0] = okCoinBTC;
        a[1] = btcChinaBTC;
        a[2] = hbBTC;
        a[3] = ybBTC;
        double temp = 0;
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = 0; j < a.length - 1 - i; j++) {
                if (a[j] > a[j + 1]) {
                    temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
        }

        double price1 = a[1];
        double price2 = a[2];

        Double avgBTC = 0.00d;
        Double sumBtc = NumberUtils.add(price1, price2);
        avgBTC = NumberUtils.divide(sumBtc, 2, 2);

        JSONObject btcPriceObj = new JSONObject();
        btcPriceObj.put("okCoinBTC", okCoinBTC);
        btcPriceObj.put("btcChinaBTC", btcChinaBTC);
        btcPriceObj.put("hbBTC", hbBTC);
        object.put("btcPrice", btcPriceObj);
        object.put("avgBTC", avgBTC);
    }

    public static void main(String args[]) throws Exception,
            InterruptedException {
        redis();
        // aa();
//        Date dd=new Date(1485177715*1000l);
//        System.out.println(DateFormatUtil.format(dd,DateFormatUtil.YMDHMS_PATTERN));
//        ArrayList<Double> list = new ArrayList();
//
//        list.add(22.0d);
//        list.add(2.0d);
//        list.add(1.0);
//        list.add(10.0d);
//        Collections.sort(list);
//        Collections.reverse(list);
//        for(Double d:list){
//            System.out.println(d);
//        }
//
//        // 字符串排序
//        //Collections.sort(list);
//        System.out.println(list.toString()); // [105, 168.61, 242, 317, 68.9, 92.8]
//        //Collections.reverse(list);
//        System.out.println(list.toString()); // [68.9, 92.8, 105, 168.61, 242, 317]
//
//        Double aaa=12.010000d;
//        Integer priceInteger = NumberUtils.getInteger(aaa);
//
//        //将小数转为整数
//        Integer priceDecimal = NumberUtils.floatDecimal2Integer(aaa, NumberUtils.multiplePrice);
//        System.out.println(priceInteger);
//        System.out.println(priceDecimal);
//        System.out.println(NumberUtils.integertFloatDecimal(priceDecimal,NumberUtils.multiplePrice));

        //JSONObject object=new JSONObject();
        //getBTC_NEW_PRICE(object);
        //redis();
//        String endpoint = "cn-beijing.log.aliyuncs.com"; // 选择与上面步骤创建Project所属区域匹配的
//        // Endpoint
//        String accessKeyId = "Y6G2pVpsa2wbloBQ"; // 使用你的阿里云访问密钥AccessKeyId
//        String accessKeySecret = "dlH4KDRz6poW4FCe6JaL8bOFifYYRn"; // 使用你的阿里云访问密钥AccessKeySecret
//        String project = "o2-trading"; // 上面步骤创建的项目名称
//        String logstore = "meta"; // 上面步骤创建的日志库名称
//        // 构建一个客户端实例
//        Client client = new Client(endpoint, accessKeyId, accessKeySecret);
//        // 列出当前Project下的所有日志库名称
//        int offset = 0;
//        int size = 100;
//        String logStoreSubName = "";
//        ListLogStoresRequest req1 = new ListLogStoresRequest(project, offset,
//                size, logStoreSubName);
//        ArrayList<String> logStores = client.ListLogStores(req1).GetLogStores();
//        System.out.println("ListLogs:" + logStores.toString() + "\n");
//        // 写入日志
//        String topic = "";
//        String source = "";
//        // 连续发送10个数据包，每个数据包有10条日志
//        for (int i = 0; i < 10; i++) {
//            Vector<LogItem> logGroup = new Vector<LogItem>();
//            for (int j = 0; j < 10; j++) {
//                LogItem logItem = new LogItem(
//                        (int) (new Date().getTime() / 1000));
//                logItem.PushBack("index", String.valueOf(i * 10 + j));
//                logGroup.add(logItem);
//            }
//            PutLogsRequest req2 = new PutLogsRequest(project, logstore, topic,
//                    source, logGroup);
//            client.PutLogs(req2);
//            /*
//            发送的时候也可以指定将数据发送至有一个特定的shard，只要设置shard的hashkey，则数据会写入包含该hashkey的range所对应的shard，具体api参考以下接口：
//             public PutLogsResponse PutLogs(
//                String project,
//                String logStore,
//                String topic,
//                List<LogItem> logItems,
//                String source,
//                String shardHash  // 根据hashkey确定写入shard，hashkey可以是MD5(ip)或MD5(id)等
//            ) throws LogException;
//            */
//        }
//        // 把0号shard中，最近1分钟写入的数据都读取出来。
//        int shard_id = 0;
//        long curTimeInSec = System.currentTimeMillis() / 1000;
//        GetCursorResponse cursorRes = client.GetCursor(project, logstore,
//                shard_id, curTimeInSec - 60);
//        String beginCursor = cursorRes.GetCursor();
//        cursorRes = client.GetCursor(project, logstore, shard_id,
//                Consts.CursorMode.END);
//        String endCursor = cursorRes.GetCursor();
//        String curCursor = beginCursor;
//        while (curCursor.equals(endCursor) == false) {
//            int loggroup_count = 2; // 每次读取两个loggroup
//            BatchGetLogResponse logDataRes = client.BatchGetLog(project,
//                    logstore, shard_id, loggroup_count, curCursor, endCursor);
//            List<LogGroupData> logGroups = logDataRes.GetLogGroups();
//            for (LogGroupData logGroup : logGroups) {
//                System.out.println("Source:" + logGroup.GetSource());
//                System.out.println("Topic:" + logGroup.GetTopic());
//                for (LogItem log : logGroup.GetAllLogs()) {
//                    System.out.println("LogTime:" + log.GetTime());
//                    List<LogContent> contents = log.GetLogContents();
//                    for (LogContent content : contents) {
//                        System.out.println(content.GetKey() + ":"
//                                + content.GetValue());
//                    }
//                }
//            }
//            String next_cursor = logDataRes.GetNextCursor();
//            System.out.println("The Next cursor:" + next_cursor);
//            curCursor = next_cursor;
//        }
//        // ！！！重要提示 : 只有打开索引功能，才能调用一下接口 ！！！
//        // 等待1分钟让日志可查询
//        try {
//            Thread.sleep(60 * 1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        // 查询日志分布情况
//        String query = "index";
//        int from = (int) (new Date().getTime() / 1000 - 300);
//        int to = (int) (new Date().getTime() / 1000);
//        GetHistogramsResponse res3 = null;
//        while (true) {
//            GetHistogramsRequest req3 = new GetHistogramsRequest(project,
//                    logstore, topic, query, from, to);
//            res3 = client.GetHistograms(req3);
//            if (res3 != null && res3.IsCompleted()) // IsCompleted()返回true，表示查询结果是准确的，如果返回false，则重复查询
//            {
//                break;
//            }
//            Thread.sleep(200);
//        }
//        System.out.println("Total count of logs is " + res3.GetTotalCount());
//        for (Histogram ht : res3.GetHistograms()) {
//            System.out.printf("from %d, to %d, count %d.\n", ht.GetFrom(),
//                    ht.GetTo(), ht.GetCount());
//        }
//        // 查询日志数据
//        long total_log_lines = res3.GetTotalCount();
//        int log_offset = 0;
//        int log_line = 10;
//        while (log_offset <= total_log_lines) {
//            GetLogsResponse res4 = null;
//            // 对于每个log offset,一次读取10行log，如果读取失败，最多重复读取3次。
//            for (int retry_time = 0; retry_time < 3; retry_time++) {
//                GetLogsRequest req4 = new GetLogsRequest(project, logstore,
//                        from, to, topic, query, log_offset, log_line, false);
//                res4 = client.GetLogs(req4);
//                if (res4 != null && res4.IsCompleted()) {
//                    break;
//                }
//                Thread.sleep(200);
//            }
//            System.out.println("Read log count:"
//                    + String.valueOf(res4.GetCount()));
//            log_offset += log_line;
//        }
    }
}
