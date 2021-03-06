package person.lyjyy.core.net.client;


import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import person.lyjyy.core.net.handler.ClientStateHandler;
import person.lyjyy.core.net.handler.MessageHandler;
import person.lyjyy.core.net.handler.codec.MessageDecode;
import person.lyjyy.core.net.handler.codec.MessageEncode;
import person.lyjyy.core.protocol.OutProtocol;

import java.util.concurrent.TimeUnit;

/**
 * Created by yujie.li on 14-7-20.
 */
public class Client {
    private String host;
    private int port;
    private Bootstrap bootstrap;
    private MessageHandler messageHandler;
    private volatile boolean isConnect = false;
    private Channel channel;
    private final static Logger log = LoggerFactory.getLogger(Client.class);

    public Client(String host,int port) {
        this.host = host;
        this.port = port;
        this.messageHandler = new MessageHandler();
        init();
    }

    public MessageHandler getHandler() {
        return this.messageHandler;
    }

    public void init() {
        bootstrap = new Bootstrap();
        final Client client = this;
        bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE,false)
                .group(new NioEventLoopGroup())
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast("encoder",new MessageEncode())
                                .addLast("decoder",new MessageDecode(0xfffffff))
                                .addLast("idleEvent",new IdleStateHandler(10,2,10))
                                .addLast("idleHander",new ClientStateHandler(bootstrap,client))
                                .addLast("handler", messageHandler);
                    }
                });
    }

    public void connect() {
        ChannelFuture future = bootstrap.connect(host,port);
        while(true) {
            boolean ret = future.awaitUninterruptibly(2000, TimeUnit.MILLISECONDS);
            if(ret && future.isSuccess()) {
                break;
            }
            log.warn("连接" + host + ":" + port + "失败，等待1秒重试");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
        }
    }

    public void setConnect(boolean flag) {
        this.isConnect = flag;
    }

    public boolean isConnect() {
        return this.isConnect;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public <T> void write(OutProtocol<T> t) {
        channel.write(t);
    }

    public void flush() {
        channel.flush();
    }
}
