package oa.mingdao.com.utils;

/**
 * Created by Lara Croft on 2019/5/30.
 */

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;

/**
 * 微信工具类
 */
public class WechatDecryptDataUtil {

    public static void main(String[] args) throws Exception{
//        String result = decryptData(
//                "1IyetLFFpNdx/NGKCaLKbZfSfqZ7eTsiEtP8AfRPyHHvBHFpH/78sbWBgGtJn79dD7TYJY6qSCWT2kiQuOtb6+WqdlY5Tc22kD1cKVLAW6RtXFa13ibaoBlaA4qRt0gCLVql/iJxMvB12L+hDVGt45I6gNALLuQWetvC5JCNR/6jSgIEO2/VqkMYZz0udzIXpOswskEIVb/00bN4qDqr0w==",
//                "xG37x3LJWI22/5FuzH1TDQ==",
//                "k4fdLE/1cz2PQVw9jM5hOA=="
//        );
       // System.out.println(result);


       // ======>encryptedData========>iv=CRQFgvS89UFy5glzGe2r3A=========>sessionKey=XDRzE6NiP6pBaP1OC4N1Vg=========>infoId=6665448408024227840cn.xinzhu.common.BizException: java.lang.NullPointerException
        //======>encryptedData=mwGmzmbKBglDfDge4b2L7NSyL9wQ1k9D66hkrbQon9NvguS77BhACW0OZAJVkoMT=======>iv=WCFSrN1yx2k4o8qqTlmHRA=========>sessionKey=XDRzE6NiP6pBaP1OC4N1Vg=========>infoId=6665448408024227840	at java.lang.System.arraycopy(Native Method)

          //      ======>encryptedData========>iv=WCFSrN1yx2k4o8qqTlmHRA=========>sessionKey=XDRzE6NiP6pBaP1OC4N1Vg=========>infoId=6665448408024227840	at java.lang.System.arraycopy(Native Method)

        String encryptedData = "mwGmzmbKBglDfDge4b2L7NSyL9wQ1k9D66hkrbQon9NvguS77BhACW0OZAJVkoMT" ;
        String sessionKey = "XDRzE6NiP6pBaP1OC4N1Vg==";
        String iv = "WCFSrN1yx2k4o8qqTlmHRA==";

        for(int x=0;x<100;x++)
        System.out.println(decryptNew(encryptedData,
                sessionKey,iv));
    }

    public static String decryptData(String encryptDataB64, String sessionKeyB64, String ivB64) {
        return new String(decryptOfDiyIV(Base64.decode(encryptDataB64), Base64.decode(sessionKeyB64), Base64.decode(ivB64)));
    }

    private static final String KEY_ALGORITHM = "AES";
    private static final String ALGORITHM_STR = "AES/CBC/PKCS7Padding";
    private static Key key;
    private static Cipher cipher;

    private static void init(byte[] keyBytes) {
        // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
        int base = 16;
        if (keyBytes.length % base != 0) {
            int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
            keyBytes = temp;
        }
        // 初始化
        Security.addProvider(new BouncyCastleProvider());
        // 转化成JAVA的密钥格式
        key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
        try {
            // 初始化cipher
            cipher = Cipher.getInstance(ALGORITHM_STR, "BC");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解密方法
     *
     * @param encryptedData 要解密的字符串
     * @param keyBytes      解密密钥
     * @param ivs           自定义对称解密算法初始向量 iv
     * @return 解密后的字节数组
     */
    private static byte[] decryptOfDiyIV(byte[] encryptedData, byte[] keyBytes, byte[] ivs) {
        byte[] encryptedText = null;
        init(keyBytes);
        try {
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivs));
            encryptedText = cipher.doFinal(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedText;
    }


    private static  String decryptNew(String encryptedData, String sessionKey, String iv) throws Exception {
        String result = "";
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
        try {
            // 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                result = new String(resultByte, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}