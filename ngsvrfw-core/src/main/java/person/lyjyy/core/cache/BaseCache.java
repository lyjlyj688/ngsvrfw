package person.lyjyy.core.cache;

import person.lyjyy.core.domain.InstanceObj;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by yujie.li on 14-7-21.
 */
public class BaseCache<T extends InstanceObj> {

    private ConcurrentMap<Long,Map<String,T>> map = new ConcurrentHashMap<Long, Map<String, T>>();

    public InstanceObj get(long guid, String key) throws CloneNotSupportedException {
        Map<String,T> tm = map.get(guid);
        if(tm == null) {
            return null;
        }
        InstanceObj obj = tm.get(key);
        if(obj == null) {
            return null;
        }
        return obj.newInstance();
    }

    public void update(long guid, T instanceObj) {
        Map<String,T> tm = map.get(guid);
        if(tm == null) {
            return;
        }
        tm.put(instanceObj.getObjKey(),instanceObj);
    }

    public void add(long guid, T instanceObj) {
        Map<String,T> tm = map.get(guid);
        if(tm == null) {
            tm = new HashMap<String, T>();
            map.put(guid,tm);
        }
        tm.put(instanceObj.getObjKey(),instanceObj);
    }

    public void remove(long guid, T instanceObj) {
        Map<String,T> tm = map.get(guid);
        if(tm == null) {
            return;
        }
        tm.remove(instanceObj.getObjKey());
    }

    public Map<String, T> getMap(long guid) {
        return map.get(guid);
    }

}
