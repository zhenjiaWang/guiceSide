package oa.mingdao.com.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhenjiaWang on 16/7/14.
 */
public class DrdsIDUtils {


    public static Map<DrdsTable, IdWorker> idWorkerMap = new HashMap<>();

    static {
        IdWorker sysIdWorker = new IdWorker(1);
        idWorkerMap.put(DrdsTable.SYS, sysIdWorker);

        IdWorker wxPayIdWorker = new IdWorker(2);
        idWorkerMap.put(DrdsTable.WXPAY, wxPayIdWorker);

        IdWorker wxdWorker = new IdWorker(3);
        idWorkerMap.put(DrdsTable.WX, wxdWorker);

        IdWorker manageWorker = new IdWorker(4);
        idWorkerMap.put(DrdsTable.MANAGE, manageWorker);

        IdWorker customerWorker = new IdWorker(5);
        idWorkerMap.put(DrdsTable.CUSTOMER, customerWorker);

        IdWorker workWorker = new IdWorker(6);
        idWorkerMap.put(DrdsTable.WORK, workWorker);

        IdWorker reaearchWorker = new IdWorker(7);
        idWorkerMap.put(DrdsTable.RESEARCH, workWorker);

    }

    public static Long getID(DrdsTable drdsTable) {
        return idWorkerMap.get(drdsTable).getId();
    }

    public static void main(String[] args) {
    }
}
