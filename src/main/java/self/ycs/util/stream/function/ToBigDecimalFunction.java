package self.ycs.util.stream.function;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * @author Y-cs
 * @date 2021/5/21 14:39
 */
public interface ToBigDecimalFunction<T> {

    BigDecimal applyAsBigDecimal(T value);


    static ToBigDecimalFunction<BigDecimal> identity() {
        return t -> t;
    }
}
