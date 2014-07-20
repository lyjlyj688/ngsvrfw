package person.lyjyy.datasvr.cache;

import person.lyjyy.common.cache.ICache;
import person.lyjyy.common.domain.RemoteObj;

import java.util.Map;

/**
 * Created by yujie.li on 14-7-21.
 */
public class DsCache implements ICache<RemoteObj>{
    @Override
    public RemoteObj get(String key) {
        return null;
    }

    @Override
    public void update(String key, RemoteObj remoteObj) {

    }

    @Override
    public Map<String, RemoteObj> getMap(String key) {
        return null;
    }
}
