package self.ycs.util.stream.collect;

import self.ycs.util.stream.function.ToBigDecimalFunction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Y-cs
 * @date 2021/5/29 10:17
 */
public class BigDecimalCollect {

    public static Collector<BigDecimal, BigDecimal[], BigDecimal> sum() {
        return BigDecimalCollect.sum(ToBigDecimalFunction.identity());
    }

    public static <T> Collector<T, BigDecimal[], BigDecimal> sum(ToBigDecimalFunction<? super T> mapper) {
        return new CollectExtend<>(
                () -> new BigDecimal[1],
                (a, t) -> {
                    if (a[0] == null) {
                        a[0] = BigDecimal.ZERO;
                    }
                    BigDecimal decimal = mapper.applyAsBigDecimal(t);
                    if (decimal != null) {
                        a[0] = a[0].add(decimal);
                    }
                },
                (a, b) -> {
                    a[0] = a[0].add(b[0]);
                    return a;
                },
                a -> a[0], CollectInfo.CH_NO_ID);
    }

    public static Collector<BigDecimal, ?, BigDecimal> avg() {
        return BigDecimalCollect.avg(ToBigDecimalFunction.identity());
    }

    public static <T> Collector<T, ?, BigDecimal> avg(ToBigDecimalFunction<? super T> mapper) {
        return BigDecimalCollect.avg(mapper, 10, RoundingMode.HALF_UP);
    }

    public static <T> Collector<T, ?, BigDecimal> avg(ToBigDecimalFunction<? super T> mapper, int scale) {
        return BigDecimalCollect.avg(mapper, scale, RoundingMode.HALF_UP);
    }

    public static <T> Collector<T, ?, BigDecimal> avg(ToBigDecimalFunction<? super T> mapper, int scale, RoundingMode roundingMode) {
        return new CollectExtend<T, List<BigDecimal>, BigDecimal>(
                LinkedList::new,
                (a, t) -> {
                    BigDecimal decimal = mapper.applyAsBigDecimal(t);
                    if (decimal != null) {
                        a.add(decimal);
                    }
                },
                (a, b) -> {
                    a.addAll(b);
                    return a;
                },
                a -> {
                    if (a.size() == 0) {
                        return BigDecimal.ZERO;
                    }
                    BigDecimal collect = a.stream().collect(BigDecimalCollect.sum());
                    return collect.divide(BigDecimal.valueOf(a.size()), scale, roundingMode);
                }, CollectInfo.CH_NO_ID);
    }

    public static Collector<BigDecimal, ?, BigDecimal> min() {
        return BigDecimalCollect.min(ToBigDecimalFunction.identity());
    }

    public static <T> Collector<T, ?, BigDecimal> min(ToBigDecimalFunction<? super T> mapper) {
        return new CollectExtend<>(
                () -> new BigDecimal[1],
                (a, t) -> {
                    BigDecimal decimal = mapper.applyAsBigDecimal(t);
                    if (decimal != null) {
                        if (a[0] == null) {
                            a[0] = decimal;
                        } else {
                            a[0] = a[0].compareTo(decimal) < 0 ? a[0] : decimal;
                        }
                    }
                },
                (a, b) -> a[0].compareTo(b[0]) < 0 ? a : b,
                a -> a[0], CollectInfo.CH_NO_ID);
    }

    public static Collector<BigDecimal, ?, BigDecimal> max() {
        return BigDecimalCollect.max(ToBigDecimalFunction.identity());
    }

    public static <T> Collector<T, ?, BigDecimal> max(ToBigDecimalFunction<? super T> mapper) {
        return new CollectExtend<>(
                () -> new BigDecimal[1],
                (a, t) -> {
                    BigDecimal decimal = mapper.applyAsBigDecimal(t);
                    if (decimal != null) {
                        if (a[0] == null) {
                            a[0] = decimal;
                        } else {
                            a[0] = a[0].compareTo(decimal) > 0 ? a[0] : decimal;
                        }
                    }
                },
                (a, b) -> a[0].compareTo(b[0]) > 0 ? a : b,
                a -> a[0], CollectInfo.CH_NO_ID);
    }

}
