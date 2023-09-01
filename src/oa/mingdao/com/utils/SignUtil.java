package oa.mingdao.com.utils;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Lara Croft on 2020/6/22.
 */
public class SignUtil {
    public static String sha1(String str) throws IOException {
        return byte2hex(getSHA1Digest(str));
    }

    private static byte[] getSHA1Digest(String data) throws IOException {
        byte[] bytes;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            bytes = md.digest(data.getBytes("utf-8"));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse);
        }
        return bytes;
    }

    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }

    public static String sign(Map<String, String> param, String secret) throws IOException {
        if (param == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        List<String> paramNames = new ArrayList<>(param.size());
        paramNames.addAll(param.keySet());
        Collections.sort(paramNames);
        sb.append(secret);
        for (String paramName : paramNames) {
            sb.append(paramName).append(param.get(paramName));
        }
        sb.append(secret);
        return sha1(sb.toString());
    }

}