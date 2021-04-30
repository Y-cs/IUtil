package self.ycs.util.chain;

/**
 * @author Y-cs
 * @date 2021/4/29 17:02
 */
public abstract class Chain<T> {

    private Chain<T> nextChain;

    public Chain<T> setNextChain(Chain<T> nextChain) {
        this.nextChain = nextChain;
        return nextChain;
    }

    public void next(T t) {
        if (nextChain != null) {
            nextChain.runNext(t);
        }
    }

    public abstract void runNext(T t);

}
