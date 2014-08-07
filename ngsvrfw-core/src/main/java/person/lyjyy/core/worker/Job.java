package person.lyjyy.core.worker;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

import java.util.concurrent.TimeUnit;

/**
 * Created by yujie.li on 14-7-22.
 */
public class Job {
    public final static Timer timer = new HashedWheelTimer();
    public static <T> Timeout addJob(final T runnable,final ThreadModel workThread,long timeDelay,TimeUnit unit) {
        return timer.newTimeout(new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                workThread.add(runnable);
            }
        },timeDelay,unit);
    }
}
