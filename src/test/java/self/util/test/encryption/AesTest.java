package self.util.test.encryption;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.junit.Test;
import self.ycs.util.encryption.AesEncryption;
import self.ycs.util.encryption.Encryption;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author Y-cs
 * @date 2021/4/24 14:39
 */
public class AesTest {

    @Test
    public void encoding() {
        AesEncryption aesEncryption = new AesEncryption();
        Key key = aesEncryption.getKey(AesEncryption.LENGTH128);
        System.out.println(Base64.encode(key.getEncoded()));
        String yuanchangshuai = aesEncryption.encoding("yuanchangshuai", key);
        System.out.println(yuanchangshuai);
        String decoding = aesEncryption.decoding(yuanchangshuai, key);
        System.out.println(decoding);
    }

    @Test
    public void getKey() throws NoSuchAlgorithmException {
        //输入字符一致时返回的key一致
        //1UyM9SkO3/OumSOgwfXqgA==              aaaa
        //1UyM9SkO3/OumSOgwfXqgA==              aaaa
        //输入的字符不一致时返回的不一致
        //CC3oId8JsbUAnRK47lzCbg==              aaaaa
        //输入为""也一致
        //vhvewKp0tNyweZQ+cFKAlg==
        //vhvewKp0tNyweZQ+cFKAlg==
        //不输入的时候不一致
        //em/e/vjZjfWDnjxnckcKmQ==
        //vclXqoucJ6+GJc+5VzMndA==
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        //生成一个128位的随机源,根据传入的字节数组
        keyGenerator.init(128);
        //产生原始对称密钥
        SecretKey secretKey = keyGenerator.generateKey();
        //获得原始对称密钥的字节数组
        byte[] keyBytes = secretKey.getEncoded();
        // key转换,根据字节数组生成AES密钥
        SecretKeySpec aes = new SecretKeySpec(keyBytes, "AES");
        System.out.println(Base64.encode(aes.getEncoded()));
    }

}
