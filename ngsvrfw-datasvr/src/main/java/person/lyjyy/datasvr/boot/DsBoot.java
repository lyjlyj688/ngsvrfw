package person.lyjyy.datasvr.boot;

import person.lyjyy.core.boot.IBoot;
import person.lyjyy.core.cache.DsCache;
import person.lyjyy.core.conf.DefaultProtocolConfig;
import person.lyjyy.core.conf.ProtocolConfig;
import person.lyjyy.core.net.server.Server;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by yujie.li on 14-7-21.
 */
public class DsBoot implements IBoot{
    @Override
    public void start() throws Throwable {
        Properties p = new Properties();
        p.load(new FileInputStream(new File("server.cf")));
        DsCache.cache = new DsCache("sqlmap/sqlconfig.xml","db.cf");
        Server svr = new Server(Integer.valueOf(p.getProperty("ds.port")));
        ProtocolConfig pc = new DefaultProtocolConfig("protocol");
        svr.getMessageHandler().setProtocolConfig(pc);
        svr.bind();
    }
}
