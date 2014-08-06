package person.lyjyy.core.worker;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by yujie.li on 14-7-22.
 */
public class SingleThreadPool extends Thread{

    private static SingleThreadPool pool;

    public SingleThreadPool getInstance() {
        if(pool == null) {
            synchronized (SingleThreadPool.class) {
                if(pool == null) {
                    pool = new SingleThreadPool();
                    pool.setName("SingleThreadPool");
                    pool.setDaemon(true);
                    pool.start();
                }
            }
        }
        return pool;
    }

    private SingleThreadPool(){};

    private LinkedBlockingQueue<Runnable> runnableList = new LinkedBlockingQueue<Runnable>();

    public void exec(Runnable runnable) {
        runnableList.add(runnable);
    }

    public void run() {
        while (true) {
            try {
                Runnable r = runnableList.take();
                r.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
