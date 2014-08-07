package person.lyjyy.core.worker;

import io.netty.buffer.ByteBuf;
import person.lyjyy.core.protocol.InProtocol;

/**
 * Created by yujie.li on 14-8-7.
 */
public class DefaultProtocolWorkThread extends AbstractProtocolWorkThread<InProtocol<ByteBuf>>{

    @Override
    protected void before(InProtocol<ByteBuf> byteBufInProtocol) {

    }

    @Override
    protected void after(InProtocol<ByteBuf> byteBufInProtocol) {

    }
}
