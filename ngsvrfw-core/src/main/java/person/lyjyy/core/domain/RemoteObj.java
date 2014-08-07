package person.lyjyy.core.domain;

import person.lyjyy.core.protocol.InProtocol;
import person.lyjyy.core.protocol.OutProtocol;
import person.lyjyy.core.protocol.ProtocolHandler;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.nio.ByteBuffer;

/**
 * Created by yujie.li on 14-7-21.
 */
public abstract class RemoteObj<T> extends InstanceObj implements InProtocol<T>,OutProtocol<T>{
    public abstract int getRemoteObjType();

    public abstract T encode(T buff);

    public abstract void decode(T buff);

    public abstract RemoteObj  newInstance();

    @Override
    public int getThreadIndex(int para) {
        throw new NotImplementedException();
    }

    @Override
    public int getProtocolId() {
        throw new NotImplementedException();
    }

    @Override
    public String getHandler() {
        throw new NotImplementedException();
    }

}
