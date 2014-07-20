package person.lyjyy.common.cache;

import person.lyjyy.common.domain.BaseObj;

import java.util.Map;

/**
 * Created by yujie.li on 14-7-21.
 */
public interface ICache<T extends BaseObj> {

    public T get(String key);

    public void update(String key,T t);

    public Map<String,T> getMap(String key);

}
