package self.util.test.encryption;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.junit.Test;
import self.ycs.util.encryption.AesEncryption;
import self.ycs.util.encryption.EccEncryption;

import java.security.Key;
import java.security.KeyPair;

/**
 * @author Y-cs
 * @date 2021/4/24 14:39
 */
public class EccTest {

    @Test
    public void encoding() {
        EccEncryption eccEncryption = new EccEncryption();
        KeyPair key = eccEncryption.getKey();
        String yuanchangshuai = eccEncryption.encoding("yuanchangshuai", key);
        System.out.println(yuanchangshuai);
        String decoding = eccEncryption.decoding(yuanchangshuai, key);
        System.out.println(decoding);
    }

}
