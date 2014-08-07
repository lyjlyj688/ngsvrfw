package person.lyjyy.core.conf;

import person.lyjyy.core.protocol.InProtocol;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by yujie.li on 14-8-7.
 */
public class DefaultProtocolConfig implements ProtocolConfig{

    private InProtocol[] protocols = new InProtocol[5000];

    public DefaultProtocolConfig(String dir) {
        try {
            File[] list = new File(dir).listFiles();
            for(File file : list) {
                Properties p = new Properties();
                p.load(new FileInputStream(file));
                for(String key : p.stringPropertyNames()) {
                    InProtocol in = (InProtocol) Class.forName(p.getProperty(key)).newInstance();
                    put(in);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void put(InProtocol in) {
        if(protocols[in.getProtocolId()] != null) {
           throw new RuntimeException("存在重复的protocol:" + in.getProtocolId());
        }
        protocols[in.getProtocolId()] = in;
    }

    @Override
    public InProtocol get(int id) {
        return protocols[id].newInstance();
    }
}
