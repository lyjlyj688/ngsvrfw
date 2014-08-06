package person.lyjyy.core.factory;

import person.lyjyy.core.cache.BaseCache;
import person.lyjyy.core.domain.InstanceObj;
import person.lyjyy.core.domain.RemoteObj;

/**
 * Created by yujie.li on 14-8-4.
 */
public class GsCacheFactory {

    private static BaseCache<InstanceObj> gs = new BaseCache();

    public static void init(BaseCache<InstanceObj> gsCache) {
        gs = gsCache;
    }

    public static BaseCache<InstanceObj> getCache() {
        return gs;
    }
}
