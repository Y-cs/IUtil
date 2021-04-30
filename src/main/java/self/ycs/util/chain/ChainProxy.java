package self.ycs.util.chain;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.Proxy;

import java.lang.reflect.Method;

/**
 * @author Y-cs
 * @date 2021/4/29 17:35
 */
public class ChainProxy implements InvocationHandler {

    private ChainProxy chainProxy;

    public <T extends ChainProxy> void setNext(T t) {
        chainProxy = t;
        Enhancer.create(t.getClass(), this);
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        method.invoke(o, objects);
        return chainProxy;
    }
}
