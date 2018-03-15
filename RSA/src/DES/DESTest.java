package DES;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DESTest {


    public static void main(String[] args) {
        // TODO Auto-generated method stub

        String content="sherwinxie1312";
        String key="01234567";

        System.out.println("加密前内容为："+content+" 十六进制为："+byteToHexString(content.getBytes()));
        byte[] encrypted=DES_CBC_Encrypt(content.getBytes(), key.getBytes());
        System.out.println("加密后："+byteToHexString(encrypted));
        byte[] decrypted=DES_CBC_Decrypt(encrypted, key.getBytes());
        System.out.println("解密后内容为："+content+" 十六进制为："+byteToHexString(decrypted));
    }


    public static byte[] DES_CBC_Encrypt(byte[] content, byte[] keyBytes){
        try {
            DESKeySpec keySpec=new DESKeySpec(keyBytes);
            SecretKeyFactory keyFactory=SecretKeyFactory.getInstance("DES");
            SecretKey key=keyFactory.generateSecret(keySpec);// 生成密钥

            Cipher cipher=Cipher.getInstance("DES/CBC/PKCS5Padding");//常见的模式有四种：ECB（电码本模式），CBC（加密块链模式），OFB（输出反馈模式），CFB（加密反馈模式）
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(keySpec.getKey()));
            byte[] result=cipher.doFinal(content);//加密
            return result;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("Exception:"+e.toString());
        }
        return null;
    }

    public static byte[] DES_CBC_Decrypt(byte[] content, byte[] keyBytes){
        try {
            DESKeySpec keySpec=new DESKeySpec(keyBytes);
            SecretKeyFactory keyFactory=SecretKeyFactory.getInstance("DES");
            SecretKey key=keyFactory.generateSecret(keySpec);

            Cipher cipher=Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(keyBytes));
            byte[] result=cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("exception:"+e.toString());
        }
        return null;
    }

    public static String byteToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length);
        String sTemp;
        for (int i = 0; i < bytes.length; i++) {
            sTemp = Integer.toHexString(0xFF & bytes[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

  
}