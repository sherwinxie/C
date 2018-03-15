package RSASignature;

import java.util.Map;

public class RSATester {
    static String publicKey;
    static String privateKey;

    static {
        try {
            Map<String, Object> keyMap = RSAUtils.genKeyPair();
            publicKey = RSAUtils.getPublicKey(keyMap);
            privateKey = RSAUtils.getPrivateKey(keyMap);
            System.out.println("公钥: \n\r" + publicKey);
            System.out.println("私钥： \n\r" + privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        test();
        testSign();
    }

    static void test() throws Exception {
        System.out.println("-------------RSA公钥加密-----------");
        String source = "这是一行要进行RSA加密的原始数据sherwin1111111111";
        System.out.println("\r加密前文字：\r\n" + source);
        byte[] data = source.getBytes();
        //公钥加密
        byte[] encodedData = RSAUtils.encryptByPublicKey(data, publicKey);
        System.out.println("加密后文字：\r\n" + new String(bytesToHex(encodedData)));
        //私钥解密
        byte[] decodedData = RSAUtils.decryptByPrivateKey(encodedData, privateKey);
        String target = new String(decodedData);
        System.out.println("解密后文字: \r\n" + target);
    }

    static void testSign() throws Exception {
        System.out.println("-------------RSA私钥加密-----------");
        String source = "测试RSA+数字签名的原始数据sherwin222222222222";
        System.out.println("加密前文字：\r\n" + source);
        byte[] data = source.getBytes();
        //私钥加密
        byte[] encodedData = RSAUtils.encryptByPrivateKey(data, privateKey);
        System.out.println("加密后：\r\n" + new String(bytesToHex(encodedData)));
        //公钥解密
        byte[] decodedData = RSAUtils.decryptByPublicKey(encodedData, publicKey);
        String target = new String(decodedData);
        System.out.println("解密后: \r\n" + target);

        System.out.println("-------------私钥签名——公钥验证签名-----------");
        String sign = RSAUtils.sign(encodedData, privateKey);
        System.out.println("私钥签名:\r\n" + sign);
        boolean status = RSAUtils.verify(encodedData, publicKey, sign);
        System.out.println("公钥验证结果:\r\n" + status);
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
