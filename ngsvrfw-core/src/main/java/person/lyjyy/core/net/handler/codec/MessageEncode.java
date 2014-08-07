package person.lyjyy.core.net.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.MessageToByteEncoder;
import person.lyjyy.core.protocol.OutProtocol;

/**
 * Created by yujie.li on 14-7-22.
 */
public class MessageEncode extends MessageToByteEncoder{

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        int writeIndex = out.writerIndex();
        out.writeInt(0);
        OutProtocol o = (OutProtocol) msg;
        o.encode(out);
        out.setInt(writeIndex,out.writerIndex() - writeIndex - 4);
    }

}
