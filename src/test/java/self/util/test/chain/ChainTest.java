package self.util.test.chain;

import org.junit.Test;
import self.ycs.util.chain.Chain;

/**
 * @author Y-cs
 * @date 2021/4/29 17:09
 */
public class ChainTest {

    static class A extends Chain<String> {
        @Override
        public void runNext(String s) {
            System.out.println("A:"+s);
            super.next(s);
        }
    }

    static class B extends Chain<String> {

        @Override
        public void runNext(String s) {
            System.out.println("B:"+s);
            super.next(s);
        }
    }

    static class C extends Chain<String> {
        @Override
        public void runNext(String s) {
            System.out.println("C:"+s);
            super.next(s);
        }
    }

    @Test
    public void test() {
        Chain<String> stringChain = new A();
        stringChain.setNextChain(new B()).setNextChain(new C());
        stringChain.runNext("run");

    }


}
