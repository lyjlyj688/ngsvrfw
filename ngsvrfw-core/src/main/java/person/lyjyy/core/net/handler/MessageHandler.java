package person.lyjyy.core.net.handler;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yujie.li on 14-7-20.
 */
public class MessageHandler extends ChannelDuplexHandler{

    final static Logger log = LoggerFactory.getLogger(MessageHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        log.error("pipeline error:", cause);
        cause.printStackTrace();
    }
}
