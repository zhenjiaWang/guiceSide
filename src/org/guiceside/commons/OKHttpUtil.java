package org.guiceside.commons;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import okhttp3.*;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.guiceside.commons.lang.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhenjiaWang on 15/8/26.
 */
public class OKHttpUtil {
    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder().
            connectTimeout(40, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS).build();
    ;


    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static final MediaType XML = MediaType.parse("application/xml; charset=utf-8");

    public static String formatParams(List<BasicNameValuePair> params) {
        return URLEncodedUtils.format(params, "UTF-8");
    }

    public static String get(String url, Map<String, String> paramsMap) throws IOException {
        String responseStr = null;
        List<BasicNameValuePair> nameValuePairs = null;
        if (paramsMap != null && !paramsMap.isEmpty()) {
            nameValuePairs = new ArrayList<BasicNameValuePair>();
            Set<String> keys = paramsMap.keySet();
            for (String key : keys) {
                BasicNameValuePair basicNameValuePair = new BasicNameValuePair(key, paramsMap.get(key));
                nameValuePairs.add(basicNameValuePair);
            }
            url += "?" + formatParams(nameValuePairs);
        }


        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            responseStr = response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
        return responseStr;
    }

    public static String post(String url, Map<String, String> paramsMap) throws IOException {
        String responseStr = null;

        RequestBody formBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                JSONObject.fromObject(paramsMap).toString());

        //RequestBody formBody = formEncodingBuilder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            responseStr = response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
        return responseStr;
    }


    public static String post(String url, String jsonData) throws IOException {
        String responseStr = null;
        RequestBody body = RequestBody.create(JSON, jsonData);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            responseStr = response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
        return responseStr;
    }

    public static String postXML(String url, String xmlData) throws IOException {
        String responseStr = null;
        RequestBody body = RequestBody.create(XML, xmlData);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            responseStr = response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
        return responseStr;
    }


    public static byte[] postByte(String url, String jsonData) throws IOException {
        byte[] responseStr = null;
        RequestBody body = RequestBody.create(JSON, jsonData);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            responseStr = response.body().bytes();
        } else {
            throw new IOException("Unexpected code " + response);
        }
        return responseStr;
    }

    public static void main(String []args)throws Exception{
        Map<String, JSONObject> map = new HashMap<>();
        String url = "https://open.lixinger.com/api/cn/company";
        JSONObject prams = new JSONObject();
        prams.put("token", "e435e1bf-1287-4ffb-b1c8-6343b34689a1");
        prams.put("includeDelisted", true);
        String r = OKHttpUtil.post(url, prams.toString());
        if (StringUtils.isNotBlank(r)) {
            JSONObject result = JSONObject.fromObject(r);
            if (result.containsKey("code") && result.getInt("code") == 1 && result.containsKey("data")) {
                JSONArray array = result.getJSONArray("data");
                if (!array.isEmpty()) {
                    for (Object obj : array) {
                        JSONObject object = (JSONObject) obj;
                        map.put(object.getString("stockCode"), object);
                    }
                }
            }
        } else {
            System.out.println(r);
        }
        System.out.println(map.size());
    }
}
