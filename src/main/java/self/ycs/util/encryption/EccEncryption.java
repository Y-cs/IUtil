package self.ycs.util.encryption;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import self.ycs.util.coding.Base64Util;
import self.ycs.util.exception.EncryptionException;
import sun.security.ec.ECKeyPairGenerator;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Security;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author Y-cs
 * @date 2021/4/24 15:49
 */
public class EccEncryption {
    private final static String ENCRYPTION_ALGORITHM = "EC";
    private final static String ENCRYPTION_TRANSFORMATION = "ECIES";
    private final static String ENCRYPTION_PROVIDER = "BC";

    private final static int DEFAULT_KEY_SIZE = 256;

    static {
        //JDK中自带了椭圆曲线的签名，但是没有实现椭圆曲线的加密解密。不过bouncycastle库实现了，下面的代码需要bouncycastle库。
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 获取key
     * @return
     * @throws EncryptionException
     */
    public KeyPair getKey() throws EncryptionException {
        return getKey(DEFAULT_KEY_SIZE);
    }

    /**
     * 获取key
     * @param length 112-571  to   ECKeyPairGenerator
     * @return
     * @throws EncryptionException
     * @see ECKeyPairGenerator
     */
    public KeyPair getKey(int length) throws EncryptionException {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ENCRYPTION_ALGORITHM, ENCRYPTION_PROVIDER);
            keyPairGenerator.initialize(length);
            //生成钥匙对
            return keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            throw new EncryptionException(e);
        }
    }

    /**
     * 加密
     * @param str
     * @param keyPair
     * @return
     * @throws EncryptionException
     */
    public String encoding(String str, KeyPair keyPair) throws EncryptionException {
        return encoding(str, keyPair.getPublic().getEncoded());
    }

    /**
     * 解密
     * @param str
     * @param publicKey
     * @return
     * @throws EncryptionException
     */
    public String encoding(String str, byte[] publicKey) throws EncryptionException {
        try {
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);
            KeyFactory keyFactory = KeyFactory.getInstance(ENCRYPTION_ALGORITHM);
            ECPublicKey pubKey = (ECPublicKey) keyFactory.generatePublic(x509KeySpec);
            Cipher cipher = Cipher.getInstance(ENCRYPTION_TRANSFORMATION, ENCRYPTION_PROVIDER);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            byte[] codes = cipher.doFinal(Base64Util.decode(str));
            return Base64Util.encode(codes);
        } catch (Exception e) {
            throw new EncryptionException(e);
        }
    }

    public String decoding(String str, KeyPair keyPair) throws EncryptionException {
        return decoding(str, keyPair.getPrivate().getEncoded());
    }

    public String decoding(String str, byte[] privateKey) throws EncryptionException {
        try {
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
            KeyFactory keyFactory = KeyFactory.getInstance(ENCRYPTION_ALGORITHM);
            ECPrivateKey priKey = (ECPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
            Cipher cipher = Cipher.getInstance(ENCRYPTION_TRANSFORMATION, ENCRYPTION_PROVIDER);
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            byte[] codes = cipher.doFinal(Base64Util.decode(str));
            return Base64Util.encode(codes);
        } catch (Exception e) {
            throw new EncryptionException(e);
        }
    }

}
