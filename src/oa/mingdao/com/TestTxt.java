package oa.mingdao.com;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import org.guiceside.commons.OKHttpUtil;
import org.guiceside.commons.lang.DateFormatUtil;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhenjiawang on 2016/12/24.
 */
public class TestTxt {

    public static String convertMD5(String inStr){

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++){
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;

    }
    public static void main(String args[]) throws Exception {
        // endpoint以杭州为例，其它region请按实际情况填写
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
// accessKey请登录https://ak-console.aliyun.com/#/查看
        String accessKeyId = "Y6G2pVpsa2wbloBQ";
        String accessKeySecret = "dlH4KDRz6poW4FCe6JaL8bOFifYYRn";
        System.out.println(DateFormatUtil.parse("2017-04-15 12:14:40",DateFormatUtil.YMDHMS_PATTERN).getTime());
        Map<String,String> stringStringMap=new HashMap<>();
        stringStringMap.put("usableDate","20170411");
        stringStringMap.put("bankSeqNo","277468222414848");
        stringStringMap.put("status","1");
        stringStringMap.put("remarks","test");
        String str=OKHttpUtil.post("http://182.92.96.194:8080/v1/public/xl/updateStatus",stringStringMap);
        System.out.println(str);
//        String str = "啊实打实 wqwqwwwwws";
//        try {
//            String dir =
//                    "/project/" + File.separator + "系统日志";
//            File fi = new File(dir);
//            if (!fi.exists()) {
//                fi.mkdirs();
//            }
//            Date date = new Date();
//            String time = new SimpleDateFormat("yyyy-MM-dd").format(date);
//            dir = fi.getPath() + File.separator + time + ".txt";
//            fi = new File(dir);
//            if (!fi.exists() && !fi.isDirectory()) {
//                fi.createNewFile();
//                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fi)));
//                pw.println(str);
//                pw.close();
//            } else {
//                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fi, true)));
//                pw.println(str);
//                pw.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


//        File f = new File("a.txt");
//        FileOutputStream fop = new FileOutputStream(f);
//        // 构建FileOutputStream对象,文件不存在会自动新建
//
//        OutputStreamWriter writer = new OutputStreamWriter(fop, "UTF-8");
//        // 构建OutputStreamWriter对象,参数可以指定编码,默认为操作系统默认编码,windows上是gbk
//        StringBuilder sb=new StringBuilder();
//        sb.append("中文输入");
//        sb.append("\r\n");
//        sb.append("English");
//
//
//        writer.append(sb.toString());
//        // 写入到缓冲区
//
//
//        // 刷新缓存冲,写入到文件,如果下面已经没有写入的内容了,直接close也会写入
//
//        writer.close();
//        //关闭写入流,同时会把缓冲区内容写入文件,所以上面的注释掉
//
//        fop.close();
//        // 关闭输出流,释放系统资源
//
//        FileInputStream fip = new FileInputStream(f);
//        // 构建FileInputStream对象
//
//        // InputStreamReader reader = new InputStreamReader(fip, "UTF-8");
//        // 构建InputStreamReader对象,编码与写入相同
//
//        // 创建OSSClient实例
//        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
//        // 创建上传Object的Metadata
//        ObjectMetadata meta = new ObjectMetadata();
//// 设置上传内容类型
//        meta.setContentType("text/plain");
//// 上传文件流
//        //InputStream inputStream = new FileInputStream("localFile");
//        ossClient.putObject("o2btc-file", "balanceWithdraw.txt", fip);
//// 关闭client
//        ossClient.shutdown();
//
////        while (reader.ready()) {
////            sb.append((char) reader.read());
////            // 转成char加到StringBuffer对象中
////        }
////        System.out.println(sb.toString());
////        reader.close();
//        // 关闭读取流
//
//        fip.close();
//
//        String md5 = BinaryUtil.encodeMD5(sb.toString().getBytes());
//        // 关闭输入流,释放系统资源
//        System.out.println(fileMD5("a.txt"));

//        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
//
//        OSSObject ossObject = ossClient.getObject("o2btc-file", "balanceWithdraw.txt");
//
//        File file = new File("/project/balanceWithdraw.txt");
//
//        File disFile=new File("dis.txt");
//
//        ossClient.getObject(new GetObjectRequest("o2btc-file", "balanceWithdraw.txt"), disFile);
//
//
////        BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent(),"gb18030"));
////        System.out.println(fileMD5(ossObject.getObjectContent()));
////        StringBuilder sb=new StringBuilder();
////        while (true) {
////            String line = reader.readLine();
////            if (line == null) break;
////            sb.append(line);
////            System.out.println("\n" + line);
////        }
////        reader.close();
//// 关闭client
//        ossClient.shutdown();
//
//        //System.out.println(sb.toString().length());
//        //System.out.println(sb.toString().getBytes("gb2312").length);
//
//        System.out.println(fileMD5(disFile));
//
//        if(disFile.exists()){
//            System.out.println(disFile.getAbsolutePath());
//            disFile.deleteOnExit();
//        }
        //System.out.println(BinaryUtil.encodeMD5(sb.toString().getBytes("gb2312")));
    }

    public static String byteArrayToHex(byte[] byteArray) {

        // 首先初始化一个字符数组，用来存放每个16进制字符

        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};


        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））

        char[] resultCharArray = new char[byteArray.length * 2];


        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去

        int index = 0;

        for (byte b : byteArray) {

            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];

            resultCharArray[index++] = hexDigits[b & 0xf];

        }


        // 字符数组组合成字符串返回

        return new String(resultCharArray);
    }


    public static String fileMD5(File file) throws Exception {


        // 缓冲区大小（这个可以抽出一个参数）

        int bufferSize = 256 * 1024;

        FileInputStream fileInputStream = new FileInputStream(file);

        DigestInputStream digestInputStream = null;


        try {

            // 拿到一个MD5转换器（同样，这里可以换成SHA1）

            MessageDigest messageDigest = MessageDigest.getInstance("MD5");


            // 使用DigestInputStream

           // fileInputStream = new FileInputStream(inputStream);

            digestInputStream = new DigestInputStream(fileInputStream, messageDigest);


            // read的过程中进行MD5处理，直到读完文件

            byte[] buffer = new byte[bufferSize];

            while (digestInputStream.read(buffer) > 0) ;


            // 获取最终的MessageDigest

            messageDigest = digestInputStream.getMessageDigest();


            // 拿到结果，也是字节数组，包含16个元素

            byte[] resultByteArray = messageDigest.digest();


            // 同样，把字节数组转换成字符串

            return byteArrayToHex(resultByteArray);


        } catch (NoSuchAlgorithmException e) {

            return null;

        } finally {

            try {

                digestInputStream.close();

            } catch (Exception e) {

            }

            try {

                fileInputStream.close();

            } catch (Exception e) {

            }

        }

    }

}
