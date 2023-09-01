package oa.mingdao.com.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.guiceside.commons.JsonDataProcessor;
import org.guiceside.commons.JsonValueProcessor;
import org.guiceside.commons.lang.BeanUtils;
import org.guiceside.commons.lang.DateFormatUtil;
import org.guiceside.commons.lang.StringUtils;
import org.guiceside.persistence.entity.IdEntity;
import org.guiceside.persistence.entity.IdEntityPK;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Lara Croft on 2021/12/27.
 */
public class JSONUtils {
    public JSONUtils() {
    }

    public static JSONObject formIdEntity(Object obj) {
        JSONObject jsonObject = packObj(obj, 1);
        return jsonObject;
    }

    private static JSONObject packObj(Object obj, int level) {
        if (obj == null) {
            return null;
        } else {
            JSONObject jsonObject = null;

            try {
                Class claz = obj.getClass();

                try {
                    Field[] fs = claz.getDeclaredFields();
                    if (fs != null && fs.length > 0) {
                        jsonObject = new JSONObject();
                        Field[] arr$ = fs;
                        int len$ = fs.length;

                        for (int i$ = 0; i$ < len$; ++i$) {
                            Field field = arr$[i$];
                            if (!field.getName().equals("serialVersionUID")) {
                                field.setAccessible(true);
                                boolean identity = IdEntity.class.isAssignableFrom(field.getType());
                                Object fieldValue = null;
                                if (!identity) {
                                    fieldValue = field.get(obj);
                                    Class fieldTypeClass = field.getType();
                                    if (fieldTypeClass != null) {
                                        if (fieldValue != null) {
                                            if (fieldTypeClass.equals(Date.class)) {
                                                Date dateObj = (Date) fieldValue;
                                                String dateStr = DateFormatUtil.format(dateObj, "yyyy-MM-dd HH:mm:ss");
                                                if (StringUtils.isNotBlank(dateStr) && dateStr.endsWith("00:00:00")) {
                                                    dateStr = DateFormatUtil.format(dateObj, "yyyy-MM-dd");
                                                }

                                                fieldValue = dateStr;
                                            } else if (fieldTypeClass.equals(Long.class)) {
                                                fieldValue = fieldValue.toString();
                                            } else if (IdEntityPK.class.isAssignableFrom(field.getType())) {
                                                Object pkObj = fieldValue;
                                                Class<?> clazPK = fieldValue.getClass();
                                                if (clazPK != null) {
                                                    Field[] fsPK = clazPK.getDeclaredFields();
                                                    if (fsPK != null && fsPK.length > 0) {
                                                        JSONObject pkJSON = new JSONObject();
                                                        Field[] _arr$ = fsPK;
                                                        int _len$ = fsPK.length;

                                                        for (int _i$ = 0; _i$ < _len$; ++_i$) {
                                                            Field fieldPK = _arr$[_i$];
                                                            fieldPK.setAccessible(true);
                                                            Object fieldValuePK = fieldPK.get(pkObj);
                                                            if (fieldValuePK != null) {
                                                                pkJSON.put(fieldPK.getName(), fieldValuePK.toString());
                                                            } else {
                                                                pkJSON.put(fieldPK.getName(), "");
                                                            }
                                                        }

                                                        fieldValue = pkJSON;
                                                    }
                                                }
                                            }
                                        } else if (fieldTypeClass.equals(String.class)) {
                                            fieldValue = "";
                                        } else if (fieldTypeClass.equals(Double.class)) {
                                            fieldValue = "";
                                        } else if (fieldTypeClass.equals(Long.class)) {
                                            fieldValue = "";
                                        } else if (fieldTypeClass.equals(Integer.class)) {
                                            fieldValue = "";
                                        } else if (fieldTypeClass.equals(Date.class)) {
                                            fieldValue = "";
                                        }
                                    }
                                } else if (level >= 1) {
                                    fieldValue = field.get(obj);
                                    fieldValue = packObj(fieldValue, level - 1);
                                }

                                jsonObject.put(field.getName(), fieldValue);
                            }
                        }
                    }
                } catch (Exception var21) {
                    ;
                }
            } catch (Exception var22) {
                ;
            }

            return jsonObject;
        }
    }

    public static JSONObject formIdEntity(Object obj, int level) {
        JSONObject jsonObject = packObj(obj, level);
        return jsonObject;
    }

    private static String aliasField(String field) {
        if (StringUtils.isNotBlank(field)) {
            if (field.indexOf(".") == -1) {
                return field;
            } else {
                String[] fields = field.split("\\.");
                return fields != null && fields.length > 0 ? fields[fields.length - 1] : null;
            }
        } else {
            return null;
        }
    }

    public static JSONObject formObject(Object obj) {
        JSONObject jsonObject = null;

        try {
            jsonObject = JSONObject.fromObject(obj);
        } catch (Exception var3) {
            ;
        }

        return jsonObject;
    }

    public static JSONArray formList(List<JSONObject> objectList) {
        JSONArray jsonArray = null;
        if (objectList != null && !objectList.isEmpty()) {
            jsonArray = new JSONArray();
            Iterator i$ = objectList.iterator();

            while (i$.hasNext()) {
                JSONObject jsonObject = (JSONObject) i$.next();
                jsonArray.add(jsonObject);
            }
        }

        return jsonArray;
    }

    public static JSONObject formObjectIgnore(Object obj, String... ignoreField) {
        JSONObject jsonObject = null;

        try {
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
            jsonConfig.setExcludes(ignoreField);
            jsonObject = JSONObject.fromObject(obj, jsonConfig);
        } catch (Exception var4) {
            ;
        }

        return jsonObject;
    }

    public static JSONObject formObjectInclude(Object obj, String... includeField) {
        return formObjectInclude(obj, (JsonDataProcessor) null, (Map) null, includeField);
    }

    public static JSONObject formObjectInclude(Object obj, Map<String, String> keyMap, String... includeField) {
        return formObjectInclude(obj, (JsonDataProcessor) null, keyMap, includeField);
    }

    public static List<JSONObject> formListInclude(List<Object> objList, JsonDataProcessor jsonDataProcessor, Map<String, String> keyMap, String... includeField) {
        List<JSONObject> objectList = null;
        if (objList != null && !objList.isEmpty()) {
            objectList = new ArrayList();
            Iterator i$ = objList.iterator();

            while (i$.hasNext()) {
                Object obj = i$.next();
                JSONObject o = formObjectInclude(obj, jsonDataProcessor, keyMap, includeField);
                if (o != null) {
                    objectList.add(o);
                }
            }
        }

        return objectList;
    }

    public static JSONObject formObjectInclude(Object obj, JsonDataProcessor jsonDataProcessor, Map<String, String> keyMap, String... includeField) {
        JSONObject jsonObject = null;

        try {
            if (includeField != null && includeField.length > 0) {
                String[] arr$;
                int len$;
                int i$;
                String field;
                String key;
                if (keyMap != null && !keyMap.isEmpty()) {
                    jsonObject = new JSONObject();
                    arr$ = includeField;
                    len$ = includeField.length;

                    for (i$ = 0; i$ < len$; ++i$) {
                        field = arr$[i$];
                        key = null;
                        if (keyMap.containsKey(field)) {
                            key = (String) keyMap.get(field);
                        } else {
                            key = aliasField(field);
                        }

                        if (StringUtils.isNotBlank(key)) {
                            if (jsonDataProcessor == null) {
                                jsonObject.put(key, BeanUtils.getValue(obj, field));
                            } else {
                                jsonObject.put(key, jsonDataProcessor.process(field, obj));
                            }
                        }
                    }
                } else {
                    jsonObject = new JSONObject();
                    arr$ = includeField;
                    len$ = includeField.length;

                    for (i$ = 0; i$ < len$; ++i$) {
                        field = arr$[i$];
                        key = aliasField(field);
                        if (StringUtils.isNotBlank(key)) {
                            if (jsonDataProcessor == null) {
                                jsonObject.put(key, BeanUtils.getValue(obj, field));
                            } else {
                                jsonObject.put(key, jsonDataProcessor.process(field, obj));
                            }
                        }
                    }
                }
            }
        } catch (Exception var10) {
            ;
        }

        return jsonObject;
    }

    public static <T> T toBean(JSONObject obj, Class<T> type, JsonValueProcessor jsonValueProcessor, JsonDataProcessor jsonDataProcessor, String... includeField) {
        T result = null;

        try {
            if (includeField != null && includeField.length > 0) {
                result = type.newInstance();
                String[] arr$ = includeField;
                int len$ = includeField.length;

                for (int i$ = 0; i$ < len$; ++i$) {
                    String field = arr$[i$];
                    Object valueObj = obj.get(field);
                    if (jsonDataProcessor != null) {
                        valueObj = jsonDataProcessor.process(field, valueObj);
                    }

                    Field declaredField = null;

                    try {
                        declaredField = BeanUtils.getDeclaredField(result, field);
                        if (declaredField != null) {
                            BeanUtils.setValue(result, field, valueObj);
                        }
                    } catch (Exception var13) {
                        result = jsonValueProcessor.process(field, valueObj, result);
                    }
                }
            }
        } catch (Exception var14) {
            ;
        }

        return result;
    }
}
