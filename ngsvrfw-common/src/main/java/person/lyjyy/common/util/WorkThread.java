package person.lyjyy.common.util;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by yujie.li on 14-7-22.
 */
public abstract class WorkThread<T> implements Runnable{

    private LinkedBlockingQueue<T> queue;

    protected abstract void before(T t);
    protected abstract void exec(T t);
    protected abstract void after(T t);

    public void run() {
        while(true) {
            try {
                T t = queue.take();
                before(t);
                exec(t);
                after(t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void add(T t) {
        queue.add(t);
    }
}
