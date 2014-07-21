package person.lyjyy.common.net.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInboundHandler;

/**
 * Created by yujie.li on 14-7-20.
 */
public class Server {
    int port;
    private ChannelInboundHandler messageHandler;//处理写入逻辑

    //启动网络服务
    public void start() {
//        ServerBootstrap bootstrap = new ServerBootstrap();
//        bootstrap.
    }
}
