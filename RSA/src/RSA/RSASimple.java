package RSA;

public class RSASimple {
    /**
     * 加密、解密算法
     *
     * @param key   E 公钥 或 D  密钥
     * @param message 数据
     * @return
     *
     *
     * 计算公钥和密钥
     * 假设p = 3、q = 17（p，q都是素数即可。），则N = pq = 51；
     * r = (p-1)(q-1) = (3-1)(17-1) = 32；
     * 根据模反元素的计算公式，我们可以得出，e·d ≡ 1 (mod 32),即e·d = 32n+1 (n为正整数)；我们假设n=1，则e·d = 33。e、d为正整数，并且e与r互质，则e = 3，d = 11。（两个数交换一下也可以。）
     * 到这里，公钥和密钥已经确定。公钥为(N, e) = (51, 3)，密钥为(N, d) = (51, 11)。
     */

    public static long rsa(int baseNum, int key, long message) {
        if (baseNum < 1 || key < 1) {
            return 0L;
        }
        //加密或者解密之后的数据
        long rsaMessage = 0L;

        //加密核心算法
        rsaMessage = Math.round(Math.pow(message, key)) % baseNum;//2^3=8 % 51
        return rsaMessage;
    }

    public static void main(String[] args) {
        //基数
        int baseNum = 3 * 17;//51
        //公钥
        int keyE = 3;
        //密钥
        int keyD = 11;
        //未加密的数据
        long msg = 2L;
        //加密后的数据
        long encodeMsg = rsa(baseNum, keyE, msg);
        //解密后的数据
        long decodeMsg = rsa(baseNum, keyD, encodeMsg);

        System.out.println("加密前：" + msg);
        System.out.println("加密后：" + encodeMsg);
        System.out.println("解密后：" + decodeMsg);
        System.out.println(""+ Math.round(Math.pow(8, 11)) % 51);
    }
}
