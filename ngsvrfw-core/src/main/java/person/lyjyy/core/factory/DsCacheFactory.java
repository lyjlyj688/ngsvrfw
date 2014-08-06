package person.lyjyy.core.factory;

import person.lyjyy.core.cache.BaseCache;
import person.lyjyy.core.domain.InstanceObj;
import person.lyjyy.core.domain.RemoteObj;

/**
 * Created by yujie.li on 14-8-4.
 */
public class DsCacheFactory {
    private static BaseCache<RemoteObj> ds = new BaseCache();

    public static void init(BaseCache<RemoteObj> dsCache) {
        ds = dsCache;
    }

    public static BaseCache<RemoteObj> getCache() {
        return ds;
    }
}
