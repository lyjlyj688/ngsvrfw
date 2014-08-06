package person.lyjyy.core.worker;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by yujie.li on 14-7-22.
 */
public abstract class WorkThread<T> implements Runnable{

    protected LinkedBlockingQueue<T> queue;


    public void add(T t) {
        queue.add(t);
    }
}
