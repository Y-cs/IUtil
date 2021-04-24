package self.ycs.util.exception;

/**
 * @author Y-cs
 * @date 2021/4/24 14:55
 */
public class EncryptionException extends RuntimeException{

    public EncryptionException(){

    }

    public EncryptionException(Exception e) {
        super(e);
    }
}
