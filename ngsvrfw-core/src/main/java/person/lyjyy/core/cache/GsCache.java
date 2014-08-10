package person.lyjyy.core.cache;

/**
 * Created by yujie.li on 14-8-11.
 */
public class GsCache {

    public static ThreadLocal<ThreadData> tl = new ThreadLocal<ThreadData>();

    public static BaseCache cache = new BaseCache();
}
