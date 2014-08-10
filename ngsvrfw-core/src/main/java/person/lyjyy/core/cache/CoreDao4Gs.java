package person.lyjyy.core.cache;

import person.lyjyy.core.domain.BaseObj;
import person.lyjyy.core.domain.InstanceObj;

/**
 * Created by yujie.li on 14-8-4.
 */
public class CoreDao4Gs {



    public void update(String sql,InstanceObj obj) {
        GsCache.tl.get().update(sql,obj);
    }

    public void delete(String sql,InstanceObj obj) {
        GsCache.tl.get().delete(sql,obj);
    }

    public void insert(String sql,InstanceObj obj) {
        GsCache.tl.get().insert(sql,obj);
    }

    public InstanceObj get(long guid,String key,int storgeType) {
        try {
            return GsCache.tl.get().get(guid,key);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            throw new RuntimeException("获取数据错误",e);
        }
    }
}
