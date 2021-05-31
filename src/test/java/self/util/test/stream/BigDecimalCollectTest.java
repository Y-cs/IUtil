package self.util.test.stream;

import org.junit.Before;
import org.junit.Test;
import self.ycs.util.stream.collect.BigDecimalCollect;
import self.ycs.util.stream.function.ToBigDecimalFunction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Y-cs
 * @date 2021/5/29 17:59
 */
public class BigDecimalCollectTest {

    private List<BigDecimal> bigDecimals;

    @Before
    public void before(){
        bigDecimals=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            bigDecimals.add(new BigDecimal(new Random().nextInt(10)));
        }
        System.out.println(bigDecimals);
    }

    @Test
    public void sumTest(){
        BigDecimal sum = bigDecimals.stream().collect(BigDecimalCollect.sum());
        System.out.println(sum);
    }

    @Test
    public void avgTest(){
        BigDecimal avg = bigDecimals.stream().collect(BigDecimalCollect.avg());
        System.out.println(avg);
    }

    @Test
    public void minTest(){
        BigDecimal avg = bigDecimals.stream().collect(BigDecimalCollect.min());
        System.out.println(avg);
    }

    @Test
    public void maxTest(){
        BigDecimal avg = bigDecimals.stream().collect(BigDecimalCollect.max());
        System.out.println(avg);
    }

}
