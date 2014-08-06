package person.lyjyy.core.worker;

import person.lyjyy.core.protocol.InProtocol;

/**
 * Created by yujie.li on 14-8-4.
 */
public abstract class AbstractProtocolWorkThread<T extends InProtocol> extends WorkThread<T>{

    @Override
    protected void exec(T o) {
        o.getHandler().handle(o);
    }

}
