package self.ycs.util.encryption;

import self.ycs.util.exception.EncryptionException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * @author Y-cs
 * @date 2021/4/24 14:14
 */
public interface Encryption {


    /**
     * 获取密钥
     * @return
     * @throws EncryptionException
     */
    Key getKey() throws EncryptionException;

    /**
     * 获取密钥
     * @param length
     * @return
     * @throws EncryptionException
     */
    Key getKey(int length) throws EncryptionException;

    /**
     * 加密
     * @param str
     * @param key
     * @return
     * @throws EncryptionException
     */
    String encoding(String str, Key key) throws EncryptionException;

    /**
     * 解码
     * @param str
     * @param key
     * @return
     * @throws EncryptionException
     */
    public String decoding(String str, Key key) throws EncryptionException;

}
