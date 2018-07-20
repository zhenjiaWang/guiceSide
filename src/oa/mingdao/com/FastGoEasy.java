package oa.mingdao.com;

import io.goeasy.GoEasy;
import org.guiceside.commons.TimeUtils;

import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author GoEasy
 */
public class FastGoEasy {

    private static ExecutorService executorService = Executors.newFixedThreadPool(12);
    private static GoEasy goEasy = new GoEasy("1f5421ba-1c45-4f4b-b9bd-41acf3550908");


    public static void publish(final String channel, final String content) {
        executorService.execute(new Runnable() {
            public void run() {
                long startTime = System.currentTimeMillis();

                goEasy.publish(channel, content);
                long endTime = System.currentTimeMillis();
                System.out.println("FastGoEasy goeasy push time=" + TimeUtils.getTimeDiff(startTime, endTime));
            }
        });
    }


}
