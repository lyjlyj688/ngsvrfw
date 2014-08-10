package person.lyjyy.core.cache;

import person.lyjyy.core.domain.*;
import person.lyjyy.core.worker.SendCommandThread;
import sun.security.jca.GetInstance;

import javax.naming.InitialContext;
import java.lang.management.OperatingSystemMXBean;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yujie.li on 14-8-4.
 */
public class ThreadData {

    private Map<Long,Map<String,UpdateObj>> data;

    public ThreadData() {
        data = new HashMap<Long, Map<String, UpdateObj>>();
    }

    public void startTrans() {
        data.clear();
    }

    public void rollback() {
        data.clear();
    }

    public void commit() {
        DsCommandBatch batch = null;
        DsCommand comm = null;
        for(Map<String,UpdateObj> map : data.values()) {
            batch = new DefaultDsCommandBatch();
            for(UpdateObj obj : map.values()) {
                if(obj.sql != null && obj.obj.getStoreType() == InstanceObj.TYPE_NOL) {
                    comm = new DefaultDsCommand();
                    comm.setObj((RemoteObj) obj.obj);
                    comm.setSql(obj.sql);
                    comm.setOptType(obj.opt);
                    batch.addCommand(comm);
                }
                GsCache.cache.updateObj(obj.obj);
                if(obj.next != null && obj.next.sql != null && obj.next.obj.getStoreType() == InstanceObj.TYPE_NOL) {
                    comm = new DefaultDsCommand();
                    comm.setObj((RemoteObj) obj.next.obj);
                    comm.setSql(obj.next.sql);
                    comm.setOptType(obj.next.opt);
                    batch.addCommand(comm);
                }
                GsCache.cache.updateObj(obj.next.obj);
            }
            SendCommandThread.sct.add(batch);
        }
        data.clear();
    }

    public void update(String sql,InstanceObj obj) {
        obj.setOptType(InstanceObj.OPT_UPDATE);
        Map<String,UpdateObj> map = data.get(obj.getGuid());
        if(map != null) {
            UpdateObj old = map.get(obj.getObjKey());
            if(old == null) {
                UpdateObj newobj = new UpdateObj();
                newobj.obj = obj;
                newobj.opt = InstanceObj.OPT_UPDATE;
                newobj.sql = sql;
                map.put(obj.getObjKey(),newobj);
            }else {
                switch (old.opt) {
                    case InstanceObj.OPT_ADD:
                        UpdateObj newobj = new UpdateObj();
                        newobj.obj = obj;
                        newobj.sql = sql;
                        newobj.opt = InstanceObj.OPT_UPDATE;
                        old.next = newobj;
                        break;
                    case InstanceObj.OPT_UPDATE:
                        old.obj = obj;
                        break;
                    case InstanceObj.OPT_DEL:break;
                    default:
                        old.obj = obj;
                        old.sql = sql;
                        old.opt = InstanceObj.OPT_UPDATE;
                        break;
                }
            }
        }
    }

    public InstanceObj get(long guid,String key) throws CloneNotSupportedException {
        Map<String,UpdateObj> objMap = data.get(guid);
        if(objMap != null) {
            UpdateObj obj = objMap.get(key);
            if(obj != null && obj.opt == InstanceObj.OPT_DEL) {
                return null;
            }
            if(obj == null) {
                InstanceObj obj0 = GsCache.cache.get(guid,key);
                if(obj0 != null) {
                    obj = new UpdateObj();
                    obj.obj = obj0;
                    objMap.put(obj0.getObjKey(),obj);
                    return obj0;
                }
                return null;
            }
            return obj.obj;
        }else {
            InstanceObj obj0 = GsCache.cache.get(guid,key);
            if(obj0 != null) {
                UpdateObj obj = new UpdateObj();
                obj.obj = obj0;
                objMap = new HashMap<String, UpdateObj>();
                objMap.put(key,obj);
                data.put(guid,objMap);
            }
            return obj0;
        }

    }

    public void delete(String sql,InstanceObj obj) {
        obj.setOptType(InstanceObj.OPT_DEL);
        Map<String,UpdateObj> map = data.get(obj.getGuid());
        if(map != null) {
            UpdateObj old = map.get(obj.getObjKey());
            if(old == null) {
                UpdateObj newobj = new UpdateObj();
                newobj.obj = obj;
                newobj.opt = InstanceObj.OPT_DEL;
                newobj.sql = sql;
                map.put(obj.getObjKey(),newobj);
            }else {
                switch (old.opt) {
                    case InstanceObj.OPT_ADD:
                        map.remove(obj.getObjKey());break;
                    case InstanceObj.OPT_UPDATE:
                        old.obj = obj;
                        old.sql = sql;
                        old.opt = InstanceObj.OPT_DEL;break;
                    case InstanceObj.OPT_DEL:break;
                    default:;
                }
            }
        }
    }

    public void insert(String sql,InstanceObj obj) {
        obj.setOptType(InstanceObj.OPT_ADD);
        Map<String,UpdateObj> map = data.get(obj.getGuid());
        if(map != null) {
            UpdateObj old = map.get(obj.getObjKey());
            if(old == null) {
                UpdateObj newobj = new UpdateObj();
                newobj.obj = obj;
                newobj.sql = sql;
                map.put(obj.getObjKey(),newobj);
            }else {
                switch (old.obj.getOptType()) {
                    case InstanceObj.OPT_ADD:
                        old.obj = obj;break;
                    case InstanceObj.OPT_UPDATE:
                        throw new RuntimeException("存在重复的key:" + obj.getObjKey());
                    case InstanceObj.OPT_DEL:
                        UpdateObj newobj = new UpdateObj();
                        newobj.obj = obj;
                        newobj.sql = sql;
                        old.next = newobj;
                        break;
                    default:break;
                }
            }
        }
    }

    class UpdateObj {
        String sql;
        byte opt = InstanceObj.OPT_NA;
        InstanceObj obj;
        UpdateObj next;
    }
}
