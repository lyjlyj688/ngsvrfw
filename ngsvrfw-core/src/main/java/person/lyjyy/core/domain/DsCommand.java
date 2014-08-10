package person.lyjyy.core.domain;
import person.lyjyy.core.protocol.ProtocolHandler;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.nio.ByteBuffer;

/**
 * Created by yujie.li on 14-8-4.
 */
public abstract class DsCommand<T> extends RemoteObj<T>{

    protected RemoteObj<T> obj;

    protected String sql;

    public RemoteObj<T> getObj() {
        return obj;
    }

    public void setObj(RemoteObj<T> obj) {
        this.obj = obj;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public  int getStoreType() {
        return InstanceObj.TYPE_NOL;
    }

    @Override
    public RemoteObj newInstance() {
        throw new NotImplementedException();
    }

    @Override
    public String getObjKey() {
        throw new NotImplementedException();
    }

    @Override
    public int getObjType() {
        throw new NotImplementedException();
    }

    @Override
    public int getRemoteObjType() {
        throw new NotImplementedException();
    }

    public String toString() {
        return sql;
    }
}
