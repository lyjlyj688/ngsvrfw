package person.lyjyy.core.cache;

import person.lyjyy.core.domain.InstanceObj;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yujie.li on 14-8-4.
 */
public class ThreadData {

    private Map<Long,Map<String,InstanceObj>> data;

    public ThreadData() {
        data = new HashMap<Long, Map<String, InstanceObj>>();
    }

    public void startTrans() {
        data.clear();
    }

    public void rollback() {
        data.clear();
    }

    public void commit() {

    }

    public void update() {

    }

    public void get() {

    }

    public void delete() {

    }

    public void insert() {

    }
}
