package person.lyjyy.core.domain;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yujie.li on 14-8-4.
 */
public abstract class DsCommandBatch<T> extends RemoteObj<T>{

    protected List<DsCommand<T>> commandList = new ArrayList<DsCommand<T>>();

    public List<DsCommand<T>> getCommandList() {
        return commandList;
    }

    public void addCommand(DsCommand<T> command) {
        commandList.add(command);
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
}
