package self.util.test.struct;

import org.junit.Test;
import self.ycs.util.struct.Btree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author Y-cs
 * @date 2021/4/30 14:34
 */
public class BtreeTest {

    @Test
    public void test1() {
        Btree<Integer, Integer> btree = new Btree<>(2, 2, Integer::compare);
//        Random random = new Random();
        int[] ints = {66, 59, 6, 32, 7, 45,32,66,11,3,99,228,12,45,67,356,53};
        for (int i = 0; i < ints.length; i++) {
            btree.put(ints[i], i);
        }
        System.out.println(btree);
    }


    @Test
    public void testList() {
        ArrayList<Integer> integers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        List<Integer> integers1 = integers.subList(0, 3);
        List<Integer> integers2 = new ArrayList<>(integers.subList(3, integers.size()));
        integers1.add(21);
        System.out.println(integers);
        System.out.println(integers1);
        System.out.println(integers2);
    }

    @Test
    public void testList1() {
        ArrayList<Integer> integers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        integers.add(2, 21);
        System.out.println(integers);
    }

    @Test
    public void testInt() {
        int i = Integer.MAX_VALUE;
        int i1 = i >> 31;
        System.out.println(i1);
    }

}
