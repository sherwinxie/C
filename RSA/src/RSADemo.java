import java.security.*;
import java.security.interfaces.*;
import java.io.*;
import java.math.*;
public class RSADemo {
    /**
     *  生成密钥并保存的函数
     *  将公钥和私钥以文件流的形式保存在当前文件目录下生成密钥并保存函数
     *  将公钥和私钥以文件流的形式保存在当前文件目录下
     */
    public static void generateKey() {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(1024);//RSA的加密位设为1024位
            KeyPair kp = kpg.genKeyPair();
            PublicKey pbkey = kp.getPublic();
            PrivateKey prkey = kp.getPrivate();
            // 保存公钥  
            FileOutputStream f1 = new FileOutputStream("pubkey.txt");
            ObjectOutputStream b1 = new ObjectOutputStream(f1);
            b1.writeObject(pbkey);
            // 保存私钥  
            FileOutputStream f2 = new FileOutputStream("privatekey.txt");
            ObjectOutputStream b2 = new ObjectOutputStream(f2);
            b2.writeObject(prkey);
        } catch (Exception e) {
        }
    }

    /**
     * encrypt()加密函数，以BigInteger类型储存便于提取出公钥中的（e，N）
     * @throws Exception
     */
    public static void encrypt(String msg) throws Exception {
        String content = msg;
        // 获取公钥及参数e,n
        FileInputStream f = new FileInputStream("pubkey.txt");
        ObjectInputStream b = new ObjectInputStream(f);
        RSAPublicKey pbk = (RSAPublicKey) b.readObject();
        BigInteger e = pbk.getPublicExponent();//获得公钥的指数
        BigInteger n = pbk.getModulus();
        System.out.println("公钥 e= " + e+"\n"+"n= " + n);
        // 获取明文m
        byte ptext[] = content.getBytes("UTF-8");
        BigInteger m = new BigInteger(ptext);
        // 计算密文c
        BigInteger c = m.modPow(e, n);
        System.out.println("加密后为： " + c);
        // 保存密文
        String cs = c.toString();
        BufferedWriter out =
                new BufferedWriter(
                        new OutputStreamWriter(new FileOutputStream("encrypt.txt")));
        out.write(cs, 0, cs.length());
        out.close();
    }

    /**
     * decrypt()解密函数，以BigInteger类型储存便于提取出公钥中的（d，N）
     * @throws Exception
     */
    public static void decrypt() throws Exception {
        // 读取密文  
        BufferedReader in =
                new BufferedReader(
                        new InputStreamReader(new FileInputStream("encrypt.txt")));

        String ctext = in.readLine();
        BigInteger c = new BigInteger(ctext);
        // 读取私钥  
        FileInputStream f = new FileInputStream("privatekey.txt");
        ObjectInputStream b = new ObjectInputStream(f);
        RSAPrivateKey prk = (RSAPrivateKey) b.readObject();
        BigInteger d = prk.getPrivateExponent();
        // 获取私钥参数及解密  
        BigInteger n = prk.getModulus();
        System.out.println("私钥"+"d= "+ d+"\n"+" n= " + n);
        BigInteger m = c.modPow(d, n);
        // 显示解密结果  
        System.out.println("解密后大整数为： " + m);
        byte[] mContent = m.toByteArray();
        String s = new String(mContent,"UTF-8");
        System.out.println("\n解密后的明文:" + s);

    }
    public static void main(String args[]) {
        try {
            String contentTest = "RSA算法加密的原文加中文标点符号，。~￥ ---Sherwin";
//            String contentTest = "Totally English and ,.@$! ---Sherwin";
            generateKey();
            encrypt(contentTest);
            decrypt();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}  