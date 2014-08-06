package person.lyjyy.core.net.handler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import person.lyjyy.core.net.client.Client;
import person.lyjyy.core.worker.Job;

import java.util.concurrent.TimeUnit;

/**
 * Created by yujie.li on 14-7-21.
 */
@ChannelHandler.Sharable
public class ClientStateHandler extends ChannelDuplexHandler implements TimerTask{

    private final static Logger log = LoggerFactory.getLogger(ClientStateHandler.class);
    private Bootstrap bootstrap;
    private Client client;

    public ClientStateHandler(Bootstrap bootstrap,Client client) {
        this.bootstrap = bootstrap;
        this.client = client;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        client.setConnect(true);
        ctx.fireChannelActive();
    }

    /**
     * Calls {@link ChannelHandlerContext#fireChannelInactive()} to forward
     * to the next {@link io.netty.channel.ChannelInboundHandler} in the {@link io.netty.channel.ChannelPipeline}.
     *
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        client.setConnect(false);
        ctx.fireChannelInactive();
    }

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent) {
            IdleStateEvent id = (IdleStateEvent) evt;
            if(id.state() == IdleState.READER_IDLE) {
                ctx.channel().close();
                log.info("idlehander read out time,close.");
            }else {
                ctx.channel().write(new byte[]{1});
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof byte[] && ((byte[])msg).length == 1) {//心跳包
            return;
        }
    }


    protected void reconnect() {
        Job.timer.newTimeout(this,1000, TimeUnit.MILLISECONDS);
    }

    @Override
    public void run(Timeout timeout) throws Exception {
        bootstrap.connect();
    }

}
