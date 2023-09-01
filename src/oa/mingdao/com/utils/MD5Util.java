package oa.mingdao.com.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class MD5Util {
    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String MD5Encode(String origin) {
        String result = null;
        try {
            result = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            result = byteArrayToHexString(md.digest(result.getBytes("UTF-8")));
        } catch (Exception exception) {
        }
        return result;
    }


    public static String encryptMd5(String string) throws UnsupportedEncodingException {
        return encryptMd5(string, "UTF-8");
    }

    public static String encryptMd5(String string, String charSet) throws UnsupportedEncodingException {
        return DigestUtils.md5Hex(string.getBytes(charSet));
    }
}
