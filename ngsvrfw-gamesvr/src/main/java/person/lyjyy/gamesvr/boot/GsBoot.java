package person.lyjyy.gamesvr.boot;
import person.lyjyy.core.boot.IBoot;
import person.lyjyy.core.conf.DefaultProtocolConfig;
import person.lyjyy.core.conf.ProtocolConfig;
import person.lyjyy.core.conf.RemoteObjConfig;
import person.lyjyy.core.conf.ServerInfo;
import person.lyjyy.core.factory.BeanFactory;
import person.lyjyy.core.factory.GsAround;
import person.lyjyy.core.net.client.Client;
import person.lyjyy.core.net.server.Server;
import person.lyjyy.gamesvr.core.ObjConfig;

/**
 * Created by yujie.li on 14-7-21.
 */
public class GsBoot implements IBoot{

    static BeanFactory beanFactory;

    @Override
    public void start() throws Throwable {
        //初始化bean
        beanFactory = new BeanFactory("bean",new GsAround());
        //初始化obj配置
        ObjConfig objConfig = new ObjConfig();
        objConfig.init();
        RemoteObjConfig.set(objConfig);
        //初始化协议配置
        ProtocolConfig protocolConfig = new DefaultProtocolConfig("protocol");
        //初始化ds连接
        ServerInfo.init("server.cf");
        Client client = new Client(ServerInfo.dsIp,ServerInfo.dsPort);
        client.getHandler().setProtocolConfig(protocolConfig);
        client.init();
        client.connect();
        //开始加载数据  具体业务实现
        //启动服务器
        Server server = new Server(ServerInfo.gsPort);
        server.getMessageHandler().setProtocolConfig(protocolConfig);
        server.init();
        server.bind();
    }
}
