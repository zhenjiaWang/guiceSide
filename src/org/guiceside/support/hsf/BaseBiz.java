package org.guiceside.support.hsf;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import ognl.OgnlException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.*;
import org.guiceside.commons.JsonUtils;
import org.guiceside.commons.Page;
import org.guiceside.commons.lang.BeanUtils;
import org.guiceside.commons.lang.DateFormatUtil;
import org.guiceside.commons.lang.StringUtils;
import org.guiceside.persistence.entity.IdEntity;
import org.guiceside.persistence.entity.Tracker;
import org.guiceside.support.converter.DateConverter;

import java.util.Date;
import java.util.List;

public class BaseBiz {

//    @Inject
//    private HSFServiceFactory hsfServiceFactory;
//
//    public <T> T getService(Class<T> serviceClass) throws Exception {
//        return hsfServiceFactory.consumer(serviceClass);
//    }


    protected String get(Object entity, String property) {
        Object result = null;
        if (entity != null) {
            try {
                result = BeanUtils.getValue(entity, property);
            } catch (OgnlException e) {
                result = null;
            }
        }
        return StringUtils.defaultIfEmpty(result);
    }

    protected String getDate(Object entity, String property, String f) {
        Object result = null;
        if (entity != null) {
            try {
                result = BeanUtils.getValue(entity, property);
            } catch (OgnlException e) {
                result = null;
            }
        }
        return StringUtils.defaultIfEmptyByDate((Date) result, f);
    }

    protected <T> T get(Object entity, String property, Class<T> type) {
        Object result = null;
        if (entity != null) {
            try {
                result = BeanUtils.getValue(entity, property);
            } catch (OgnlException e) {
                result = null;
            }
        }
        result = StringUtils.defaultIfEmpty(result);
        result = BeanUtils.convertValue(result, type);
        return (T) result;
    }

    protected String getJsonStr(JSONObject jsonObject, String key) {
        String result;
        try {
            result = jsonObject.getString(key);
        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    protected String getJsonStr(JSONObject jsonObject, String key, String defaultValue) {
        String result = getJsonStr(jsonObject, key);
        if (StringUtils.isBlank(result)) {
            result = defaultValue;
        }
        return result;
    }

    protected int getJsonInt(JSONObject jsonObject, String key) {
        int result;
        try {
            result = jsonObject.getInt(key);
        } catch (Exception e) {
            result = -1;
        }
        return result;
    }

    protected double getJsonDouble(JSONObject jsonObject, String key) {
        double result;
        try {
            result = jsonObject.getDouble(key);
        } catch (Exception e) {
            result = -1.00;
        }
        return result;
    }

    protected JSONArray buildList2Array(List<IdEntity> idEntityList) {
        JSONArray jsonArray = null;
        if (idEntityList != null && !idEntityList.isEmpty()) {
            jsonArray = new JSONArray();
            for (IdEntity idEntity : idEntityList) {
                JSONObject btcInObj = JsonUtils.formIdEntity(idEntity);
                jsonArray.add(btcInObj);
            }
        }
        return jsonArray;
    }

    protected JSONObject buildPage2Obj(Page page) {
        if (page == null) {
            return null;
        }
        JSONObject pageObj = new JSONObject();
        pageObj.put("currentPage", page.getCurrentPage());
        pageObj.put("everyPage", page.getEveryPage());
        pageObj.put("totalPage", page.getTotalPage());
        pageObj.put("totalRecord", page.getTotalRecord());
        pageObj.put("haxPrePage", page.isHasPrePage());
        pageObj.put("haxNextPage", page.isHasNextPage());
        pageObj.put("haxPrePage", page.isHasPrePage());
        pageObj.put("haxNextPage", page.isHasNextPage());
        pageObj.put("nextIndex", page.getNextIndex());
        pageObj.put("preIndex", page.getPreIndex());
        return pageObj;
    }

    protected void bind(IdEntity entity, Long userId) throws Exception {
        if (entity instanceof Tracker) {
            BeanUtils.setValue(entity, "created", DateFormatUtil.getCurrentDate(true));
            BeanUtils.setValue(entity, "updated", DateFormatUtil.getCurrentDate(true));
            try {
                if (userId != null) {
                    BeanUtils.setValue(entity, "createdBy", userId + "");
                    BeanUtils.setValue(entity, "updatedBy", userId + "");
                } else {
                    BeanUtils.setValue(entity, "createdBy", "system");
                    BeanUtils.setValue(entity, "updatedBy", "system");
                }
            } catch (Exception e) {

            }
        }
        try {
            String useYn = BeanUtils.getValue(entity, "useYn", String.class);
            if (StringUtils.isBlank(useYn)) {
                BeanUtils.setValue(entity, "useYn", "N");
            }
        } catch (Exception e) {
            BeanUtils.setValue(entity, "useYn", "N");
        }
    }

    protected boolean isTime(Integer openHour, Integer openMinute,
                             Integer closeHour, Integer closeMinute) {
        boolean flag = false;
        Date date = DateFormatUtil.getCurrentDate(true);

        String dateStr = DateFormatUtil.format(date, DateFormatUtil.YEAR_MONTH_DAY_PATTERN);

        String openStr = dateStr + " " + openHour + ":" + openMinute + ":00";
        String closeStr = dateStr + " " + closeHour + ":" + closeMinute + ":00";
        Date openDate = DateFormatUtil.parse(openStr, DateFormatUtil.YMDHMS_PATTERN);
        Date closeDate = DateFormatUtil.parse(closeStr, DateFormatUtil.YMDHMS_PATTERN);


        Date startDate = date;
        Date endDate = date;

        long currentTimeStart = startDate.getTime();
        long currentTimeEnd = endDate.getTime();
        long openDateTime = openDate.getTime();
        long closeDateTime = closeDate.getTime();


        if (currentTimeStart >= openDateTime && currentTimeEnd <= closeDateTime) {
            flag = true;
        }
        return flag;
    }

    /**
     * 静态方法,注册ApacheConvert策略
     */
    public static void registConverter() {
        ConvertUtils.register(new StringConverter(), String.class);
        ConvertUtils.register(new IntegerConverter(null), Integer.class);
        ConvertUtils.register(new LongConverter(null), Long.class);
        ConvertUtils.register(new FloatConverter(null), Float.class);
        ConvertUtils.register(new DoubleConverter(null), Double.class);
        ConvertUtils.register(new DateConverter(), Date.class);
    }

    static {
        registConverter();
    }
}
