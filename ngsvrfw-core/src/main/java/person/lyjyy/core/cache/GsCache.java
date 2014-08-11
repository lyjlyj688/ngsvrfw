package person.lyjyy.core.cache;

import person.lyjyy.core.worker.SendCommandThread;

/**
 * Created by yujie.li on 14-8-11.
 */
public class GsCache {

    public static ThreadLocal<ThreadData> tl = new ThreadLocal<ThreadData>();

    public static SendCommandThread sct = new SendCommandThread();

    public static BaseCache cache = new BaseCache();
}
