package person.lyjyy.core.net.handler;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yujie.li on 14-7-29.
 */
@ChannelHandler.Sharable
public class ServerStateHandler extends ChannelDuplexHandler {

    private static final Logger log = LoggerFactory.getLogger(ServerStateHandler.class);

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent) {
            IdleStateEvent id = (IdleStateEvent) evt;
            if(id.state() == IdleState.READER_IDLE) {
                ctx.channel().close();
                log.info("idlehander read out time,close.");
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof byte[] && ((byte[])msg).length == 1) {//心跳包
            ctx.channel().write(new byte[]{1});//写入ping返回包
            return;
        }
    }
}
