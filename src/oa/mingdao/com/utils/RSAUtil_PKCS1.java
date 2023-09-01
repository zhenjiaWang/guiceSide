package oa.mingdao.com.utils;

import sun.security.util.DerInputStream;
import sun.security.util.DerValue;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.util.Base64;

public class RSAUtil_PKCS1 {
    private static final String privKeyPEM = "-----BEGIN PRIVATE KEY-----\n" +
            "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDQmFLHbsfKqagq\n" +
            "2sRnb5YthppMeCtM8gmIJOpbvyd1VG30zm71zH5XSNZrmvxi7JVZJa1pCjLlz+Sp\n" +
            "nxfjS1gqmO2Tpn47A06uxrOG5H8YpyLoc2lpJ0bvbGQqS9WfNMMjEadq2h+zWZ0j\n" +
            "p+L+wO19StvfrQi/LowZjhnNSabti2bNpMKIVb52haxcjSqiSsyYIEqzWnzE/tBr\n" +
            "nB4jAE3LuC27bVAKQ3VU57whNmUIJwRL3sxEFL2OfmEGQBmbcJhPh+YomFyRyMhT\n" +
            "UAcaLS5SEoSUnTYTbfkOPmbwrCsVvwLCtDFkSyp4AJfFgbR2t55bXi825Y0bNc2Q\n" +
            "or6thiwZAgMBAAECggEBALDuk0Bwee0wLOF0wKVP5B1nj9ePEoNoxacCUPtTT7/Y\n" +
            "xo+gzkOVmhClSqLpSm0Kab+9ErMHIJcH2Nf7h0KXdQhkwRe9YFLkAkiDaGHhzOB3\n" +
            "XEe5JU85LpUE5o1t15v8IAzN+6jJrZvuRjipPgSCVq8w4QF//uxTKii+/KnBcGMJ\n" +
            "kt5uETd4uzcjLP0/4tLs6atQE66l3O5s81XSShTYhY7mGbz/wwIUB7u5t82Zzm84\n" +
            "py5aqSeBOYveLSpRmy3zGJIDXU7h9oo0cyp9Fl0u6RBtZPUdiJBB7hbEqeKCL0SZ\n" +
            "NSVFDpygZDWh8En1gFBW+jiM80X39UvATmpfCaPGXBECgYEA+ykWfFU7qxq+E5zL\n" +
            "SOwKGOvSJvCesrKgonQfyN4E9jpOHyacgviDwE59crH0LACKzu2gZ3ZWMr+TGtAM\n" +
            "/x03tEyGYdEq02G21f/UtS2b7F7lFzHlfzGzq2MeM4GvmDtzoV2ASqo7c5Qc7KCY\n" +
            "wLJXPgN1xnjzJ/r5NPVVl0OifL0CgYEA1J1FROhdJcjd01W1cCJR+lk0f3AurYqB\n" +
            "SB3EHFDki1jmh1paxN9tZ0MmxZOFpKUB25kw/X5tPcWHvvVgoz9Rcq00y3DDvA5s\n" +
            "orhtWTL4iuLNwtjacHR4lv1bnVNKW2I9E+ouryEcz/vlTJS2SMQ6TpQe00jHkwvF\n" +
            "3QX9FTNo2I0CgYEAqYA4OZ64DyiUIN5PXx5VTi5QfJElOQpOWHSwQrp7dMUyldlU\n" +
            "BRu8ptsR1Ib+d3fCNhaui0SUtdkOaSkBrGZGgrqmcntcQJ4Qa2olXkSylxoP19CH\n" +
            "xoh/beoNpssd+0ocJknY3Ar45m6N+rADhwCU2jgxkslrqI67Ap+TrOsrTK0CgYBQ\n" +
            "3IOEwo7ymONsxdMck6D0AstJkIPgMqYapF96mXViXexHHCEbcRItzMdJK2MPjEYZ\n" +
            "m7ibIGMxtBd9k9suGx3B7IgAu6Flu2KrvFKyMwV95OZ2rXzeeh2G51LZkdzcxo8O\n" +
            "LOmWZ5SKsXWy23g8Uo0OZ50VpD9q+HZHKkjnsEa23QKBgCZRM2S/bCmHE9vHz+5l\n" +
            "NUB8fHATzhxDndJ8abU1ABfn/EuIcssdPxN7MlzFozqK7SMP3AJWwU4KnpAIHG18\n" +
            "C/xq2zAUkg0pJkzxWKOlyPszdzBq7zg/bGkSumh0bzDpxLFPexQsXxTSt3+WFVz5\n" +
            "F9BFyXYa5ngu/znrlLup998R\n" +
            "-----END PRIVATE KEY-----";

    // 用此方法先获取秘钥
    public static String getPrivateKey(String encrypt_random_key) throws Exception {

        String privKeyPEMnew = privKeyPEM.replaceAll("\\n", "").replace("-----BEGIN RSA PRIVATE KEY-----", "").replace("-----END RSA PRIVATE KEY-----", "");
        byte[] bytes = Base64.getDecoder().decode(privKeyPEMnew);

        DerInputStream derReader = new DerInputStream(bytes);
        DerValue[] seq = derReader.getSequence(0);
        BigInteger modulus = seq[1].getBigInteger();
        BigInteger publicExp = seq[2].getBigInteger();
        BigInteger privateExp = seq[3].getBigInteger();
        BigInteger prime1 = seq[4].getBigInteger();
        BigInteger prime2 = seq[5].getBigInteger();
        BigInteger exp1 = seq[6].getBigInteger();
        BigInteger exp2 = seq[7].getBigInteger();
        BigInteger crtCoef = seq[8].getBigInteger();

        RSAPrivateCrtKeySpec keySpec = new RSAPrivateCrtKeySpec(modulus, publicExp, privateExp, prime1, prime2, exp1, exp2, crtCoef);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        // 64位解码加密后的字符串
        byte[] inputByte = Base64.getMimeDecoder().decode(encrypt_random_key);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        String s = new String(cipher.doFinal(inputByte));

        return s;
    }
}
