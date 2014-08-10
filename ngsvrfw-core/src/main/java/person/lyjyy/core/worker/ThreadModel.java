package person.lyjyy.core.worker;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by yujie.li on 14-7-22.
 */
public abstract class ThreadModel<T> implements Runnable{

    protected LinkedBlockingQueue<T> queue = new LinkedBlockingQueue<T>();


    public void add(T t) {
        queue.add(t);
    }
}
