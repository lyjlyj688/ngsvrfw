package person.lyjyy.core.net.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.timeout.IdleStateHandler;
import person.lyjyy.core.net.handler.MessageHandler;
import person.lyjyy.core.net.handler.ServerStateHandler;
import person.lyjyy.core.net.handler.codec.MessageDecode;
import person.lyjyy.core.net.handler.codec.MessageEncode;

import java.util.concurrent.TimeUnit;

/**
 * Created by yujie.li on 14-7-20.
 */
public class Server {
    int port;
    protected ServerBootstrap bootstrap;
    protected MessageHandler messageHandler;

    public Server(int port) {
        this.port = port;
        this.messageHandler = new MessageHandler();
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    //启动网络服务
    public void init() {
        bootstrap = new ServerBootstrap();
        bootstrap.group(new NioEventLoopGroup(2),new NioEventLoopGroup(4))
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .option(ChannelOption.SO_KEEPALIVE,false)
                .option(ChannelOption.TCP_NODELAY,true)
                .option(ChannelOption.SO_SNDBUF,0x3ffff)
                .option(ChannelOption.SO_RCVBUF,0xffff)
                .handler(new ChannelInitializer() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast("encoder",new MessageEncode())
                                .addLast("decoder",new MessageDecode(0xfffff))
                                .addLast("idleEvent",new IdleStateHandler(10,2,10))
                                .addLast("idleHander",new ServerStateHandler())
                                .addLast("handler", messageHandler);
                    }
                });
    }

    public void bind() throws Throwable {
        ChannelFuture future = bootstrap.bind(port);
        boolean ret = future.awaitUninterruptibly(1000, TimeUnit.MILLISECONDS);
        if(!ret || !future.isSuccess()) {
            throw  future.cause();
        }
    }
}
