package person.lyjyy.core.cache;

import person.lyjyy.core.domain.BaseObj;
import person.lyjyy.core.domain.InstanceObj;

/**
 * Created by yujie.li on 14-8-4.
 */
public class CoreDao4Gs {

    public static ThreadLocal<ThreadData> tl = new ThreadLocal<ThreadData>();

    public static BaseCache cache = new BaseCache();

    public void update(String sql,InstanceObj obj) {
        tl.get().update(sql,obj);
    }

    public void delete(String sql,InstanceObj obj) {
        tl.get().delete(sql,obj);
    }

    public void insert(String sql,InstanceObj obj) {
        tl.get().insert(sql,obj);
    }

    public InstanceObj get(long guid,String key,int storgeType) {
        try {
            return tl.get().get(guid,key);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            throw new RuntimeException("获取数据错误",e);
        }
    }
}
