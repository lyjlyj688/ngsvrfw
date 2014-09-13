package person.lyjyy.ngsvrfw.demo.gs.login.domain;

import io.netty.buffer.ByteBuf;
import person.lyjyy.core.domain.RemoteObj;

/**
 * Created by yujie.li on 14-9-10.
 */
public class Account extends RemoteObj<ByteBuf>{
    @Override
    public int getRemoteObjType() {
        return 0;
    }

    @Override
    public ByteBuf encode(ByteBuf buff) {
        return null;
    }

    @Override
    public void decode(ByteBuf buff) {

    }

    @Override
    public RemoteObj newInstance() {
        return null;
    }

    @Override
    public int getObjType() {
        return 0;
    }

    @Override
    public int getStoreType() {
        return 0;
    }

    @Override
    public String getObjKey() {
        return null;
    }
}
