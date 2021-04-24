package self.ycs.util.coding;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * @author Y-cs
 * @date 2021/4/24 16:56
 */
public class Base64Util {


    private final static BASE64Decoder BASE64_DECODER = new BASE64Decoder();
    private final static BASE64Encoder BASE64_ENCODER = new BASE64Encoder();

    public static String encode(byte[] bs) {
        return BASE64_ENCODER.encodeBuffer(bs);
    }

    public static byte[] decode(String str) throws IOException {
        return BASE64_DECODER.decodeBuffer(str);
    }


}
