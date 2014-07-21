package person.lyjyy.common.net.handler;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import person.lyjyy.common.conf.DefaultLogger;
import person.lyjyy.common.conf.DefaultLogger.*;

/**
 * Created by yujie.li on 14-7-21.
 */
@ChannelHandler.Sharable
public class ClientIdleHandler extends ChannelDuplexHandler{

    private final static Logger log = LoggerFactory.getLogger(ClientIdleHandler.class);

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent) {
            IdleStateEvent id = (IdleStateEvent) evt;
            if(id.state() == IdleState.READER_IDLE) {
                ctx.channel().close();
                DefaultLogger.APP_INFO.getLogger().info("idlehander read out time,close.");
            }else {
                //写入心跳
            }
        }
    }
}
