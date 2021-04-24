package self.ycs.util.encryption;

import self.ycs.util.coding.Base64Util;
import self.ycs.util.exception.EncryptionException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;

/**
 * @author Y-cs
 * @date 2021/4/24 14:06
 */
public class AesEncryption {

    public static final int LENGTH128 = 128;
    public static final int LENGTH192 = 192;
    public static final int LENGTH256 = 256;

    private final static String CODING_MODEL = "AES";

    /**
     * 获取一个key
     *
     * @return
     * @throws EncryptionException
     */
    public Key getKey() throws EncryptionException {
        return getKey(null, 0);
    }

    public Key getKey(int length) throws EncryptionException {
        return getKey(null, length);
    }

    public Key getKey(String str) throws EncryptionException {
        return getKey(str, 0);
    }

    /**
     * 获取key
     *
     * @param length
     * @return
     * @throws EncryptionException
     */
    public Key getKey(String str, int length) throws EncryptionException {
        try {
            // 生成key
            int keyLength = length == 0 ? LENGTH128 : length;
            //构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keyGenerator = KeyGenerator.getInstance(CODING_MODEL);
            //生成一个128位的随机源,根据传入的字节数组
            if (str != null) {
                keyGenerator.init(keyLength, new SecureRandom(str.getBytes(StandardCharsets.UTF_8)));
            } else {
                keyGenerator.init(keyLength);
            }
            //产生原始对称密钥
            SecretKey secretKey = keyGenerator.generateKey();
            //获得原始对称密钥的字节数组
            byte[] keyBytes = secretKey.getEncoded();
            // key转换,根据字节数组生成AES密钥
            return new SecretKeySpec(keyBytes, CODING_MODEL);
        } catch (Exception e) {
            throw new EncryptionException(e);
        }
    }


    /**
     * 加密
     *
     * @param str
     * @param key
     * @return
     * @throws EncryptionException
     */
    public String encoding(String str, Key key) throws EncryptionException {
        try {
            Cipher cipher = Cipher.getInstance(CODING_MODEL);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //将加密并编码后的内容解码成字节数组
            byte[] result = cipher.doFinal(Base64Util.decode(str));
            return Base64Util.encode(result);
        } catch (Exception e) {
            throw new EncryptionException(e);
        }
    }


    /**
     * 解密
     *
     * @param str
     * @param key
     * @return
     * @throws EncryptionException
     */
    public String decoding(String str, Key key) throws EncryptionException {
        try {
            Cipher cipher = Cipher.getInstance(CODING_MODEL);
            //初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher.doFinal(Base64Util.decode(str));
            return Base64Util.encode(result);
        } catch (Exception e) {
            throw new EncryptionException(e);
        }
    }
}
