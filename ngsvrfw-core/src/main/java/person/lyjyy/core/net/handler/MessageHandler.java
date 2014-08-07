package person.lyjyy.core.net.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import person.lyjyy.core.conf.ProtocolConfig;
import person.lyjyy.core.protocol.InProtocol;
import person.lyjyy.core.worker.ThreadModel;

/**
 * Created by yujie.li on 14-7-20.
 */
public class MessageHandler extends ChannelDuplexHandler{

    final static Logger log = LoggerFactory.getLogger(MessageHandler.class);

    protected ProtocolConfig pconfig;
    protected ThreadModel wt;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        short protocolId = buf.readShort();
        InProtocol ipl = pconfig.get(protocolId);
        ipl.decode(buf);
        wt.add(ipl);
        ReferenceCountUtil.release(buf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        log.error("pipeline error:", cause);
        cause.printStackTrace();
    }
}
