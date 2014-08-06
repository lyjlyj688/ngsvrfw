package person.lyjyy.core.protocol;

import io.netty.buffer.ByteBuf;
import person.lyjyy.core.protocol.InProtocol;
import person.lyjyy.core.protocol.OutProtocol;

/**
 * Created by yujie.li on 14-7-22.
 */
public abstract class ServerProtocol implements InProtocol<ByteBuf>,OutProtocol<ByteBuf> {

}
