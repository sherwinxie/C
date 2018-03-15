package MD5;


import java.nio.charset.Charset;
import java.security.MessageDigest;

public class MD5Test {
    public static void main(String[] args) {

        String text = "Sherwin131231";
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");//SHA-256  OR SHA-512
            byte[] input = md5.digest(text.getBytes(Charset.forName("UTF-8")));
            md5.update(input);
            String md5_Hexresult = bytesToHex(input);
//            for (byte output : input) {
//                System.out.print(output);
//            }
            System.out.println(md5_Hexresult);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //将字节数组转化为16进制字符串
    public static String bytesToHex(byte[] bytes) {
        StringBuffer md5str = new StringBuffer();
        //把数组每一字节换成16进制连成md5字符串
        int digital;
        for (int i = 0; i < bytes.length; i++) {
            //System.out.println("，   "+bytes[i]);
            digital = bytes[i];
            if(digital < 0) {  //16及以上的数转16进制是两位
                digital += 256;
            }
            if(digital < 16){
                md5str.append("0");//如果是0~16之间的数的话则变成0X
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString().toUpperCase();
    }

}