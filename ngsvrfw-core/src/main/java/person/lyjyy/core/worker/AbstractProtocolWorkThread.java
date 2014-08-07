package person.lyjyy.core.worker;

import person.lyjyy.core.factory.BeanFactory;
import person.lyjyy.core.protocol.InProtocol;
import person.lyjyy.core.protocol.ProtocolHandler;

/**
 * Created by yujie.li on 14-8-4.
 */
public abstract class AbstractProtocolWorkThread<T extends InProtocol> extends AbstractWorkThread<T> {

    protected BeanFactory factory;

    @Override
    protected void exec(T o) {
        ((ProtocolHandler)factory.getBean(o.getHandler())).handle(o);
    }

}
