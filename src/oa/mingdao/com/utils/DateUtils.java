package oa.mingdao.com.utils;

import org.guiceside.commons.lang.DateFormatUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Lara Croft on 2020/2/17.
 */
public class DateUtils {
    /**
     * date2比date1多的天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int differentDays(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) return 0;
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(startDate);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(endDate);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) { //不同一年
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {//闰年
                    timeDistance += 366;
                } else {//不是闰年
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else { //同一年
           // System.out.println(DateFormatUtil.format(endDate,null)+" --- "+DateFormatUtil.format(startDate,null)+" : " + (day2 - day1));
            return day2 - day1;
        }
    }

    public static Date getLastsettlementDay(Integer dayOfWeek, String time) throws Exception {
        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());
        int v = ca.get(Calendar.DAY_OF_WEEK);
        Date updateDate = DateFormatUtil.parse(DateFormatUtil.format(new Date(), null) + " " + time + ":00", DateFormatUtil.YMDHMS_PATTERN);
        if (!(v == dayOfWeek && ca.getTime().getTime() >= updateDate.getTime())) {//过了
            if (v > dayOfWeek) {//current week
                ca.setWeekDate(ca.get(Calendar.YEAR), ca.get(Calendar.WEEK_OF_YEAR), dayOfWeek);
            } else {//last week
                ca.add(Calendar.DAY_OF_YEAR, -(7 - dayOfWeek + v));
            }
        }
        Date date = DateFormatUtil.parse(DateFormatUtil.format(ca.getTime(), null));
        return date;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(DateFormatUtil.format(getLastsettlementDay(6,"12:30"),DateFormatUtil.YMDHMS_PATTERN));
        Date startDate=DateFormatUtil.parse("2020-1-1 23:59:59", DateFormatUtil.YMDHMS_PATTERN);
        Date endDate=DateFormatUtil.parse("2020-1-2 00:00:00", DateFormatUtil.YMDHMS_PATTERN);
      // int day= differentDays(startDate,endDate);
//        System.out.println(Math.abs(startDate.getTime()-endDate.getTime()));
//        System.out.println(1000*24*60*60);
//       System.out.println(Math.abs(startDate.getTime()-endDate.getTime())>1000*24*60*60);
//
//        System.out.println(DateFormatUtil.format(DateFormatUtil.addDay(endDate,1),null));
//        System.out.println(DateFormatUtil.format(DateFormatUtil.addMonth(endDate,6),null));
//
    //    Calendar ca = Calendar.getInstance();
//        ca.setTime(DateFormatUtil.addMonth(endDate,6));
//        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
//        System.out.println(DateFormatUtil.format(ca.getTime(),null));
//      // System.out.println(DateFormatUtil.format(DateFormatUtil.addDay(new Date(),-7),null));
//        System.out.println();

       //System.out.println(DateFormatUtil.format(DateFormatUtil.addDay(new Date(), -1),null));
        // System.out.println(MD5Util.encryptMd5("111111"));
     // System.out.println(DateFormatUtil.format(getLastsettlementDay(6,"22:30"),null));
    }
}

